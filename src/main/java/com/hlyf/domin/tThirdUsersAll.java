package com.hlyf.domin;


public class tThirdUsersAll {
    private String appId;
    private String appSercet;
    private String userName;
    private String createTime;
    private String beizhu;
    private Integer totalNumber;
    private Integer leftNumber;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSercet() {
        return appSercet;
    }

    public void setAppSercet(String appSercet) {
        this.appSercet = appSercet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(Integer leftNumber) {
        this.leftNumber = leftNumber;
    }

    @Override
    public String toString() {
        return "tThirdUsersAll{" +
                "appId='" + appId + '\'' +
                ", appSercet='" + appSercet + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", totalNumber=" + totalNumber +
                ", leftNumber=" + leftNumber +
                '}';
    }
}
