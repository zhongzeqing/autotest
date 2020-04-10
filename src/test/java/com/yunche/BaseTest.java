package com.yunche;

import cn.hutool.http.GlobalHeaders;
import com.yunche.util.HttpHelper;
import org.apache.commons.httpclient.Cookie;
import org.testng.annotations.BeforeTest;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BaseTest {

    public static final String UIC_URL = "http://172.16.8.54";
    public static final String URL = "http://172.16.8.54:81";
    public static final String USERNAME = "admin@dtstack.com";
    public static final String PASSWORD = "admin123";
    public static final String VERIFY_CODE = "123";
    public static final String TENANT_ID = "1";

    public static final String dt_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0ZW5hbnRfaWQiOiIxIiwidXNlcl9pZCI6IjEiLCJ1c2VyX25hbWUiOiJhZG1pbkBkdHN0YWNrLmNvbSIsImV4cCI6MTYxNDMwMjQ2MCwiaWF0IjoxNTgzMTk4NDY3fQ.CwfFYqmvSwlAEqPPSglQk2YQ0lehhHtFb1P6ILMov0s";

    /****************************************************************
     * 用户登录模块
     ****************************************************************/

    public static final String LOGIN="/uic/api/v2/account/login";
    public static final String SWITCH_TENANT="/uic/api/v2/account/user/switch-tenant";


    /****************************************************************
     * 主机管理
     ****************************************************************/
    public static final String HOSTPAGE="/log/api/v2/host/page";

    /****************************************************************
     * 主机组管理
     ****************************************************************/
    public static final String HOSTGROUPLIST="/log/api/v2/host/group/list";
    public static final String HOSTGROUPADD="/log/api/v2/host/group/update";
    public static final String HOSTGROUPDELETE="/log/api/v2/host/group/";





    @BeforeTest
    public void before() {

        /**登录接口*/
        String url1 = UIC_URL + LOGIN+ "?username="+USERNAME+"&password="+PASSWORD+"&verify_code="+VERIFY_CODE;
        String dt_token = HttpHelper.instance.postForm(url1);

        /**切换租户接口*/
        String url2 = UIC_URL + SWITCH_TENANT + "?tenantId="+TENANT_ID;
        String res = HttpHelper.instance.postForm(url2);

        /**获取cookie*/
        Cookie[] cookies = HttpHelper.client.getState().getCookies();
        String cookie = Arrays.stream(cookies).map(c -> c.getName() +"="+ c.getValue()).collect(Collectors.joining(";"));
        GlobalHeaders.INSTANCE.header("Cookie",cookie);

    }
}
