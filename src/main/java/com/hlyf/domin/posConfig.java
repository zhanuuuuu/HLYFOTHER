package com.hlyf.domin;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2019-04-28.
 */
public class posConfig {

    private String cID;

    private Integer iGoodsNoStart;

    private Integer iGoodsNoEnd;

    private Integer iMoneyStart;

    private Integer iMoneyEnd;

    private BigDecimal iRatio;

    private Boolean bMlt;

    private Boolean bDazhe;

    private String cCharID;

    private Integer iMoneyStart18;

    private Integer iMoneyEnd18;

    private Integer iWeightStart;

    private Integer iWeightEnd;

    private Integer iWeightStart18;

    private Integer iWeightEnd18;

    private static final long serialVersionUID = 1L;

    public Integer getiGoodsNoStart() {
        return iGoodsNoStart;
    }

    public void setiGoodsNoStart(Integer iGoodsNoStart) {
        this.iGoodsNoStart = iGoodsNoStart;
    }

    public Integer getiGoodsNoEnd() {
        return iGoodsNoEnd;
    }

    public void setiGoodsNoEnd(Integer iGoodsNoEnd) {
        this.iGoodsNoEnd = iGoodsNoEnd;
    }

    public Integer getiMoneyStart() {
        return iMoneyStart;
    }

    public void setiMoneyStart(Integer iMoneyStart) {
        this.iMoneyStart = iMoneyStart;
    }

    public Integer getiMoneyEnd() {
        return iMoneyEnd;
    }

    public void setiMoneyEnd(Integer iMoneyEnd) {
        this.iMoneyEnd = iMoneyEnd;
    }

    public BigDecimal getiRatio() {
        return iRatio;
    }

    public void setiRatio(BigDecimal iRatio) {
        this.iRatio = iRatio;
    }

    public Boolean getbMlt() {
        return bMlt;
    }

    public void setbMlt(Boolean bMlt) {
        this.bMlt = bMlt;
    }

    public Boolean getbDazhe() {
        return bDazhe;
    }

    public void setbDazhe(Boolean bDazhe) {
        this.bDazhe = bDazhe;
    }

    public String getcCharID() {
        return cCharID;
    }

    public void setcCharID(String cCharID) {
        this.cCharID = cCharID;
    }

    public Integer getiMoneyStart18() {
        return iMoneyStart18;
    }

    public void setiMoneyStart18(Integer iMoneyStart18) {
        this.iMoneyStart18 = iMoneyStart18;
    }

    public Integer getiMoneyEnd18() {
        return iMoneyEnd18;
    }

    public void setiMoneyEnd18(Integer iMoneyEnd18) {
        this.iMoneyEnd18 = iMoneyEnd18;
    }

    public Integer getiWeightStart() {
        return iWeightStart;
    }

    public void setiWeightStart(Integer iWeightStart) {
        this.iWeightStart = iWeightStart;
    }

    public Integer getiWeightEnd() {
        return iWeightEnd;
    }

    public void setiWeightEnd(Integer iWeightEnd) {
        this.iWeightEnd = iWeightEnd;
    }

    public Integer getiWeightStart18() {
        return iWeightStart18;
    }

    public void setiWeightStart18(Integer iWeightStart18) {
        this.iWeightStart18 = iWeightStart18;
    }

    public Integer getiWeightEnd18() {
        return iWeightEnd18;
    }

    public void setiWeightEnd18(Integer iWeightEnd18) {
        this.iWeightEnd18 = iWeightEnd18;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cID=").append(cID);
        sb.append(", iGoodsNoStart=").append(iGoodsNoStart);
        sb.append(", iGoodsNoEnd=").append(iGoodsNoEnd);
        sb.append(", iMoneyStart=").append(iMoneyStart);
        sb.append(", iMoneyEnd=").append(iMoneyEnd);
        sb.append(", iRatio=").append(iRatio);
        sb.append(", bMlt=").append(bMlt);
        sb.append(", bDazhe=").append(bDazhe);
        sb.append(", cCharID=").append(cCharID);
        sb.append(", iMoneyStart18=").append(iMoneyStart18);
        sb.append(", iMoneyEnd18=").append(iMoneyEnd18);
        sb.append(", iWeightStart=").append(iWeightStart);
        sb.append(", iWeightEnd=").append(iWeightEnd);
        sb.append(", iWeightStart18=").append(iWeightStart18);
        sb.append(", iWeightEnd18=").append(iWeightEnd18);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
