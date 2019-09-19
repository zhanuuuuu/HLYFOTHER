IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_minshengCopy]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_minshengCopy]

END
GO
CREATE PROC [dbo].[p_minshengCopy]
 @cBarcode varchar(100) --没用
 AS
BEGIN
    --复制表
    INSERT  t_MinShengPublicSale_z(orderSn,createDate,beginTime,endTime,totalPrice,paymentMethodName,offlineStoresId)
    SELECT a.orderSn,a.createDate,a.beginTime,a.endTime,a.totalPrice,a.paymentMethodName,a.offlineStoresId
    FROM t_MinShengPublicSale_z_beifen a LEFT JOIN t_MinShengPublicSale_z b
    ON a.orderSn=b.orderSn
    WHERE ISNULL(b.orderSn,'')=''
    --复制表
    INSERT t_MingShengPublicSaleDetail_z(orderSn,createDate,offlineStoresId,productSn,fullName,name,price,quantity,outId,barCode,[weight])
    SELECT A.orderSn,A.createDate,A.offlineStoresId,A.productSn,A.fullName,A.name,
          A.price,A.quantity,A.outId,A.barCode,A.[weight]
    FROM t_MingShengPublicSaleDetail_z_beifen A  LEFT JOIN t_MingShengPublicSaleDetail_z B
    ON A.orderSn=B.orderSn
    WHERE ISNULL(b.orderSn,'')=''
    --删除表
    DELETE t_MinShengPublicSale_z_beifen
    DELETE t_MingShengPublicSaleDetail_z_beifen

END

GO

--得到上次的结束时间   下次的开始时间
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[get_t_MingShengTime]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[get_t_MingShengTime]

END
GO
CREATE PROC [dbo].[get_t_MingShengTime]
 @cBarcode varchar(100) --没用
 AS
BEGIN

     SELECT endTime=ISNULL(MAX(endTime),'2018-12-26 10:23:20')   FROM t_MinShengPublicSale_z

END