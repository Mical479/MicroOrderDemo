package com.test.mygateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 通过的枚举
 *
 * @author MicalJ
 * @create 2019/11/29 15:13
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum CommonEnum {

    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    FALSE(404, "内部错误"),
    USER_NOT_LOGIN(233, "用户未登陆"),
    PERMISSION_TOKEN_EXPIRED(25, "用户已过期，请重新登陆"),
    PERMISSION_TOKEN_INVALID(26, "用户异常，请重新登陆");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;
}
