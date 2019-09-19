/*
	 把拿到的数据 插入临时表
*/
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_updateOrderToJd_z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_updateOrderToJd_z]

END
GO
CREATE PROC [dbo].[p_updateOrderToJd_z]
 @cSheetNo varchar(300),
 @appId varchar(300),
 @iFlag int OUT
 AS
BEGIN
  SET @iFlag =1

  UPDATE tPublicSale_JingDong_z
  SET isAccount=1
  WHERE cSheetNo=@cSheetNo

  UPDATE tPublicSaleDetail_JingDong_z
  SET isAccount=1
  WHERE cSheetNo=@cSheetNo
  --判断影响函数
  SET @iFlag= @@rowcount

  RETURN @iFlag
END

GO