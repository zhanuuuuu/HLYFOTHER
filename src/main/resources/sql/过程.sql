
/*
	扫脸支付  获取特价商品的过程
    exec p_getGeneralPreference '6928804011258','0002',1,''
*/
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_getGeneralPreference]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_getGeneralPreference]

END
GO
CREATE PROC [dbo].[p_getGeneralPreference]
 @cBarcode varchar(100),
 @cStoreNo varchar(100),
 @cNum  int
 AS
BEGIN
    --SELECT  * FROM t_PloyOfSale where  CONVERT(varchar(100), GETDATE(), 23) BETWEEN dDateStart AND dDateEnd
    SELECT isWholePackage=1,packageRate=1,youHuiAmount=fPrice_SO_old-fPrice_SO,
       cPloyNo,B.cGoodsNo,B.cGoodsName,fPrice_SO AS youHuiPrice,amount=fPrice_SO,goodsNum=1,
       fPrice_SO_old AS fPriceOdd,cPloyTypeNo,cPloyTypeName,B.cBarcode,B.cUnit,B.cSpec
    FROM  t_PloyOfSale A,t_cStoreGoods B
    where  CONVERT(varchar(100), GETDATE(), 23) BETWEEN dDateStart AND dDateEnd
    AND A.cStoreNo= @cStoreNo AND B.cStoreNo= @cStoreNo AND A.cStoreNo=B.cStoreNo AND A.cGoodsNo=B.cGoodsNo
    AND ISNULL(fQuantity_Ploy,0)>=@cNum AND B.cBarcode= @cBarcode
END

GO

/*
   下载商品信息

   EXEC p_uploadGoods_z '0002','1','30'
*/
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_uploadGoods_z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_uploadGoods_z]

END
GO
 CREATE PROC [dbo].[p_uploadGoods_z]
@cStoreNo varchar(500),  ----门店编号
@PageNum varchar(30),    ----页数
@Number  VARCHAR(20)     ----每页多少条
 AS

BEGIN
	--找到多少页  多少条记录
    IF object_id('tempdb..#temp_countNum') IS NOT NULL
	Begin
	DROP TABLE #temp_countNum
	End
	CREATE TABLE #temp_countNum(countNum VARCHAR(32),countSum VARCHAR(32))
    INSERT INTO #temp_countNum(countNum,countSum)
    EXEC('
		SELECT (COUNT(*)*1.0)/'+@Number+',  COUNT(*)  FROM
		(SELECT
				cGoodsNo,
				cStoreNo,
				cStoreName,
				cUnitedNo=ISNULL(cUnitedNo,''''),
				cGoodsName,
				cGoodsTypeno,
				cGoodsTypename,
				cBarcode,
				cUnit,
				cSpec=ISNULL(cSpec,''''),
				fNormalPrice,
				fVipPrice,
				cProductUnit,
				dCreateDate,
				pinpai,
				pinpaino
				FROM T_cStoreGoods
       WHERE  cStoreNo='''+@cStoreNo+'''  ) A
     ')


     DECLARE @countNum VARCHAR(32)
     SET @countNum=(SELECT countNum FROM #temp_countNum)

     DECLARE @countSum VARCHAR(32)
     SET @countSum=(SELECT countSum FROM #temp_countNum)

     PRINT @countSum

     IF cast(@countNum AS float)>cast(CAST(@countNum AS float) AS int)
		SET @countNum=cast(CAST(@countNum AS float) AS int)+1

     PRINT @countNum
     PRINT @countSum
    --

    DECLARE @SQL VARCHAR(8000)
	SET @SQL='	SELECT TOP '+@Number+' *
				FROM (SELECT ROW_NUMBER() OVER (ORDER BY  cGoodsNo DESC  ) AS RowNumber,*
				FROM  (
							SELECT  countNum='''+@countNum+''',countSum='''+@countSum+''',
									cGoodsNo,
									cStoreNo=ISNULL(cStoreNo,''''),
									cStoreName=ISNULL(cStoreName,''''),
									cUnitedNo=ISNULL(cUnitedNo,''''),
									cGoodsName,
									cGoodsTypeno=ISNULL(cGoodsTypeno,''''),
									cGoodsTypename=ISNULL(cGoodsTypename,''''),
									cBarcode,
									cUnit=ISNULL(cUnit,''''),
									cSpec=ISNULL(cSpec,''''),
									fNormalPrice,
									fVipPrice,
									cProductUnit=ISNULL(cProductUnit,''''),
									dCreateDate=ISNULL(dCreateDate,''''),
									pinpai=ISNULL(pinpai,''''),
									pinpaino=ISNULL(pinpaino,''''),
                fVipScore_base,
                fVipScore
									FROM T_cStoreGoods
								  WHERE  cStoreNo='''+@cStoreNo+'''

				) as  Z ) as A WHERE RowNumber > '+@Number+' *('+@PageNum+'- 1) '

  --PRINT(@SQL)
  EXEC(@SQL)

END

GO

