package com.test.mygateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MicalJ
 * @create 2019/12/26 12:10
 **/
@Configuration
public class AllConfig {

    @Bean
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }
}
