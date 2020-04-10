package com.yunche.util;



import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpHelper {


    public static String dt_token = "eyJkYXRlIjoxNTY1MzE4ODU3OTMyLCJ1aWQiOiJtdWZlbmciLCJ0eXAiOiJKV1QiLCJ0aW1lIjoxNTY1MzE4ODU3OTMyLCJhbGciOiJIUzI1NiJ9eyJuYW1lIjoieXVubmFuemhvbmd5YW4iLCJ0eXBlIjoiand0In00on5ULgWICwSDJ16fokvTkOPvbrAFsfLoIh2qYsM";//定义一个放置cookie的map

    public static String refreshToken = "";//定义一个放置cookie的map

    public static String Cookie = "";


    private final static Logger logger = Logger.getLogger(HttpHelper.class);

    private final static String OPERATER_NAME = "【http操作】";

    private final static int SUCCESS = 200;

    private final static String UTF8 = "UTF-8";

    public static HttpClient client;

    public static HttpHelper instance = new HttpHelper();

    public static CloseableHttpClient httpClient;

    /**
     * 私有化构造器
     */
    private HttpHelper() {
        HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(5000);
        params.setSoTimeout(20000);
        params.setDefaultMaxConnectionsPerHost(1000);
        params.setMaxTotalConnections(1000);
        client = new HttpClient(httpConnectionManager);
        client.getParams().setContentCharset(UTF8);
        client.getParams().setHttpElementCharset(UTF8);

        httpClient = new DefaultHttpClient();
    }

    /**
     * 解决url中包含中文以及特殊字符的问题
     * @param param
     * @return
     * @throws Exception
     */
    public String getEncode(String param) throws Exception{
        return URLEncoder.encode(param,"UTF-8");
    }


    /**
     * get请求
     *
     * @param url
     * @return
     */
    public String get(String url) {
        return instance.doGet(url);
    }

    private String doGet(String url) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始get通信，目标host:" + url);

            HttpMethod method = new GetMethod(url.toString());
            method.addRequestHeader("token", dt_token);

//            method.addRequestHeader("refreshToken",refreshToken);
            method.getParams().setContentCharset(UTF8);
            method.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
            try {
                client.executeMethod(method);
            } catch (HttpException e) {
                logger.error(new StringBuffer("发送HTTP GET给\r\n").append(url).append("\r\nHTTP异常\r\n"), e);
            } catch (IOException e) {
                logger.error(new StringBuffer("发送HTTP GET给\r\n").append(url).append("\r\nIO异常\r\n"), e);
            }

            if (method.getStatusCode() == SUCCESS) {
                try {
                    respStr = method.getResponseBodyAsString();
                    logger.info("token=" + method.getRequestHeader("token").toString());
//                    logger.info("refreshToken="+method.getRequestHeader("refreshToken").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                logger.error("请求报错");
                return null;
            }

            method.releaseConnection();

            logger.info(OPERATER_NAME + "通讯完成，返回码：" + method.getStatusCode());
            logger.info(OPERATER_NAME + "返回内容：" + method.getResponseBodyAsString());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);
        } catch (Exception e) {
            logger.info(OPERATER_NAME, e);
        }
        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }


    /**
     * get请求
     *
     * @param url
     * @return
     */
    public String get_notoken(String url) {
        return instance.doGet_notoken(url);
    }

    private String doGet_notoken(String url) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始get通信，目标host:" + url);
            HttpMethod method = new GetMethod(url.toString());
            method.getParams().setContentCharset(UTF8);
            method.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
            try {
                client.executeMethod(method);
            } catch (HttpException e) {
                logger.error(new StringBuffer("发送HTTP GET给\r\n").append(url).append("\r\nHTTP异常\r\n"), e);
            } catch (IOException e) {
                logger.error(new StringBuffer("发送HTTP GET给\r\n").append(url).append("\r\nIO异常\r\n"), e);
            }

            if (method.getStatusCode() == SUCCESS) {
                try {
                    respStr = method.getResponseBodyAsString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                logger.error("请求报错");
                return null;
            }


            method.releaseConnection();

            logger.info(OPERATER_NAME + "通讯完成，返回码：" + method.getStatusCode());
            logger.info(OPERATER_NAME + "返回内容：" + method.getResponseBodyAsString());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);
        } catch (Exception e) {
            logger.info(OPERATER_NAME, e);
        }
        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }

    /**
     * 有参数的post请求
     *
     * @param url
     * @param content
     * @return
     */
    public String post(String url, String content) {
        return instance.doPost(url, content);
    }

    private String doPost(String url, String content) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始post通信，目标host:" + url);
            logger.info("通信内容:" + url);

            PostMethod post = new PostMethod(url);
            RequestEntity requestEntity = new StringRequestEntity(content, "application/json;charse=UTF-8", UTF8);
            post.setRequestEntity(requestEntity);
            post.addRequestHeader("token", dt_token);
            // 设置格式
            post.getParams().setContentCharset(UTF8);
            post.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
            client.executeMethod(post);
            if (post.getStatusCode() == SUCCESS) {
                respStr = post.getResponseBodyAsString();
                logger.info("cookies=" + client.getState().getCookies());
                logger.info("token=" + post.getRequestHeader("token").toString());
            }else {
                logger.error("请求报错");
                return null;
            }


            logger.info(OPERATER_NAME + "通讯完成，返回码：" + post.getStatusCode());
            logger.info(OPERATER_NAME + "返回内容：" + post.getResponseBodyAsString());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);

            post.releaseConnection();
        } catch (Exception e) {
            logger.error(OPERATER_NAME, e);
        }

        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }

    /**
     * 无参数的post请求
     *
     * @param url
     * @return
     */
    public String post(String url) {
        return instance.doPost(url);
    }

    private String doPost(String url) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始post通信，目标host:" + url);

            PostMethod post = new PostMethod(url);
            post.addRequestHeader("token", dt_token);

            // 设置格式
            post.getParams().setContentCharset(UTF8);
            post.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
            client.executeMethod(post);
            if (post.getStatusCode() == SUCCESS) {
                respStr = post.getResponseBodyAsString();
                logger.info("token=" + post.getRequestHeader("token").toString());
            }else {
                logger.error("请求报错:"+post.getResponseBodyAsString());
                return null;
            }


            logger.info(OPERATER_NAME + "通讯完成，返回码：" + post.getStatusCode());
            logger.info(OPERATER_NAME + "返回内容：" + post.getResponseBodyAsString());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);

            post.releaseConnection();
        } catch (Exception e) {
            logger.error(OPERATER_NAME, e);
        }

        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }

    /**
     * form表单 post 请求
     *
     * @param url
     * @return
     */
    public String postForm(String url) {
        return instance.formPost(url);
    }

    private String formPost(String url) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始post通信，目标host:" + url);

            PostMethod post = new PostMethod(url);
