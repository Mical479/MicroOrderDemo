package com.test.orderuser.controller;

import com.test.orderuser.beans.Address;
import com.test.orderuser.beans.Cart;
import com.test.orderuser.beans.User;
import com.test.orderuser.dao.AddressDao;
import com.test.orderuser.enums.ControllerEnum;
import com.test.orderuser.exception.CustomException;
import com.test.orderuser.service.AddressService;
import com.test.orderuser.utils.JwtUtils;
import com.test.orderuser.utils.ResultUtils;
import com.test.orderuser.vo.VOTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 地址相关
 *
 * @author MicalJ
 * @create 2019/12/2 14:07
 **/
@Controller
@RequestMapping("/common/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 展示地址页面
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping("listAddress")
    public ModelAndView getAddressList(ModelAndView modelAndView,
                                       @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);
        Address address = new Address();
        address.setUserId(userId);
        List<Address> addressList = addressService.listAddress(address);
        modelAndView.addObject("addressList", addressList);
        modelAndView.setViewName("address");
        return modelAndView;

    }

    /**
     * 插入地址
     *
     * @param address
     * @return
     */
    @PostMapping("addressAdd")
    @ResponseBody
    public VOTemplate addAddress(@ModelAttribute @Valid Address address, @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);

        address.setUserId(userId);
        addressService.insertAddress(address);
        return ResultUtils.success();

    }

    /**
     * 删除地址
     *
     * @param addressId 地址ID
     * @return
     */
    @PostMapping("addressDel")
    @ResponseBody
    public VOTemplate delAddress(@RequestParam("addressId") @NotNull Integer addressId,
                                 @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);

        addressService.deleteAddress(addressId, userId);
        return ResultUtils.success();

    }

    /**
     * 更新地址信息
     *
     * @param address 地址实体
     * @return
     */
    @PostMapping("addressUpdate")
    @ResponseBody
    public VOTemplate updAddress(@ModelAttribute @Valid Address address, @RequestHeader("authorization") String token) {
        //从token中获取userID
        Integer userId = JwtUtils.getUserId(token);
        address.setUserId(userId);
        addressService.updateAddress(address);
        return ResultUtils.success();

    }
}
