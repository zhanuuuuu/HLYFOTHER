package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**@author 张    促销表
 * Created by Administrator on 2018-11-15.
 * SELECT  * FROM t_PloyOfSale where  CONVERT(varchar(100), GETDATE(), 23) BETWEEN dDateStart AND dDateEnd
SELECT isWholePackage=1,packageRate=1,youHuiAmount=fPrice_SO_old-fPrice_SO,
cPloyNo,B.cGoodsNo,B.cGoodsName,fPrice_SO AS youHuiPrice,amount=fPrice_SO,goodsNum=1,
fPrice_SO_old AS fPriceOdd,cPloyTypeNo,cPloyTypeName,B.cBarcode,B.cUnit,B.cSpec
FROM  t_PloyOfSale A,t_cStoreGoods B
where  CONVERT(varchar(100), GETDATE(), 23) BETWEEN dDateStart AND dDateEnd
AND A.cStoreNo='0002' AND B.cStoreNo='0002' AND A.cStoreNo=B.cStoreNo AND A.cGoodsNo=B.cGoodsNo AND ISNULL(fQuantity_Ploy,0)>0 AND B.cBarcode=''
 */

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class tPloyOfSale implements Serializable {
    private Integer isWholePackage;
    private Integer packageRate;
    private Double youHuiAmount;
    private String cPloyNo;
    private String cGoodsNo;
    private String cGoodsName;
    private Double youHuiPrice;
    private Double amount;
    private Integer goodsNum;
    private Double fPriceOdd;
    private String cPloyTypeNo;
    private String cPloyTypeName;
    private String cBarcode;
    private String cUnit;
    private String cSpec;
}
