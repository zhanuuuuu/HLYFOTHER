USE [PosStationHelp]
GO

/****** Object:  Table [dbo].[t_SaleSheetHelp]

  这个是部署到单店的局域网的  哈哈哈哈哈安好
  Script Date: 09/09/2019 11:53:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[t_SaleSheetHelp](
	[dSaleDate] [datetime] NOT NULL,    --销售日期：2019-07-01
	[cSaleSheetno] [varchar](32) NOT NULL,   -- 销售单号：
	[iSeed] [int] NOT NULL,           --序号
	[cStoreNo] [varchar](32) NOT NULL,   --门店编号
	[cGoodsNo] [varchar](32) NOT NULL,   --商品编号
	[cOperatorno] [varchar](16) NULL,    --操作员No
	[cOperatorName] [varchar](16) NULL,
	[bAuditing] [bit] NULL,              --是否特价
	[fVipScore] [money] NULL,            --积分值
	[fPrice] [money] NOT NULL,           --最终执行售价
	[fVipPrice] [money] NULL,            --会员价
	[fQuantity] [money] NOT NULL,        --销售数量
	[fLastSettle] [money] NOT NULL,      --最终销售金额
	[cSaleTime] [varchar](16) NOT NULL,  --销售时间：时分秒
	[cVipNo] [varchar](32) NULL,         --会员卡号
	[cWHno] [varchar](32) NULL,          --门店所属仓库号
	[cSheetNo] [varchar](32) NULL,       --备用字段
	dDatetime datetime default(getdate()),  --数据上传时间
 CONSTRAINT [PK_t_SaleSheetHelp] PRIMARY KEY CLUSTERED
(
	[dSaleDate] ASC,
	[cSaleSheetno] ASC,
	[iSeed] ASC,
	[cStoreNo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
