package com.yunche.testcase.hostgroup;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONPath;
import com.yunche.BaseTest;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

//主机组管理列表
public class HostGroupList extends BaseTest {


    @Test(groups = {"hostGroup","hostGroupList"})
    public void hostGroupList(ITestContext context) {
        String url = URL+HOSTGROUPLIST;
        String res = HttpUtil.get(url);
        String code = JSONPath.read(res,"$.result_code").toString();
        String message = JSONPath.read(res,"$.result_message").toString();
        String id = JSONPath.read(res,"$.data[?(@.groupName = '接口自动化测试主机组')][0].id").toString();
        System.out.println(id);
        Assert.assertEquals("1",code);
        Assert.assertEquals("执行成功",message);
        context.setAttribute("hostGroupId",id);
    }
}

