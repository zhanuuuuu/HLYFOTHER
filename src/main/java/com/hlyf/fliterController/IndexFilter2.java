package com.hlyf.fliterController;

import com.hlyf.config.redisCon;
import com.hlyf.dao.cluster.sCanDao;
import com.hlyf.domin.tThirdUsers;
import com.hlyf.result.ResultOk;
import com.hlyf.result.resultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import static com.hlyf.tool.JWTUtil.getUser;

/**
 * Created by Administrator on 2018-11-19.
 */
@WebFilter(urlPatterns="/*",filterName="indexFilter2")
public class IndexFilter2 implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(IndexFilter.class);


    @Autowired
    private sCanDao SCanDao;

    @Autowired
    private redisCon redison;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //京东对接需要用这个方法 千禧基本信息也需要用这个方法的
        //CommFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain, redison, SCanDao);

        //下面不拦截任何请求
        filterChain.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
    }

    private static void CommFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain, redisCon redison, sCanDao SCanDao) throws IOException, ServletException {
        HttpServletRequest request =
                servletRequest;
        HttpServletResponse response =
                servletResponse;
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");


        String uri= servletRequest.getRequestURI();

        if(redison.getRedis()) {
            Enumeration<String> e = request.getParameterNames();
            String a = "";
            while (e.hasMoreElements()) {
                String paramName = (String) e.nextElement();
                String paramValue = request.getParameterValues(paramName)[0];
                a = a + paramName + "=" + paramValue + "&";
            }
            String data = a.toString();
            String userIp = getIpAddr(request);
            //logger.info("2 in filter addr="+uri+"?"+data);
            String tocken = request.getParameter("token");

            String[] paths = new String[]{"/druid/","/html","file","getKafka",
                    "multifileUpload","request","wechat"};//,"/api/getToken"

            //记录访问
            tThirdUsers user = getUser(tocken);
            try {
                SCanDao.insert_tThirdUsers_Log(user.getAppId(), user.getUserName(), userIp, data);
            } catch (Exception e1) {
                logger.error("保存访问记录出错 在线程中  " + e1.getMessage());
            }

//            for (String path : paths) {
//                if (!uri.contains(path)) {
//                    tThirdUsers user = getUser(tocken);
//                        /*在子线程中做日志处理*/
//                    try {
//                        SCanDao.insert_tThirdUsers_Log(user.getAppId(), user.getUserName(), userIp, data);
//                    } catch (Exception e1) {
//                        logger.error("保存访问记录出错 在线程中  " + e1.getMessage());
//                    }
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        try{
////                            SCanDao.insert_tThirdUsers_Log(user.getAppId(),user.getUserName(),userIp,data);
////                        }catch (Exception e1){
////                            logger.error("保存访问记录出错 在线程中  "+e1.getMessage());
////                        }
////                    }
////                }).start();
//
//                }
//            }

        }

        //logger.info("2 in filter token="+tocken);

        filterChain.doFilter(request, response);
        //logger.info("2 out filter addr="+uri);
    }

    @Override
    public void destroy() {

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
