package com.hlyf.domin;



public class vip {

    private String cVipNo;
    private String cVipName;
    private String cTel;
    private String cSex;
    private String cVipRanck;
    private String cStoreNo;
    private String dBirthday;
    private String dValidStart;
    private String dValidEnd;
    private String fPFrate;
    private String fCurValue;
    private String fCurValue_Pos;
    private String bVipPrice;

    public String getcVipNo() {
        return cVipNo;
    }

    public void setcVipNo(String cVipNo) {
        this.cVipNo = cVipNo;
    }

    public String getcVipName() {
        return cVipName;
    }

    public void setcVipName(String cVipName) {
        this.cVipName = cVipName;
    }

    public String getcTel() {
        return cTel;
    }

    public void setcTel(String cTel) {
        this.cTel = cTel;
    }

    public String getcSex() {
        return cSex;
    }

    public void setcSex(String cSex) {
        this.cSex = cSex;
    }

    public String getcVipRanck() {
        return cVipRanck;
    }

    public void setcVipRanck(String cVipRanck) {
        this.cVipRanck = cVipRanck;
    }

    public String getcStoreNo() {
        return cStoreNo;
    }

    public void setcStoreNo(String cStoreNo) {
        this.cStoreNo = cStoreNo;
    }

    public String getdBirthday() {
        return dBirthday;
    }

    public void setdBirthday(String dBirthday) {
        this.dBirthday = dBirthday;
    }

    public String getdValidStart() {
        return dValidStart;
    }

    public void setdValidStart(String dValidStart) {
        this.dValidStart = dValidStart;
    }

    public String getdValidEnd() {
        return dValidEnd;
    }

    public void setdValidEnd(String dValidEnd) {
        this.dValidEnd = dValidEnd;
    }

    public String getfPFrate() {
        return fPFrate;
    }

    public void setfPFrate(String fPFrate) {
        this.fPFrate = fPFrate;
    }

    public String getfCurValue() {
        return fCurValue;
    }

    public void setfCurValue(String fCurValue) {
        this.fCurValue = fCurValue;
    }

    public String getfCurValue_Pos() {
        return fCurValue_Pos;
    }

    public void setfCurValue_Pos(String fCurValue_Pos) {
        this.fCurValue_Pos = fCurValue_Pos;
    }

    public String getbVipPrice() {
        return bVipPrice;
    }

    public void setbVipPrice(String bVipPrice) {
        this.bVipPrice = bVipPrice;
    }

    @Override
    public String toString() {
        return "vip{" +
                "cVipNo='" + cVipNo + '\'' +
                ", cVipName='" + cVipName + '\'' +
                ", cTel='" + cTel + '\'' +
                ", cSex='" + cSex + '\'' +
                ", cVipRanck='" + cVipRanck + '\'' +
                ", cStoreNo='" + cStoreNo + '\'' +
                ", dBirthday='" + dBirthday + '\'' +
                ", dValidStart='" + dValidStart + '\'' +
                ", dValidEnd='" + dValidEnd + '\'' +
                ", fPFrate='" + fPFrate + '\'' +
                ", fCurValue='" + fCurValue + '\'' +
                ", fCurValue_Pos='" + fCurValue_Pos + '\'' +
                ", bVipPrice='" + bVipPrice + '\'' +
                '}';
    }
}
