package com.hlyf.domin.JDcom;

/**
 * Created by Administrator on 2019-04-29.
 */
public class posstation {
    private String appId;

    private String posname;

    private String posid;

    private String cWHno;

    private String pos_Day;

    private String cStoreNo;

    private String cMachineID;

    private String cMachineKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPosname() {
        return posname;
    }

    public void setPosname(String posname) {
        this.posname = posname;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getcWHno() {
        return cWHno;
    }

    public void setcWHno(String cWHno) {
        this.cWHno = cWHno;
    }

    public String getPos_Day() {
        return pos_Day;
    }

    public void setPos_Day(String pos_Day) {
        this.pos_Day = pos_Day;
    }

    public String getcStoreNo() {
        return cStoreNo;
    }

    public void setcStoreNo(String cStoreNo) {
        this.cStoreNo = cStoreNo;
    }

    public String getcMachineID() {
        return cMachineID;
    }

    public void setcMachineID(String cMachineID) {
        this.cMachineID = cMachineID;
    }

    public String getcMachineKey() {
        return cMachineKey;
    }

    public void setcMachineKey(String cMachineKey) {
        this.cMachineKey = cMachineKey;
    }

    @Override
    public String toString() {
        return "posstation{" +
                "appId='" + appId + '\'' +
                ", posname='" + posname + '\'' +
                ", posid='" + posid + '\'' +
                ", cWHno='" + cWHno + '\'' +
                ", pos_Day='" + pos_Day + '\'' +
                ", cStoreNo='" + cStoreNo + '\'' +
                ", cMachineID='" + cMachineID + '\'' +
                ", cMachineKey='" + cMachineKey + '\'' +
                '}';
    }
}
