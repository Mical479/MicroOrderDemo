package com.test.order.service;

import com.test.order.beans.Goods;
import com.test.order.beans.Order;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/12/5 10:53
 **/
public interface OrderService {

    Order insertOrderForm(List<Integer> list, Integer userId);

    Order getOrderByOrderId(String orderId);

    void updateOrder(Order order);

    List<Order> getOrderByStatus(Order order);

    boolean updateGoodsStock(Goods goods);
}
