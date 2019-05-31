package com.hlyf.domin.JDcom;

/**
 * Created by Administrator on 2019-05-07.
 */
public class pos_jiesuan {
    private String sheetno;
    private String jstype;
    private String mianzhi;
    private String zhekou;
    private String zhaoling;
    private String shishou;
    private String jstime;
    private String zdriqi;
    private String jiaozhang;
    private String shouyinyuanno;
    private String shouyinyuanmc;
    private String netjiecun;
    private String orientmoney;
    private String leftmoney;
    private String storevalue;
    private String detail;
    private String bPost;
    private String cWHno;
    private String cBanci_ID;
    private String iLineNo_Banci;
    private String cBanci;

    public pos_jiesuan(){

    }

    public pos_jiesuan(String sheetno, String jstype, String mianzhi, String zhekou,
                       String zhaoling, String shishou, String jstime, String zdriqi,
                       String jiaozhang, String shouyinyuanno, String shouyinyuanmc,
                       String netjiecun, String orientmoney, String leftmoney,
                       String storevalue, String detail, String bPost,
                       String cWHno, String cBanci_ID,
                       String iLineNo_Banci, String cBanci) {
        this.sheetno = sheetno;
        this.jstype = jstype;
        this.mianzhi = mianzhi;
        this.zhekou = zhekou;
        this.zhaoling = zhaoling;
        this.shishou = shishou;
        this.jstime = jstime;
        this.zdriqi = zdriqi;
        this.jiaozhang = jiaozhang;
        this.shouyinyuanno = shouyinyuanno;
        this.shouyinyuanmc = shouyinyuanmc;
        this.netjiecun = netjiecun;
        this.orientmoney = orientmoney;
        this.leftmoney = leftmoney;
        this.storevalue = storevalue;
        this.detail = detail;
        this.bPost = bPost;
        this.cWHno = cWHno;
        this.cBanci_ID = cBanci_ID;
        this.iLineNo_Banci = iLineNo_Banci;
        this.cBanci = cBanci;
    }

    public String getSheetno() {
        return sheetno;
    }

    public void setSheetno(String sheetno) {
        this.sheetno = sheetno;
    }

    public String getJstype() {
        return jstype;
    }

    public void setJstype(String jstype) {
        this.jstype = jstype;
    }

    public String getMianzhi() {
        return mianzhi;
    }

    public void setMianzhi(String mianzhi) {
        this.mianzhi = mianzhi;
    }

    public String getZhekou() {
        return zhekou;
    }

    public void setZhekou(String zhekou) {
        this.zhekou = zhekou;
    }

    public String getZhaoling() {
        return zhaoling;
    }

    public void setZhaoling(String zhaoling) {
        this.zhaoling = zhaoling;
    }

    public String getShishou() {
        return shishou;
    }

    public void setShishou(String shishou) {
        this.shishou = shishou;
    }

    public String getJstime() {
        return jstime;
    }

    public void setJstime(String jstime) {
        this.jstime = jstime;
    }

    public String getZdriqi() {
        return zdriqi;
    }

    public void setZdriqi(String zdriqi) {
        this.zdriqi = zdriqi;
    }

    public String getJiaozhang() {
        return jiaozhang;
    }

    public void setJiaozhang(String jiaozhang) {
        this.jiaozhang = jiaozhang;
    }

    public String getShouyinyuanno() {
        return shouyinyuanno;
    }

    public void setShouyinyuanno(String shouyinyuanno) {
        this.shouyinyuanno = shouyinyuanno;
    }

    public String getShouyinyuanmc() {
        return shouyinyuanmc;
    }

    public void setShouyinyuanmc(String shouyinyuanmc) {
        this.shouyinyuanmc = shouyinyuanmc;
    }

    public String getNetjiecun() {
        return netjiecun;
    }

    public void setNetjiecun(String netjiecun) {
        this.netjiecun = netjiecun;
    }

    public String getOrientmoney() {
        return orientmoney;
    }

    public void setOrientmoney(String orientmoney) {
        this.orientmoney = orientmoney;
    }

    public String getLeftmoney() {
        return leftmoney;
    }

    public void setLeftmoney(String leftmoney) {
        this.leftmoney = leftmoney;
    }

    public String getStorevalue() {
        return storevalue;
    }

    public void setStorevalue(String storevalue) {
        this.storevalue = storevalue;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getbPost() {
        return bPost;
    }

    public void setbPost(String bPost) {
        this.bPost = bPost;
    }

    public String getcWHno() {
        return cWHno;
    }

    public void setcWHno(String cWHno) {
        this.cWHno = cWHno;
    }

    public String getcBanci_ID() {
        return cBanci_ID;
    }

    public void setcBanci_ID(String cBanci_ID) {
        this.cBanci_ID = cBanci_ID;
    }

    public String getiLineNo_Banci() {
        return iLineNo_Banci;
    }

    public void setiLineNo_Banci(String iLineNo_Banci) {
        this.iLineNo_Banci = iLineNo_Banci;
    }

    public String getcBanci() {
        return cBanci;
    }

    public void setcBanci(String cBanci) {
        this.cBanci = cBanci;
    }

    @Override
    public String toString() {
        return "pos_jiesuan{" +
                "sheetno='" + sheetno + '\'' +
                ", jstype='" + jstype + '\'' +
                ", mianzhi='" + mianzhi + '\'' +
                ", zhekou='" + zhekou + '\'' +
                ", zhaoling='" + zhaoling + '\'' +
                ", shishou='" + shishou + '\'' +
                ", jstime='" + jstime + '\'' +
                ", zdriqi='" + zdriqi + '\'' +
                ", jiaozhang='" + jiaozhang + '\'' +
                ", shouyinyuanno='" + shouyinyuanno + '\'' +
                ", shouyinyuanmc='" + shouyinyuanmc + '\'' +
                ", netjiecun='" + netjiecun + '\'' +
                ", orientmoney='" + orientmoney + '\'' +
                ", leftmoney='" + leftmoney + '\'' +
                ", storevalue='" + storevalue + '\'' +
                ", detail='" + detail + '\'' +
                ", bPost='" + bPost + '\'' +
                ", cWHno='" + cWHno + '\'' +
                ", cBanci_ID='" + cBanci_ID + '\'' +
                ", iLineNo_Banci='" + iLineNo_Banci + '\'' +
                ", cBanci='" + cBanci + '\'' +
                '}';
    }
}
