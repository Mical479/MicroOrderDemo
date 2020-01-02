package com.test.order.enums;

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
    NO_DEFAULT_ADDRESS(23, "请先设置默认地址"),
    PURCHARSE_CART_NO_THIS_GOODS(24, "购物车中不包含这件商品"),
    PERMISSION_TOKEN_EXPIRED(25, "用户已过期，请重新登陆"),
    PERMISSION_TOKEN_INVALID(26, "用户异常，请重新登陆"),
    ORDER_BUILD_ERROR(27, "订单生成失败"),
    ORDER_UPDATE_ERROR(31, "更新订单信息失败"),
    CART_IS_EMPTY(28, "购物车是空的哟"),
    ORDER_NOT_EXIST(29, "我们的数据库没有这个订单哦"),
    ALI_PAY_QUERY_ERROR(30, "支付宝支付查询失败"),
    ALI_REFUND_ERROR(32, "支付宝退款申请失败"),
    UPDATE_GOODS_STOCK_ERROR(33, "更新库存失败");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;
}
