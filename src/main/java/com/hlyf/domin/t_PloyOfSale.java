package com.hlyf.domin;

/**
 * Created by Administrator on 2019-01-18.
 * SELECT
 b.cStoreNo,
 a.cPloyNo,cGoodsNo,cGoodsName,
 fPrice_SO,   --促销价
 dDateStart,dDateEnd, --促销时段
 bEnabled,Week1,Week2,Week3,
 Week4,Week5,Week6,Week7,Hour0,Hour1,Hour2,Hour3,Hour4,Hour5,Hour6,Hour7,
 Hour8,Hour9,Hour10,Hour11,Hour12,Hour13,Hour14,Hour15,Hour16,Hour17,Hour18,
 Hour19,Hour20,Hour21,Hour22,Hour23,
 fVipValue, --积分值
 bLimitQty, --是否启用每单限购
 fLimitQty, --限购数量
 bJiOu, --是否奇偶销售
 fQty_Ji, --奇数量
 fPrice_ji,--奇价格
 fQty_Ou, --偶数量
 fPrice_Ou, --偶价格
 bSuspend-- 是否暂停
 FROM t_PloyOfSale a,t_PloyStore b
 where a.cPloyNo=b.cPloyNo AND b.cStoreNo='201'
 AND CONVERT(VARCHAR(100),GETDATE(),23) BETWEEN CONVERT(VARCHAR(100),a.dDateStart,23) AND CONVERT(VARCHAR(100),a.dDateEnd,23)
 */
public class t_PloyOfSale {
private String cStoreNo;//
private String cPloyNo;//
private String cGoodsNo;//
private String cGoodsName;//
private String fPrice_SO;//   --促销价
private String dDateStart;//
private String dDateEnd;// --促销时段
private String bEnabled;//
private String Week1;//
private String Week2;//
private String Week3;//
private String Week4;//
private String Week5;//
private String Week6;//
private String Week7;//
private String Hour0;//
private String Hour1;//
private String Hour2;//
private String Hour3;//
private String Hour4;//
private String Hour5;//
private String Hour6;//
private String Hour7;//
private String Hour8;//
private String Hour9;//
private String Hour10;//
private String Hour11;//
private String Hour12;//
private String Hour13;//
private String Hour14;//
private String Hour15;//
private String Hour16;//
private String Hour17;//
private String Hour18;//
private String Hour19;//
private String Hour20;//
private String Hour21;//
private String Hour22;//
private String Hour23;//
private String fVipValue;// --积分值
private String bLimitQty;// --是否启用每单限购
private String fLimitQty;// --限购数量
private String bJiOu;// --是否奇偶销售
private String fQty_Ji;// --奇数量
private String fPrice_ji;//--奇价格
private String fQty_Ou;// --偶数量
private String fPrice_Ou;// --偶价格
private String bSuspend;//-- 是否暂停

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

    public String getcStoreNo() {
        return cStoreNo;
    }

    public void setcStoreNo(String cStoreNo) {
        this.cStoreNo = cStoreNo;
    }

    public String getcPloyNo() {
        return cPloyNo;
    }

    public void setcPloyNo(String cPloyNo) {
        this.cPloyNo = cPloyNo;
    }

    public String getfPrice_SO() {
        return fPrice_SO;
    }

    public void setfPrice_SO(String fPrice_SO) {
        this.fPrice_SO = fPrice_SO;
    }

    public String getdDateStart() {
        return dDateStart;
    }

    public void setdDateStart(String dDateStart) {
        this.dDateStart = dDateStart;
    }

    public String getdDateEnd() {
        return dDateEnd;
    }

    public void setdDateEnd(String dDateEnd) {
        this.dDateEnd = dDateEnd;
    }

    public String getbEnabled() {
        return bEnabled;
    }

    public void setbEnabled(String bEnabled) {
        this.bEnabled = bEnabled;
    }

    public String getWeek1() {
        return Week1;
    }

    public void setWeek1(String week1) {
        Week1 = week1;
    }

    public String getWeek2() {
        return Week2;
    }

