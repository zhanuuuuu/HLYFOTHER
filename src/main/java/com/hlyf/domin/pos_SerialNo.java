package com.hlyf.domin;

/**
 * Created by Administrator on 2019-05-06.
 */
public class pos_SerialNo {

    private String cStoreNo;
    private String cPosID;
    private String SerNo;
    private String Zdriqi;
    private String iSeed_Max;

    public String getcStoreNo() {
        return cStoreNo;
    }

    public void setcStoreNo(String cStoreNo) {
        this.cStoreNo = cStoreNo;
    }

    public String getcPosID() {
        return cPosID;
    }

    public void setcPosID(String cPosID) {
        this.cPosID = cPosID;
    }

    public String getSerNo() {
        return SerNo;
    }

    public void setSerNo(String serNo) {
        SerNo = serNo;
    }

    public String getZdriqi() {
        return Zdriqi;
    }

    public void setZdriqi(String zdriqi) {
        Zdriqi = zdriqi;
    }

    public String getiSeed_Max() {
        return iSeed_Max;
    }

    public void setiSeed_Max(String iSeed_Max) {
        this.iSeed_Max = iSeed_Max;
    }

    @Override
    public String toString() {
        return "pos_SerialNo{" +
                "cStoreNo='" + cStoreNo + '\'' +
                ", cPosID='" + cPosID + '\'' +
                ", SerNo='" + SerNo + '\'' +
                ", Zdriqi='" + Zdriqi + '\'' +
                ", iSeed_Max='" + iSeed_Max + '\'' +
                '}';
    }
}
