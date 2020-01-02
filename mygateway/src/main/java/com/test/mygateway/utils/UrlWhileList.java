package com.test.mygateway.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MicalJ
 * @create 2019/12/5 16:52
 **/

@Configuration
public class UrlWhileList implements InitializingBean {

    private final static List<String> URL_LIST = new ArrayList<String>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //后台-获取图形验证码
//        URL_LIST.add("/xxx1-service/v1/validateCode");
//        //APP登录注册
//        URL_LIST.add("/xxx2-service/v1/token/app/login/*");
//        URL_LIST.add("/xxx3-service/v1/token/app/register/*");
        //网页登录注册
        URL_LIST.add("/user/*");
        URL_LIST.add("/static/*");
        //获取短信验证码
//        URL_LIST.add("/xxx6-service/v1/message/login");
    }

    public static List<String> getUrlList() {
        return URL_LIST;
    }
}