    public void setWeek2(String week2) {
        Week2 = week2;
    }

    public String getWeek3() {
        return Week3;
    }

    public void setWeek3(String week3) {
        Week3 = week3;
    }

    public String getWeek4() {
        return Week4;
    }

    public void setWeek4(String week4) {
        Week4 = week4;
    }

    public String getWeek5() {
        return Week5;
    }

    public void setWeek5(String week5) {
        Week5 = week5;
    }

    public String getWeek6() {
        return Week6;
    }

    public void setWeek6(String week6) {
        Week6 = week6;
    }

    public String getWeek7() {
        return Week7;
    }

    public void setWeek7(String week7) {
        Week7 = week7;
    }

    public String getHour0() {
        return Hour0;
    }

    public void setHour0(String hour0) {
        Hour0 = hour0;
    }

    public String getHour1() {
        return Hour1;
    }

    public void setHour1(String hour1) {
        Hour1 = hour1;
    }

    public String getHour2() {
        return Hour2;
    }

    public void setHour2(String hour2) {
        Hour2 = hour2;
    }

    public String getHour3() {
        return Hour3;
    }

    public void setHour3(String hour3) {
        Hour3 = hour3;
    }

    public String getHour4() {
        return Hour4;
    }

    public void setHour4(String hour4) {
        Hour4 = hour4;
    }

    public String getHour5() {
        return Hour5;
    }

    public void setHour5(String hour5) {
        Hour5 = hour5;
    }

    public String getHour6() {
        return Hour6;
    }

    public void setHour6(String hour6) {
        Hour6 = hour6;
    }

    public String getHour7() {
        return Hour7;
    }

    public void setHour7(String hour7) {
        Hour7 = hour7;
    }

    public String getHour8() {
        return Hour8;
    }

    public void setHour8(String hour8) {
        Hour8 = hour8;
    }

    public String getHour9() {
        return Hour9;
    }

    public void setHour9(String hour9) {
        Hour9 = hour9;
    }

    public String getHour10() {
        return Hour10;
    }

    public void setHour10(String hour10) {
        Hour10 = hour10;
    }

    public String getHour11() {
        return Hour11;
    }

    public void setHour11(String hour11) {
        Hour11 = hour11;
    }

    public String getHour12() {
        return Hour12;
    }

    public void setHour12(String hour12) {
        Hour12 = hour12;
    }

    public String getHour13() {
        return Hour13;
    }

    public void setHour13(String hour13) {
        Hour13 = hour13;
    }

    public String getHour14() {
        return Hour14;
    }

    public void setHour14(String hour14) {
        Hour14 = hour14;
    }

    public String getHour15() {
        return Hour15;
    }

    public void setHour15(String hour15) {
        Hour15 = hour15;
    }

    public String getHour16() {
        return Hour16;
    }

    public void setHour16(String hour16) {
        Hour16 = hour16;
    }

    public String getHour17() {
        return Hour17;
    }

    public void setHour17(String hour17) {
        Hour17 = hour17;
    }

    public String getHour18() {
        return Hour18;
    }

    public void setHour18(String hour18) {
        Hour18 = hour18;
    }

    public String getHour19() {
        return Hour19;
    }

    public void setHour19(String hour19) {
        Hour19 = hour19;
    }

    public String getHour20() {
        return Hour20;
    }

    public void setHour20(String hour20) {
        Hour20 = hour20;
    }

    public String getHour21() {
        return Hour21;
    }

    public void setHour21(String hour21) {
        Hour21 = hour21;
    }

    public String getHour22() {
        return Hour22;
    }

    public void setHour22(String hour22) {
        Hour22 = hour22;
    }

    public String getHour23() {
        return Hour23;
    }

    public void setHour23(String hour23) {
        Hour23 = hour23;
    }

    public String getfVipValue() {
        return fVipValue;
    }

    public void setfVipValue(String fVipValue) {
        this.fVipValue = fVipValue;
    }

    public String getbLimitQty() {
        return bLimitQty;
    }

