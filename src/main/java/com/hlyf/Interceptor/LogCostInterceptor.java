package com.hlyf.Interceptor;

import com.hlyf.config.interceptor;
import com.hlyf.config.redisCon;
import com.hlyf.fliterController.IndexFilter;
import com.hlyf.result.ResultOk;
import com.hlyf.result.resultMsg;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.hlyf.tool.JWTUtil.getSecret;
import static com.hlyf.tool.Md5.signIsOk;

/**
 * @author
 * @descreption  校验加密的
 */

public class LogCostInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogCostInterceptor.class);

    @Autowired
    private interceptor Interceptor;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String s=handler.toString();
//        System.out.println(s);
//        String uri=request.getRequestURI();
//        System.out.println("拦截器获取的 请求路径是 "+uri);
//        //这些路径不做拦截
//        String[] paths = new String[]{"/Scan/api/getToken","/GYL/GYL/api/getVesion"};
//        for(String path : paths) {
//            if (uri.equals(path)) {
//                return true;
//            }
//        }
//        //是否开启拦截  秘钥过滤
//        if(!Interceptor.getEncrypt()){
//            return true;
//        }
//        String sign=request.getParameter("sign");
//        System.out.println("sign "+sign);
//        String tocken=request.getHeader("token").replace(" ","").trim();
//        System.out.println("tocken "+tocken);
//
//        Map<String,String[]> requestMsg = request.getParameterMap();
//        for(String key :requestMsg.keySet())
//        {
//            for(int i=0;i<requestMsg.get(key).length;i++)
//            {
//                //打印所有请求参数值
//                System.out.println("key="+key+";value="+requestMsg.get(key)[i].toString());
//            }
//        }

        //开启验证先判断是否传递了签名
//        if(sign==null || sign.equals("")){
//            PrintWriter out=response.getWriter();
//            out.println(new resultMsg(true,"[]", ResultOk.ERROR_SECRET.getValue(),ResultOk.ERROR_SECRET.getDesc()).toString());
//            out.flush();
//            out.close();
//            return  false;
//        }


//        Enumeration<String> e = request.getParameterNames();
//        Map<String,String> mapParams=new HashMap<String, String>();
//        while (e.hasMoreElements()) {
//            String paramName = (String) e.nextElement();
//            String paramValue = request.getParameterValues(paramName)[0];
//            mapParams.put(paramName,paramValue);
//            System.out.println("key "+paramName+ " value "+paramValue);
//        }
//
//        System.out.println("参数列表: "+mapParams.toString());
//        //秘钥校验
//        if(mapParams.size()>0 && tocken!=null){
//
//            //通过token得到用户的秘钥
//            String userSecret=getSecret(tocken);
//            if(!signIsOk(userSecret,mapParams,sign) ){
//                PrintWriter out=response.getWriter();
//                out.println(new resultMsg(true,"[]", ResultOk.ERROR_SECRET.getValue(),ResultOk.ERROR_SECRET.getDesc()).toString());
//                out.flush();
//                out.close();
//                return  false;
//            }
//        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
