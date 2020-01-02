package com.test.orderuser.exception;

import com.test.orderuser.enums.CommonEnum;
import com.test.orderuser.utils.ResultUtils;
import com.test.orderuser.vo.VOTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获器
 *
 * @author MicalJ
 * @create 2019/11/29 10:05
 **/
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public VOTemplate defaultServiceHandler(CustomException e){
        log.error(this.getClass().toString() + "(code= "+ e.getCode() + ", msg=" + e.getMsg() +")");
        return ResultUtils.error(e);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public VOTemplate defaultServiceHandler(Exception e){
//        log.error(this.getClass().toString() + "( msg=" + e.getMessage() +")");
//        return ResultUtils.error(CommonEnum.FALSE);
//    }
}
