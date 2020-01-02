package com.test.orderuser;

import com.test.orderuser.beans.Cart;
import com.test.orderuser.beans.Goods;
import com.test.orderuser.beans.User;
import com.test.orderuser.dao.CartDao;
import com.test.orderuser.dao.GoodsDao;
import com.test.orderuser.dao.UserDao;
import com.test.orderuser.service.CartService;
import com.test.orderuser.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@Slf4j
class OrderUserApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDao cartDao;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUserName("Mical");
        user.setPassword("hua");
        User userByName = userDao.getUserByName("Mical");
        System.out.println(userByName);
    }

    @Test
    void testGoods(){
        Goods goodsById = goodsDao.getGoodsById(2);
        System.out.println(goodsById);
    }

    @Test
    void test(){
        Cart cart = new Cart();
        cart.setCartNumber(20);
        cart.setUserId(1);
//        Goods goods = new Goods();
//        goods.setGoodsId(2);
//        cart.setGoods(goods);
        int i = cartDao.delCart(cart);
        System.out.println(i);
    }

    @Test
    void testCart(){
        String pattern = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher("13404047128");
        System.out.println(matcher.matches());
    }

}
