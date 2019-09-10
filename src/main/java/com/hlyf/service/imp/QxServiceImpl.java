package com.hlyf.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hlyf.config.redisCon;
import com.hlyf.dao.cluster.QxDao;
import com.hlyf.domin.Qx.SaleSheetHelp;
import com.hlyf.result.ResultOk;
import com.hlyf.result.resultMsg;
import com.hlyf.service.QxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static com.hlyf.tool.Md5.MD5Encode;

/**
 * Created by Administrator on 2019-09-09.
 */
@Service
public class QxServiceImpl implements QxService {
    private static final Logger log = LoggerFactory.getLogger(QxServiceImpl.class);

    //加密的md5Key
    private static final String appKey="warelucent";

    @Autowired
    private QxDao qxDaoMapper;

    private redisCon redisCon;


    @Override
    public String transmission(String data, String appId, String sign) {

        log.info("我是拿到的请求数据 data:{}, appId:{}, sign:{}",data,appId,sign);
        //TODO 第一  解密appid
        String sheetNo="";
        String md5Key=MD5Encode(appKey+appId,"UTF-8").toUpperCase();
        //TODO 第二  拿到待签名的字符串
        ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attr.getRequest();
        Enumeration<String> e = request.getParameterNames();
        String var1 = "";
        Map<String,String> mapParams=new HashMap<String, String>();
        while (e.hasMoreElements()) {
            String paramName = (String) e.nextElement();
            String paramValue = request.getParameterValues(paramName)[0];
            var1 = var1 + paramName + "=" + paramValue + "&";
            mapParams.put(paramName,paramValue);
        }
        String signBefore=getOrderSign(mapParams);
        //TODO  校验签名字符串是否一致
        if(!sign.equals(MD5Encode(md5Key+signBefore+md5Key,"UTF-8").toUpperCase())){
            return new resultMsg(false,"[]", ResultOk.ERROR_SECRET.getValue(),ResultOk.ERROR_SECRET.getDesc()).toString();
        }
        try{
            JSONObject jsonObject= JSON.parseObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("dataDetail");
            List<SaleSheetHelp> saleSheetHelps=JSONObject.parseArray(jsonArray.toJSONString(), SaleSheetHelp.class);
            if(saleSheetHelps==null || saleSheetHelps.size()==0){
                return new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
            }
            for(SaleSheetHelp t:saleSheetHelps){
                sheetNo=t.getCSaleSheetno();
            }
            qxDaoMapper.insertList(saleSheetHelps);
        }catch (Exception e1){
            //这里自己写事物提交 如果失败  删除原来的
            if(!sheetNo.equals("")){
                qxDaoMapper.deleteSaleSheetHelp(sheetNo);
            }
            e1.printStackTrace();
            log.error("Exception {}",e1.getMessage());
            return new resultMsg(false,"[]", ResultOk.DATA_PASR_ERROR.getValue(),ResultOk.DATA_PASR_ERROR.getDesc()).toString();
        }
        JSONObject json=new JSONObject();
        json.put("cSheeetNo",sheetNo);
        return new resultMsg(true,json.toJSONString(), ResultOk.SUCCESS.getValue(),ResultOk.SUCCESS.getDesc()).toString();
    }

    private static  String getOrderSign(Map<String,String> paramValues){
        try{
            StringBuilder sb=new StringBuilder();
            List<String> paramNames=new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            Collections.sort(paramNames);
            for(String paraName:paramNames){
                if(paraName.equals("sign") || paraName.equals("")){
                    continue;
                }
                sb.append(paramValues.get(paraName));
            }

            log.info("得到的排序字符串 {}",sb.toString());
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            log.error("得到的排序出错了 {}",e.getMessage());
        }
        return "";
    }
}
