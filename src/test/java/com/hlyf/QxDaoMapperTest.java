package com.hlyf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hlyf.dao.cluster.QxDao;
import com.hlyf.dao.cluster.mingSheng;
import com.hlyf.domin.Qx.SaleSheetHelp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.hlyf.tool.Md5.MD5Encode;

/**
 * Created by Administrator on 2019-09-09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QxDaoMapperTest {

    private static final Logger log = LoggerFactory.getLogger(QxDaoMapperTest.class);

    private  String jsonString="{\n" +
            "\t\"dataDetail\": [{\n" +
            "\t\t\t\"bAuditing\": false,\n" +
            "\t\t\t\"cGoodsNo\": \"10001010\",\n" +
            "\t\t\t\"cOperatorName\": \"无名氏\",\n" +
            "\t\t\t\"cOperatorno\": \"01\",\n" +
            "\t\t\t\"cSaleSheetno\": \"0001\",\n" +
            "\t\t\t\"cSaleTime\": \"00:00:00\",\n" +
            "\t\t\t\"cSheetNo\": \"\",\n" +
            "\t\t\t\"cStoreNo\": \"000\",\n" +
            "\t\t\t\"cVipNo\": \"\",\n" +
            "\t\t\t\"cWHno\": \"000\",\n" +
            "\t\t\t\"dSaleDate\": \"2019-09-09 00:00:00.0\",\n" +
            "\t\t\t\"fLastSettle\": 1,\n" +
            "\t\t\t\"fPrice\": 1,\n" +
            "\t\t\t\"fQuantity\": 1,\n" +
            "\t\t\t\"fVipPrice\": 1,\n" +
            "\t\t\t\"fVipScore\": 1,\n" +
            "\t\t\t\"iSeed\": 7\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"bAuditing\": true,\n" +
            "\t\t\t\"cGoodsNo\": \"10001010\",\n" +
            "\t\t\t\"cOperatorName\": \"无名氏\",\n" +
            "\t\t\t\"cOperatorno\": \"01\",\n" +
            "\t\t\t\"cSaleSheetno\": \"0001\",\n" +
            "\t\t\t\"cSaleTime\": \"00:00:00\",\n" +
            "\t\t\t\"cSheetNo\": \"\",\n" +
            "\t\t\t\"cStoreNo\": \"000\",\n" +
            "\t\t\t\"cVipNo\": \"\",\n" +
            "\t\t\t\"cWHno\": \"000\",\n" +
            "\t\t\t\"dSaleDate\": \"2019-09-09 00:00:00.0\",\n" +
            "\t\t\t\"fLastSettle\": 1,\n" +
            "\t\t\t\"fPrice\": 1,\n" +
            "\t\t\t\"fQuantity\": 1,\n" +
            "\t\t\t\"fVipPrice\": 1,\n" +
            "\t\t\t\"fVipScore\": 1,\n" +
            "\t\t\t\"iSeed\": 6\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    private static final String appkey="warelucent";

    @Autowired
    private QxDao qxDaoMapper;

    //保存单号
    @Test
    public void testInsertList(){
        JSONObject jsonObject= JSON.parseObject(jsonString);
        JSONArray jsonArray=jsonObject.getJSONArray("dataDetail");
        log.info("我是jsonArray {}" ,jsonArray.toJSONString());
        List<SaleSheetHelp> saleSheetHelps=JSONObject.parseArray(jsonArray.toJSONString(), SaleSheetHelp.class);
        for(SaleSheetHelp t:saleSheetHelps){
            log.info("我是 SaleSheetHelp {}" ,JSON.toJSONString(t));
        }
        int i=qxDaoMapper.insertList(saleSheetHelps);
        log.info("我是影响行数 {} ",i);


    }

    //得到加密的key  这里是单独的签名
    @Test
    public void getKey(){

        System.out.println(MD5Encode(appkey+"2019091011070800","UTF-8"));

        log.info("我是数据 {} ",MD5Encode(appkey+"2019091011070800","UTF-8").toUpperCase());

    }


    //保存单号
    @Test
    public void testInsert(){
//        SaleSheetHelp(String dSaleDate, String cSaleSheetno, Integer iSeed, String cStoreNo,
//                String cGoodsNo, String cOperatorno,
//                String cOperatorName, Boolean bAuditing, Double fVipScore,
//                Double fPrice, Double fVipPrice,
//                Double fQuantity, Double fLastSettle, String cSaleTime, String cVipNo, String cWHno, String cSheetNo)

        SaleSheetHelp saleSheetHelp=new   SaleSheetHelp("2019-09-09", "0001", 5,"000",
                "10001010", "01",
                "无名氏", false, 1.0,
                1.0, 1.0,
                1.0, 1.0, "00:00:00",null, "000", null);

        int i=qxDaoMapper.insert(saleSheetHelp);
        log.info("我是影响行数 {} ",i);


    }
    @Test
    public void testSelect(){

        List<SaleSheetHelp> saleSheetHelps=qxDaoMapper.selectAll();
        if(saleSheetHelps!=null && saleSheetHelps.size()>0){
            log.info("我是获取到的结果集 {} ",JSONObject.toJSONString(saleSheetHelps,
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullStringAsEmpty)
            );
        }
    }

    @Test
    public void testJson(){

        log.info("我是 jsonString {}" ,jsonString);
        JSONObject jsonObject= JSON.parseObject(jsonString);
        JSONArray jsonArray=jsonObject.getJSONArray("dataDetail");
        log.info("我是jsonArray {}" ,jsonArray.toJSONString());
        List<SaleSheetHelp> saleSheetHelps=JSONObject.parseArray(jsonArray.toJSONString(), SaleSheetHelp.class);
        for(SaleSheetHelp t:saleSheetHelps){
            log.info("我是 SaleSheetHelp {}" ,JSON.toJSONString(t));
        }
    }

    @Test
    public void testDelete(){
        int i=qxDaoMapper.deleteSaleSheetHelp("0001");
        log.info("我是影响行数 {} ",i);

    }

    @Test
    public void testSelectSaleSheetHelp(){
        SaleSheetHelp saleSheetHelp=qxDaoMapper.selectByPrimaryKey("0001");
        log.info("我是获取到的数据集 {} ",JSON.toJSONString(saleSheetHelp));

    }

}