//            post.addRequestHeader("token", token);

//            post.addRequestHeader("validToken", validToken);
            // 设置格式
            post.getParams().setContentCharset(UTF8);
            post.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            client.executeMethod(post);
            if (post.getStatusCode() == SUCCESS) {
                respStr = post.getResponseBodyAsString();
                for (Cookie cookie : client.getState().getCookies()) {
                    if (cookie.getName().equals("dt_token")) {
                        logger.info("token=" + cookie.getValue());
                        respStr = cookie.getValue();
                        break;
                    }
                }

            }else {
                logger.error("请求报错:"+ post.getResponseBodyAsString());
                return null;
            }


            logger.info(OPERATER_NAME + "通讯完成，返回码：" + post.getStatusCode());
            logger.info(OPERATER_NAME + "返回内容：" + post.getResponseBodyAsString());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);

            post.releaseConnection();
        } catch (Exception e) {
            logger.error(OPERATER_NAME, e);
        }

        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }


    /**
     * 参数为json的post请求
     *
     * @param url
     * @return
     */
    public String postWithJson(String url, JSONObject jsonObject) {
        return instance.doPostWithJson(url, jsonObject);
    }

    private String doPostWithJson(String url, JSONObject jsonObject) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始post通信，目标host:" + url);
            logger.info("请求参数为：" + jsonObject.toString());

            HttpPost post = new HttpPost(url);
            post.setHeader("token", dt_token);
