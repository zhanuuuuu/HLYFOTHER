package com.hlyf.domin.JDcom;



import java.io.Serializable;

/**
 * @描述 京东销售主表
 */

public class tPublicSale_JingDong_z implements Serializable{

    private static final long serialVersionUID = -1l;

    private String cSheetNo;
    private String appSheetNo;
    private String saleTime;
    private String saleType;
    private String saleTypeName;
    private String saleAllMoney;
    private String actualMoney;
    private String discountMoney;
    private String appId;
    private String cStoreNo;
    private String cWHno;
    private int isAccount;
    private String cVipNo;
    private String machineIp;

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public String getcSheetNo() {
        return cSheetNo;
    }

    public void setcSheetNo(String cSheetNo) {
        this.cSheetNo = cSheetNo;
    }

    public String getAppSheetNo() {
        return appSheetNo;
    }

    public void setAppSheetNo(String appSheetNo) {
        this.appSheetNo = appSheetNo;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getSaleTypeName() {
        return saleTypeName;
    }

    public void setSaleTypeName(String saleTypeName) {
        this.saleTypeName = saleTypeName;
    }

    public String getSaleAllMoney() {
        return saleAllMoney;
    }

    public void setSaleAllMoney(String saleAllMoney) {
        this.saleAllMoney = saleAllMoney;
    }

    public String getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(String actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
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

    public int getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(int isAccount) {
        this.isAccount = isAccount;
    }

    public String getcVipNo() {
        return cVipNo;
    }

    public void setcVipNo(String cVipNo) {
        this.cVipNo = cVipNo;
    }

    @Override
    public String toString() {
        return "tPublicSale_JingDong_z{" +
                "cSheetNo='" + cSheetNo + '\'' +
                ", cappSheetNo='" + appSheetNo + '\'' +
                ", saleTime='" + saleTime + '\'' +
                ", saleType='" + saleType + '\'' +
                ", saleTypeName='" + saleTypeName + '\'' +
                ", saleAllMoney='" + saleAllMoney + '\'' +
                ", actualMoney='" + actualMoney + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", appId='" + appId + '\'' +
                ", cStoreNo='" + cStoreNo + '\'' +
                ", cWHno='" + cWHno + '\'' +
                ", isAccount=" + isAccount +
                ", cVipNo='" + cVipNo + '\'' +
                '}';
    }
}
