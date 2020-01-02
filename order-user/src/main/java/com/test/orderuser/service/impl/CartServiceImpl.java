package com.test.orderuser.service.impl;

import com.test.orderuser.beans.Cart;
import com.test.orderuser.dao.CartDao;
import com.test.orderuser.enums.ServiceEnum;
import com.test.orderuser.exception.CustomException;
import com.test.orderuser.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/11/29 9:51
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    /**
     * 加入购物车操作
     * @param cart
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    @Override
    public void insertGoodsToCart(Cart cart) {
        Cart cartByUserAndGoods = cartDao.getCartByUserAndGoods(cart);
        //判断该用户的购物车中是否已经存在该商品
        if (cartByUserAndGoods != null){
            if(cartDao.updateCartNumber(cart) <= 0){
                throw new CustomException(ServiceEnum.UPDATE_CART_NUMBER_ERROR);
            }
            return ;
        }
        //购物车中不存在该商品，则直接插入
        int i = cartDao.insertGoodsToCart(cart);
        if (i <= 0) {
            throw new CustomException(ServiceEnum.INSERT_GOODS_TO_CART_ERROR);
        }
    }



    /**
     * 用户获取购物车
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<Cart> getCartList(Integer userId) {
        List<Cart> cartsList = cartDao.getCartsListByUser(userId);
        return cartsList;
    }

    /**
     * 用户删除购物车
     *
     * @param cart 购物车设置了goods中的goodsID表示删除某项物品，若没设置表示清空购物车
     * @return
     */
    @Override
    public int delCart(Cart cart) {
        int i = cartDao.delCart(cart);
        if (i > 0) {
            return i;
        } else {
            throw new CustomException(ServiceEnum.USER_DOES_NOT_HAVA_THIS_GOODS_IN_CART);
        }
    }

    /**
     * 更新购物车数量
     * @param cart
     * @return
     */
    @Override
    public void updateCartNumber(Cart cart) {
        Cart cartByUserAndGoods = cartDao.getCartByUserAndGoods(cart);
        if (cartByUserAndGoods != null) {
            //当更新要减少时，判断需要减少的数量是否小于购物车中的数量，
            if (cartByUserAndGoods.getCartNumber() + cart.getCartNumber() < 0){
                throw new CustomException(ServiceEnum.UPDATE_CART_NUMBER_IS_INVALID);
            }
            //判断购物车中该商品的数量是否大于1，若大于1则递减，小于1则删除该物品
            if (cartByUserAndGoods.getCartNumber() <= 1 && cart.getCartNumber() < 0) {
                delCart(cart);
            }else {
                if (cartDao.updateCartNumber(cart) <= 0) {
                    throw new CustomException(ServiceEnum.UPDATE_CART_NUMBER_ERROR);
                }
            }
        }else {
            throw new CustomException(ServiceEnum.USER_DOES_NOT_HAVA_THIS_GOODS_IN_CART);
        }
    }

}
