package com.yunche.testcase.hostgroup;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yunche.BaseTest;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class HostGroupDelete extends BaseTest {

    @Test(groups = {"hostGroup","hostGroupDelete"} )

        public void hostGroupDelete(ITestContext context) {
            String url = URL+HOSTGROUPDELETE;
            Object id = context.getAttribute("hostGroupId");
            String res = HttpUtil.post(url+"/"+id+"/delete","");
            System.out.println(res);

        }
}
