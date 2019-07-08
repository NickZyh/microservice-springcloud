package com.springcloud.microservicefeignconsumer.service.impl;

import com.springcloud.microservicefeignconsumer.service.UserService;
import com.springcloud.microservicefeignconsumer.user.GetUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Zyh
 * @Date 2019/6/30 16:04
 * @Description
 * @Note
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private com.springcloud.microservicefeignconsumer.feign.service.UserService userService;

    @Override
    public GetUserResponseDTO getUser(Long id) {
        return userService.getUser(id);
    }
}
