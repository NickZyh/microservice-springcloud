package com.springcloud.microservicezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy // 开启网关功能
public class MicroserviceZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceZuulApplication.class, args);
    }

}