//            post.setHeader("Content-Type", "application/json");

            StringEntity se = new StringEntity(jsonObject.toJSONString(),Charset.forName("UTF-8"));

            se.setContentType("application/json");
//            se.setContentEncoding("UTF-8");


//            System.out.println(IOUtils.toString(se.getContent(),"UTF-8"));
            post.setEntity(se);


            HttpResponse response = null;

            response = httpClient.execute(post);


            if (response.getStatusLine().getStatusCode() == SUCCESS) {

//                respStr = IOUtils.toString(response.getEntity().getContent(), UTF8);
                logger.info("token=" + post.getHeaders("token")[0].toString());
            }else {
                logger.error("请求报错");
                return null;
            }


            logger.info(OPERATER_NAME + "通讯完成，返回码：" + response.getStatusLine().getStatusCode());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);

            post.releaseConnection();

        } catch (Exception e) {
            logger.error(OPERATER_NAME, e);
        }

        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }


    /**
     * 多参数
     *
     * @param url
     * @param content
     * @param token
     * @param refreshValue
     * @return
     */

    public String post_1(String url, String content, String token, String refreshValue) {
        return instance.dopost_1(url, content, token, refreshValue);
    }

    private String dopost_1(String url, String content, String token, String refreshValue) {
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始post通信，目标host:" + url);
            logger.info("通信内容:" + url);

            PostMethod post = new PostMethod(url);
            RequestEntity requestEntity = new StringRequestEntity(content, "application/json;charse=UTF-8", UTF8);
            post.setRequestEntity(requestEntity);
            post.addRequestHeader("token", token);
            post.addRequestHeader("refreshToken", refreshValue);
            // 设置格式
            post.getParams().setContentCharset(UTF8);
            post.setRequestHeader("Accept-Language","zh-cn,zh;q=0.5");
            client.executeMethod(post);
            if (post.getStatusCode() == SUCCESS) {
                respStr = post.getResponseBodyAsString();
                logger.info("token=" + post.getRequestHeader("token").toString());
                logger.info("refreshToken=" + post.getRequestHeader("refreshToken").toString());
            }else {
                logger.error("请求报错");
                return null;
            }


            logger.info(OPERATER_NAME + "通讯完成，返回码：" + post.getStatusCode());
            logger.info(OPERATER_NAME + "返回内容：" + post.getResponseBodyAsString());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);

            post.releaseConnection();
        } catch (Exception e) {
            logger.error(OPERATER_NAME, e);
        }

        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }

    public String post_2(String url, Map<String,String> map){
        return instance.doPost_2(url,map);
    }
    private String doPost_2(String url, Map<String,String> map){
        long beginTime = System.currentTimeMillis();
        String respStr = StringUtils.EMPTY;
        try {
            logger.info(OPERATER_NAME + "开始post通信，目标host:" + url);
            HttpPost post = new HttpPost(url);
            List<BasicNameValuePair> pair =new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String,String> entry:
                    map.entrySet()) {
                pair.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(pair));
            post.setHeader("token",dt_token);
            //post.setHeader("Content-Type", "application/json;charset=UTF-8");
            HttpResponse response = null;

            response = httpClient.execute(post);


            if (response.getStatusLine().getStatusCode() == SUCCESS) {

//                respStr = IOUtils.toString(response.getEntity().getContent(),UTF8);
                logger.info("token="+post.getHeaders("token")[0].toString());
            }else {
                logger.error("请求报错:"+ EntityUtils.toString(response.getEntity()));
                return null;
            }


            logger.info(OPERATER_NAME + "通讯完成，返回码：" + response.getStatusLine().getStatusCode());
            logger.info(OPERATER_NAME + "结束..返回结果:" + respStr);

            post.releaseConnection();
        }catch (Exception e){
            e.getMessage();
        }
        long endTime = System.currentTimeMillis();
        logger.info(OPERATER_NAME + "共计耗时:" + (endTime - beginTime) + "ms");

        return respStr;
    }
}

