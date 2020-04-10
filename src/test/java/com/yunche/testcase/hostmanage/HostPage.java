package com.yunche.testcase.hostmanage;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yunche.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HostPage extends BaseTest {
    @Test(dataProvider = "normal_data")
    public void hostPage(boolean isASC,String keyword,String orderField,int pageNum,int pageSize){
        String url = URL+HOSTPAGE;

        JSONObject persistParam = new JSONObject();
        persistParam.put("isASC",isASC);
        persistParam.put("keyword",keyword);
        persistParam.put("orderField",orderField);
        persistParam.put("pageNum",pageNum);
        persistParam.put("pageSize",pageSize);
        String res = HttpUtil.post(url, JSON.toJSONString(persistParam));
        String code = JSONPath.read(res,"$.result_code").toString();
        String message = JSONPath.read(res,"$.result_message").toString();
        Assert.assertEquals("1",code);
        Assert.assertEquals("执行成功",message);
    }
    @DataProvider
    public Object[][] normal_data(){
        return new Object[][]{
                {false,null,"id",1,10},
        };
    }
}
