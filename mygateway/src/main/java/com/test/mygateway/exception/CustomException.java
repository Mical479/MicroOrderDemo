package com.test.mygateway.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MicalJ
 * @create 2019/12/5 14:06
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {

    private int code;
    private String msg;

    public CustomException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CustomException(CommonEnum serviceEnum){
        this.code = serviceEnum.getCode();
        this.msg = serviceEnum.getMsg();
    }
}

