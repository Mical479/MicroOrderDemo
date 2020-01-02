package com.test.orderuser.controller;

import com.test.orderuser.beans.Cart;
import com.test.orderuser.beans.Goods;
import com.test.orderuser.beans.User;
import com.test.orderuser.enums.CommonEnum;
import com.test.orderuser.enums.ControllerEnum;
import com.test.orderuser.exception.CustomException;
import com.test.orderuser.service.CartService;
import com.test.orderuser.service.GoodsService;
import com.test.orderuser.utils.JwtUtils;
import com.test.orderuser.utils.ResultUtils;
import com.test.orderuser.vo.VOTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Enumeration;
import java.util.List;

/**
 * 商品操作
 *
 * @author MicalJ
 * @create 2019/11/27 15:52
 **/
@Controller
@RequestMapping("/common/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CartService cartService;

    /**
     * 获取商品列表
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping("/listgoods")
    public ModelAndView showGoodsList(ModelAndView modelAndView, @RequestHeader("authorization") String token) {

        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);

        List<Goods> goodsList = goodsService.getGoodsList();
        modelAndView.addObject("goodsList", goodsList);
        modelAndView.setViewName("goods");

        List<Cart> cartList = cartService.getCartList(userId);
        long count = cartList.stream().mapToInt(Cart::getCartNumber).sum();
        modelAndView.addObject("cartSize", count);
        return modelAndView;
    }

    /**
     * 加入购物车操作
     * @param
     * @return
     */
    @PostMapping("/addtocart")
    @ResponseBody
    public VOTemplate addGoodsToCart(@RequestParam("cartNumber") @NotNull Integer cartNumber,
                                     @RequestParam(value = "goodsId") @NotNull Integer goodsId,
                                     @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);
        Goods goodsById = goodsService.getGoodsById(goodsId);
        if (goodsById == null){
            throw new CustomException(CommonEnum.GOODS_DOES_NOT_IN_OUR_STORE);
        }
        Cart cart = new Cart();
        cart.setCartNumber(cartNumber);
        cart.setGoods(goodsById);
        cart.setUserId(userId);
        cartService.insertGoodsToCart(cart);
        return ResultUtils.success();

    }
}
