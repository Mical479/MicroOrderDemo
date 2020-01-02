package com.test.ordereureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OrderEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderEurekaApplication.class, args);
    }

}
