package com.test.orderuser.controller;

import com.test.order.beans.Order;
import com.test.orderuser.service.OrderClientService;
import com.test.orderuser.vo.VOTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/12/9 11:11
 **/
@Controller
@Slf4j
@RequestMapping("/common/order")
public class OrderController {

    @Autowired
    private OrderClientService orderClientService;

    /**
     * 跳转到已支付页面
     * @param modelAndView
     * @param token
     * @return
     */
    @RequestMapping("listOrder")
    public ModelAndView orderList(ModelAndView modelAndView, @RequestHeader("authorization") String token) {
        VOTemplate voTemplate = orderClientService.getOrders(token);
        modelAndView.addObject("orders", voTemplate.getData());
        modelAndView.setViewName("orders");
        log.info(voTemplate.toString());
        return modelAndView;
    }

    /**
     * 跳转到未支付页面
     * @param modelAndView
     * @param token
     * @return
     */
    @RequestMapping("failedOrder")
    public ModelAndView failedOrderList(ModelAndView modelAndView, @RequestHeader("authorization") String token) {
        VOTemplate failedOrders = orderClientService.getFailedOrders(token);
        modelAndView.addObject("orders", failedOrders.getData());
        modelAndView.setViewName("failedOrders");
        return modelAndView;
    }
}
