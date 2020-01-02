package com.test.orderuser.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 控制层
 *
 * @author MicalJ
 * @create 2019/11/29 15:00
 **/
@NoArgsConstructor
@AllArgsConstructor
public enum  ControllerEnum {

    USER_NOT_REAL(50, "非真实用户"),
    USER_NAME_OR_PASSWORD_ERROR(51, "用户名或密码错误");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;
}
