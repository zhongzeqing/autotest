package com.yunche.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONParser {
    JSONObject internalJSON;

    public String getCity(JSONObject jo) {
        String city = "";
        try {
     //先获取反馈中的result这个一个内部JSON对象　
            JSONObject internalJSON = jo.getJSONObject("result");
            //再根据键名查找键值
            String province = internalJSON.getString("city");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }
}