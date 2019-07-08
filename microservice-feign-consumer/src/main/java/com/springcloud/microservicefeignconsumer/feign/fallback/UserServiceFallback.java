package com.springcloud.microservicefeignconsumer.feign.fallback;

import com.springcloud.microservicefeignconsumer.feign.service.UserService;
import com.springcloud.microservicefeignconsumer.user.GetUserResponseDTO;
import org.springframework.stereotype.Component;

/**
 * @Author Zyh
 * @Date 2019/6/30 15:30
 * @Description
 * @Note
 */
@Component
public class UserServiceFallback implements UserService {

    @Override
    public GetUserResponseDTO getUser(Long id) {

        GetUserResponseDTO responseDTO = new GetUserResponseDTO();

        responseDTO.setId(-1L);
        responseDTO.setName("默认用户");

        return responseDTO;
    }


}
