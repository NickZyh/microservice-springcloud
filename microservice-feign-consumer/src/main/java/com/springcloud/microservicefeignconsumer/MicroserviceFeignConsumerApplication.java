package com.springcloud.microservicefeignconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard
public class MicroserviceFeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceFeignConsumerApplication.class, args);
    }

}
