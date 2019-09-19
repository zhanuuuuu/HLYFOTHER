--会员积分日志表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[otherVipUpLog]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[otherVipUpLog]
CREATE TABLE otherVipUpLog(
createTime   DATETIME DEFAULT (GETDATE()),
appId VARCHAR (64) DEFAULT  '',
machineId VARCHAR (100) DEFAULT '',
vipNo      VARCHAR(64)  DEFAULT '',
addMoney   MONEY DEFAULT 0.0,
oddMoney   MONEY DEFAULT 0.0,
newMoney   MONEY DEFAULT 0.0
)