    public void setbLimitQty(String bLimitQty) {
        this.bLimitQty = bLimitQty;
    }

    public String getfLimitQty() {
        return fLimitQty;
    }

    public void setfLimitQty(String fLimitQty) {
        this.fLimitQty = fLimitQty;
    }

    public String getbJiOu() {
        return bJiOu;
    }

    public void setbJiOu(String bJiOu) {
        this.bJiOu = bJiOu;
    }

    public String getfQty_Ji() {
        return fQty_Ji;
    }

    public void setfQty_Ji(String fQty_Ji) {
        this.fQty_Ji = fQty_Ji;
    }

    public String getfPrice_ji() {
        return fPrice_ji;
    }

    public void setfPrice_ji(String fPrice_ji) {
        this.fPrice_ji = fPrice_ji;
    }

    public String getfQty_Ou() {
        return fQty_Ou;
    }

    public void setfQty_Ou(String fQty_Ou) {
        this.fQty_Ou = fQty_Ou;
    }

    public String getfPrice_Ou() {
        return fPrice_Ou;
    }

    public void setfPrice_Ou(String fPrice_Ou) {
        this.fPrice_Ou = fPrice_Ou;
    }

    public String getbSuspend() {
        return bSuspend;
    }

    public void setbSuspend(String bSuspend) {
        this.bSuspend = bSuspend;
    }

    @Override
    public String toString() {
        return "t_PloyOfSale{" +
                "cStoreNo='" + cStoreNo + '\'' +
                ", cPloyNo='" + cPloyNo + '\'' +
                ", fPrice_SO='" + fPrice_SO + '\'' +
                ", dDateStart='" + dDateStart + '\'' +
                ", dDateEnd='" + dDateEnd + '\'' +
                ", bEnabled='" + bEnabled + '\'' +
                ", Week1='" + Week1 + '\'' +
                ", Week2='" + Week2 + '\'' +
                ", Week3='" + Week3 + '\'' +
                ", Week4='" + Week4 + '\'' +
                ", Week5='" + Week5 + '\'' +
                ", Week6='" + Week6 + '\'' +
                ", Week7='" + Week7 + '\'' +
                ", Hour0='" + Hour0 + '\'' +
                ", Hour1='" + Hour1 + '\'' +
                ", Hour2='" + Hour2 + '\'' +
                ", Hour3='" + Hour3 + '\'' +
                ", Hour4='" + Hour4 + '\'' +
                ", Hour5='" + Hour5 + '\'' +
                ", Hour6='" + Hour6 + '\'' +
                ", Hour7='" + Hour7 + '\'' +
                ", Hour8='" + Hour8 + '\'' +
                ", Hour9='" + Hour9 + '\'' +
                ", Hour10='" + Hour10 + '\'' +
                ", Hour11='" + Hour11 + '\'' +
                ", Hour12='" + Hour12 + '\'' +
                ", Hour13='" + Hour13 + '\'' +
                ", Hour14='" + Hour14 + '\'' +
                ", Hour15='" + Hour15 + '\'' +
                ", Hour16='" + Hour16 + '\'' +
                ", Hour17='" + Hour17 + '\'' +
                ", Hour18='" + Hour18 + '\'' +
                ", Hour19='" + Hour19 + '\'' +
                ", Hour20='" + Hour20 + '\'' +
                ", Hour21='" + Hour21 + '\'' +
                ", Hour22='" + Hour22 + '\'' +
                ", Hour23='" + Hour23 + '\'' +
                ", fVipValue='" + fVipValue + '\'' +
                ", bLimitQty='" + bLimitQty + '\'' +
                ", fLimitQty='" + fLimitQty + '\'' +
                ", bJiOu='" + bJiOu + '\'' +
                ", fQty_Ji='" + fQty_Ji + '\'' +
                ", fPrice_ji='" + fPrice_ji + '\'' +
                ", fQty_Ou='" + fQty_Ou + '\'' +
                ", fPrice_Ou='" + fPrice_Ou + '\'' +
                ", bSuspend='" + bSuspend + '\'' +
                '}';
    }
}
