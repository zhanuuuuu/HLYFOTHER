package com.hlyf.domin.JDcom;

/**
 * Created by Administrator on 2018-11-24.
 */

public class tPublicSaleDetail_JingDong_z {

    private String cSheetNo;
    private String appId;
    private String cStoreNo;
    private String cWHno;
    private String bAuditing;
    private String cGoodsNo;
    private String cGoodsName;
    private String cBarcode;
    private String fNormalPrice;
    private String discountPrice;
    private String discountMoney;
    private String iSeed;
    private String fQuantity;
    private String cVipNo;
    private String machineIp;
    private String bWeight;

    private String cFreshBarcode;  //生鲜原始码   没有解析的数据

    public String getcFreshBarcode() {
        return cFreshBarcode;
    }

    public void setcFreshBarcode(String cFreshBarcode) {
        this.cFreshBarcode = cFreshBarcode;
    }

    public String getbWeight() {
        return bWeight;
    }

    public void setbWeight(String bWeight) {
        this.bWeight = bWeight;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public String getcVipNo() {
        return cVipNo;
    }

    public void setcVipNo(String cVipNo) {
        this.cVipNo = cVipNo;
    }

    public String getcSheetNo() {
        return cSheetNo;
    }

    public void setcSheetNo(String cSheetNo) {
        this.cSheetNo = cSheetNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getcStoreNo() {
        return cStoreNo;
    }

    public void setcStoreNo(String cStoreNo) {
        this.cStoreNo = cStoreNo;
    }

    public String getcWHno() {
        return cWHno;
    }

    public void setcWHno(String cWHno) {
        this.cWHno = cWHno;
    }

    public String getbAuditing() {
        return bAuditing;
    }

    public void setbAuditing(String bAuditing) {
        this.bAuditing = bAuditing;
    }

    public String getcGoodsNo() {
        return cGoodsNo;
    }

    public void setcGoodsNo(String cGoodsNo) {
        this.cGoodsNo = cGoodsNo;
    }

    public String getcGoodsName() {
        return cGoodsName;
    }

    public void setcGoodsName(String cGoodsName) {
        this.cGoodsName = cGoodsName;
    }

    public String getcBarcode() {
        return cBarcode;
    }

    public void setcBarcode(String cBarcode) {
        this.cBarcode = cBarcode;
    }

    public String getfNormalPrice() {
        return fNormalPrice;
    }

    public void setfNormalPrice(String fNormalPrice) {
        this.fNormalPrice = fNormalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getiSeed() {
        return iSeed;
    }

    public void setiSeed(String iSeed) {
        this.iSeed = iSeed;
    }

    public String getfQuantity() {
        return fQuantity;
    }

    public void setfQuantity(String fQuantity) {
        this.fQuantity = fQuantity;
    }

    @Override
    public String toString() {
        return "tPublicSaleDetail_JingDong_z{" +
                "cSheetNo='" + cSheetNo + '\'' +
                ", appId='" + appId + '\'' +
                ", cStoreNo='" + cStoreNo + '\'' +
                ", cWHno='" + cWHno + '\'' +
                ", bAuditing='" + bAuditing + '\'' +
                ", cGoodsNo='" + cGoodsNo + '\'' +
                ", cGoodsName='" + cGoodsName + '\'' +
                ", cBarcode='" + cBarcode + '\'' +
                ", fNormalPrice='" + fNormalPrice + '\'' +
                ", discountPrice='" + discountPrice + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", iSeed='" + iSeed + '\'' +
                ", fQuantity='" + fQuantity + '\'' +
                ", cVipNo='" + cVipNo + '\'' +
                ", machineIp='" + machineIp + '\'' +
                ", bWeight='" + bWeight + '\'' +
                ", cFreshBarcode='" + cFreshBarcode + '\'' +
                '}';
    }
}
