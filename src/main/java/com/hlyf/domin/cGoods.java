package com.hlyf.domin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * cGoodsNo,cStoreNo,cStoreName,cUnitedNo,cGoodsName,cGoodsTypeno,
 * cGoodsTypename,cBarcode,cUnit,cSpec,fNormalPrice,fVipPrice,
 * cProductUnit,dCreateDate,pinpai,pinpaino
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class cGoods implements Serializable {
    private int countNum;
    private int countSum;
    private String cGoodsNo;
    private String cStoreNo;
    private String cStoreName;
    private String cUnitedNo;
    private String cGoodsName;
    private String cGoodsTypeno;
    private String cGoodsTypename;
    private String cBarcode;
    private String cUnit;
    private String cSpec;
    private Double fNormalPrice;
    private Double fVipPrice;
    private String cProductUnit;
    private String dCreateDate;
    private String pinpai;
    private String pinpaino;
    private Double fVipScore_base;
    private Double fVipScore;

}
