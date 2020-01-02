package com.test.order.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 订单实体类
 *
 * @author MicalJ
 * @create 2019/12/5 9:05
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    private String orderId;

    private Integer orderCount;

    private Double orderPrice;

    private Integer orderStatus;

    private Date orderCreateTime;

    private Date orderPayTime;

    private Date orderSignTime;

    private Integer userId;

    private Integer addressId;

    private List<DetailOrder> orderList;
}
