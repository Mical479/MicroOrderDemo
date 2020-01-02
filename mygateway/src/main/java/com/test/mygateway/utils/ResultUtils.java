package com.test.mygateway.utils;

import com.test.mygateway.exception.CommonEnum;
import com.test.mygateway.exception.CustomException;
import com.test.mygateway.vo.VOTemplate;

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


    public static VOTemplate error(CustomException exception){
        VOTemplate template = new VOTemplate(exception.getCode(), exception.getMsg());
        return template;
    }

    public static VOTemplate error(CommonEnum commonEnum){
        VOTemplate template = new VOTemplate(commonEnum.getCode(), commonEnum.getMsg());
        return template;
    }
}
