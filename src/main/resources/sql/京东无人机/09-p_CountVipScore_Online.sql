USE [posstation101]
GO
/****** Object:  UserDefinedFunction [dbo].[p_CountVipScore]    Script Date: 07/01/2019 11:57:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/*
select [dbo].[p_CountVipScore]('2312312',100)

*/





--ALTER         Function [dbo].[p_CountVipScore] 
alter        Function [dbo].[p_CountVipScore_Online] 
(  @cSheetno varchar(32),

  @VipRate money
 ) returns Money 
AS
begin
  declare @dDateNow datetime
  declare @fValue_Ret money
  select @dDateNow=dbo.getdaystr(dDate) from CurrentDate
  --set @dDateNow=dbo.getdaystr(dbo.getdate())
		set @fValue_Ret= round((select CountVipScore=isnull(sum(isnull(
										case when isnull(x.bWeight,0)=0
										then 
											case when x.fNormalVipScore>=0
											then
												case when isnull(x.fVipScore,0)*isnull(x.fValue_Con,0)>0
														 then x.fLastSettle*x.fValue_Score/cast(x.fValue_Con as money)
														 else isnull(x.fVipScore*x.fQuantity,0)
												end 
													/*
													(x.fVipScore)*
													case when isnull(x.bUpdate,0)=0 
															 then 1*x.fQuantity*@VipRate/100
															 else x.fPrice/(case when x.fSpecPrice=0 then x.fPrice else x.fSpecPrice end) 
																		*x.fQuantity*@VipRate/100
													end
												 */
											else
												case when isnull(x.fVipScore,0)*isnull(x.fValue_Con,0)>0
														 then x.fLastSettle*x.fValue_Score/cast(x.fValue_Con as money)
														 else isnull(x.fVipScore*x.fQuantity,0)
												end 
													/*
														(x.fVipScore)*
														case when isnull(x.bUpdate,0)=0 
																 then 1*x.fQuantity*@VipRate/100
																 else x.fPrice/(case when x.fSpecPrice=0 then x.fPrice else x.fSpecPrice end)
																	*x.fQuantity*@VipRate/100
														end
												 */ 		
											end
										else
										
												case when isnull(x.fValue_Con,0)*isnull(x.fVipscore,0)>=0 
         										 then x.fLastSettle*x.fValue_Score/cast(x.fValue_Con as money) 
														 else  x.fVipScore*x.fQuantity 
												end
											 /*
												case when x.fVipScore<>0 then x.fLastSettle else 0 end
											 */
										end
												 
 									,0)),0)
						from  (select c.cGoodsTypeno,c.fValue_Con,c.fValue_Score, a.cGoodsNo,a.cGoodsName,a.bAuditing,
										a.fVipscore,a.fPrice,a.fLastSettle,a.fQuantity,a.fNormalVipScore,a.bUpdate,
										a.bWeight,a.fSpecPrice,a.cSaleSheetno_time
										from pos_SaleSheetDetailTemp a,pos_Goods b,pos_Goodstype c
										where a.cGoodsNo=b.cGoodsNo and b.cGoodsTypeNo=c.cGoodsTypeNo 
										and a.cSaleSheetno_time=@cSheetno 
										and a.fPrice<>0
									) x 
						/*
									left join 
									(select a.cGoodsNo,a.fVipValue 
											from  dbo.Pos_VipcGoodsPriceDetail a,dbo.Pos_VipcGoodsPrice b 
											where a.cSheetno=b.cSheetno and @dDateNow between b.dDateBgn and b.dDateEnd
									) y on x.cGoodsNo=y.cGoodsNo
								 where x.cSaleSheetno_time=@cSheetno and x.fPrice<>0
    				*/			
					 ),2)
	return @fValue_Ret
end







