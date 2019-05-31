package com.hlyf.domin.mingsheng;

/**
 * Created by Administrator on 2018-11-28.
 */
public class t_MingShengPublicSaleDetail_z {

    private String orderSn;
    private String createDate;
    private String offlineStoresId;
    private String productSn;
    private String fullName;
    private String name;
    private Double price;
    private Double quantity;
    private String outId;
    private String barCode;
    private Double weight;

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

    public String getOfflineStoresId() {
        return offlineStoresId;
    }

    public void setOfflineStoresId(String offlineStoresId) {
        this.offlineStoresId = offlineStoresId;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "t_MingShengPublicSaleDetail_z{" +
                "orderSn='" + orderSn + '\'' +
                ", createDate='" + createDate + '\'' +
                ", offlineStoresId='" + offlineStoresId + '\'' +
                ", productSn='" + productSn + '\'' +
                ", fullName='" + fullName + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", outId='" + outId + '\'' +
                ", barCode='" + barCode + '\'' +
                ", weight=" + weight +
                '}';
    }
}
