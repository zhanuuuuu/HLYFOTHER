package com.hlyf.domin;



import java.io.Serializable;

/**
 * @author Administrator 销售表
 */

public class tPublicSale_z implements Serializable{

    private static final long serialVersionUID = -1l;

    private String cSheetNo;
    private String saleTime;
    private String saleType;
    private String saleTypeName;
    private String saleAllMoney;
    private String actualMoney;
    private String discountMoney;
    private String appId;
    private String cStoreNo;
    private String cWHno;
    private String appIdSure;

    private String cMachineID;
    private String cMachineName;

    public String getcMachineID() {
        return cMachineID;
    }

    public void setcMachineID(String cMachineID) {
        this.cMachineID = cMachineID;
    }

    public String getcMachineName() {
        return cMachineName;
    }

    public void setcMachineName(String cMachineName) {
        this.cMachineName = cMachineName;
    }

    public String getAppIdSure() {
        return appIdSure;
    }

    public void setAppIdSure(String appIdSure) {
        this.appIdSure = appIdSure;
    }

    public String getcSheetNo() {
        return cSheetNo;
    }

    public void setcSheetNo(String cSheetNo) {
        this.cSheetNo = cSheetNo;
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

    @Override
    public String toString() {
        return "tPublicSale_z{" +
                "cSheetNo='" + cSheetNo + '\'' +
                ", saleTime='" + saleTime + '\'' +
                ", saleType='" + saleType + '\'' +
                ", saleTypeName='" + saleTypeName + '\'' +
                ", saleAllMoney='" + saleAllMoney + '\'' +
                ", actualMoney='" + actualMoney + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", appId='" + appId + '\'' +
                ", cStoreNo='" + cStoreNo + '\'' +
                ", cWHno='" + cWHno + '\'' +
                ", appIdSure='" + appIdSure + '\'' +
                ", cMachineID='" + cMachineID + '\'' +
                ", cMachineName='" + cMachineName + '\'' +
                '}';
    }
}
