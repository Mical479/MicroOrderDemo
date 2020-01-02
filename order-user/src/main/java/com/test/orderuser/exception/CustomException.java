package com.test.orderuser.exception;

import com.test.orderuser.enums.CommonEnum;
import com.test.orderuser.enums.ControllerEnum;
import com.test.orderuser.enums.ServiceEnum;
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

    public CustomException(ControllerEnum controllerEnum){
        this.code = controllerEnum.getCode();
        this.msg = controllerEnum.getMsg();
    }

    public CustomException(CommonEnum controllerEnum){
        this.code = controllerEnum.getCode();
        this.msg = controllerEnum.getMsg();
    }

    public CustomException(ServiceEnum serviceEnum){
        this.code = serviceEnum.getCode();
        this.msg = serviceEnum.getMsg();
    }
}
