USE [Posmanagement_main]
GO
/****** Object:  StoredProcedure [dbo].[p_GetMinShengThirdData]    Script Date: 03/06/2019 10:23:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER proc [dbo].[p_GetMinShengThirdData]
as
begin
    if(select object_id('tempdb..#temp_MinshengList')) is not null
	begin
		drop table #temp_MinshengList
	end

	select orderSn as sheetno,dbo.getDayStr(createdate) as zdriqi,
	createdate as jsTime,paymentMethodName as jsType,totalPrice as shishou,
	offlineStoresId as cStoreNo
	into #temp_MinshengList
	from Posmanagement_main.dbo.t_MinShengPublicSale_z
    where offlineStoresId<>'' and is_tiQue=0

    if(select object_id('tempdb..#temp_MinshengSaleList')) is not null
	begin
		drop table #temp_MinshengSaleList
	end
    select a.orderSn,a.offlineStoresId as cStoreNo,sum(a.price*a.quantity) as fLastSettle,b.shishou,c.cSupNo,c.cGoodsNo
    into #temp_MinshengSaleList
    from Posmanagement_main.dbo.t_MingShengPublicSaleDetail_z a,#temp_MinshengList b,t_cStoreGoods c
    where a.orderSn=b.sheetno and a.offlineStoresId=b.cStoreNo and a.offlineStoresId=c.cStoreNo
    and a.OutID=c.cGoodsNo
    group BY a.orderSn,b.shishou,a.offlineStoresId,c.cSupNo,c.cGoodsNo
    if(select object_id('tempdb..#temp_MinshengSaleListDiff')) is not null
	begin
		drop table #temp_MinshengSaleListDiff
	end

    select  a.sheetno into #temp_MinshengSaleListDiff from (
    select sheetno,sum(shishou) as shishou  from #temp_MinshengList
    group by sheetno) a,(select orderSn,sum(fLastSettle)  as shishou  from #temp_MinshengSaleList
    group by orderSn) b
    where a.sheetno=b.orderSn and a.shishou<>b.shishou

    delete a
    from #temp_MinshengList a,#temp_MinshengSaleListDiff b
    where a.sheetno=b.sheetno
    delete a
    from #temp_MinshengSaleList a,#temp_MinshengSaleListDiff b
    where a.orderSn=b.sheetno


    if(select object_id('tempdb..#temp_MinshengSaleList0')) is not null
	begin
		drop table #temp_MinshengSaleList0
	end
    select distinct orderSn as sheetno,a.cGoodsNo,a.cStoreNo,a.cSupNo into #temp_MinshengSaleList0
    from #temp_MinshengSaleList a,t_Store b
    where a.cStoreNo=b.cStoreNo
    --and fLastSettle=shishou

    insert into dbo.jiesuan(zdriqi,sheetno,jstype,cStoreNo,mianzhi,zhekou,zhaoling,shishou,jstime,jiaozhang,
    shouyinyuanno,shouyinyuanmc,
	detail,tag_daily,bGroupSale,cWhNo,cStoreName,bGoodsOut)
	SELECT distinct a.zdriqi,a.sheetno,a.jsType,a.cStoreNo,a.shishou,0,0,a.shishou,a.jsTime,0,'9999','线上支付',
	a.jsType,1,0,c.cWhNo,c.cWh,0
	from #temp_MinshengList a,#temp_MinshengSaleList0 b ,t_WareHouse c
	where a.sheetno=b.sheetno and a.cStoreNo=c.cStoreNo and isnull(c.bMainSale,0)=1

	insert into t_SaleSheetDetail(dSaleDate,cSaleSheetno,iSeed,cGoodsNo,
	cGoodsName ,cBarCode,cOperatorno,cOperatorName,
	fVipScore,fPrice,fNormalSettle,fVipPrice,fQuantity,fLastSettle0,fLastSettle,
	cSaleTime,tag_daily,
	cWHno,cStoreNo,bAuditing,dFinanceDate,cSupNo,bGoodsOut)
    SELECT b.dSaleDate,b.cSaleSheetno,b.iSeed,a.cGoodsNo,
	a.cGoodsName ,a.cBarCode,b.cOperatorno,b.cOperatorName,
	b.fVipScore,b.fPrice,b.fNormalSettle,b.fVipPrice,b.fQuantity,b.fLastSettle0,b.fLastSettle,
	b.cSaleTime,b.tag_daily,
	c.cWHno,b.cStoreNo,b.bAuditing ,b.dSaleDate,b.cSupNo,0
	from t_Goods a,(
		select dbo.getDayStr(createdate) as dSaleDate,a.orderSn as cSaleSheetno,ROW_NUMBER() OVER(order by orderSn desc) AS iSeed,
		a.outId as mingsheng_cGOodsNo,'' as cGoodsName,'' as cBarCode,'99999' as cOperatorno,'线上支付' as cOperatorName,
		a.price*quantity as fVipScore,a.price as fPrice,a.price*quantity as fNormalSettle,
		a.price as fVipPrice,a.quantity as fQuantity,a.price*quantity as fLastSettle0,a.price*quantity as fLastSettle,
		dbo.getTimeStr(createdate) as cSaleTime,0 as tag_daily,'' as cWhNo,b.cStoreNo,0 as bAuditing,b.cSupNo
		from Posmanagement_main.dbo.t_MingShengPublicSaleDetail_z a,#temp_MinshengSaleList0 b
		where a.orderSn=b.sheetno and a.offlineStoresId=b.cStoreNo and a.outId=b.cGoodsNo
		) b,t_WareHouse c
    where a.cGoodsNo=b.mingsheng_cGOodsNo and b.cStoreNo=c.cStoreNo and isnull(c.bMainSale,0)=1


    update a
    set  a.is_tiQue=1
    from Posmanagement_main.dbo.t_MinShengPublicSale_z a,#temp_MinshengSaleList0 b
    where a.orderSn=b.SheetNo and a.offlineStoresId=b.cStoreNo

end