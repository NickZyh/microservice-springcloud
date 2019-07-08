package com.springcloud.microservicebaseservice.service;

import com.springcloud.microservicebaseservice.dto.user.GetUserResponseDTO;

/**
 * @Author Zyh
 * @Date 2019/6/30 14:57
 * @Description
 * @Note
 */
public interface UserService {

    GetUserResponseDTO getUser(Long id);
}
