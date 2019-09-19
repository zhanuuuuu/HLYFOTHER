package com.hlyf.fliterController;


import com.hlyf.config.redisCon;
import com.hlyf.result.ResultOk;
import com.hlyf.result.resultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.hlyf.tool.JWTUtil.getSecret;
import static com.hlyf.tool.Md5.signIsOk;

/**
 * Created by Administrator on 2018-06-22.
 */
//@Configuration
@WebFilter(urlPatterns="/*",filterName="indexFilter")
//@ConfigurationProperties(prefix="cluster")
public class IndexFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(IndexFilter.class);

    @Autowired
    private redisCon redison;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //京东对接需要用这个方法 千禧基本信息也需要用这个方法的
        CommFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
        //下面这个方法是不拦截请求
       // filterChain.doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
    }

    private void CommFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =
                servletRequest;
        HttpServletResponse response =
                servletResponse;
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        // ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        System.out.println("IsRedis-------------------------------------------="+redison.getRedis());

        System.out.println("IsRedis---getEncrypt   ------------------------------="+redison.getEncrypt());
        String uri= servletRequest.getRequestURI();

        Enumeration<String> e = request.getParameterNames();
        String a = "";
        Map<String,String> mapParams=new HashMap<String, String>();
        while (e.hasMoreElements()) {
            String paramName = (String) e.nextElement();
            String paramValue = request.getParameterValues(paramName)[0];
            a = a + paramName + "=" + paramValue + "&";
            mapParams.put(paramName,paramValue);
        }
        String data = a.toString();
        //logger.info("in filter addr="+uri+"?"+data);

        String tocken=request.getParameter("token");
        if(tocken==null){
            tocken="";
        }
        //logger.info("in filter token="+tocken);

        String appId=request.getHeader("requestid");
        if(appId==null){
            appId="";
        }
        //logger.info("in filter appId="+appId);

        if(redison.getRedis()){
            PrintWriter out = response.getWriter();
            //判断是不是登陆   是登陆就放行
            String[] paths = new String[]{"/Hlyf/api/getToken","/Scan/api/getToken","/druid/","/GetredisCon","/html","file",
                    "getKafka","multifileUpload","request","wechat"};
//            for(String path : paths) {
//                if (uri.equals(path)) {
//                    response.reset();
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            }
            for(String path : paths) {
                if (uri.contains(path)) {
                    response.reset();
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            logger.info("tocken is                              ="+tocken);
            if(tocken==null){
                tocken="";
            }
            //token拦截  tockenAndAppIdExist    tockenExist(tocken.trim())
            if(tocken==null || !tockenAndAppIdExist(appId,tocken.trim())) {
                out.println(new resultMsg(false, "[]", ResultOk.Tocken_FAIL.getValue(), ResultOk.Tocken_FAIL.getDesc()).toString());
                out.flush();
                out.close();
                return;
            }

            //        //秘钥校验
        if(mapParams.size()>0 && tocken!=null && !tocken.trim().equals("")){

            String sign=request.getParameter("sign");

            if(sign==null || sign.equals("") ){
                out.println(new resultMsg(true,"[]", ResultOk.ERROR_SECRET.getValue(),ResultOk.ERROR_SECRET.getDesc()).toString());
                out.flush();
                out.close();
                return;
            }

            //通过token得到用户的秘钥
            String userSecret=getSecret(tocken);
            if(!signIsOk(userSecret,mapParams,sign) ){
                out.println(new resultMsg(true,"[]", ResultOk.ERROR_SECRET.getValue(),ResultOk.ERROR_SECRET.getDesc()).toString());
                out.flush();
                out.close();
                return;
            }
        }


            response.reset();
        }
        //手动封装方法
        filterChain.doFilter(request, response);
        //logger.info("out filter addr="+uri);
    }

    @Override
    public void destroy() {
        
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 判断token是否存在数据库
     * @param token
     * @return
     */
    public Boolean tockenExist(String token){
         try{
            if(redisTemplate.hasKey(token)){
                return true;
            }
         }catch (Exception e){
             logger.error("获取tocken异常");
             return false;
         }

        return false;
    }

    public Boolean tockenAndAppIdExist(String appId,String token){
        try{
            String redisToken=(String)redisTemplate.opsForValue().get(appId);
            logger.info("redisToken  "+redisToken);
            logger.info("tokenToken  "+token);
            if(redisToken!=null && redisToken.equals(token)){
                return true;
            }
//            if(redisTemplate.hasKey(token)){
//                return true;
//            }
        }catch (Exception e){
            logger.error("获取tocken异常");
            return false;
        }
        return false;
    }
}
