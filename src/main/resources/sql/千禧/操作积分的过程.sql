IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].[p_updateVipOther_z]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[p_updateVipOther_z]
END
GO
CREATE PROC [dbo].[p_updateVipOther_z]
 @appId varchar(64),
 @machineId varchar(64),
 @vipNo varchar(64),
 @addScore MONEY =0                --增加多少积分
 AS
BEGIN
  DECLARE @iFlag INT
  SET @iFlag = 0

  --
  DECLARE @fCurValue_Pos MONEY
  SET @fCurValue_Pos = (SELECT fCurValue_Pos FROM t_Vip WHERE cVipno=@vipNo)

  UPDATE t_vip SET fcurvalue=fcurvalue+@addScore,fCurValue_Pos=fCurValue_Pos+@addScore where cVipNo=@vipNo

  --判断影响函数
  SET @iFlag= @@rowcount

  PRINT(@iFlag)
  IF @iFlag > 0
  BEGIN
    INSERT INTO otherVipUpLog ( appId,machineId ,vipNo ,addMoney,oddMoney,newMoney) VALUES
     (@appId,@machineId,@vipNo,@addScore,@fCurValue_Pos,@fCurValue_Pos+@addScore)
  END


END

GO