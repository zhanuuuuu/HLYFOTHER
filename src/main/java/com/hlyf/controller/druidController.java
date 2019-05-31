package com.hlyf.controller;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Administrator on 2018-12-03.
 */
//@WebServlet(urlPatterns = "/druid/*",
//        initParams = {
//                @WebInitParam(name = "allow", value = "127.0.0.1,192.168.163.1,183.95.48.240"),// IP白名单(没有配置或者为空，则允许所有访问)
//                @WebInitParam(name = "deny", value = "192.168.1.73"),// IP黑名单 (存在共同时，deny优先于allow)
//                @WebInitParam(name = "loginUsername", value = "admin"),// 用户名
//                @WebInitParam(name = "loginPassword", value = "zmy"),// 密码
//                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
//        })
public class druidController  {//extends StatViewServlet

    private static final long serialVersionUID = -2688872071445249539L;
}
