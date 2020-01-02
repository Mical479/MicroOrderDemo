package com.test.order.controller;

import com.test.order.beans.Order;
import com.test.order.enums.CommonEnum;
import com.test.order.enums.OrderStatus;
import com.test.order.exception.CustomException;
import com.test.order.service.OrderService;
import com.test.order.utils.JwtUtils;
import com.test.order.utils.ResultUtils;
import com.test.order.vo.VOTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单控制层
 *
 * @author MicalJ
 * @create 2019/12/5 10:31
 **/
@Controller
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 生成订单数据，并返回给前端
     *
     * @param
     * @return
     */
    @RequestMapping("order_count")
    @ResponseBody
    public VOTemplate doOrder(@RequestHeader("authorization") String token, @RequestBody @NotNull List<Integer> list) {
        lock.lock();
        try {
            if (list.size() > 0) {
                Integer userId = JwtUtils.getUserId(token);
                Order order = orderService.insertOrderForm(list, 1);
                return ResultUtils.success(order);
            }
        }finally {
            lock.unlock();
        }
        throw new CustomException(CommonEnum.CART_IS_EMPTY);
    }

    /**
     * 已支付订单的查询
     * @param token
     * @return
     */
    @RequestMapping("getPaiedOrder/{authorization}")
    @ResponseBody
    public VOTemplate getOrders(@PathVariable("authorization") String token) {
        log.info("token " + token);
        Integer userId = JwtUtils.getUserId(token);
        List<Order> orderByStatus = orderService.getOrderByStatus(Order.builder().orderStatus(OrderStatus.PAY_SUCCESS)
                .userId(userId).build());
        if (orderByStatus != null && orderByStatus.size() > 0) {
            return ResultUtils.success(orderByStatus);
        }
        return ResultUtils.error("该用户没有已支付的订单哦");
    }

    /**
     * 未支付的订单
     * @param token
     * @return
     */
    @RequestMapping("getFailedOrder/{authorization}")
    @ResponseBody
    public VOTemplate getFailedOrders(@PathVariable("authorization") String token) {
        Integer userId = JwtUtils.getUserId(token);
        List<Order> orderByStatus = orderService.getOrderByStatus(Order.builder().orderStatus(OrderStatus.NEW_ORDER)
                .userId(userId).build());
        if (orderByStatus != null && orderByStatus.size() > 0){
            return ResultUtils.success(orderByStatus);
        }
        return ResultUtils.error("该用户暂时没有订单哦");
    }

    /**
     * 根据订单ID获取订单
     * @param orderId
     * @return
     */
    @RequestMapping("getOrderById/{orderId}")
    @ResponseBody
    public VOTemplate getOrderById(@PathVariable("orderId") String orderId){
        Order orderByOrderId = orderService.getOrderByOrderId(orderId);
        return ResultUtils.success(orderByOrderId);
    }
}
