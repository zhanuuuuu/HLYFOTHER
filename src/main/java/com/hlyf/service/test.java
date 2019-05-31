package com.hlyf.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018-11-01.
 */
public class test {
    public static void main(String[] args) {
        //testRegex();

        String str="[\n" +
                "  {\"cardno\":\"\",\"storecode\":\" 0011 \",\"goodscode\":\"\",\"barcode\":\"123456\",\"num\":1,\"price\":1,\"oprice\":1},\n" +
                "  {\"cardno\":\"\",\"storecode\":\" 0012 \",\"goodscode\":\"\",\"barcode\":\"223456\",\"num\":1,\"price\":1,\"oprice\":1},\n" +
                "  {\"cardno\":\"\",\"storecode\":\" 0013 \",\"goodscode\":\"\",\"barcode\":\"323456\",\"num\":1,\"price\":1,\"oprice\":1}\n" +
                "]";

        try{
            JSONArray array=JSONArray.fromObject(str);
            System.out.println(array.size());
            for(int i=0;i<array.size()-1;i++){
                System.out.println(array.getJSONObject(i));
                System.out.println(array.getJSONObject(i).getString("barcode"));
            }
            System.out.println(Arrays.asList(array).toString());
        }catch (Exception e){
            System.out.println("json解析出错"+e);
            //System.out.println("json解析出错");
            //logger.error("json解析出错");
        }
    }
    public static void testRegex() {
        String barcodeS="6928804011258|6928804011159|6925788301269|6925788301269";//"6928804011258|6928804011159"
        List<String> stringList=new ArrayList<String>();
        if(barcodeS.contains("|")){
            String[] arr = barcodeS.split("\\|"); // 用,分割
            System.out.println(Arrays.toString(arr));
            for(String barcode:arr){
                stringList.add(barcode);
            }
            System.out.println("我包含|"+stringList.toString());
        }else{
            stringList.add(barcodeS);
            System.out.println("我不包含|"+stringList.toString());
        }
    }
}
