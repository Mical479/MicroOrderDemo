package com.test.orderuser.service;

import com.test.orderuser.beans.Cart;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/11/29 9:50
 **/
public interface CartService {

    void insertGoodsToCart(Cart cart);

    List<Cart> getCartList(Integer userId);

    int delCart(Cart cart);

    void updateCartNumber(Cart cart);
}
