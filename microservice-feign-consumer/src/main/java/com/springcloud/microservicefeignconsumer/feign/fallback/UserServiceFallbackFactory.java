package com.springcloud.microservicefeignconsumer.feign.fallback;

import com.springcloud.microservicefeignconsumer.feign.service.UserService;
import com.springcloud.microservicefeignconsumer.user.GetUserResponseDTO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author Zyh
 * @Date 2019/6/30 15:32
 * @Description
 * @Note
 */
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceFallbackFactory.class);

    @Override
    public UserService create(Throwable throwable) {

        return new UserService() {
            @Override
            public GetUserResponseDTO getUser(Long id) {
                UserServiceFallbackFactory.logger.info(throwable.getMessage());

                GetUserResponseDTO responseDTO = new GetUserResponseDTO();
                responseDTO.setId(-1L);
                responseDTO.setName("默认用户");

                return responseDTO;
            }
        };
    }
}
