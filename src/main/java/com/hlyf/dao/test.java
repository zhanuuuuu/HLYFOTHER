package com.hlyf.dao;

/**
 * Created by Administrator on 2018-11-01.
 */
public class test {
    public static void main(String[] args){
        String jsonStr="{\n" +
                "\t\"sale\": [{\n" +
                "\t\t\"actualMoney\": \"0\",\n" +
                "\t\t\"appId\": \"appId\",\n" +
                "\t\t\"appSheetNo\": \"917925513001121\",\n" +
                "\t\t\"cStoreNo\": \"005\",\n" +
                "\t\t\"cWHno\": \"005\",\n" +
                "\t\t\"discountMoney\": \"0\",\n" +
                "\t\t\"saleAllMoney\": \"0\",\n" +
                "\t\t\"saleTime\": \"2019-07-27 11:18:32\",\n" +
                "\t\t\"saleType\": \"4\",\n" +
                "\t\t\"saleTypeName\": \"京东支付\"\n" +
                "\t}],\n" +
                "\t\"saleDatial\": [{\n" +
                "\t\t\"appId\": \"abc\",\n" +
                "\t\t\"bAuditing\": \"0\",\n" +
                "\t\t\"bWeight\": \"0\",\n" +
                "\t\t\"cBarcode\": \"6948229400123\",\n" +
                "\t\t\"cGoodsName\": \"格伦比琪雪糕80g*2支\",\n" +
                "\t\t\"cGoodsNo\": \"300980\",\n" +
                "\t\t\"cStoreNo\": \"005\",\n" +
                "\t\t\"cWHno\": \"005\",\n" +
                "\t\t\"discountMoney\": \"3.00\",\n" +
                "\t\t\"discountPrice\": \"3.00\",\n" +
                "\t\t\"fNormalPrice\": \"3.0\",\n" +
                "\t\t\"fQuantity\": \"10\"\n" +
                "\t}]\n" +
                "}";
        jsonStr=jsonStr.replaceAll("\\t","").replaceAll("\\n","");
        System.out.println(jsonStr);
    }
}
