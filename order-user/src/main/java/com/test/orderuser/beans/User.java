package com.test.orderuser.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 *
 * @author MicalJ
 * @create 2019/11/26 15:04
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
