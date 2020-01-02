package com.test.orderuser.service;

import com.test.orderuser.vo.VOTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author MicalJ
 * @create 2019/12/9 14:47
 **/
@FeignClient("micro-order")
public interface OrderClientService {

    @GetMapping("/order/getPaiedOrder/{authorization}")
    VOTemplate getOrders(@PathVariable("authorization")String token);

    @GetMapping("/order/getFailedOrder/{authorization}")
    VOTemplate getFailedOrders(@PathVariable("authorization")String token);

    @GetMapping("/order/getOrderById/{orderId}")
    VOTemplate getOrderById(@PathVariable("orderId") String orderId);
}
