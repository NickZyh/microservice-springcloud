package com.springcloud.microservicefeignconsumer.user;

import lombok.Data;

/**
 * @Author Zyh
 * @Date 2019/6/30 14:58
 * @Description
 * @Note
 */
@Data
public class GetUserRequestDTO {

    private Long id;

    private String name;
}
