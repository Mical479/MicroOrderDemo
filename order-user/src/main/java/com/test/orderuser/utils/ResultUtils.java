package com.test.orderuser.utils;

import com.test.orderuser.enums.CommonEnum;
import com.test.orderuser.enums.ControllerEnum;
import com.test.orderuser.enums.ServiceEnum;
import com.test.orderuser.exception.CustomException;
import com.test.orderuser.vo.VOTemplate;

/**
 * 对返回前端的数据封装
 *
 * @author MicalJ
 * @create 2019/11/29 15:12
 **/
public class ResultUtils {

    public static VOTemplate success(Object o){
        VOTemplate template = new VOTemplate(CommonEnum.SUCCESS);
        template.setData(o);
        return template;
    }

    public static VOTemplate success(){
        return success(null);
    }

    public static VOTemplate error(ControllerEnum controllerEnum){
        VOTemplate template = new VOTemplate(controllerEnum.getCode(), controllerEnum.getMsg());
        return template;
    }

    public static VOTemplate error(CustomException exception){
        VOTemplate template = new VOTemplate(exception.getCode(), exception.getMsg());
        return template;
    }

    public static VOTemplate error(ServiceEnum serviceEnum){
        VOTemplate template = new VOTemplate(serviceEnum.getCode(), serviceEnum.getMsg());
        return template;
    }

    public static VOTemplate error(CommonEnum commonEnum){
        VOTemplate template = new VOTemplate(commonEnum.getCode(), commonEnum.getMsg());
        return template;
    }
}
