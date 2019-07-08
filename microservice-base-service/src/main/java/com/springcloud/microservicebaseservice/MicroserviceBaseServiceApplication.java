package com.springcloud.microservicebaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 使用该注解表示eureka客户端,也可以使用@EnableDiscoveryClient,两者没区别,只是@EnableDiscoveryClient是eureka原生的
public class MicroserviceBaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceBaseServiceApplication.class, args);
    }

}
