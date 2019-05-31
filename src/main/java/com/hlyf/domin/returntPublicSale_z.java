package com.hlyf.domin;



import java.io.Serializable;

/**
 * @author Administrator 退货表
 * returnSale     int DEFAULT 1,                         --是否是退货   1 正常销售   2 代表退货
    returnTime     DATETIME DEFAULT (GETDATE()),           --退货时间
    returncSheetNo VARCHAR(300),                          --如果是退货  退货以前的单号
    beizhu          VARCHAR(300)  DEFAULT '正常销售',      --备注  正常销售和退货
 */

public class returntPublicSale_z {

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
    private String returnSale;
    private String returnTime;
    private String returncSheetNo;
    private String beizhu;
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

    public String getReturnSale() {
        return returnSale;
    }

    public void setReturnSale(String returnSale) {
        this.returnSale = returnSale;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturncSheetNo() {
        return returncSheetNo;
    }

    public void setReturncSheetNo(String returncSheetNo) {
        this.returncSheetNo = returncSheetNo;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    @Override
    public String toString() {
        return "returntPublicSale_z{" +
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
                ", returnSale='" + returnSale + '\'' +
                ", returnTime='" + returnTime + '\'' +
                ", returncSheetNo='" + returncSheetNo + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", appIdSure='" + appIdSure + '\'' +
                ", cMachineID='" + cMachineID + '\'' +
                ", cMachineName='" + cMachineName + '\'' +
                '}';
    }
}
