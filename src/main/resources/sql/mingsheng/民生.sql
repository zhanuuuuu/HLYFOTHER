--主库 posmain t_goods  表增加的字段
ALTER TABLE T_GOODS ADD  mingsheng_cGoodsNo  VARCHAR(100) DEFAULT ''   --民生对应的商品id
ALTER TABLE T_GOODS ADD  mingsheng_isImg  int DEFAULT 0                 --0没有上传图片 1上传了图片
ALTER TABLE T_GOODS ADD  mingsheng_isKuCun  int DEFAULT 0              --0没有上传库存  1上传了库存
ALTER TABLE T_GOODS ADD  mingsheng_isMarketable  int DEFAULT 0         --0没有上架  1上架
alter table t_Goods add cGoodsImagePathThird varchar(300)              -- 民生的图片



-- 销售表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[t_MinShengPublicSale_z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表
drop table [dbo].[t_MinShengPublicSale_z]
GO
CREATE TABLE t_MinShengPublicSale_z(
orderSn    VARCHAR(300) PRIMARY KEY,                --民生单号
createDate   DATETIME DEFAULT (GETDATE()),            --民生创建时间
beginTime   DATETIME DEFAULT (GETDATE()),             --下载开始时间
endTime     DATETIME DEFAULT (GETDATE()),             --下载结束时间
totalPrice    MONEY DEFAULT 0.0,                      --民生订单总金额
paymentMethodName   VARCHAR(100) DEFAULT '',         --民生支付方式
offlineStoresId      VARCHAR(100) DEFAULT '',         --民生门店编号
is_tiQue        int DEFAULT 0                           -- 0 没有提取   1 已经提取
)

-- 销售表详情
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[t_MingShengPublicSaleDetail_z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表详情
drop table [dbo].[t_MingShengPublicSaleDetail_z]
GO
CREATE TABLE t_MingShengPublicSaleDetail_z(
orderSn    VARCHAR(300),                              --民生单号
createDate   DATETIME DEFAULT (GETDATE()),            --民生创建时间
offlineStoresId      VARCHAR(100) DEFAULT '',         --民生门店编号
productSn       VARCHAR(100) DEFAULT '',              --民生商品编号
fullName     Varchar(500) DEFAULT '',                 --民生商品全名
name       VARCHAR(200) DEFAULT '',                   --民生商品名称
price   Money,                                          --民生售价
quantity  Money,                                        --民生商品数量
outId  VARCHAR(200) DEFAULT '',                        --对应我们的数据库的商品编号
barCode  VARCHAR(200) DEFAULT '',                      --民生商品条码
weight      Money                                        --民生重量g
)

-- 销售表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[t_MinShengPublicSale_z_beifen]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表
drop table [dbo].[t_MinShengPublicSale_z_beifen]
GO
CREATE TABLE t_MinShengPublicSale_z_beifen(
orderSn    VARCHAR(300) PRIMARY KEY,                --民生单号
createDate   DATETIME DEFAULT (GETDATE()),            --民生创建时间
beginTime   DATETIME DEFAULT (GETDATE()),             --下载开始时间
endTime     DATETIME DEFAULT (GETDATE()),             --下载结束时间
totalPrice    MONEY DEFAULT 0.0,                      --民生订单总金额
paymentMethodName   VARCHAR(100) DEFAULT '',         --民生支付方式
offlineStoresId      VARCHAR(100) DEFAULT '',         --民生门店编号
is_tiQue        int DEFAULT 0                           -- 0 没有提取   1 已经提取
)

-- 销售表详情
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[t_MingShengPublicSaleDetail_z_beifen]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
-- 销售表详情
drop table [dbo].[t_MingShengPublicSaleDetail_z_beifen]
GO
CREATE TABLE t_MingShengPublicSaleDetail_z_beifen(
orderSn    VARCHAR(300),                              --民生单号
createDate   DATETIME DEFAULT (GETDATE()),            --民生创建时间
offlineStoresId      VARCHAR(100) DEFAULT '',         --民生门店编号
productSn       VARCHAR(100) DEFAULT '',              --民生商品编号
fullName     Varchar(500) DEFAULT '',                 --民生商品全名
name       VARCHAR(200) DEFAULT '',                   --民生商品名称
price   Money,                                        --民生售价
quantity  Money,                                      --民生商品数量
outId  VARCHAR(200) DEFAULT '',                       --对应我们的数据库的商品编号
barCode  VARCHAR(200) DEFAULT '',                     --民生商品条码
weight      Money                                     --民生重量g
)


