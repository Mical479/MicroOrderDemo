package com.test.order.exception;

import com.test.order.enums.CommonEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务层抛出的异常
 *
 * @author MicalJ
 * @create 2019/11/29 9:55
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

    public CustomException(CommonEnum commonEnum){
        this.code = commonEnum.getCode();
        this.msg = commonEnum.getMsg();
    }

}
