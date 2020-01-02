package com.test.order.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 *
 * @author MicalJ
 * @create 2019/12/5 9:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String roleName;
}
