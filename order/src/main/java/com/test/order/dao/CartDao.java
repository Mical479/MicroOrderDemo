package com.test.order.dao;

import com.test.order.beans.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 购物车
 *
 * @author MicalJ
 * @create 2019/11/29 9:24
 **/
@Mapper
public interface CartDao {

    /**
     * 根据用户取出购物车数据
     * @param userId
     * @return
     */
    List<Cart> getCartsListByUser(Integer userId);

    /**
     * 根据购物车id，批量取出数据
     * @param list
     * @return
     */
    List<Cart> getCartListByCartId(List<Integer> list);

    /**
     * 根据购物车ID，批量删除数据
     * @param list
     * @return
     */
    int deleteCartListByCartId(List<Integer> list);

}
