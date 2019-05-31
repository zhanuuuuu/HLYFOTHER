package com.hlyf.test;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-11-19.
 */
public class test {

    public static void main(String[] args) {
        //TEST1();
//        String str="{" +
//                "\"orderData\": {" +
//                "\"storecode\": \"0012\"," +
//                "\"cardno\": \"12\"," +
//                "\t\t\"orderid\": \"123\"," +
//                "\t\t\"goodsamount\": \"0.40\"," +
//                "\t\t\"goodssum\": 3,\n" +
//                "\t\t\"goodspayamount\": \"0.40\"," +
//                "\t\t\"discount\": \"0.00\"," +
//                "\t\t\"goodsdatas\": [{\n" +
//                "\t\t\t\"GoodsSpec\": \"1*1\"," +
//                "\t\t\t\"IsWholePack\": 0,\n" +
//                "\t\t\t\"Img\": \"\",\n" +
//                "\t\t\t\"BarCode\": \"2231470000032\",\n" +
//                "\t\t\t\"Num\": 1,\n" +
//                "\t\t\t\"GoodsCode\": \"3147000003\",\n" +
//                "\t\t\t\"YouHuiAmount\": \"\",\n" +
//                "\t\t\t\"SalePrice\": 0.2,\n" +
//                "\t\t\t\"PackRate\": 1,\n" +
//                "\t\t\t\"GoodsName\": \"大号购物袋\",\n" +
//                "\t\t\t\"Unit\": \"\",\n" +
//                "\t\t\t\"goodsamount\": 0.2,\n" +
//                "\t\t\t\"OldSalePrice\": \"\"\n" +
//                "\t\t}]\n" +
//                "\t},\n" +
//                "\t\"payData\": {\n" +
//                "\t\t\"success\": true,\n" +
//                "\t\t\"obj\": {\n" +
//                "\t\t\t\"alipay_trade_pay_response\": {\n" +
//                "\t\t\t\t\"code\": \"10000\",\n" +
//                "\t\t\t\t\"msg\": \"Success\",\n" +
//                "\t\t\t\t\"buyer_logon_id\": \"136***@163.com\",\n" +
//                "\t\t\t\t\"buyer_pay_amount\": \"0.40\",\n" +
//                "\t\t\t\t\"buyer_user_id\": \"2088002429412534\",\n" +
//                "\t\t\t\t\"fund_bill_list\": [{\n" +
//                "\t\t\t\t\t\"amount\": \"0.40\",\n" +
//                "\t\t\t\t\t\"fund_channel\": \"PCREDIT\"\n" +
//                "\t\t\t\t}],\n" +
//                "\t\t\t\t\"gmt_payment\": \"2018-05-12 15:30:19\",\n" +
//                "\t\t\t\t\"invoice_amount\": \"0.40\",\n" +
//                "\t\t\t\t\"out_trade_no\": \"3791100034995204502141394944\",\n" +
//                "\t\t\t\t\"point_amount\": \"0.00\",\n" +
//                "\t\t\t\t\"receipt_amount\": \"0.40\",\n" +
//                "\t\t\t\t\"store_name\": \"精品超市(长申店)\",\n" +
//                "\t\t\t\t\"total_amount\": \"0.40\",\n" +
//                "\t\t\t\t\"trade_no\": \"2018051221001004530531164774\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t\"msg\": \"支付成功\"\n" +
//                "\t\t}\n" +
//                "\t}\n" +
//                "}";
//        str=str.replaceAll("\\s*","").replaceAll("\\n","").trim();
//
//        System.out.println(str);
//        JSONObject jsonObject=JSONObject.fromObject(str);
//        System.out.println();

    }

    public static void TEST1() {
        List<String> list=new ArrayList<String>();
        list.add("\"张望\"");
        list.add("\"李四\"");
        list.add("\"小王\"");
        list.add("\"123321\"");
        System.out.println(list.toString());

        List<String> list1=new ArrayList<String>();
        list1.add(list.toString());
        list1.add(list.toString());
        list1.add(list.toString());
        System.out.println(list1.toString());

        String string="{" +
                "\"table\": \"item_info\"," +
                "\"columns\": [\"brand_no\", \"id\", \"item_clsno\", \"item_name\", \"item_no\", \"item_size\", \"item_subno\", \"oper_date\", \"price\", \"sale_price\", \"supplier_no\", \"unit_no\", \"vip_price\"]," +
                "\"rows\": [" +
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +
                "]" +
                "}";
    }


}
