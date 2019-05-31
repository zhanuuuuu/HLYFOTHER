package com.hlyf.scheduled;



import com.hlyf.dao.cluster.mingSheng;
import com.hlyf.dao.cluster.sCanDao;
import com.hlyf.domin.mingsheng.t_MinShengPublicSale_z;
import com.hlyf.domin.mingsheng.t_MingShengPublicSaleDetail_z;

import com.hlyf.domin.mingsheng.t_MingShengTime;
import com.hlyf.minsheng.RopUtils;
import com.hlyf.service.sCanService;
import com.hlyf.tool.String_Tool;
import com.hlyf.tool.listBean;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hlyf.tool.HttpTookitt.sendPost;

/**
 * Created by Administrator on 2018-11-19.
 * minsheng:
 appid: 462
 method: minshengec.supplier.order.get
 supplierid: 2247
 appscret: 438F6241DD9511E4B9E70050568E0E81
 */
@Component
@Configurable
@Configuration
public class MyStaticTask {

    private static final Logger logger = LoggerFactory.getLogger(MyStaticTask.class);

    @Autowired
    private sCanService CanService;

    @Autowired
    private mingSheng mingSheng;

    @Autowired
    private sCanDao SCanDao;

    @Value("${minsheng.appid}")
    private  String appid;
    @Value("${minsheng.appscret}")
    private  String appscret;
    @Value("${minsheng.supplierid}")
    private  String supplierid;

    @Value("${minsheng.url}")
    private  String url;
    @Value("${minsheng.isopen}")
    private Boolean isopen;

