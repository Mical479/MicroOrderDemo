package com.test.order.dao;

import com.test.order.beans.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单
 *
 * @author MicalJ
 * @create 2019/12/5 9:11
 **/
@Mapper
public interface OrderDao {

    /**
     * 插入订单
     * @param order 订单实体
     * @return
     */
    int insertOrder(Order order);

    /**
     * 根据用户ID获取订单
     * @param userId
     * @return
     */
    List<Order> getOrderListByUser(Integer userId);

    Order getOrderById(String orderId);

    /**
     * 动态更新订单状态、订单支付时间、订单签收时间这三个信息
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 根据订单的状态和订单的user_id查询相关订单
     * @param order
     * @return
     */
    List<Order> getPayOrders(Order order);
}
