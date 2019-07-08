package com.springcloud.microserviceeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 使用该注解表示为eureka-server端
public class MicroserviceEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEurekaApplication.class, args);
    }

}
