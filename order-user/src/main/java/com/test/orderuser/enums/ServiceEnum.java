package com.test.orderuser.enums;

import lombok.*;

/**
 * 服务层异常响应结果枚举类
 *
 * @author MicalJ
 * @create 2019/11/29 9:59
 **/
@NoArgsConstructor
@AllArgsConstructor
public enum ServiceEnum {

    INSERT_GOODS_TO_CART_ERROR(10, "物品加入购物车失败"),
    SELECT_CART_ERROR(11, "购物车查询失败"),
    USER_DOES_NOT_HAVA_THIS_GOODS_IN_CART(12, "该物品未在购物车中"),
    INSERT_INTO_ADDRESS_ERROR(13, "插入地址失败"),
    DELETE_ADDRESS_ERROR(14, "删除地址失败"),
    UPDATE_ADDRESS_NOT_SUCCESS(15, "未成功更新地址"),
    UPDATE_CART_NUMBER_ERROR(16, "更新购物车物品数量失败"),
    UPDATE_CART_NUMBER_IS_INVALID(17, "更新物品数量非法");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;
}
