-- 判断要创建的表名是否存在          第三方数据表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tThirdUsers]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 删除表
drop table [dbo].[tThirdUsers]
GO
CREATE TABLE tThirdUsers(
appId     VARCHAR(200) PRIMARY KEY,
appSercet VARCHAR(1000),
userName  VARCHAR(200),
createTime DATETIME DEFAULT (GETDATE()),          --创建时间
totalNumber  int DEFAULT 1000,                    --第三方每天获取token的总次数  默认每天可以访问1000次
leftNumber   int DEFAULT 1000,                    --第三方每天访问token还剩下多少次   访问一次减一次   直到访问次数为零   0   就不让在获取token
beizhu      VARCHAR(100)  DEFAULT ''
)



-- 日志表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tThirdUsers_Log]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 删除表
drop table [dbo].[tThirdUsers_Log]
GO
CREATE TABLE tThirdUsers_Log(
id     BIGINT  IDENTITY(1,1) PRIMARY KEY,
appId     VARCHAR(200),
userName  VARCHAR(200),
userIp    VARCHAR(200),
visitTime  DATETIME DEFAULT (GETDATE()),
visitData  TEXT
)

-- 支付方式表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tMethod_of_payment]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 删除表
drop table [dbo].[tMethod_of_payment]
GO
CREATE TABLE tMethod_of_payment(
id     BIGINT  IDENTITY(1,1) PRIMARY KEY,
payId     INT,
payMethod VARCHAR (100)
)
insert into tMethod_of_payment(payId,payMethod) values(1,'支付宝支付')
insert into tMethod_of_payment(payId,payMethod) values(2,'微信支付')
insert into tMethod_of_payment(payId,payMethod) values(3,'其他支付')
insert into tMethod_of_payment(payId,payMethod) values(4,'京东支付')
-- 销售表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tPublicSale_z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表
drop table [dbo].[tPublicSale_z]
GO
CREATE TABLE tPublicSale_z(
cSheetNo    VARCHAR(300),
saleTime   DATETIME DEFAULT (GETDATE()),
createTime   DATETIME DEFAULT (GETDATE()),
saleType     INT,                                      --支付方式状态
saleTypeName    VARCHAR(100),                           --支付方式名称
saleAllMoney    MONEY DEFAULT 0.0,                    --支付总金额  （备注：总金额=实付金额+折扣金额）
actualMoney     MONEY DEFAULT 0.0,                   --实付金额   正数 代表卖货  负数  代表退货    金钱单位全部是元
discountMoney     MONEY DEFAULT 0.0,                 --折扣金额
appId         VARCHAR(100) ,                          --第三方id
cStoreNo      VARCHAR(100) DEFAULT '',               --门店编号
cWHno          VARCHAR(100) DEFAULT '',               --仓库编号
returnSale     int DEFAULT 1,                         --是否是退货   1 正常销售   2 代表退货
returnTime     DATETIME DEFAULT (GETDATE()),           --退货时间
returncSheetNo VARCHAR(300) DEFAULT '',                          --如果是退货  退货以前的单号
beizhu          VARCHAR(300)  DEFAULT '正常销售',      --备注  正常销售和退货
is_tiQue        int DEFAULT 0                           -- 0 没有提取   1 已经提取
Primary Key (cSheetNo,appId)
)

ALTER TABLE tPublicSale_z ADD  appIdSure VARCHAR(100) DEFAULT ''
ALTER TABLE tPublicSale_z ADD  cMachineID VARCHAR(100) DEFAULT ''
ALTER TABLE tPublicSale_z ADD  cMachineName VARCHAR(100) DEFAULT ''



-- 销售表详情
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tPublicSaleDetail_z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表详情
drop table [dbo].[tPublicSaleDetail_z]
GO
CREATE TABLE tPublicSaleDetail_z(
cSheetNo    VARCHAR(300),
createTime   DATETIME DEFAULT (GETDATE()),
appId         VARCHAR(100) DEFAULT '',               --第三方id
cStoreNo      VARCHAR(100) DEFAULT '',               --门店编号
cWHno          VARCHAR(100) DEFAULT '',               --仓库编号
bAuditing      INT ,                                   --是否折扣 1 不折扣  0 折扣
cGoodsNo       VARCHAR(100) DEFAULT '',
cGoodsName     Varchar(200) DEFAULT '',
cBarcode       VARCHAR(100) DEFAULT '',
fNormalPrice   Money,                                    --正常售价
discountPrice  Money,                                    --折扣售价
discountMoney  Money,                                    --此商品如果参与折扣  优惠的金额
iSeed          INT,                                       --序号
fQuantity      Money                                      --数量  称重表示g  其他表示个
)



