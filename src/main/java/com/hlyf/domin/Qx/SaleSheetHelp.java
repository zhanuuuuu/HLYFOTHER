package com.hlyf.domin.Qx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by Administrator on 2019-09-09.
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class SaleSheetHelp {

    private String dSaleDate;

    private String cSaleSheetno;

    private Integer iSeed;

    private String cStoreNo;

    private String cGoodsNo;

    private String cOperatorno;

    private String cOperatorName;

    private Boolean bAuditing;

    private Double fVipScore;

    private Double fPrice;

    private Double fVipPrice;

    private Double fQuantity;

    private Double fLastSettle;

    private String cSaleTime;

    private String cVipNo;

    private String cWHno;

    private String cSheetNo;

    //private Date dDatetime;


    public SaleSheetHelp(String dSaleDate, String cSaleSheetno, Integer iSeed, String cStoreNo,
                         String cGoodsNo, String cOperatorno,
                         String cOperatorName, Boolean bAuditing, Double fVipScore,
                         Double fPrice, Double fVipPrice,
                         Double fQuantity, Double fLastSettle, String cSaleTime, String cVipNo, String cWHno, String cSheetNo) {
        this.dSaleDate = dSaleDate;
        this.cSaleSheetno = cSaleSheetno;
        this.iSeed = iSeed;
        this.cStoreNo = cStoreNo;
        this.cGoodsNo = cGoodsNo;
        this.cOperatorno = cOperatorno;
        this.cOperatorName = cOperatorName;
        this.bAuditing = bAuditing;
        this.fVipScore = fVipScore;
        this.fPrice = fPrice;
        this.fVipPrice = fVipPrice;
        this.fQuantity = fQuantity;
        this.fLastSettle = fLastSettle;
        this.cSaleTime = cSaleTime;
        this.cVipNo = cVipNo;
        this.cWHno = cWHno;
        this.cSheetNo = cSheetNo;
    }
}
