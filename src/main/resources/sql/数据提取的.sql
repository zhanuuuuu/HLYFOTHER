/*

针对第三方的

  EXEC   p_OtherThirdData_z
*/

ALTER proc [dbo].[p_OtherThirdData_z]
as
begin
    if(select object_id('tempdb..#temp_OherDataList')) is not null
	begin
		drop table #temp_OherDataList
	end

	select cSheetNo as sheetno,dbo.getDayStr(createTime) as zdriqi,
	saleTime as jsTime,saleTypeName as jsType,saleAllMoney as shishou,
	cStoreNo,appId
	into #temp_OherDataList
	from tPublicSale_z
    where cStoreNo<>'' and ISNULL(is_tiQue,0)=0

    if(select object_id('tempdb..#temp_OherSaleList')) is not null
	begin
		drop table #temp_OherSaleList
	end
    select a.appId,a.cSheetNo,a.cStoreNo as cStoreNo,sum(a.fNormalPrice*a.fQuantity) as fLastSettle,b.shishou,c.cSupNo,c.cGoodsNo
    into #temp_OherSaleList
    from tPublicSaleDetail_z a,#temp_OherDataList b,t_cStoreGoods c
    where a.cSheetNo=b.sheetno and a.cStoreNo=b.cStoreNo and a.cStoreNo=c.cStoreNo
    and a.cGoodsNo=c.cGoodsNo
    group BY a.cSheetNo,b.shishou,a.cStoreNo,c.cSupNo,c.cGoodsNo,a.appId

    if(select object_id('tempdb..#temp_OherSaleListDiff')) is not null
	begin
		drop table #temp_OherSaleListDiff
	end

    select  a.sheetno into #temp_OherSaleListDiff from (
    select sheetno,sum(shishou) as shishou  from #temp_OherDataList
    group by sheetno) a,(select cSheetNo,sum(fLastSettle)  as shishou  from #temp_OherSaleList
    group by cSheetNo) b
    where a.sheetno=b.cSheetNo and a.shishou<>b.shishou

    delete a
    from #temp_OherDataList a,#temp_OherSaleListDiff b
    where a.sheetno=b.sheetno
    delete a
    from #temp_OherSaleList a,#temp_OherSaleListDiff b
    where a.cSheetNo=b.sheetno


    if(select object_id('tempdb..#temp_OherSaleList0')) is not null
	begin
		drop table #temp_OherSaleList0
	end
    select distinct a.appId,cSheetNo as sheetno,a.cGoodsNo,a.cStoreNo,a.cSupNo into #temp_OherSaleList0
    from #temp_OherSaleList a,t_Store b
    where a.cStoreNo=b.cStoreNo
    --and fLastSettle=shishou

    insert into dbo.jiesuan(zdriqi,sheetno,jstype,cStoreNo,mianzhi,zhekou,zhaoling,shishou,jstime,jiaozhang,
    shouyinyuanno,shouyinyuanmc,
	detail,tag_daily,bGroupSale,cWhNo,cStoreName,bGoodsOut)
	SELECT distinct a.zdriqi,a.sheetno,a.jsType,a.cStoreNo,a.shishou,0,0,a.shishou,a.jsTime,0,a.appId,'线上支付',
	a.jsType,1,0,c.cWhNo,c.cWh,0
	from #temp_OherDataList a,#temp_OherSaleList0 b ,t_WareHouse c
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
		select dbo.getDayStr(createTime) as dSaleDate,a.cSheetNo as cSaleSheetno,ROW_NUMBER() OVER(order by a.cSheetNo desc) AS iSeed,
		a.cGoodsNo as mingsheng_cGOodsNo,'' as cGoodsName,'' as cBarCode,b.appId as cOperatorno,'线上支付' as cOperatorName,
		a.fNormalPrice*fQuantity as fVipScore,a.fNormalPrice as fPrice,a.fNormalPrice*fQuantity as fNormalSettle,
		a.fNormalPrice as fVipPrice,a.fQuantity as fQuantity,a.fNormalPrice*fQuantity as fLastSettle0,a.fNormalPrice*fQuantity as fLastSettle,
		dbo.getTimeStr(createTime) as cSaleTime,0 as tag_daily,'' as cWhNo,b.cStoreNo,0 as bAuditing,b.cSupNo
		from tPublicSaleDetail_z a,#temp_OherSaleList0 b
		where a.cSheetNo=b.sheetno and a.cStoreNo=b.cStoreNo and a.cGoodsNo=b.cGoodsNo
		) b,t_WareHouse c
    where a.cGoodsNo=b.mingsheng_cGOodsNo and b.cStoreNo=c.cStoreNo and isnull(c.bMainSale,0)=1


    update a
    set  a.is_tiQue=1
    from tPublicSale_z a,#temp_OherSaleList0 b
    where a.cSheetNo=b.SheetNo and a.cStoreNo=b.cStoreNo

end