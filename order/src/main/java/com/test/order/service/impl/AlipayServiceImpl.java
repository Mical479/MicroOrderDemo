package com.test.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.order.beans.Order;
import com.test.order.beans.PagePayRequestContent;
import com.test.order.config.AlipayConfig;
import com.test.order.enums.CommonEnum;
import com.test.order.enums.OrderStatus;
import com.test.order.exception.CustomException;
import com.test.order.service.AlipayService;
import com.test.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付实现类
 *
 * @author lcc
 * @data :2018年6月4日 上午10:18:07
 */
@Service("alipayService")
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 调取支付宝接口 web端支付
     */
    DefaultAlipayClient alipayClient = new DefaultAlipayClient(
            AlipayConfig.GATEWAYURL, AlipayConfig.APP_ID, AlipayConfig.MERCHANT_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

    @Override
    public String webPagePay(String outTradeNo, Double totalAmount, String subject) throws Exception {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        /** 异步通知，支付完成后，需要进行的异步处理*/
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        /** 同步通知，支付完成后，支付成功页面*/
        alipayRequest.setReturnUrl(AlipayConfig.return_url);

        PagePayRequestContent content = PagePayRequestContent.builder().outTradeNo(outTradeNo)
                .totalAmount(String.valueOf(totalAmount))
                .subject("我的订单")
                .body("商品")
                .productCode("FAST_INSTANT_TRADE_PAY")
                .timeoutExpress("90m")
                .build();

        String s = objectMapper.writeValueAsString(content);
        log.info("ContentValue: {}", s);
        alipayRequest.setBizContent(objectMapper.writeValueAsString(content));

        /**转换格式*/
        AlipayTradePagePayResponse payResponse = alipayClient.pageExecute(alipayRequest);
        String result = payResponse.getBody();
        return result;
    }

    @Override
    public String refund(String outTradeNo) throws AlipayApiException {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        Order orderByOrderId = orderService.getOrderByOrderId(outTradeNo);
        /** 调取接口*/
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"refund_amount\":\"" + orderByOrderId.getOrderPrice() + "\","
                + "\"refund_reason\":\"" + "不想要了" + "\","
                + "\"out_request_no\":\"" + outTradeNo + "\"}");
        AlipayTradeRefundResponse response = alipayClient.execute(alipayRequest);
        if (response.isSuccess()) {
            //退款成功，更新订单状态
            orderService.updateOrder(Order.builder().orderId(outTradeNo).orderStatus(OrderStatus.REFUND_ORDER_SUCCESS).build());

            return response.getBody();
        }
        log.warn("支付宝退款失败：" + response.getBody());
        throw new CustomException(CommonEnum.ALI_REFUND_ERROR);
    }

    /**
     * 支付结果查询
     *
     * @param outTradeNo 订单编号（唯一）
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String query(String outTradeNo) throws AlipayApiException {
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        /**请求接口*/
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"trade_no\":\"" + "" + "\"}");
        //转换格式，发起退款请求
        AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
        if (response.isSuccess()) {
            return response.getBody();
        }
        throw new CustomException(CommonEnum.ALI_PAY_QUERY_ERROR);
    }

    @Override
    public String close(String outTradeNo) throws AlipayApiException {
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"trade_no\":\"" + "" + "\"}");

        String result = alipayClient.execute(alipayRequest).getBody();
        log.info("发起退款：" + outTradeNo + " " + result);
        return result;
    }

    @Override
    public String refundQuery(String outTradeNo) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        /** 请求接口*/
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"out_request_no\":\"" + outTradeNo + "\"}");

        /** 格式转换*/
        String result = alipayClient.execute(alipayRequest).getBody();

        return result;
    }

}

