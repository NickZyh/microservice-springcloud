package com.springcloud.microservicefeignconsumer.controller;

import com.springcloud.microservicefeignconsumer.service.UserService;
import com.springcloud.microservicefeignconsumer.user.GetUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zyh
 * @Date 2019/6/30 16:07
 * @Description
 * @Note
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public GetUserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
