package com.hlyf.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hlyf.domin.tThirdUsers;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @Author zmy
 * @Description JWT 工具类
 * @Date 2018-04-07
 * @Time 22:48
 */
public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    // 过期时间 24 小时
    //private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;
    private static final long EXPIRE_TIME = 60 * 1000*2;
    // 密钥
    private static final String SECRET = "Scan+JWT";

    /**
     * 生成 token, 5min后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String createToken(String username) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     *获得token中的信息
     * @param token
     * @return   返回用户信息
     */
    public static String getSecret(String token){
        tThirdUsers user=new tThirdUsers();
        try{
            String userString=getUsername(token);
            JSONObject jsonObject=JSONObject.fromObject(userString);
            //System.out.println("我是tocken      "+jsonObject);
            JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("userList"));
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                user.setAppId(object.get("appId").toString());
                user.setAppSercet(object.get("appSercet").toString());
                user.setUserName(object.get("userName").toString());
            }
            return user.getAppSercet();
            //System.out.println(ThreeDESUtil.encrypt("张明阳", "123456788765432112345678", "01234567"));
            //这里进行得到秘钥
//            System.out.println(ThreeDESUtil.encrypt(
//                    (user.getAppId()+user.getAppSercet()).trim(),
//                    "123456788765432112345678",//固定值不要改变
//                    "01234567")//固定值不要改变
//                    .replaceAll("=","")
//                    .replaceAll("\\+","")
//                    .replaceAll("\\s*","")
//                    .replaceAll("\\n","")
//                    .trim());
//            return  ThreeDESUtil.encrypt(
//                    (user.getAppId()+user.getAppSercet()).trim(),
//                    "123456788765432112345678",//固定值不要改变
//                    "01234567")//固定值不要改变
//                    .replaceAll("=","")
//                    .replaceAll("\\+","")
//                    .replaceAll("\\s*","")
//                    .replaceAll("\\n","")
//                    .trim();
        }catch (Exception e){
            logger.error("得到appSecret出错",e.getMessage());
            //e.printStackTrace();
        }
        return user.getAppSercet();
    }


    public static tThirdUsers getUser(String token){
        tThirdUsers user=new tThirdUsers("","","");
        //如果为空或者不存在   直接返回
        if(token==null || token.trim().equals("")){
            return user;
        }
        try{
            String userString=getUsername(token);
            JSONObject jsonObject=JSONObject.fromObject(userString);
            JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("userList"));
            for(int i=0;i<jsonArray.size();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                user.setAppId(object.get("appId").toString());
                user.setAppSercet(object.get("appSercet").toString());
                user.setUserName(object.get("userName").toString());
            }
            return user;
        }catch (Exception e){
            logger.error("得到User出错",e.getMessage());
            //e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDI2ODM5MzgsInVzZXJuYW1lIjoie1wiYXBwSWRcIjpcImFiY1wiLFwidXNlckxpc3RcIjpbe1wiYXBwU2VyY2V0XCI6XCJhc2NmZ2hoXCIsXCJhcHBJZFwiOlwiYWJjXCIsXCJ1c2VyTmFtZVwiOlwi5Yi36IS45pSv5LuYXCJ9XSxcInJhbmRvd1wiOlwiMTU0MjY4MzgxODIyM1wifSJ9.zDLrVUR-2KnCp1H2qYtSxWKv_mPVeL3_869Q3eUuAno";

        String userString=getSecret(token);
        System.out.println(userString);

//        try{
//            String userString=getUsername(token);
//            System.out.println(userString);
//            JSONObject jsonObject=JSONObject.fromObject(userString);
//            System.out.println(jsonObject);
//            System.out.println(jsonObject.get("userList"));
//            JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("userList"));
//            for(int i=0;i<jsonArray.size();i++){
//                JSONObject object=jsonArray.getJSONObject(i);
//                System.out.println(object.get("appSercet"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
