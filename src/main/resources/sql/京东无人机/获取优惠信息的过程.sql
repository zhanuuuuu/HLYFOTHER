
--动态调用过程获取数据的过程
--EXEC p_ProcessPosSheet_Z '0002','02','2019043012011993595','13628672210',100,0,'posstation101.dbo.p_ProcessPosSheet'
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_ProcessPosSheet_Z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_ProcessPosSheet_Z]

END
GO
CREATE procedure [dbo].[p_ProcessPosSheet_Z]
@cStoreNo varchar(32),
@cPosID varchar(32),
@cSaleSheetNo varchar(32),
@cVipNo varchar(32),
@fVipRate varchar(32),
@bDiscount varchar(32),
@callName VARCHAR(80)
AS
BEGIN
  DECLARE @SQLWHERE VARCHAR(800)

  SET  @SQLWHERE = 'EXEC '+@callName+' '''+@cStoreNo+''','''
                          +@cPosID+''','''+@cSaleSheetNo+''','''
                          +@cVipNo+''','''+@fVipRate+''','+@bDiscount

  PRINT(@SQLWHERE)

  EXEC (@SQLWHERE)
END



--获取增加的积分的值
--EXEC p_getVipScoreAdd '2019043012011993595',100,'posstation006.dbo.p_CountVipScore_Online'
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_getVipScoreAdd]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_getVipScoreAdd]

END
GO
CREATE procedure [dbo].[p_getVipScoreAdd]
@cSaleSheetNo varchar(32),
@fVipRate varchar(32),
@callName VARCHAR(80)
AS
BEGIN
  DECLARE @SQLWHERE VARCHAR(800)

  SET  @SQLWHERE = 'SELECT '+@callName+' ('
                          +''''+@cSaleSheetNo+''','
                          +@fVipRate+') AS vipAddScore '

  PRINT(@SQLWHERE)

 EXEC (@SQLWHERE)
END