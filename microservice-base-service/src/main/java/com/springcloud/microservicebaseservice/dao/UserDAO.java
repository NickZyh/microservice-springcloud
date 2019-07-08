package com.springcloud.microservicebaseservice.dao;

import com.springcloud.microservicebaseservice.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyh
 * @Date 2019/6/30 15:02
 * @Description
 * @Note
 */
@Component
public class UserDAO {

    private Map<Long, User> userMap = new HashMap<>();

    public UserDAO() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("周宇航");
        userMap.put(user1.getId(), user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("李四");
        userMap.put(user2.getId(), user2);
    }

    public User getUser(Long id) {
        return userMap.get(id);
    }
}
