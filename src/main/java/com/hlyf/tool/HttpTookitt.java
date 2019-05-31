package com.hlyf.tool;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.management.RuntimeErrorException;

import com.hlyf.minsheng.RopUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class HttpTookitt {
	
	 //链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        BufferedReader in = null;// 读取响应输入流
        try {
            url = new URL(urlList);
            
            HttpURLConnection httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDI4NzQ5MDEsImV4cCI6MTU0Mjk2MTMwMSwidWlkIjoiMTAwNTU5In0.tEiu-_YMG5lW9uaLkeKOHy5WviimRjze0cL_XgbbtzI");
            httpConn.setRequestProperty("content-type", "application/json"); // 设置发送数据的格式
            //User-Agent: {name}/{version}  X-Request-ID: request-id
            httpConn.setRequestProperty("User-Agent", "{HL}/{1.0.0}"); 
            httpConn.setRequestProperty("X-Request-ID", ""+(new Random(1).nextInt()*1000000)); 
            // 建立实际的连接
            httpConn.connect();
            
         // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
//            in = new BufferedReader(new InputStreamReader(httpConn
//                    .getInputStream(), "UTF-8"));
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	 /**
     * @param content
     * @param charset
     * @return
     * @throws
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
	/**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    static String sign(String text, String key, String input_charset) {
    	text =  key+text +key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    /**
     * 签名排序
     * @param paramValues
     * @return
     */
    public static String getOrderSign(Map<String, String> paramValues) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            Collections.sort(paramNames);
            for (String paramName : paramNames) {
                sb.append(paramValues.get(paramName));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeErrorException(null,"");
        }
    }
    
    /**
     * 发送GET请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, String parameters) {
        String result="";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = parameters;// 编码之后的参数
        PrintWriter out=null;
        try {
            
            String full_url = url + "" + params;
            System.out.println(full_url);
            // 创建URL对象
            URL connURL = new URL(full_url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDI4NzQ5MDEsImV4cCI6MTU0Mjk2MTMwMSwidWlkIjoiMTAwNTU5In0.tEiu-_YMG5lW9uaLkeKOHy5WviimRjze0cL_XgbbtzI");
            httpConn.setRequestProperty("content-type", "application/json"); // 设置发送数据的格式
            //User-Agent: {name}/{version}  X-Request-ID: request-id
            httpConn.setRequestProperty("User-Agent", "{HL}/{1.0.0}"); 
            httpConn.setRequestProperty("X-Request-ID", ""+(new Random(1).nextInt()*1000000)); 
            // 建立实际的连接
            httpConn.connect();
            
//            // 获取HttpURLConnection对象对应的输出流
//            out = new PrintWriter(httpConn.getOutputStream());
//            // 发送请求参数
//            out.write(params);
//            // flush输出流的缓冲
//            out.flush();
            
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
            	if(out!=null){
            		out.close();
            	}
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result ;
    }

    /**
     * 发送GET请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String result="";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            System.out.println(full_url);
            // 创建URL对象
            URL connURL = new URL(full_url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDI4NzQ5MDEsImV4cCI6MTU0Mjk2MTMwMSwidWlkIjoiMTAwNTU5In0.tEiu-_YMG5lW9uaLkeKOHy5WviimRjze0cL_XgbbtzI");
            httpConn.setRequestProperty("content-type", "application/json"); // 设置发送数据的格式
            //User-Agent: {name}/{version}  X-Request-ID: request-id
            httpConn.setRequestProperty("User-Agent", "{HL}/{1.0.0}"); 
            httpConn.setRequestProperty("X-Request-ID", ""+(new Random(1).nextInt()*1000000)); 
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result ;
    }

    public static String sendPost(String url, Map<String, String> parameters, Map<String, String> map) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        String params ="";// 编码之后的参数
        StringBuffer sb = new StringBuffer();// 处理请求参数
        try {
        	 
	         // 编码请求参数
	         if (parameters.size() == 1) {
	             for (String name : parameters.keySet()) {
	                 sb.append(name).append("=").append(
	                         java.net.URLEncoder.encode(parameters.get(name),
	                                 "UTF-8"));
	             }
	             params = sb.toString();
	         } else {
	             for (String name : parameters.keySet()) {
	                 sb.append(name).append("=").append(
	                         java.net.URLEncoder.encode(parameters.get(name),
	                                 "UTF-8")).append("&");
	             }
	             String temp_params = sb.toString();
	             params = temp_params.substring(0, temp_params.length() - 1);
	         }
        	System.out.println("params   "+params);
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("content-type", "application/json"); // 设置发送数据的格式
            //User-Agent: {name}/{version}  X-Request-ID: request-id
            httpConn.setRequestProperty("User-Agent", "{HL}/{1.0.0}"); 
            httpConn.setRequestProperty("X-Request-ID", ""+(new Random(1).nextInt()*1000000)); 
     
            if(map!=null){
            	for(Map.Entry<String, String> entry : map.entrySet()){
            		//设置编码
            		httpConn.setRequestProperty(entry.getKey(),
            				java.net.URLEncoder.encode(entry.getValue(),
	                                 "UTF-8"));
            	}
            }
           
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
         
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;//读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            
            //System.out.println("url:\n"+params);
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            
          //Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)
         //Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)
            
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            httpConn.setRequestProperty("Authorization",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");

            //httpConn.setReadTimeout(5000);
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
         
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    
    
    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址

     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, String param, Map<String, String> map) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        String params ="";// 编码之后的参数
        try {
        	if(param!=null){
//        		params = java.net.URLEncoder.encode(param,
//                        "UTF-8");	//UTF-8
        		
        		params = param;	//UTF-8
        	}
        	System.out.println("params   "+params);
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("content-type", "application/json"); // 设置发送数据的格式
            //User-Agent: {name}/{version}  X-Request-ID: request-id
            httpConn.setRequestProperty("User-Agent", "{HL}/{1.0.0}"); 
            httpConn.setRequestProperty("X-Request-ID", ""+(new Random(1).nextInt()*1000000)); 
     
            if(map!=null){
            	for(Map.Entry<String, String> entry : map.entrySet()){
            		//设置编码
            		httpConn.setRequestProperty(entry.getKey(),entry.getValue());
            	}
            }
           
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
         
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
 
    
    /**
     * 发送POST请求   body传递参数
     *

     *            请求参数，Map类型。
     * @return 远程响应结果
     * @throws UnsupportedEncodingException 
     */
    public static String sendPost(String strURL,String param,Map<String,String> map,String n) throws UnsupportedEncodingException {
       
		String result = "";// 返回的结果
        OutputStreamWriter out = null;
        InputStream is = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "*"); // 设置接收数据的格式
            connection.setRequestProperty("content-type", "application/json"); // 设置发送数据的格式
            //User-Agent: {name}/{version}  X-Request-ID: request-id
            connection.setRequestProperty("User-Agent", "{HL}/{1.0.0}"); 
            connection.setRequestProperty("X-Request-ID", ""+(new Random(1).nextInt()*1000000)); 
            if(map!=null){
            	for(Map.Entry<String, String> entry : map.entrySet()){
            		//设置编码
            		connection.setRequestProperty(entry.getKey(),entry.getValue());
            	}
            }
            connection.connect();

            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(param);
            out.flush();
            out.close();

            // 读取响应
            is = connection.getInputStream();
            int length = (int) connection.getContentLength();// 获取长度
            System.out.println(length);
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                result = new String(data, "UTF-8"); // utf-8编码
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
  
        return result;
    }
    
    /**
     * 测试自己加密的接口
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * 
     * 秘钥 ascfghh    token
     */
    public static void testPostHl() throws JSONException, UnsupportedEncodingException{
    	 
    	String jsonStr="{\n" +
				"\t\"sale\": [{\n" +
				"\t\t\"actualMoney\": \"25.30\",\n" +
				"\t\t\"saleTime\": \"2018-11-24 16:44:30\",\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"saleTypeName\": \"支付宝支付\",\n" +
				"\t\t\"saleType\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"discountMoney\": \"4.70\",\n" +
				"\t\t\"saleAllMoney\": \"30\",\n" +
				"\t\t\"cSheetNo\": \"10\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t \"returnSale\": \"1\",\n" +
				"\t\t\"returnTime\": \"2018-03-02 15:12:32\",\n" +
				"\t\t\"returncSheetNo\": \"08\",\n" +
				"\t\t\"beizhu\": \"退货\"\n" +
				"\t}],\n" +
				"\t\"saleDatial\": [{\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"10\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"0\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"10\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"1\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"10\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"2\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}]\n" +
				"}";
		String danhao="04";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");
		System.out.println(jsonStr);
    	Map<String, String> form = new HashMap<String, String>();

    	form.put("requestid", "abc");

		Map<String, String> params = new HashMap<String, String>();
		params.put("cBarCode", "6928804011258");
		params.put("zmy", "张");
		params.put("cSheetNo", "10");
		/**
	     * 查询所有商品
	     * @param cStoreNo
	     * @param pageNum
	     * @param number
	     * @param cardno
	     * @return
	     */

		params.put("cStoreNo1", "000");
		params.put("pageNum", "1");
		params.put("number", "2");
		params.put("data", jsonStr);

		params.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDMzMDQ0MTEsInVzZXJuYW1lIjoie1wiYXBwSWRcIjpcImFiY1wiLFwidXNlckxpc3RcIjpbe1wiYXBwU2VyY2V0XCI6XCJhc2NmZ2hoXCIsXCJhcHBJZFwiOlwiYWJjXCIsXCJ1c2VyTmFtZVwiOlwi5Yi36IS45pSv5LuYXCJ9XSxcInJhbmRvd1wiOlwiMTU0MzMwNDI5MTM5OVwifSJ9.hu1J-WEXWJ6jsN9Dw5V8-uttKefuC1B38IXcUFKw2Gg");
		System.out.println(sign(getOrderSign(params),"ascfghh","UTF-8"));

		params.put("sign", sign(getOrderSign(params),"ascfghh","UTF-8").toUpperCase());

		 StringBuffer sb = new StringBuffer();// 处理请求参数

		 String param="";

	     // 编码请求参数
	     if (params.size() == 1) {
	         for (String name : params.keySet()) {
	             sb.append(name).append("=").append(
	                     java.net.URLEncoder.encode(params.get(name),
	                             "UTF-8"));
	         }
	         param=sb.toString();
	     } else {
	         for (String name : params.keySet()) {
	             sb.append(name).append("=").append(
	                     java.net.URLEncoder.encode(params.get(name),
	                             "UTF-8")).append("&");
	         }
	         String temp_params = sb.toString();
	         param = temp_params.substring(0, temp_params.length() - 1);
	     }

		//sendPost(String url, Map<String, String> parameters, Map<String, String> map)
     //http://localhost:12379/Hlyf/api/getGoodsInfo
     //
        String result =sendPost("http://47.104.246.36:12379/Hlyf/api/getOrder?"+param,form, form);

        System.out.println(result);
    }

    public static void testPostJson() throws JSONException, UnsupportedEncodingException{
  
    	Map<String, String> form = new HashMap<String, String>();
		//6928804011258|6928804011159|6925788301269|6925788301269
		form.put("cStoreNo1", "0002");
		form.put("cBarcode", "6928804011258");
		form.put("num", "122222222222222222222222222222222222222222222222222222222222222222");
		form.put("cardno", "0");
		form.put("appId", "abc");
		// { "secret" : "15423504379395" }
		
		//http://localhost:12379/Scan/angus/zmy
		
		JSONObject json=new JSONObject();
		json.put("test", "15423504379395");
		//http://boss.cuntutu.com/client/login
    	String result =sendPost("https://www.airable.club/wxcard/index/test",json.toString(),null);
        System.out.println(result);
    }
    
    

    /**
     * 主函数，测试请求
     *
     * @param args
     * @throws JSONException 
     */
    public static void main(String[] args) throws JSONException {
        //test1();
//    	testThread() ;   
//       testMingShengGoodsSave();//测试通过
//       testMingShengGoodsUpdate();//测试通过
//    	testMingSHshegGoodsImages();//测试通过
//        testMingSHshegGoodsPriceUpdate();  //通
//    	testMingSHshegGoodsLevelUpdate();   //不同
    	
//     	testMingSHshegGoodsKuCunUpdate();   //通
    	
//    	testMingSHshegGoodsBarcodeUpdate();  //通
    	
//   	testMingSHshegGoodsSelect();  //测试通过
    	
    	testMinShengSelectOrder();
    	//testgetGoodsInfo();

    	try {
    		//testBodyMSY();    //获取tocken
    		//testMSYAddGoods();
    		//testMSYupload();//下载  下载地址的
    		//testPostHl();  //测试华隆自己的接口
    		
//    		String url = "http://img.17gaoda.com/cvs/bill/20181115/47edc890728202de414c903765501c78.csv";
//            String path="F:/test/14c903765501c78.csv";
//            downloadPicture(url,path);
    		
//    		for(int i=0;i<10;i++){
//    			new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						try {
//							testPostHl();
//						} catch (UnsupportedEncodingException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} 
//						
//					}
//				}).start();
//    		}
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
    /**
     * 下载数据
     * @throws JSONException  
     */
    public static void testMSYupload() throws JSONException{
    	String parm="{\"date\":2018-11-16}";
    	Map<String, String> form = new HashMap<String, String>();
    	form.put("date", "2018-11-13");
        String result =sendGet("http://boss.cuntutu.com/client/bill",form);
        System.out.println(result);
    }
    public static void testMSYAddGoods() throws JSONException{
    	
    	
		Map<String, String> form = new HashMap<String, String>();
		form.put("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDI4NzQ5MDEsImV4cCI6MTU0Mjk2MTMwMSwidWlkIjoiMTAwNTU5In0.tEiu-_YMG5lW9uaLkeKOHy5WviimRjze0cL_XgbbtzI");

	
		String parm="{" +
                "\"table\": \"item_info\"," +
                "\"columns\": [\"brand_no\", \"id\", \"item_clsno\", \"item_name\", \"item_no\", \"item_size\", \"item_subno\", \"oper_date\", \"price\", \"sale_price\", \"supplier_no\", \"unit_no\", \"vip_price\"]," +
                "\"rows\": [" +
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +","+
                "[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]" +
                "]" +
                "}";
		
        String result =sendPost("http://boss.cuntutu.com/client/upload",parm, form);
        System.out.println(result);
    }
    
    public static void testBodyMSY() throws JSONException{
    	
    	
		Map<String, String> form = new HashMap<String, String>();
		form.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDI1OTg0MTEsImV4cCI6MTU0MjY4NDgxMSwidWlkIjoiMTAwNTU5In0.zEEa7UDrYmoBh6e3GM_OHRSZcLnZq2mrGqVhyT05k9w");

	
		String parm="{\"secret\":15423504379395}";
		
        String result =sendPost("http://boss.cuntutu.com/client/login",parm, form);
        System.out.println(result);
    }
    
    public static void testHeader() throws JSONException{
    	
    	JSONObject json=new JSONObject();
    	json.put("abc", "123");
		Map<String, String> form = new HashMap<String, String>();
		//6928804011258|6928804011159|6925788301269|6925788301269
		form.put("cStoreNo", "0002");
		form.put("cBarcode", "6928804011258");
		form.put("num", "122222222222222222222222222222222222222222222222222222222222222222");
		form.put("cardno", "0");
		form.put("test", "abc");
		

		json.put("test", "15423504379395");
		//http://boss.cuntutu.com/client/login
		
        String result =sendPost("http://boss.cuntutu.com/client/login",json.toString(), form);
        System.out.println(result);
    }
    /*
     * 测试http请求的方法 
     */
    public static void testgetGoodsInfo() throws JSONException{
    	
    	JSONObject json=new JSONObject();
    	json.put("abc", "123");
		Map<String, String> form = new HashMap<String, String>();
		//6928804011258|6928804011159|6925788301269|6925788301269
		form.put("cStoreNo", "0002");
		form.put("cBarcode", "6928804011258");
		form.put("num", "122222222222222222222222222222222222222222222222222222222222222222");
		form.put("cardno", "0");
		
		
        String result =sendPost("http://localhost:12379/Scan/api/getGeneralPreference", form);
        System.out.println(result);
    }
    
    /*
     * 测试http请求的方法 
     */
    public static void testHttp(){
    	Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("supplierId", 2247);	
		goodsSpecvalueMap.put("beginindex", 1);
		
		 Gson gson = new Gson();
	     String str = gson.toJson(goodsSpecvalueMap);
	     System.out.println(str);
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.supplier.order.get");
		//form.put("jsonStr", str);//supplierId=462
		form.put("format", "json");
		form.put("supplierId", "2247");
		form.put("timestamp", String_Tool.DataBaseTime());
		form.put("orderStatus", "3");
		form.put("receiveType", "2");
		form.put("pageIndex", "1");
		form.put("pageSize", "10");
		form.put("type", "0");
		form.put("beginDate", "2018-12-01 12:22:33");
		form.put("endDate", "2018-11-31 12:22:33");
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
        String result =sendPost("http://localhost:12379/HLYFOTHER/angus/zmy", form);
        System.out.println(result);
    }
    /*
     * 查询订单的api
     * http://111.202.180.129:19600/router?
     * format=json&appKey=462&v=1.0
     * &timestamp=2014-05-1 4%2005:22:46&
     * method=minshengec.supplier.order.get&
     * supplierId=462&orderStatus=3
     * &receiv eType=2&pageIndex=1&pageSize=10
     * &consignee=&type=0
     * &beginDate=2013-05-14%2005:22 :46
     * &endDate=2015-05-14%2005:22:46
     * &sign=73E17C336A48634B2D34F21AD6E5C398DDF49E2 9 
     */
    private static void testMinShengSelectOrder() throws JSONException {

		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");              //appId
		form.put("v", "1.0");
		form.put("method", "minshengec.supplier.order.get");
		form.put("format", "json");
		form.put("supplierId", "2247");        //供应商id
		form.put("timestamp", String_Tool.DataBaseTime());
		form.put("orderStatus", "2");          //已完成的订单
		form.put("receiveType", "1");
		form.put("pageIndex", "1");
		form.put("pageSize", "2");
		form.put("type", "0");
		form.put("beginDate", "2018-01-01 12:22:33");
		form.put("endDate", "2018-11-31 12:22:33");
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
        
       JSONObject json=new JSONObject(result);
	     //System.out.println(json.getString("message"));
		
	}

	/*
     * http://111.202.180.129:19600/router?format=json&appKey=2&v=1.0&method=minshengec.goods.search
     * &jsonStr={%22beginindex%22:%221%22,%22supplierId%22:1727,%22endDate%22:%222014 22}
     */
    private static void testMingSHshegGoodsSelect() throws JSONException {
    	
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("supplierId", 2247);	
		goodsSpecvalueMap.put("beginindex", 1);
		
		 Gson gson = new Gson();
	     String str = gson.toJson(goodsSpecvalueMap);
	     System.out.println(str);
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.goods.search");
		form.put("jsonStr", str);
		form.put("format", "json");
		form.put("timestamp", String_Tool.DataBaseTime());
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
        
       JSONObject json=new JSONObject(result);
	     System.out.println(json.getString("message"));
    	
		
	}

	/*
     * http://111.202.180.129:19600/router?
     * format=json&appKey=1&v=1.0&method=minshengec.productyg.barcode.update 
     * &supplierId=1&dtosJson= [{"barCode":123123, "outId ":1},{"barCode":456456, "outId ":2}] 
     */
    private static void testMingSHshegGoodsBarcodeUpdate() {

    	List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("outId", 224714);	
		goodsSpecvalueMap.put("barCode", "224714123");
	
		goodsSpecvalueList.add(goodsSpecvalueMap);
		System.out.println(goodsSpecvalueList);
		 Gson gson = new Gson();
	     String str = gson.toJson(goodsSpecvalueList);
	     System.out.println(str);
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.productyg.barcode.update");
		form.put("dtosJson", str);
		form.put("format", "json");//
		form.put("supplierId", "2247");
		form.put("timestamp", String_Tool.DataBaseTime());
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
    	
		
	}

    /*
     * http://111.202.180.129:19600/router?format=json&appKey=1&v=1.0
     * &method=minshengec.productyg.storage.update &supplierId=1&
     * dtosJson= [{"outId":1, "storage ":10,”houseId”:”1”},{"outId":2, "storage ":10,”houseId”:”1”}] 
     */
    private static void testMingSHshegGoodsKuCunUpdate() {

    	List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("outId", "224714");	
		goodsSpecvalueMap.put("storage", 1);
		goodsSpecvalueMap.put("houseId", 2250);
		goodsSpecvalueList.add(goodsSpecvalueMap);
		System.out.println(goodsSpecvalueList);
		 Gson gson = new Gson();
	     String str = gson.toJson(goodsSpecvalueList);
	     System.out.println(str);
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.productyg.storage.update");
		form.put("dtosJson", str);
		form.put("format", "json");
		form.put("supplierId", "2247");
		form.put("timestamp", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
    	
		
	}

	/*
     * http://111.202.180.129:19600/router?format=json&appKey=1&v=1.0&method=minshengec.productyg.shelves.update 
     * &supplierId=1&dtosJson= [{"outId":1, "is_marketable":1},{"outId":2, "isMarketable":1}]
     */
    	private static void testMingSHshegGoodsLevelUpdate() {
  
    	
    	List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("outId", "224714");	
		goodsSpecvalueMap.put("isMarketable", 1);
		goodsSpecvalueList.add(goodsSpecvalueMap);
		System.out.println(goodsSpecvalueList);
		 Gson gson = new Gson();
	     String str = gson.toJson(goodsSpecvalueList);
	     System.out.println(str);
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.productyg.shelves.update");
		form.put("dtosJson", str);
		form.put("format", "json");//
		form.put("supplierId", "2247");
		form.put("timestamp", String_Tool.DataBaseTime());
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
    	
		
	}

    /*
     * http://111.202.180.129:19600/router?format=json&appKey=1&v=1.0 &method=minshengec.productyg.price.update&supplier_id=1& dtosJson= 
     * [{"outId":1,"price":"5.50","marketPrice":"10.50"},{"outId":2,"price":"6.50","marketPrice":"20.50"}] 
     */
    private static void testMingSHshegGoodsPriceUpdate() throws JSONException {
  

//		List<JSONObject> goods = new ArrayList<JSONObject>();
//		JSONObject goodsSpecvalueMapaa = new JSONObject();
//		goodsSpecvalueMapaa.put("outId", "224714");	
//		goodsSpecvalueMapaa.put("price", 118.51);
//		goodsSpecvalueMapaa.put("marketPrice", 128.51);
//		
//		System.out.println(goodsSpecvalueMapaa.toString());
//		goods.add(goodsSpecvalueMapaa);
//		
//		System.out.println(goods.toString());
		
		
		List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("outId", "224714");	
		goodsSpecvalueMap.put("price", 118.51);
		goodsSpecvalueMap.put("marketPrice", 128.51);
		goodsSpecvalueList.add(goodsSpecvalueMap);
		System.out.println(goodsSpecvalueList);
		 Gson gson = new Gson();
	     String str = gson.toJson(goodsSpecvalueList);
	     System.out.println(str);
	     
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.productyg.price.update");
		form.put("dtosJson", str);
		form.put("format", "json");//
		form.put("supplierId", "2247");
		String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		System.out.println("时间"+time);
		form.put("timestamp",time );
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
		System.out.println(sign);
        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
    	
	}

	/*
     * http://111.202.180.129:19600/router?format=json&appKey=2&v=1.0
     * &method=minshengec.product.imag&jsonStr={"goodsId":1,
     * "image":[{"isMain":1,"orders":1,"photoPath":"http://img.minshengec } 
     * 
     * http://111.231.217.84:8080/ZAKHSURE/images/1539847170615-387_2018_10_18_03_19_30_29.png
     */
    private static void testMingSHshegGoodsImages() {
		// TODO Auto-generated method stub
    	Map<String, Object> paramsMap = new HashMap<String, Object>(); 
    	paramsMap.put("goodsId", "541188");
    	paramsMap.put("specValueId", 9842);
    	
    	List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		
//		for(int i=0;i<5;i++){
//			Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
//			if(i==2){
//				goodsSpecvalueMap.put("isMain", 1);	
//			}else{
//				goodsSpecvalueMap.put("isMain", 0);
//			}
//			
//			goodsSpecvalueMap.put("photoPath", "http://111.231.217.84:8080/ZAKHSURE/images/1539847170615-387_2018_10_18_03_19_30_29.png");
//			goodsSpecvalueMap.put("orders", i+1);
//			
//			goodsSpecvalueList.add(goodsSpecvalueMap);
//		}
    	Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		
		goodsSpecvalueMap.put("isMain", 1);	
		
		
		goodsSpecvalueMap.put("photoPath", "http://111.231.217.84:8080/ZAKHSURE/images/1539847170615-387_2018_10_18_03_19_30_29.png");
		goodsSpecvalueMap.put("orders", 1);
		
		goodsSpecvalueList.add(goodsSpecvalueMap);
		System.out.println(goodsSpecvalueList);
	
		
	
		paramsMap.put("image", goodsSpecvalueList);
		
		 Gson gson = new Gson();
	     String str = gson.toJson(paramsMap);
	     
	     System.out.println(str);
	        
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.product.imag");// <--指定服务名
		form.put("jsonStr", str);
		form.put("format", "json");
		form.put("timestamp", String_Tool.DataBaseTime());
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
		

        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
    	
    	
		
	}

	/*测试修改商品信息
     * http://111.202.180.129:19600/router?format=json&appKey=2&v=1.0&method=minshengec.goods.update&jsonStr={%22goodsPropvalueList%22:[{%22porpvalueId%22:104062,%22propId%22:100004}],%22id%22:0,%22outId%22:%22czw%22,%22style 2goodsSpecvalueList%22:[{%22specId%22:1,%22specOptionName%22:%22%E5%8E%9F%E6%9C%A8%E8%89%B2%22,%22specValueId%22:15}],%22id%22:0,%22isList%22:true,%22isMarketable%22:false,
     * %22marke rId%22:0,%22type%22:0,%22unit%22:%22czw333%22,%22volume%22:1,%22weight%22:1}]} 
     */
	private static void testMingShengGoodsUpdate() {
		
		Map<String, Object> paramsMap = new HashMap<String, Object>(); 
		paramsMap.put("outId", "224714");
		paramsMap.put("supplierId", 2247);
		//paramsMap.put("styleNo", "BOD007002CBJ1");
		
		List<Map<String, Long>> goodsPropvalueList = new ArrayList<Map<String, Long>>();
		Map<String, Long> goodsPropvalueMap = new HashMap<String, Long>();
		goodsPropvalueMap.put("propId", new Long(100281));
		goodsPropvalueMap.put("porpvalueId", new Long(128200));
		goodsPropvalueList.add(goodsPropvalueMap);
		//paramsMap.put("goodsPropvalueList", goodsPropvalueList);
		
		List<Map<String, Object>> writeProductList = new ArrayList<Map<String, Object>>();
		Map<String, Object> writeProductMap = new HashMap<String, Object>();
		//writeProductMap.put("barCode", "6937999600291");
		writeProductMap.put("brand", 511);
		writeProductMap.put("isList", 1);
		writeProductMap.put("isOverseas", 0);
		writeProductMap.put("marketPrice", 17.00);
		writeProductMap.put("price", 13.5);
		writeProductMap.put("memo", "");
		writeProductMap.put("name", "多潘立酮分散片(邦能)");
		writeProductMap.put("outId", "224714");
		writeProductMap.put("productCategory", 795);
		writeProductMap.put("unit", "个");
		writeProductMap.put("weight", 100);
		
		List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("specId", 5);
		goodsSpecvalueMap.put("specValueId", 9842);
		goodsSpecvalueList.add(goodsSpecvalueMap);
		writeProductMap.put("goodsSpecvalueList", goodsSpecvalueList);
		
		writeProductList.add(writeProductMap);
		paramsMap.put("writeProductList", writeProductList);
		
		 Gson gson = new Gson();
	     String str = gson.toJson(paramsMap);
	     
	     System.out.println(str);
	        
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.goods.update");// <--指定服务名
		form.put("jsonStr", str);
		form.put("format", "json");
		form.put("timestamp", String_Tool.DataBaseTime());
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
		

        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
		
	}

	private static void test1() {
		HashMap<String, String> map = new HashMap<String, String>();
        map.put("goodsID", "2000185");
        map.put("amount", "1234");
        map.put("unit", "箱");
        map.put("expireDate", "2017-10-20 10:25:25.000");

		/*
		 * "goodsID": "12345678" , “amount”:123, //商品数量
       “unit”:”箱”, “expireDate”:”2017-10-20-12-35-50” //失效日期
		 */
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("goodsID", "20001849");
        map1.put("amount", "13");
        map1.put("unit", "个");
        map1.put("expireDate", "2017-10-20 10:25:25.000");
        List<HashMap> list=new ArrayList<HashMap>();
        list.add(map);
        list.add(map1);

        System.out.println(map.toString());
        Gson gson = new Gson();
        String str = gson.toJson(map);

        System.out.println(str);

        str = gson.toJson(list);

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("access_token", "张明阳");
        parameters.put("age", "123");

        String result =sendPost("https://api.weixin.qq.com/card/mpnews/gethtml", parameters);
        System.out.println(result);

     //   https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        Map<String, String> ps = new HashMap<String, String>();

        ps.put("grant_type", "client_credential");
        ps.put("appid", "wx7a16caec7c2d6bd8");
        ps.put("secret", "9a81f95779a47b1784d9d46e46cdad39");

        String tocken =sendGet("https://api.weixin.qq.com/cgi-bin/token", ps);
        System.out.println(result);
	}
	
	/*
	 * 搞死服务器
	 */
	private static void testThread() {
		


        for(int i=0;i<300;i++){
        	
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					testMingShengGoodsSave();
					
				}
			}).start();;
        }

        
	}

	private static void testMingShengGoodsSave() {
		// TODO Auto-generated method stub
		
		
		Map<String, Object> paramsMap = new HashMap<String, Object>(); 
		paramsMap.put("outId", "224714");
		paramsMap.put("supplierId", 2247);
		//paramsMap.put("styleNo", "BOD007002CBJ1");
		
		List<Map<String, Long>> goodsPropvalueList = new ArrayList<Map<String, Long>>();
		Map<String, Long> goodsPropvalueMap = new HashMap<String, Long>();
		goodsPropvalueMap.put("propId", new Long(100281));
		goodsPropvalueMap.put("porpvalueId", new Long(128200));
		goodsPropvalueList.add(goodsPropvalueMap);
		//paramsMap.put("goodsPropvalueList", goodsPropvalueList);
		
		List<Map<String, Object>> writeProductList = new ArrayList<Map<String, Object>>();
		Map<String, Object> writeProductMap = new HashMap<String, Object>();
		//writeProductMap.put("barCode", "6937999600291");
		writeProductMap.put("brand", 511);
		writeProductMap.put("isList", 1);
		writeProductMap.put("isOverseas", 0);
		writeProductMap.put("marketPrice", 17.00);
		writeProductMap.put("price", 13.5);
		writeProductMap.put("memo", "");
		writeProductMap.put("name", "多潘立酮分散片(邦能)");
		writeProductMap.put("outId", "224714");
		writeProductMap.put("productCategory", 795);
		writeProductMap.put("unit", "个");
		writeProductMap.put("weight", 100);
		
		List<Map<String, Object>> goodsSpecvalueList = new ArrayList<Map<String, Object>>();
		Map<String, Object> goodsSpecvalueMap = new HashMap<String, Object>();
		goodsSpecvalueMap.put("specId", 5);
		goodsSpecvalueMap.put("specValueId", 9842);
		goodsSpecvalueList.add(goodsSpecvalueMap);
		writeProductMap.put("goodsSpecvalueList", goodsSpecvalueList);
		
		writeProductList.add(writeProductMap);
		paramsMap.put("writeProductList", writeProductList);
		
		 Gson gson = new Gson();
	     String str = gson.toJson(paramsMap);
	     
	     System.out.println(str);
	        
		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", "462");
		form.put("v", "1.0");
		form.put("method", "minshengec.goods.save");// <--指定服务名
		form.put("jsonStr", str);
		form.put("format", "json");
		form.put("timestamp", String_Tool.DataBaseTime());
		String sign = RopUtils.sign(form, "438F6241DD9511E4B9E70050568E0E81");
		form.put("sign", sign);
		

        String result =sendPost("http://111.198.131.250:19600/router", form);
        System.out.println(result);
		
	}

}
