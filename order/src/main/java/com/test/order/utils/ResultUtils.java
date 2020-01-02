package com.test.order.utils;

import com.test.order.enums.CommonEnum;
import com.test.order.exception.CustomException;
import com.test.order.vo.VOTemplate;

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

    public static VOTemplate error(CommonEnum commonEnum){
        VOTemplate template = new VOTemplate(commonEnum.getCode(), commonEnum.getMsg());
        return template;
    }

    public static VOTemplate error(Object o){
        VOTemplate template = new VOTemplate();
        template.setCode(400);
        template.setMsg(o.toString());
        return template;
    }

    public static VOTemplate error(CustomException cs){
        VOTemplate template = new VOTemplate();
        template.setCode(cs.getCode());
        template.setMsg(cs.getMsg());
        return template;
    }
}