-- 销售表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tPublicSale_JingDong_z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表
drop table [dbo].[tPublicSale_JingDong_z]

CREATE TABLE tPublicSale_JingDong_z(
cSheetNo    VARCHAR(30),                             --我们数据库中的单号
appSheetNo  VARCHAR(30),                             --第三方数据库中的单号
saleTime   DATETIME DEFAULT (GETDATE()),              --单据销售时间（结算时间）
createTime   DATETIME DEFAULT (GETDATE()),            --单据创建时间
saleType     INT,                                      --支付方式状态
saleTypeName    VARCHAR(64),                           --支付方式名称
saleAllMoney    MONEY DEFAULT 0.0,                    --支付总金额  （备注：总金额=实付金额+折扣金额）
actualMoney     MONEY DEFAULT 0.0,                   --实付金额   正数 代表卖货  负数  代表退货    金钱单位全部是元
discountMoney     MONEY DEFAULT 0.0,                 --折扣金额
appId         VARCHAR(30) ,                          --第三方id
cStoreNo      VARCHAR(20) DEFAULT '',               --门店编号
cWHno          VARCHAR(20) DEFAULT '',               --仓库编号
returnSale     int DEFAULT 1,                         --是否是退货   1 正常销售   2 代表退货
returnTime     DATETIME DEFAULT (GETDATE()),           --退货时间
returncSheetNo VARCHAR(30) DEFAULT '',                          --如果是退货  退货以前的单号
beizhu          VARCHAR(100)  DEFAULT '正常销售',      --备注  正常销售和退货
is_tiQue        int DEFAULT 0,                           -- 0 没有提取   1 已经提取
cVipNo VARCHAR(20),                                   --会员卡号
isAccount        int DEFAULT 0 ,                          -- 0未支付单据  1已支付单子
machineIp      VARCHAR(100) DEFAULT  ''
Primary Key (cSheetNo)
)

CREATE NONCLUSTERED INDEX tPublicSale_JingDong_z_Idx ON tPublicSale_JingDong_z(cSheetNo)  --创建索引

-- 销售表详情
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tPublicSaleDetail_JingDong_z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表详情
drop table [dbo].[tPublicSaleDetail_JingDong_z]

CREATE TABLE tPublicSaleDetail_JingDong_z(
cSheetNo    VARCHAR(30),
createTime   DATETIME DEFAULT (GETDATE()),
appId         VARCHAR(30) DEFAULT '',               --第三方id
cStoreNo      VARCHAR(20) DEFAULT '',               --门店编号
cWHno          VARCHAR(20) DEFAULT '',              --仓库编号
bAuditing      INT ,                                  --是否折扣 1 不折扣  0 折扣
cGoodsNo       VARCHAR(30) DEFAULT '',
cGoodsName     Varchar(64) DEFAULT '',
cBarcode       VARCHAR(30) DEFAULT '',
fNormalPrice   Money,                                    --正常售价
discountPrice  Money,                                    --折扣售价
discountMoney  Money,                                    --此商品如果参与折扣  优惠的金额
iSeed          INT,                                       --序号
fQuantity      Money,                                      --数量  称重表示g  其他表示个
cVipNo VARCHAR(20),                                   --会员卡号
isAccount        int DEFAULT 0,                          -- 0未支付单据  1已支付单子
machineIp      VARCHAR(100) DEFAULT  '',                --机器id
bWeight        BIT  DEFAULT  0,                           --是否称重
cFreshBarcode  VARCHAR(30) DEFAULT  ''                  --原始生鲜码  没有解析的
)
CREATE NONCLUSTERED INDEX tPublicSaleDetail_JingDong_z_Idx ON tPublicSaleDetail_JingDong_z(cSheetNo)  --创建索引


-- 对应的无人售货机器表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[posstation]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 对应的无人售货机器表
drop table [dbo].[posstation]

CREATE TABLE posstation(
	appId  VARCHAR(32)   NOT NULL, --第三方账户id
	posname varchar(64) NOT NULL,  --
	posid varchar(2) NOT NULL,     --对应的机器编号（华隆分配）
	cWHno varchar(32) DEFAULT '',	--仓库编号
	Pos_Day varchar(64) NULL,       --数据库表名称 Posmanagement_main.dbo.test
	cStoreNo varchar(32) DEFAULT '', --门店编号
	cMachineID varchar(64) NOT NULL,  --机器id
	cMachineKey varchar(32) NOT NULL,  -- key  -- MD5(warelucent+cMachineID).toUpper MD5加密后 转大写
	cStoreName  VARCHAR(64) NOT NULL,
	appName  VARCHAR(100) NOT NULL,
)

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



