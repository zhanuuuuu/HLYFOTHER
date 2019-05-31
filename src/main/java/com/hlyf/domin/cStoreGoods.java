package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-11-13.
 * SELECT TOP 100 cGoodsNo,cGoodsName,cBarcode,cStoreNo,cStoreName,cUnit,
 cSpec,fNormalPrice,fVipPrice,fVipScore,fCKPrice,isWholePack=1,
 youHuiAmount=0.0,amount=fNormalPrice,num=1,packRate=1 FROM t_cStoreGoods
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class cStoreGoods implements Serializable{
    private String cGoodsNo;
    private String cGoodsName;
    private String cBarcode;
    private String cStoreNo;
    private String cStoreName;
    private String cUnit;
    private String cSpec;
    private Double fNormalPrice;
    private Double fVipPrice;

    private Double fCKPrice;
    private Integer isWholePack;
    private Double youHuiAmount;
    private Double amount;
    private Integer num;
    private Integer packRate;
    private Double fVipScore_base;
    private Double fVipScore;


}
