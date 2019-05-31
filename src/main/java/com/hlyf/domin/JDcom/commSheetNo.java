package com.hlyf.domin.JDcom;

/**
 * Created by Administrator on 2019-05-06.
 * 获取单号的公共类  所用获取单号都可以映射到该类
 */
public class commSheetNo {
    private String cSheetNo;

    public String getcSheetNo() {
        return cSheetNo;
    }

    public void setcSheetNo(String cSheetNo) {
        this.cSheetNo = cSheetNo;
    }

    @Override
    public String toString() {
        return "commSheetNo{" +
                "cSheetNo='" + cSheetNo + '\'' +
                '}';
    }
}
