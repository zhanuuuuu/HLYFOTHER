package com.hlyf.controller;



import com.hlyf.config.redisCon;
import com.hlyf.domin.test1;
import com.hlyf.result.ResultError;
import com.hlyf.result.resultMsg;
import com.hlyf.service.testService;
import com.hlyf.tool.JWTUtil;
import com.hlyf.tool.listBean;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static com.hlyf.tool.FileBean.getImageStr;


/**
 * Created by Administrator on 2018-06-23.
 */
@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private testService testService;

    @Autowired
    private redisCon redisCon;

    //定义信号资源包的总数 只有2个
    Semaphore semaphore=new Semaphore(2);


    @GetMapping("/request")
    @ResponseBody
    public String Resquest(){
        //设置这个接口可用的资源数
        int availablePermits=semaphore.availablePermits();
        if(availablePermits>0){
            System.out.println("抢到资源");
        }else{
            System.out.println("资源已被占用，稍后再试");
            return "Resource is busy！";
        }
        try {
            //请求占用一个资源
            semaphore.acquire(1);
            System.out.println("资源正在被使用");
            //放大资源占用时间，便于观察
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            semaphore.release(1);//释放一个资源
            System.out.println("-----------释放资源包----------");
        }
        return "Success";
    }

    @RequestMapping(value = "/GetredisCon",method = {RequestMethod.GET,RequestMethod.POST})
    public String GetredisCon() {
        return new resultMsg(true,redisCon.getEncrypt()+"", ResultError.SUCCESS.getValue(),ResultError.SUCCESS.getDesc()).toString();
    }

    @RequestMapping(value = "/GetTestData",method = {RequestMethod.GET,RequestMethod.POST})
    public String GetTestData() {
        List<test1> list=testService.getTestsS();
        String str= listBean.getBeanJson(list);
        return new resultMsg(true,str, ResultError.SUCCESS.getValue(),ResultError.SUCCESS.getDesc()).toString();
    }

    @RequestMapping(value = "/GetConn/{page}/{pagsize}",method = RequestMethod.GET)
    public String TestConn(@PathVariable("page") Integer page,@PathVariable("pagsize") Integer pagsize) {
        String str="";
        return new resultMsg(true,"["+str+"]","1004","数据集为空").toString();
    }

    @RequestMapping(value = "/GetJWTtocke",method = RequestMethod.GET)
    public String GetJWTtocke(@RequestParam(value = "name",required = false)String name){


        String JWTtocke= JWTUtil.getUsername(name);

        return ("JWTtocke 存储的字符串为: "+ JWTtocke);
    }

    @RequestMapping(value = "/SetJWTtocke",method = RequestMethod.GET)
    public String SetJWTtocke(@RequestParam(value = "name",required = false)String name){
        String JWTtocke= JWTUtil.createToken(name);
        return ("JWTtocke 存储的字符串为: "+ JWTtocke);
    }


    @RequestMapping(value = "/GetImageBase64",method = RequestMethod.GET)
    public String GetImageBase64() {
        String str=getImageStr("C:\\Users\\Administrator\\Desktop\\why\\baishang.png");
        logger.info(str);
        return new resultMsg(true,"["+str+"]","1004","数据集为空").toString();
    }

    @RequestMapping(value = "/angus/zmy", method = RequestMethod.GET)
    @ResponseBody
    public String getProcess(HttpServletRequest request){
        String retrunValue = "Hello, Angus! This is GET request!";
        System.out.println("=======GET Process=======");

        Map<String,String[]> requestMsg = request.getParameterMap();
        Enumeration<String> requestHeader = request.getHeaderNames();

        System.out.println("------- header -------");
        while(requestHeader.hasMoreElements()){
            String headerKey=requestHeader.nextElement().toString();
            //打印所有Header值
            System.out.println("headerKey="+headerKey+";value="+request.getHeader(headerKey));
        }

        System.out.println("------- parameter -------");
        for(String key :requestMsg.keySet())
        {
            for(int i=0;i<requestMsg.get(key).length;i++)
            {
                //打印所有请求参数值

                System.out.println("key="+key+";value="+requestMsg.get(key)[i].toString());
            }
        }
        return retrunValue;
    }

    @RequestMapping(value = "/angus/zmy", method = RequestMethod.POST)
    @ResponseBody
    public String postProcess(HttpServletRequest request){
        String retrunValue = "Hello, Angus! This is POST Request!";
        System.out.println("=======POST Process=======");

        Enumeration<String> requestHeader = request.getHeaderNames();
        InputStream io = null;
        String body;
        System.out.println("------- body -------");
        try{
            io = request.getInputStream();
            body = IOUtils.toString(io);
            //打印BODY内容
            System.out.println("Request Body="+body);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("------- header -------");
        while(requestHeader.hasMoreElements()){
            String headerKey=requestHeader.nextElement().toString();
            //打印所有Header值
            System.out.println("headerKey="+headerKey+";value="+request.getHeader(headerKey));
        }
        System.out.println("------- parameters -------");

        Map<String,String[]> requestMsg = request.getParameterMap();
        for(String key :requestMsg.keySet())
        {
            for(int i=0;i<requestMsg.get(key).length;i++)
            {
                //打印所有请求参数值
                System.out.println("key="+key+";value="+requestMsg.get(key)[i].toString());
            }
        }
        return retrunValue;
    }
    //获取访问者的ip地址
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
}
