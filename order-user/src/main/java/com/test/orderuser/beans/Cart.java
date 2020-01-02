package com.test.orderuser.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车
 *
 * @author MicalJ
 * @create 2019/11/29 9:19
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer cartId;

    private Integer cartNumber;

    private Integer userId;

    private Goods goods;
}
