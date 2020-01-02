package com.test.orderuser.controller;

import com.test.orderuser.service.OrderClientService;
import com.test.orderuser.vo.VOTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author MicalJ
 * @create 2019/12/6 11:58
 **/
@Controller
@RequestMapping("/common/user")
public class CommonController {

    @Autowired
    private OrderClientService orderClientService;

    @RequestMapping("order")
    public ModelAndView getPage(@RequestParam("orderId") String orderId, ModelAndView modelAndView){
        VOTemplate orderById = orderClientService.getOrderById(orderId);
        System.out.println(orderById.getData());
        modelAndView.addObject("order", orderById.getData());
        modelAndView.setViewName("order");
        return modelAndView;
    }
}
