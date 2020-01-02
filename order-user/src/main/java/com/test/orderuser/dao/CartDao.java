package com.test.orderuser.dao;

import com.test.orderuser.beans.Cart;
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
     * 插入购物车
     * @param cart
     * @return
     */
    int insertGoodsToCart(Cart cart);

    /**
     * 根据用户取出购物车数据
     * @param userId
     * @return
     */
    List<Cart> getCartsListByUser(Integer userId);

    /**
     * 从购物车中移除某个物品
     * @param cart 若cart中的cartId为空，则表示清空该用户的购物车
     * @return
     */
    int delCart(Cart cart);

    /**
     * 判断该用户是否已将某个商品加入购物车，若已加入购物车，则只增加数量，否则就新插入一条数据
     */
    Cart getCartByUserAndGoods(Cart cart);

    /**
     * 跟新购物车数量
     * @param cart
     * @return
     */
    int updateCartNumber(Cart cart);

}
