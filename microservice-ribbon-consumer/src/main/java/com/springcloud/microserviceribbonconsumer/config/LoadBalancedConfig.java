package com.springcloud.microserviceribbonconsumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Zyh
 * @Date 2019/6/29 21:58
 * @Description
 * @Note
 */
@Configuration
public class LoadBalancedConfig {

    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }
}
