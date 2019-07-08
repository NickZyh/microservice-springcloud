package com.springcloud.microservicebaseservice.service.impl;

import com.springcloud.microservicebaseservice.dao.UserDAO;
import com.springcloud.microservicebaseservice.dto.user.GetUserResponseDTO;
import com.springcloud.microservicebaseservice.entity.User;
import com.springcloud.microservicebaseservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Zyh
 * @Date 2019/6/30 15:01
 * @Description
 * @Note
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public GetUserResponseDTO getUser(Long id) {
        User user = userDAO.getUser(id);

        GetUserResponseDTO responseDTO = new GetUserResponseDTO();
        // 转换的对象不能为null
        BeanUtils.copyProperties(user, responseDTO);

        return responseDTO;
    }
}
