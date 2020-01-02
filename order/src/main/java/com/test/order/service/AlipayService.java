package com.test.order.service;

import com.alipay.api.AlipayApiException;

/**
 * 支付接口
 * @author lcc
 * @data :2018年6月4日 上午10:18:39
 */
public interface AlipayService {

    /**
     * web端订单支付
     * @param outTradeNo    订单编号（唯一）
     * @param totalAmount   订单价格
     * @param subject       商品名称
     */
    String webPagePay(String outTradeNo,Double totalAmount,String subject) throws Exception;

    /**
     * 退款
     * @param outTradeNo    订单编号
     */
    String refund(String outTradeNo) throws AlipayApiException;

    /**
     * 交易查询
     * @param outTradeNo 订单编号（唯一）
     */
    String query(String outTradeNo) throws AlipayApiException;

    /**
     * 交易关闭
     * @param outTradeNo 订单编号（唯一）
     */
    String close(String outTradeNo) throws AlipayApiException;

    /**
     * 退款查询
     * @param outTradeNo 订单编号（唯一）
//     * @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     */
    String refundQuery(String outTradeNo) throws AlipayApiException;
}

