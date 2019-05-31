package com.hlyf.kafka.message;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019-02-21.
 */
public class OrderBasic implements Serializable {

        /**
         * 订单ID
         */
        private String orderId;
        /**
         * 订单编号
         */
        private String orderNumber;
        /**
         * 订单日期
         */
        private Date date;
        /**
         * 订单信息
         */
        private List<String> desc;

    public OrderBasic(String orderId, String orderNumber, Date date, List<String> desc) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.date = date;
        this.desc = desc;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderBasic{" +
                "orderId='" + orderId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", date=" + date +
                ", desc=" + desc +
                '}';
    }
}
