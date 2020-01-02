package com.test.mygateway.vo;

import com.test.mygateway.exception.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回前端的统一模板
 *
 * @author MicalJ
 * @create 2019/11/29 15:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VOTemplate<T> {
    private Integer code;
    private String msg;
    private T data;

    public VOTemplate(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public VOTemplate(CommonEnum commonEnum){
        this.code = commonEnum.getCode();
        this.msg = commonEnum.getMsg();
    }
}
