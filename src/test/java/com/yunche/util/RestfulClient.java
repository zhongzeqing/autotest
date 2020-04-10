package com.yunche.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class RestfulClient {
    CloseableHttpClient httpclient;
    HttpGet httpGet;
    HttpPost httpPost;
    CloseableHttpResponse httpResponse;
    int responseCode;
    JSONObject responseBody;
    HashMap<String, String> responseHeads;

    //通过httpclient获取请求的反馈
    public void getResponse(String url) throws ClientProtocolException, IOException {
        httpclient = HttpClients.createDefault();
        httpGet = new HttpGet(url);
        httpResponse = httpclient.execute(httpGet);
    }

    //以JSON格式获取到反馈的主体
    public JSONObject getBodyInJSON() throws ParseException, IOException {
        HttpEntity entity;
        String entityToString;
        entity = httpResponse.getEntity();
        entityToString = EntityUtils.toString(entity);
        responseBody = JSON.parseObject(entityToString);

        System.out.println("This is your response body" + responseBody);

        return responseBody;
    }

    //以哈希图的方式获取到反馈头部
    public HashMap<String, String> getHeaderInHash() {
        Header[] headers;
        headers = httpResponse.getAllHeaders();

        responseHeads = new HashMap<String, String>();

        for (Header header : headers) {
            responseHeads.put(header.getName(), header.getValue());
        }

        System.out.println("This is your response header" + responseHeads);

        return responseHeads;
    }

    //获取反馈状态码
    public int getCodeInNumber() {
        responseCode = httpResponse.getStatusLine().getStatusCode();

        System.out.println("This is your response code" + responseCode);

        return responseCode;
    }


    //通过httpclient获取post请求的反馈
    public void sendPost(String url, List<NameValuePair> params, HashMap<String, String> headers) throws ClientProtocolException, IOException{
        //创建post请求对象
        httpPost = new HttpPost(url);

        //设置请求主体格式
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //设置头部信息
        Set<String> set = headers.keySet();
        for(Iterator<String> iterator = set.iterator(); iterator.hasNext();){
            String key = iterator.next();
            String value = headers.get(key);
            httpPost.addHeader(key, value);
        }
//        httpResponse = httpclient.execute(httpPost);
    }
}