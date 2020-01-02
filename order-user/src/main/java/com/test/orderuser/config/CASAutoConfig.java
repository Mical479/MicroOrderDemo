package com.test.orderuser.config;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * CAS客户端过滤配置
 *
 * @author MicalJ
 * @create 2019/11/28 14:39
 **/
//@Configuration
public class CASAutoConfig {

//    @Value("${cas.server-url-prefix}")
//    private String serverUrlPrefix;
//
//    @Value("${cas.server-login-url}")
//    private String serverLoginUrl;
//
//    @Value("${cal.client-host-url")
//    private String clientHostUrl;
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new AuthenticationFilter());
//        //设定匹配路径
//        registrationBean.addUrlPatterns("/*");
//        Map<String, String> initParams = new HashMap<>();
//        initParams.put("casServerLoginUrl", serverUrlPrefix);
//        initParams.put("serverName", clientHostUrl);
//        //忽略的url，“|”分割多个url
//        initParams.put("ignorePattern", "/index");
//        registrationBean.setInitParameters(initParams);
//        //设定加载顺序
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

}
