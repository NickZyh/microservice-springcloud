package com.springcloud.microserviceconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer // 开启配置中心server
public class MicroserviceConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceConfigApplication.class, args);
    }

}
