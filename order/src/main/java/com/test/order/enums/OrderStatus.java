package com.test.order.enums;

/**
 * 订单状态码
 *
 * @author MicalJ
 * @create 2019/12/6 15:42
 **/
public class OrderStatus {

    /**
     * 新创建订单，待支付
     */
    public static final Integer NEW_ORDER = 0;

    /**
     * 订单取消
     */
    public static final Integer ORDER_CANCELED = 5;

    /**
     * 订单支付成功
     */
    public static final Integer PAY_SUCCESS = 1;

    /**
     * 订单支付失败
     */
    public static final Integer PAY_ERROR = 2;

    /**
     * 订单退款成功
     */
    public static final Integer REFUND_ORDER_SUCCESS = 3;

    /**
     * 退款失败订单
     */
    public static final Integer REFUND_ORDER_ERROR = 4;
}
