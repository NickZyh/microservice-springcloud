package com.springcloud.microservicefeignconsumer.feign.service;

import com.springcloud.microservicefeignconsumer.feign.fallback.UserServiceFallbackFactory;
import com.springcloud.microservicefeignconsumer.user.GetUserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Zyh
 * @Date 2019/6/30 15:12
 * @Description
 * @Note
 */
@FeignClient(name = "MICROSERVICE-BASE-SERVICE", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService {

    @GetMapping("/users/{id}")
    GetUserResponseDTO getUser(@PathVariable("id") Long id);
}
