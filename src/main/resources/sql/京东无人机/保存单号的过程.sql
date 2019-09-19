/*
	 保存单号的过程  分库的过程
	 EXEC p_saveSheetNo '1005','05','2019-05-06','05201905050001',36
*/
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_saveSheetNo]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_saveSheetNo]

END
GO
CREATE PROC [dbo].[p_saveSheetNo]
@cStoreNo VARCHAR(32),
@cPosID VARCHAR(32),
@Zdriqi VARCHAR(32),
@SerNo VARCHAR(32),
@iSeed_Max INT
AS
BEGIN
  if exists(select * from pos_SerialNo where cStoreNo=@cStoreNo and cPosID=@cPosID and Zdriqi=@Zdriqi )
  BEGIN
    update pos_SerialNo
    set SerNo=@SerNo,iSeed_Max=@iSeed_Max
    where cStoreNo=@cStoreNo and cPosID=@cPosID and Zdriqi=@Zdriqi
  END ELSE
  BEGIN
    insert into pos_SerialNo
    (cStoreNo, cPosID, SerNo, Zdriqi, iSeed_Max)
    select cStoreNo=@cStoreNo,cPosID=@cPosID, SerNo=@SerNo, Zdriqi=@Zdriqi, iSeed_Max=@iSeed_Max
  END
END

GO
--保存单号  主库的过程
--EXEC p_saveSheetNo_Z '1005','05','2019-05-06','05201905050001',39,'posstation101.dbo.p_saveSheetNo'
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_saveSheetNo_Z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_saveSheetNo_Z]

END
GO
CREATE procedure [dbo].[p_saveSheetNo_Z]
@cStoreNo VARCHAR(32),
@cPosID VARCHAR(32),
@Zdriqi VARCHAR(32),
@SerNo VARCHAR(32),
@iSeed_Max VARCHAR(32),
@callName VARCHAR(80)
AS
BEGIN
  DECLARE @SQLWHERE VARCHAR(8000)

  SET  @SQLWHERE = 'EXEC '+@callName+' '''+@cStoreNo+''','''
                          +@cPosID+''','''+@Zdriqi+''','''
                          +@SerNo+''','+@iSeed_Max

  PRINT(@SQLWHERE)

  EXEC (@SQLWHERE)
END

GO

--获取单号
--EXEC p_getPos_SerialNoSheetNo '1005','05','2019-05-06'
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_getPos_SerialNoSheetNo]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_getPos_SerialNoSheetNo]

END
GO
CREATE PROC [dbo].[p_getPos_SerialNoSheetNo]
@cStoreNo VARCHAR(32),
@cPosID VARCHAR(32),
@Zdriqi VARCHAR(32)
AS
BEGIN
  DECLARE @cSheetNo VARCHAR(32)
  DECLARE @rightNum INT

  IF exists(select SerNo from pos_SerialNo where cStoreNo=@cStoreNo and cPosID=@cPosID and Zdriqi=@Zdriqi )
  BEGIN
	--取值
	SET @cSheetNo=(select SerNo from pos_SerialNo where cStoreNo=@cStoreNo and cPosID=@cPosID and Zdriqi=@Zdriqi)
	PRINT(@cSheetNo)
	--截取后四位
	SET @cSheetNo=(select RIGHT(@cSheetNo,4))
	PRINT(@cSheetNo)
	--转换int 计算 并 加 1
	SET @rightNum=(SELECT CAST(@cSheetNo AS INT)+1)
	PRINT(@rightNum)
	--转换varchar
	SET @cSheetNo=(select CAST(@rightNum AS VARCHAR(32)))
	PRINT(@cSheetNo)
	--不足4位前面补0
	WHILE(LEN(@cSheetNo) <4)
	BEGIN
		SET @cSheetNo='0'+@cSheetNo
	END
	--最后计算出结果
	SELECT cSheetNo=@cPosID+REPLACE(@Zdriqi,'-','')+@cSheetNo

  END
  ELSE
  BEGIN
   -- 直接返回一个4位的单号
	SELECT cSheetNo=@cPosID+REPLACE(@Zdriqi,'-','')+'0001'

  END
END

GO
--获取单号的  在主库执行
--EXEC p_getPos_SerialNoSheetNo_Z '1005','05','2019-05-06','posstation101.dbo.p_getPos_SerialNoSheetNo'
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_getPos_SerialNoSheetNo_Z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_getPos_SerialNoSheetNo_Z]
END
GO
CREATE procedure [dbo].[p_getPos_SerialNoSheetNo_Z]
@cStoreNo VARCHAR(32),
@cPosID VARCHAR(32),
@Zdriqi VARCHAR(32),
@callName VARCHAR(80)
AS
BEGIN
  DECLARE @SQLWHERE VARCHAR(8000)

  SET  @SQLWHERE = 'EXEC '+@callName+' '''+@cStoreNo+''','''
                          +@cPosID+''','''+@Zdriqi+''''

  PRINT(@SQLWHERE)

  EXEC (@SQLWHERE)
END