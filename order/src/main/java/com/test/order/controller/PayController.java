package com.test.order.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.test.order.beans.Order;
import com.test.order.config.AlipayConfig;
import com.test.order.enums.OrderStatus;
import com.test.order.service.AlipayService;
import com.test.order.service.OrderService;
import com.test.order.service.impl.OrderServiceImpl;
import com.test.order.utils.ResultUtils;
import com.test.order.vo.VOTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付接口
 *
 * @author MicalJ
 * @create 2019/12/6 14:33
 **/

/**
 * 支付宝
 *
 * @author lcc
 * @data :2018年6月4日 上午10:55:46
 */
@Slf4j
@RequestMapping("/order")
@Controller
public class PayController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * web 订单支付
     */
    @PostMapping("getPagePay")
    public String getPagePay(@RequestParam("orderId") @NotBlank String outTradeNo, ModelMap map) throws Exception {
        /** 模仿数据库，从后台调数据*/
        Order orderByOrderId = orderService.getOrderByOrderId(outTradeNo);

        String pay = alipayService.webPagePay(outTradeNo, orderByOrderId.getOrderPrice(), AlipayConfig.SUBJECT);
        System.out.println(pay);
        map.addAttribute("form", pay);
        return "pay";
    }

    /**
     * 返回地址，验证支付是否成功，支付成功就返回到成功页面
     *
     * @return
     */
    @GetMapping("returnUrl")
    public String getReturn(HttpServletRequest request) throws Exception {
        //获取支付宝Get过来的反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            log.warn(name + "======" + valueStr);
            params.put(name, valueStr);
        }
        log.info("AliPayResponse " + params);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
                AlipayConfig.SIGN_TYPE);

        if (signVerified) {
            String outTradeNo = params.get("out_trade_no");
            //前端返回同步调用结果后，再从Ali查询该订单的真实状态，没有异常就返回成功
            String response = alipayService.query(outTradeNo);
            log.info("AliQueryResponse " + response);
            //从redis中删除订单
            redisTemplate.delete(OrderServiceImpl.ORDER_PREFIX + outTradeNo);
            //修改订单状态
            Order order = Order.builder().orderStatus(OrderStatus.PAY_SUCCESS)
                    .orderPayTime(new Date())
                    .orderId(outTradeNo)
                    .build();
            orderService.updateOrder(order);
            return "redirect:http://127.0.0.1:8003/common/order/listOrder";
        }

        log.error("AliPayError");
        return "error";
    }

    @PostMapping("notifyUrl")
    public void getNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String msg = "success";
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("AliNotifyResponse " + params);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
                AlipayConfig.SIGN_TYPE);
        if (signVerified) {
            String outTradeNo = params.get("out_trade_no");

            //修改订单状态
            Order order = Order.builder().orderStatus(OrderStatus.PAY_SUCCESS)
                    .orderPayTime(new Date())
                    .orderId(outTradeNo)
                    .build();
            orderService.updateOrder(order);
            log.info("AliNotifySuccess");
        }
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(msg.getBytes());
        out.flush();
        out.close();
    }


    /**
     * 交易查询
     */
    @PostMapping("aipayQuery")
    @ResponseBody
    public VOTemplate<?> alipayQuery() throws Exception {
        /**调取支付订单号*/
        String outTradeNo = "19960310621214";

        String query = alipayService.query(outTradeNo);

        /*JSONObject jObject = new JSONObject();
        jObject.get(query);*/
        return ResultUtils.success(query);
    }

    /**
     * 退款
     *
     * @throws AlipayApiException
     */
    @GetMapping("alipayRefund")
    @ResponseBody
    public VOTemplate<?> alipayRefund(
            @RequestParam("outTradeNo") String outTradeNo
    ) throws AlipayApiException {

        String refund = alipayService.refund(outTradeNo);

        log.info("支付宝退款信息：" + refund);

        return ResultUtils.success(refund);
    }

    /**
     * 退款查询
     *
     * @throws AlipayApiException
     */
    @PostMapping("refundQuery")
    @ResponseBody
    public VOTemplate<?> refundQuery() throws AlipayApiException {

        /** 调取数据*/
        String outTradeNo = "13123";

        String refund = alipayService.refundQuery(outTradeNo);

        return ResultUtils.success(refund);

    }

    /**
     * 交易关闭
     *
     * @throws AlipayApiException
     */
    @PostMapping("alipayclose")
    @ResponseBody
    public VOTemplate<?> alipaycolse(@RequestParam("orderId")String orderId) throws AlipayApiException {

        String close = alipayService.close(orderId);

        return ResultUtils.success(close);
    }

}


