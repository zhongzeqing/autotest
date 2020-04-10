package com.yunche.testcase.hostgroup;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yunche.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HostGroupAdd extends BaseTest {
    @Test(dataProvider = "normal_data",groups = {"hostGroup","hostGroupAdd"})
    public void hostGroupAdd(String groupName,String groupDesc) {
        String url = URL+HOSTGROUPADD;

        JSONObject persistParam = new JSONObject();
        persistParam.put("groupNAme",groupName);
        persistParam.put("groupDesc",groupDesc);
        String res = HttpUtil.post(url, JSON.toJSONString(persistParam));
        String code = JSONPath.read(res,"$.result_code").toString();
        String message = JSONPath.read(res,"$.result_message").toString();
        Assert.assertEquals("1",code);
        Assert.assertEquals("执行成功",message);
    }
    @DataProvider
    public Object[][] normal_data(){
            return new Object[][]{
                    {"接口自动化测试主机组","AutotestGroup"}
            };
    }
}
