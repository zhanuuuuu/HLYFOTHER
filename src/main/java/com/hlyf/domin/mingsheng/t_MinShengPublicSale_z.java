package com.hlyf.domin.mingsheng;

import net.sf.json.JSONArray;

import java.util.List;

/**
 * Created by Administrator on 2018-11-28.
 */
public class t_MinShengPublicSale_z {
    private String orderSn;
    private String createDate;
    private String beginTime;
    private String endTime;
    private Double totalPrice;
    private String paymentMethodName;
    private String offlineStoresId;
    private List<t_MingShengPublicSaleDetail_z> orderItems;

    public List getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getOfflineStoresId() {
        return offlineStoresId;
    }

    public void setOfflineStoresId(String offlineStoresId) {
        this.offlineStoresId = offlineStoresId;
    }

    @Override
    public String toString() {
        return "t_MinShengPublicSale_z{" +
                "orderSn='" + orderSn + '\'' +
                ", createDate='" + createDate + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", offlineStoresId='" + offlineStoresId + '\'' +
                '}';
    }
}
