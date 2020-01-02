package com.test.orderuser.controller;

import com.test.orderuser.beans.Cart;
import com.test.orderuser.beans.User;
import com.test.orderuser.enums.ControllerEnum;
import com.test.orderuser.exception.CustomException;
import com.test.orderuser.service.CartService;
import com.test.orderuser.utils.JwtUtils;
import com.test.orderuser.utils.ResultUtils;
import com.test.orderuser.vo.VOTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 购物车页面
 *
 * @author MicalJ
 * @create 2019/12/2 10:26
 **/
@Controller
@RequestMapping("/common/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 查询购物车，并返回购物车页面
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping("listCart")
    public ModelAndView getCartList(ModelAndView modelAndView, @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);

        List<Cart> cartList = cartService.getCartList(userId);
        long count = cartList.stream().mapToInt(Cart::getCartNumber).sum();
        modelAndView.addObject("cartSize", count);
        modelAndView.addObject("myCarts", cartList);
        modelAndView.setViewName("cart");
        return modelAndView;

    }

    /**
     * 更新购物车某件商品的数量
     *
     * @param cartNumber
     * @param cartId
     * @return
     */
    @PostMapping("cartUpdate")
    @ResponseBody
    public VOTemplate updateCartNumber(@RequestParam("cartNumber") @NotNull Integer cartNumber,
                                       @RequestParam("cartId") @NotNull Integer cartId,
                                       @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);
        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setUserId(userId);
        cart.setCartNumber(cartNumber);
        cartService.updateCartNumber(cart);
        return ResultUtils.success();

    }

    /**
     * 删除购物车某件物品，或清空购物车
     *
     * @param cartId
     * @param flag    是否清空购物车
     * @return
     */
    @PostMapping("cartDel")
    @ResponseBody
    public VOTemplate deleteCart(@RequestParam("cartId") Integer cartId,
                                 @RequestParam("deleteAll") Boolean flag,
                                 @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);
        Cart cart = new Cart();
        cart.setUserId(userId);
        if (flag) {
            cartService.delCart(cart);
        } else {
            cart.setCartId(cartId);
            cartService.delCart(cart);
        }
        return ResultUtils.success();

    }
}
