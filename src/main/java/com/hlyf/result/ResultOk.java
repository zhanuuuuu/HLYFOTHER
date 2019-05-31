package com.hlyf.result;

/**
 * @author 张
 *
 */
public enum ResultOk {
    SUCCESS("1001","正确"),
    SUCCESS_NULL("1002","数据集为空"),
    ERROR_UNKNOWN("1003","系统繁忙，请稍后再试...."),
    DATA_PASR_ERROR("1004","数据格式不正确"),
    USER_NOT_EXIT("1005","该用户不存在"),
    ERROR_SECRET("1006","签名失败"),
    ORDERNO_EXIST("1007","单号重复"),
    ORDERNO_ORIGINAL_EXIST("1008","退货原始单号不存在"),
    TOKEN_LEFT_ISNOT("1009","获取token次数已经用完"),
    ORDERNO_ERROR("1010","单号有误"),
    STORENO_OR_WHNO_ERROR("1011","门店编号或仓库编号有误"),
    RETURN_ORDERNO_ERROR("1012","退货单号有误"),
    ORDERNO_ISNOT_KONW("1013","该单号无效"),
    Tocken_FAIL("9998","token无效");
    private String value;
    private String desc;

    private ResultOk(String value, String desc) {
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
