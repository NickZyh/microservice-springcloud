package com.springcloud.microservicefeignconsumer.service;

import com.springcloud.microservicefeignconsumer.user.GetUserResponseDTO;

/**
 * @Author Zyh
 * @Date 2019/6/30 16:03
 * @Description
 * @Note
 */
public interface UserService {

    GetUserResponseDTO getUser(Long id);
}
