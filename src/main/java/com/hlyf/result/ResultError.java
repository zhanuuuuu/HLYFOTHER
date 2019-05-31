package com.hlyf.result;

/**
 * Created by Administrator on 2018-03-22.
 * //这里是所用返回信息的封装
 */
public enum  ResultError {

    SUCCESS("1001","正确"),
    SUCCESS_NULL("1002","数据集为空"),
    ERROR_UNKNOWN("1003","系统繁忙，请稍后再试...."),
    ERROR_SECRET_KEY("9997","秘钥错误,请联系管理员生成正确的秘钥"),
    MACHINE_SECRET_KEY("9996","机器秘钥错误,请联系管理员生成正确的秘钥"),
    MACHINE_REGIST("9995","该门店未找到该设备信息,请联系管理员注册"),
    SHEETNO_ERROR("9994","单号生成出错,请联系管理员检查设备是否正常运行"),
    USER_APPSTOR("1004","当前账户已被禁止使用使用，请联系管理员");

    private String value;
    private String desc;

    private ResultError(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}