    @Value("${minsheng.cxisopen}")
    private Boolean cxisopen;
    @Value("${minsheng.amount}")
    private String amount;
    /**
     * 固定cron配置定时任务
     */
    @Scheduled(cron = "0/20 * * * * ?")
    public void doTask(){
        System.out.println("执行了MyStaticTask,时间为:"+new Date(System.currentTimeMillis()));
    }
    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date ()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron(){
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }
    //十分钟执行一次
    @Scheduled(cron = "0 */10 *  * * * ")
    public void cxGetData(){
//         获取开始的时间
//        t_MingShengTime t_mingShengTime=mingSheng.get_t_MingShengTime("");
//        logger.error("我是定时器 0 */5 *  * * *  测试的  的 哈哈  "
//        +(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"  "+t_mingShengTime.toString());
        String endTime=String_Tool.DataBaseTime();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day1 = c.get(Calendar.DATE);
        int HOUR=c.get(Calendar.HOUR);
        c.set(Calendar.DATE, day1 - 1);
        c.set(Calendar.HOUR, HOUR - 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        System.out.println(dayAfter);
        logger.info(" 时间       "+dayAfter);

        if(cxisopen){
            //在线程中执行
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CanService.upload_MingShengOrder_byPage(dayAfter,
                            endTime,"1",amount,
                            appid,appscret,
                            supplierid,url);
                }
            }).start();
        }
    }
    //每天凌晨执行
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void updateLeftNumerUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SCanDao.update_leftNumber();
                    logger.info("修改访问次数成功");
                }catch (Exception e){
                    logger.error("修改访问次数失败");
                }
            }
        }).start();
    }
    //每两个小时执行一次 @Scheduled(cron = "0 0 2/2 * * ? ") 服务启动后两个小时后开始执行定时器  这个cron可以在线生成   也可自行研究
    //这里只是测试用里 0 0 3 1/1 * ?
    //0 0 11 * * ? *
    @Scheduled(cron = "0 0 3 * * ? ")//每天的凌晨3点执行  0 0 3 * * ?  前面没有空格   后面有空格
    public void uploadMingShengOrder(){

        //获取开始的时间
        t_MingShengTime t_mingShengTime=mingSheng.get_t_MingShengTime("");
        logger.error("我是定时器  0 0 3 * * ?  的 哈哈  "
                     +(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"  "+t_mingShengTime.toString());
        String endTime=String_Tool.DataBaseTime();
        if(isopen){
            //在线程中执行
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CanService.upload_MingShengOrder_byPage(t_mingShengTime.getEndTime(),
                            endTime,"1",amount,
                            appid,appscret,
                            supplierid,url);
                }
            }).start();
        }


    }


    private SimpleDateFormat dateFormat(){

        String aa="{\"secret\":15423504379395}";
        return new SimpleDateFormat ("HH:mm:ss");

    }

    //测试数据不用理会
    public void uploadMingShengOrderTrue(String btn,String etn,String pageNum,
                                          String pagenumber,String appid,
                                          String miyao,String supplierid,String url){
        Map<String, String> form = new HashMap<String, String>();
        form.put("appKey", appid);              //appId  "462"
        form.put("v", "1.0");
        form.put("method", "minshengec.supplier.order.get");
        form.put("format", "json");
        form.put("supplierId", supplierid);        //供应商id
        form.put("timestamp", String_Tool.DataBaseTime());
        form.put("orderStatus", "2");          //已完成的订单
        form.put("receiveType", "1");
        form.put("pageIndex", pageNum);
        form.put("pageSize", pagenumber);
        form.put("type", "0");
        form.put("beginDate", btn);//"2018-01-01 12:22:33"
        form.put("endDate", etn);
        String sign = RopUtils.sign(form, miyao);//"438F6241DD9511E4B9E70050568E0E81"
        form.put("sign", sign);

        String result =sendPost(url, form);//"http://111.198.131.250:19600/router"
        System.out.println(result);

        JSONObject json=new JSONObject(result);
        System.out.println(json.getInt("totalResults"));
//        System.out.println(json.get("orderInfos"));
        System.out.println("判断   "+ json.get("orderInfos").toString());
        if(json.get("orderInfos").toString().equals("null")){
            System.out.println("没有值");
            return;
        }
        JSONArray jsonArray=JSONArray.fromObject(json.get("orderInfos").toString());
//        System.out.println("是我"+jsonArray);
//        System.out.println("是我2"+jsonArray.get(0));

        List<t_MinShengPublicSale_z> t_MinShengPublicSale_z_list=JSONArray.toList(jsonArray,new t_MinShengPublicSale_z(),new JsonConfig());
        for(t_MinShengPublicSale_z t:t_MinShengPublicSale_z_list){
            if(t.getOfflineStoresId()==null){
                t.setOfflineStoresId("");
            }
            t.setBeginTime(btn);
            t.setEndTime(etn);
        }
        String str= listBean.getBeanJson(t_MinShengPublicSale_z_list);
        System.out.println(" 我是 t_MinShengPublicSale_z_list"+str);
        List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist=new ArrayList<t_MingShengPublicSaleDetail_z>();

        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=new JSONObject(jsonArray.get(i).toString());
            String setOfflineStoresId="";
            String OrderSn=jsonObject.getString("orderSn");
            if(jsonObject.has("offlineStoresId")){
                setOfflineStoresId=jsonObject.getInt("offlineStoresId")+"";
            }
           // System.out.println(" 我是 totalPrice"+jsonObject.getDouble("totalPrice"));
            JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("orderItems").toString());

            List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist_item=
                    JSONArray.toList(jsonArray2,new t_MingShengPublicSaleDetail_z(),new JsonConfig());;
            for(t_MingShengPublicSaleDetail_z t:t_MingShengPublicSaleDetail_zlist_item){
                t.setOfflineStoresId(setOfflineStoresId);
                t.setOrderSn(OrderSn);
            }
            t_MingShengPublicSaleDetail_zlist.addAll(t_MingShengPublicSaleDetail_zlist_item);
//            for(int j=0;j<jsonArray2.size();j++){
//                JSONObject jsonObject1=new JSONObject(jsonArray2.get(j).toString());
//                System.out.println(" 我是 price"+jsonObject1.getDouble("price"));
//            }

        }
        str= listBean.getBeanJson(t_MingShengPublicSaleDetail_zlist);

        System.out.println("我是orderItems中的打印"+str);
    }

    public static void main(String[] args) {
        MyStaticTask myStaticTask=new MyStaticTask();
        myStaticTask.uploadMingShengOrderTrue("2018-10-31 10:23:20.0",
                                 "2018-11-31 10:23:20.0","120","50",
                                 "462","438F6241DD9511E4B9E70050568E0E81",
                               "2247","http://111.198.131.250:19600/router");
    }
}
