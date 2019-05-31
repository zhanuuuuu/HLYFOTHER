package com.hlyf.controller;

import com.google.gson.GsonBuilder;
import com.hlyf.config.redisCon;
import com.hlyf.domin.*;
import com.hlyf.domin.JDcom.*;
import com.hlyf.kafka.message.OrderBasic;
import com.hlyf.result.ResultError;
import com.hlyf.result.ResultOk;
import com.hlyf.result.resultMsg;
import com.hlyf.service.sCanService;
import com.hlyf.service.userService;
import com.hlyf.tool.JWTUtil;
import com.hlyf.tool.ThreeDESUtil;
import com.hlyf.tool.listBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.hlyf.tool.Md5.MD5Encode;
import static com.hlyf.tool.Md5.loginsignIsOk;

/**
 * Created by Administrator on 2018-11-13.
 */
@RestController
public class ScanConntroller {
    private static final Logger logger = LoggerFactory.getLogger(ScanConntroller.class);
    private static final String appKey="warelucent";
    @Autowired
    private sCanService CanService;

    @Autowired
    private userService UserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private redisCon redison;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @RequestMapping(value = "/api/getKafka", method = RequestMethod.POST)
//    @ResponseBody
//    public  String getKafka(){
//        List<String> list=new ArrayList<>();
//        list.add("你是哪位");
//        list.add("是我啦跌");
//        OrderBasic orderBasic=new OrderBasic("111111111111111111","张敏杨",new Date(),list);
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        String message = builder.create().toJson(orderBasic);
//        kafkaTemplate.send("topic-order", message);
//        logger.info("\n生产消息至Kafka\n" + message);
////
////        KafkaProducer kafkaProducer=new KafkaProducer();
////        kafkaProducer.sendOrderMessage(orderBasic);
//        return orderBasic.toString();
//    }
    /**
     *
     * @param appId 用户id
     * @param sign  加密以后的签名
     * @return    返回用户信息和token
     */
    @RequestMapping(value = "/api/getToken", method = RequestMethod.POST)
    @ResponseBody
    public  String getToken(@RequestParam(value = "appId") String appId,
                            @RequestParam(value = "sign") String sign){

        try{
            List<tThirdUsers> list=UserService.get_tThirdUsersS(appId);

            String result="";
            if(list!=null && !list.isEmpty()){
                //
                tThirdUsersAll tThirdUsersOne=CanService.get_tThirdUsersAllS(appId);
                try{
                    if(tThirdUsersOne!=null){
                        //判断次数
                        if(tThirdUsersOne.getLeftNumber()<=0){
                            return new resultMsg(true,"[]",ResultOk.TOKEN_LEFT_ISNOT.getValue(),ResultOk.TOKEN_LEFT_ISNOT.getDesc()).toString();
                        }
                    }else {
                        return new resultMsg(true,"[]",ResultOk.USER_NOT_EXIT.getValue(),ResultOk.USER_NOT_EXIT.getDesc()).toString();
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                    return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
                }
                tThirdUsers user=list.get(0);
                try{
                    //验证秘钥是否是正确的
                    if(!user.getAppSercet().equals(MD5Encode(appKey+user.getAppId(),"UTF-8"))){
                        return new resultMsg(false, "[]", ResultError.ERROR_SECRET_KEY.getValue(),  ResultError.ERROR_SECRET_KEY.getDesc()).toString();
                    }
                    /**
                     * @说明
                     */
//                    user.setAppSercet(ThreeDESUtil.
//                            encrypt((user.getAppId()+user.getAppSercet()).trim(),
//                                    "123456788765432112345678",  //固定值   不要改变
//                                    "01234567")                       //固定值    不要改变
//                                    .replaceAll("=","")
//                                    .replaceAll("\\+","")
//                                    .replaceAll("\\s*","")
//                                    .replaceAll("\\n","")
//                                    .trim());
                    //logger.info("签名 user.setAppSercet "+user.getAppSercet());

                    //验证签名
                    if(!loginsignIsOk(user.getAppSercet(),appId,sign)){
                        return new resultMsg(false, "[]", ResultOk.ERROR_SECRET.getValue(), ResultOk.ERROR_SECRET.getDesc()).toString();
                    }
                }catch (Exception e){
                    logger.error("签名出错",e.getMessage());
                    return new resultMsg(false, "[]", ResultOk.ERROR_SECRET.getValue(), ResultOk.ERROR_SECRET.getDesc()).toString();
                }

                try{
                    //这里转换出来的appSecret是代表
                    result= listBean.getBeanJson(list);

                    //重构appSecret   重新赋值为空值
                    for(tThirdUsers users:list){
                        users.setAppSercet("");
                    }
                    /* 逻辑有点复杂 */
                    String resutTrue=listBean.getBeanJson(list);
                    //上面这一行这里面包含的appSrcet 并不是数据库中的appSecret  这里已经转化成真实的（经过算法处理的appScret了）
                    JSONObject jsonObject =new JSONObject();
                    jsonObject.put("userList",resutTrue);
                        //生成tocken
                        JSONObject json =new JSONObject();
                        json.put("appId",appId);
                        json.put("userList",result);
                        json.put("randow",""+System.currentTimeMillis());
                        String tocken=JWTUtil.createToken(json.toString());
                    jsonObject.put("token",tocken);

                    try{
                        //保存数据到缓存
                        redisTemplate.opsForValue().set(appId,tocken,redison.getIsredistime(), TimeUnit.SECONDS);
                        //redisTemplate.opsForValue().set(tocken,appId,redison.getIsredistime(), TimeUnit.SECONDS);
                        //减少一次
                        CanService.update_leftNumber_reduceS(appId);
                        return new resultMsg(true,jsonObject.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                    }catch (Exception e){
                        return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.USER_NOT_EXIT.getValue(),ResultOk.USER_NOT_EXIT.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     *
     * @param cStoreNo       门店编号
     * @param cStoreName     门店名称
     * @return                门店集合
     */
    @RequestMapping(value = "/api/getStore", method = RequestMethod.POST)
    @ResponseBody
    public  String findStore(@RequestParam(value = "cStoreNo",required = false) String cStoreNo,
                             @RequestParam(value = "cStoreName",required = false) String cStoreName){
        try{
            List<Store> list =CanService.get_cStroeS(cStoreNo);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     * @param cStoreNo       门店编号
     * @param cStoreName     门店名称
     * @return                门店集合
     * @描述                   得到门店会员商品
     */
    @RequestMapping(value = "/api/getVipGoods", method = RequestMethod.POST)
    @ResponseBody
    public  String findVipGoods(@RequestParam(value = "cStoreNo",required = false,defaultValue = "buzhidao") String cStoreNo,
                             @RequestParam(value = "cStoreName",required = false) String cStoreName){
        try{
            //'%,0001,%'
            List<t_VipcGoodsPrice> list =CanService.gett_VipcGoodsPriceS(("%,"+cStoreNo+",%").trim());
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }


    /**
     * @param cStoreNo       门店编号
     * @param cStoreName     门店名称
     * @return                门店集合
     * @描述                   得到门店促销商品t_PloyOfSale
     */
    @RequestMapping(value = "/api/getPloyOfSaleGoods", method = RequestMethod.POST)
    @ResponseBody
    public  String getPloyOfSaleGoods(@RequestParam(value = "cStoreNo",required = false,defaultValue = "buzhidao") String cStoreNo,
                                @RequestParam(value = "cStoreName",required = false) String cStoreName){
        try{
            List<t_PloyOfSale> list =CanService.gett_t_PloyOfSaleS((cStoreNo).trim());
            String result="";
            if(list!=null && !list.isEmpty()){

                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }


    /**
     * @param cStoreNo       门店编号
     * @param cStoreName     门店名称  不需要上传
     * @return                自定义组合促销商品集合
     * @描述                   得到门店自定义组合促销商品t_PloyGroupOfSale
     */
    @RequestMapping(value = "/api/getPloyGroupOfSaleGoods", method = RequestMethod.POST)
    @ResponseBody
    public  String getPloyGroupOfSaleGoods(@RequestParam(value = "cStoreNo",required = false,defaultValue = "buzhidao") String cStoreNo,
                                      @RequestParam(value = "cStoreName",required = false) String cStoreName){
        try{
            List<t_PloyGroupOfSale> list =CanService.selectByNowDataS("%,"+(cStoreNo).trim()+"%");
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     * @param cStoreNo       门店编号
     * @param cStoreName     门店名称  不需要上传
     * @return                品类打折促销商品集合
     * @描述                   得到门店品类打折促销商品t_PloyOfSale_GoodsType
     */
    @RequestMapping(value = "/api/getPloyOfSaleGoodsType", method = RequestMethod.POST)
    @ResponseBody
    public  String getPloyOfSaleGoodsType(@RequestParam(value = "cStoreNo",required = false,defaultValue = "buzhidao") String cStoreNo,
                                           @RequestParam(value = "cStoreName",required = false) String cStoreName){
        try{
            List<t_PloyOfSale_GoodsType> list =CanService.gett_PloyOfSale_GoodsTypeS((cStoreNo).trim());
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    @RequestMapping(value = "/api/getPaymentMethod", method = RequestMethod.POST)
    @ResponseBody
    public  String get_tMethod_of_payment(){
        try{
            List<tMethod_of_payment> list =CanService.get_tMethod_of_paymentS();
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }
    /**
     *
     * @param cStoreNo       门店编号
     * @return                得到仓库
     */
    @RequestMapping(value = "/api/getWarehouse", method = RequestMethod.POST)
    @ResponseBody
    public  String getWarehouse(@RequestParam(value = "cStoreNo",required = false) String cStoreNo){
        try{
            List<tWareHouse> list =CanService.get_tWareHouseS(cStoreNo);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /** 得到生鲜码规则
     *
     * @param tableName     客户手机号
     * @param condition      附件条件
     * @return              生鲜码规则
     */
    @RequestMapping(value = "/api/getFreshCodeRule", method = RequestMethod.POST)
    @ResponseBody
    public  String getFreshCodeRule(@RequestParam(value = "tableName",required = false,defaultValue = "posstation101.dbo.Pos_Config") String tableName,
                                     @RequestParam(value = "condition",required = false,defaultValue = "条码秤") String condition,
                                     @RequestParam(value = "cStoreNo",required = false,defaultValue = "cStoreNo") String cStoreNo){
        try{

            List<posConfig> list=CanService.selectAllS(tableName,condition);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }
    /**
     *
     * @param mobileNo     客户手机号
     * @return              会员卡信息
     */
    @RequestMapping(value = "/api/getUserCard", method = RequestMethod.POST)
    @ResponseBody
    public  String findVip(@RequestParam(value = "mobileNo",required = true) String mobileNo){
        try{
            if(mobileNo.trim().equals("")){
                mobileNo="-1";//这里避免读出所有的‘’的会员卡号的信息
            }
            List<vip> list =CanService.get_vipS(mobileNo);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     *
     * @param mobileNo     客户手机号
     * @return              会员卡信息
     */
    @RequestMapping(value = "/api/getVipInfo", method = RequestMethod.POST)
    @ResponseBody
    public  String getVipInfo(@RequestParam(value = "mobileNo",required = true) String mobileNo){
        try{
            if(mobileNo.trim().equals("")){
                mobileNo="-1";//这里避免读出所有的‘’的会员卡号的信息
            }List<t_vipInfo> list =CanService.get_vipInfoS(mobileNo);

            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     *  更改积分信息
     */
    @RequestMapping(value = "/api/updateVipValue", method = RequestMethod.POST)
    @ResponseBody
    public  String updateVipValue(@RequestParam(value = "machineId",required = true) String machineId,
                                  @RequestParam(value = "vipNo",required = true) String vipNo,
                                  @RequestParam(value = "addScore",required = true) String addScore,
                                  HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        try{
            Integer integer=CanService.update_VipS(appId,machineId,vipNo,Double.parseDouble(addScore));
            return new resultMsg(true,"[]", ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }

    /**
     *
     * @param cBarCode   商品条码
     * @param cStoreNo   门店编号
     * @param cardNo     会员卡号
     * @return           门店信息
     * <pre>
     *     原来的接口名字
     *     @RequestMapping(value = "/api/getGoodsInfo", method = RequestMethod.POST)
           @ResponseBody
           @Deprecated //废弃的接口  不让实时访问   京东的可以实时访问
     * </pre>
     */
    @RequestMapping(value = "/api/getGoodsInfo", method = RequestMethod.POST)
    @ResponseBody
    public  String getGoodsInfo(@RequestParam(value = "cBarCode",required = true) String cBarCode,
                                @RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                                @RequestParam(value = "cardNo",required = false) String cardNo){

        try{
            List<String> stringList=new ArrayList<String>();
            if(cBarCode.contains("|") && !cBarCode.trim().equals("|")){
                String[] arr =null;
                try{
                    arr= cBarCode.split("\\|");
                    for(String barcode:arr){
                        stringList.add(barcode);
                    }
                    logger.info("我包含|"+stringList.toString());
                }catch (Exception e){
                    stringList.add(cBarCode);
                    logger.error("我是出错的数据|"+stringList.toString());
                }
            }else{
                stringList.add(cBarCode);
                logger.info("我不包含|"+stringList.toString());
            }
            if(stringList.isEmpty()){
                stringList.add("");
            }
            List<cStoreGoods> list=CanService.get_cStoreGoodsS(cStoreNo,stringList);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("code",list.size());
                    jsonObject.put("zhamount",0);
                    jsonObject.put("mjamount",0);
                    jsonObject.put("zkamount",0);
                    jsonObject.put("goodslist",result);
                    return new resultMsg(true,jsonObject.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }
            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }


    /**
     * 整单优惠(比如满减或组合等)
     * @param data   数组对象字符串
     * @return      这里是固定值   我们数据库没有
     */
    @RequestMapping(value = "/api/getPackagePreference", method = RequestMethod.POST)
    @ResponseBody
    public  String getPackagePreference(@RequestParam(value = "data",required = true) String data){
        //日志打印
        try{
            JSONArray array=JSONArray.fromObject(data);
            //这里拿到解析的数据做处理
//            for(int i=0;i<array.size()-1;i++){
//                System.out.println(array.getJSONObject(i));
//                System.out.println(array.getJSONObject(i).getString("barcode"));
//            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(true,"[]",ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
        try{
            //这里可以做相应处理结果
            if(true){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("code",0);
                jsonObject.put("packageAmount",0);
                return new resultMsg(true,jsonObject.toString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
            }else{

                return new resultMsg(true,"[]", ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     * @param cStoreNo
     * @param cBarcode
     * @param num
     * @param cardno
     * @return
     */
    @RequestMapping(value = "/api/getGeneralPreference", method = RequestMethod.POST)
    @ResponseBody
    public  String getGeneralPreference(@RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                                         @RequestParam(value = "cBarcode",required = true) String cBarcode,
                                        @RequestParam(value = "num",required = true) String num,
                                        @RequestParam(value = "cardno",required = false) String cardno){

        int number=0;
        try{
          if(NumberUtils.isNumber(num)){
              number=Integer.parseInt(num);
          }else {
              return new resultMsg(true,"[]",ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
          }
        }catch (Exception e){
            return new resultMsg(true,"[]",ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
        try{
            List<tPloyOfSale> list =CanService.get_tPloyOfSaleS(cStoreNo, cBarcode, number);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     * 查询所有商品
     * @param cStoreNo
     * @param pageNum
     * @param number
     * @param cardno
     * @return
     */
    @RequestMapping(value = "/api/getAllGoods", method = RequestMethod.POST)
    @ResponseBody
    public  String getAllGoods(@RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                                        @RequestParam(value = "pageNum",required = true) String pageNum,
                                        @RequestParam(value = "number",required = true) String number,
                                        @RequestParam(value = "cardno",required = false) String cardno){


        try{
            if(!NumberUtils.isNumber(pageNum) && !NumberUtils.isNumber(number)){
                return new resultMsg(true,"[]",ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
            }
        }catch (Exception e){
            return new resultMsg(true,"[]",ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
        try{
            List<cGoods> list =CanService.get_cGoodsS(cStoreNo, pageNum, number);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }

            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }

    /**
     * 下单接口
     * @param data
     * @return
     */
    @RequestMapping(value = "/api/importSales", method = RequestMethod.POST)
    @ResponseBody
    public  String importSales(@RequestParam(value = "data",required = true) String data,HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        logger.info("我是订单模块的" +appId);

        try {
            String jsonStr=data.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");
            logger.info("数据集合 jsonStr"+jsonStr);
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONArray jsonArray_sale=JSONArray.fromObject(jsonObject.get("sale"));
            JSONArray jsonArray_saleDatial=JSONArray.fromObject(jsonObject.get("saleDatial"));
            List<tPublicSaleDetail_z> list_tPublicSaleDetail_z=JSONArray.toList(jsonArray_saleDatial,new tPublicSaleDetail_z(),new JsonConfig());
            for(tPublicSaleDetail_z t:list_tPublicSaleDetail_z){
                t.setAppId(appId);
            }
            String str= listBean.getBeanJson(list_tPublicSaleDetail_z);
            logger.info("数据集合 tPublicSaleDetail_z"+str);
            List<tPublicSale_z> list_tPublicSale_z=JSONArray.toList(jsonArray_sale,new tPublicSale_z(),new JsonConfig());
            for(tPublicSale_z t:list_tPublicSale_z){
                t.setAppId(appId);
            }
            str= listBean.getBeanJson(list_tPublicSale_z);
            logger.info("数据集合 tPublicSale_z"+str);
            String result=CanService.placing_order(list_tPublicSale_z,list_tPublicSaleDetail_z);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }


    /**
     * 生成订单的接口 此方废弃不用了
     * @param data
     * @return
     */
    @RequestMapping(value = "/api/createImportSalesOrder", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public  String createImportSalesOrder(@RequestParam(value = "data",required = true) String data,HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        logger.info("我是订单模块的" +appId);

        //设置单号
        try {
            String jsonStr=data.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");
            logger.info("数据集合 jsonStr"+jsonStr);
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONArray jsonArray_sale=JSONArray.fromObject(jsonObject.get("sale"));
            JSONArray jsonArray_saleDatial=JSONArray.fromObject(jsonObject.get("saleDatial"));
            List<tPublicSaleDetail_JingDong_z> list_tPublicSaleDetail_JingDong_z=JSONArray.toList(jsonArray_saleDatial,new tPublicSaleDetail_JingDong_z(),new JsonConfig());
            for(tPublicSaleDetail_JingDong_z t:list_tPublicSaleDetail_JingDong_z){
                t.setAppId(appId);
            }
            String str= listBean.getBeanJson(list_tPublicSaleDetail_JingDong_z);
            logger.info("数据集合 list_tPublicSaleDetail_JingDong_z"+str);
            List<tPublicSale_JingDong_z> list_tPublicSale_JingDong_z=JSONArray.toList(jsonArray_sale,new tPublicSale_JingDong_z(),new JsonConfig());
            for(tPublicSale_JingDong_z t:list_tPublicSale_JingDong_z){
                t.setAppId(appId);
            }
            str= listBean.getBeanJson(list_tPublicSale_JingDong_z);
            logger.info("数据集合 list_tPublicSale_JingDong_z"+str);
            String result=CanService.placing_order_JD(list_tPublicSale_JingDong_z,list_tPublicSaleDetail_JingDong_z);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }


    /**
     * 京东获取优惠商品信息的接口
     * @param data  数据集
     * @param vipNo  会员号
     * @param fVipRate  打折率
     * @param bDiscount 是否打折
     * @param cStoreNo   门店编号
     * @param cMachineID 机器编号
     * @param cMachineKey  编号key
     * @param request     获取
     * @return
     */
    @RequestMapping(value = "/api/getPreGoodsInfo", method = RequestMethod.POST)
    @ResponseBody
    public  String getPreGoodsInfo(@RequestParam(value = "data",required = true) String data,
                                   @RequestParam(value = "vipNo",required = false,defaultValue = "09") String vipNo,
                                   @RequestParam(value = "fVipRate",required = false,defaultValue = "100") String fVipRate,
                                   @RequestParam(value = "bDiscount",required = false,defaultValue = "0") String bDiscount,
                                   @RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                                   @RequestParam(value = "cMachineID",required = true) String cMachineID,
                                   @RequestParam(value = "cMachineKey",required = true) String cMachineKey,
                                   HttpServletRequest request) {
        String appId=request.getHeader("requestid");

        logger.info("我是订单模块的" +appId);
        //获取无人售货机对应我们的数据库中的前台机器的编号
        if(!cMachineKey.trim().equals(MD5Encode(appKey+cMachineID,"UTF-8").trim().toUpperCase())){
            return new resultMsg(false, "[]", ResultError.MACHINE_SECRET_KEY.getValue(),  ResultError.MACHINE_SECRET_KEY.getDesc()).toString();
        }

        posstation posstation=CanService.select_posstationS(appId,cMachineID,cStoreNo);
        if(posstation==null){
            return new resultMsg(false, "[]", ResultError.MACHINE_REGIST.getValue(),  ResultError.MACHINE_REGIST.getDesc()).toString();
        }


        //设置appId
        try {
            String jsonStr=data.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");
            logger.info("数据集合 jsonStr"+jsonStr);
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONArray jsonArray_sale=JSONArray.fromObject(jsonObject.get("sale"));
            JSONArray jsonArray_saleDatial=JSONArray.fromObject(jsonObject.get("saleDatial"));
            List<tPublicSaleDetail_JingDong_z> list_tPublicSaleDetail_JingDong_z=JSONArray.toList(jsonArray_saleDatial,new tPublicSaleDetail_JingDong_z(),new JsonConfig());
            for(tPublicSaleDetail_JingDong_z t:list_tPublicSaleDetail_JingDong_z){
                t.setAppId(appId);
                t.setcVipNo(vipNo);
                t.setcStoreNo(cStoreNo);
                t.setMachineIp(posstation.getPosid());
                //设置原始生鲜码
                if(t.getcFreshBarcode()==null){
                    t.setcFreshBarcode("");
                }
            }
            String str= listBean.getBeanJson(list_tPublicSaleDetail_JingDong_z);
            logger.info("数据集合 list_tPublicSaleDetail_JingDong_z"+str);
            List<tPublicSale_JingDong_z> list_tPublicSale_JingDong_z=JSONArray.toList(jsonArray_sale,new tPublicSale_JingDong_z(),new JsonConfig());
            for(tPublicSale_JingDong_z t:list_tPublicSale_JingDong_z){
                t.setAppId(appId);
                t.setMachineIp(posstation.getPosid());
                t.setcVipNo(vipNo);
                t.setcStoreNo(cStoreNo);
            }
            str= listBean.getBeanJson(list_tPublicSale_JingDong_z);
            logger.info("数据集合 list_tPublicSale_JingDong_z"+str);
            String result=CanService.getPreGoodsInfoS(list_tPublicSale_JingDong_z,list_tPublicSaleDetail_JingDong_z,fVipRate,bDiscount,posstation.getPos_Day(),posstation);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }

    /**
     *  数据提交
     * @param data
     * @param cStoreNo
     * @param cMachineID
     * @param cMachineKey
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/submitData", method = RequestMethod.POST)
    @ResponseBody
    public  String submitData(@RequestParam(value = "data",required = true) String data,
                              @RequestParam(value = "saleType",required = true) String saleType,
                               @RequestParam(value = "cStoreNo",required = true) String cStoreNo,
                               @RequestParam(value = "cMachineID",required = true) String cMachineID,
                               @RequestParam(value = "cMachineKey",required = true) String cMachineKey,
                                   HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        logger.info("我是订单模块的" +appId);

        //获取无人售货机对应我们的数据库中的前台机器的编号
        if(!cMachineKey.trim().equals(MD5Encode(appKey+cMachineID,"UTF-8").trim().toUpperCase())){
            return new resultMsg(false, "[]", ResultError.MACHINE_SECRET_KEY.getValue(),  ResultError.MACHINE_SECRET_KEY.getDesc()).toString();
        }

        posstation posstation=CanService.select_posstationS(appId,cMachineID,cStoreNo);
        if(posstation==null){
            return new resultMsg(false, "[]", ResultError.MACHINE_REGIST.getValue(),  ResultError.MACHINE_REGIST.getDesc()).toString();
        }

        //获取单号
        commSheetNo commSheetNo=null;
        try{
            commSheetNo=CanService
                    .getCommSheetNoS(cStoreNo,posstation.getPosid().trim(),
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                            posstation.getPos_Day().trim()+".dbo.p_getPos_SerialNoSheetNo");
        }catch (Exception e){
            logger.info("生成单号出错(错误信息): {}",e.getMessage());
            return new resultMsg(false, "[]", ResultError.SHEETNO_ERROR.getValue(),  ResultError.SHEETNO_ERROR.getDesc()).toString();

        }
        //设置appId
        try {
            String jsonStr=data.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");
            logger.info("数据集合 jsonStr"+jsonStr);
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("data"));
            List<POS_SaleSheetDetail> list=JSONArray.toList(jsonArray1,new POS_SaleSheetDetail(),new JsonConfig());

            String result=CanService.insert_jiesuan_sure(list,commSheetNo,posstation,saleType);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }

    /**
     * 退货接口
     * @param data
     * @return
     */
    @RequestMapping(value = "/api/returnSales", method = RequestMethod.POST)
    @ResponseBody
    public  String returnSales(@RequestParam(value = "data",required = true) String data,HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        logger.info("我是退货模块的" +appId);
        try {
            String jsonStr=data.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");
            logger.info("退货数据集合 jsonStr"+jsonStr);
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONArray jsonArray_sale=JSONArray.fromObject(jsonObject.get("sale"));
            JSONArray jsonArray_saleDatial=JSONArray.fromObject(jsonObject.get("saleDatial"));
            List<tPublicSaleDetail_z> list_tPublicSaleDetail_z=JSONArray.toList(jsonArray_saleDatial,new tPublicSaleDetail_z(),new JsonConfig());
            for(tPublicSaleDetail_z t:list_tPublicSaleDetail_z){
                t.setAppId(appId);
            }
            String str= listBean.getBeanJson(list_tPublicSaleDetail_z);
            logger.info("退货数据集合 tPublicSaleDetail_z"+str);
            List<returntPublicSale_z> list_returntPublicSale_z=JSONArray.toList(jsonArray_sale,new returntPublicSale_z(),new JsonConfig());
            for(returntPublicSale_z t:list_returntPublicSale_z){
                t.setAppId(appId);
                //--是否是退货   1 正常销售   2 代表退货
                t.setReturnSale("2");
                t.setBeizhu("退货");
            }
            str= listBean.getBeanJson(list_returntPublicSale_z);
            logger.info("退货数据集合 returntPublicSale_z"+str);
            String result=CanService.placing_ruturnorder(list_returntPublicSale_z,list_tPublicSaleDetail_z);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }


    /**
     * 订单查询
     * @param cSheetNo
     * @return
     */
    @RequestMapping(value = "/api/getOrder", method = RequestMethod.POST)
    @ResponseBody
    public  String getOrder(@RequestParam(value = "cSheetNo",required = true) String cSheetNo,HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        try {
            String result=CanService.get_Order(cSheetNo,appId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }

    /**
     * 得到单据支付状态及信息
     * @param cSheetNo
     * @return
     */
    @RequestMapping(value = "/api/getCreateOrderType", method = RequestMethod.POST)
    @ResponseBody
    public  String getCreateOrderType(@RequestParam(value = "cSheetNo",required = true) String cSheetNo,HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        try {
            String result=CanService.get_Order_JD(cSheetNo,appId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }

    /**
     * 更改该订单状态 改成已支付
     * @param cSheetNo
     * @return
     */
    @RequestMapping(value = "/api/commitOrderAcounmt", method = RequestMethod.POST)
    @ResponseBody
    public  String commitOrderAcounmt(@RequestParam(value = "cSheetNo",required = true) String cSheetNo,HttpServletRequest request) {
        String appId=request.getHeader("requestid");
        try {
            String result=CanService.update_DingdanType(cSheetNo,appId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false, "[]", ResultOk.DATA_PASR_ERROR.getValue(), ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
    }
    /**
     * 得到商品库存
     * @param cGoodsNo
     * @param cStoreNo
     * @return
     */
    @RequestMapping(value = "/api/getGoodsKuCurQty", method = RequestMethod.POST)
    @ResponseBody
    public  String getGoodsKuCurQty(@RequestParam(value = "cGoodsNo",required = true) String cGoodsNo,
                                @RequestParam(value = "cStoreNo",required = true) String cStoreNo){

        try{
            List<String> stringList=new ArrayList<String>();
            if(cGoodsNo.contains("|") && !cGoodsNo.trim().equals("|")){
                String[] arr =null;
                try{
                    arr= cGoodsNo.split("\\|");
                    for(String barcode:arr){
                        stringList.add(barcode);
                    }
                    logger.info("我包含|"+stringList.toString());
                }catch (Exception e){
                    stringList.add(cGoodsNo);
                    logger.error("我是出错的数据|"+stringList.toString());
                }
            }else{
                stringList.add(cGoodsNo);
                logger.info("我不包含|"+stringList.toString());
            }
            if(stringList.isEmpty()){
                stringList.add("");
            }
            List<t_goodsKuCurQty_wei> list=CanService.get_t_goodsKuCurQty_weiS(cStoreNo,stringList);
            String result="";
            if(list!=null && !list.isEmpty()){
                try{
                    result= listBean.getBeanJson(list);
                    return new resultMsg(true,result, ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info(e.getMessage());
                    return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
                }
            }else {
                return new resultMsg(true,"[]",ResultOk.SUCCESS_NULL.getValue(),ResultOk.SUCCESS_NULL.getDesc()).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new resultMsg(false,"[]",ResultOk.ERROR_UNKNOWN.getValue(),ResultOk.ERROR_UNKNOWN.getDesc()).toString();
        }
    }


}
