package com.test.orderuser.controller;

import com.test.orderuser.beans.User;
import com.test.orderuser.dao.UserDao;
import com.test.orderuser.enums.ControllerEnum;
import com.test.orderuser.service.UserService;
import com.test.orderuser.utils.JwtUtils;
import com.test.orderuser.utils.ResultUtils;
import com.test.orderuser.vo.VOTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MicalJ
 * @create 2019/11/27 11:16
 **/
@RequestMapping("/user")
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String getLoginPage() {
        return "index";
    }

    @PostMapping("/to_login")
    @ResponseBody
    public VOTemplate toLogin(@RequestParam("userName") @NotBlank String userName,
                              @RequestParam("password") @NotBlank String password) {
        User user = userService.getUserByUserName(userName);
        if (user != null && password.equals(user.getPassword())){
            String jwt = JwtUtils.createJWT(user.getUserId(), userName);
            Map map = new HashMap(1);
            map.put("token", jwt);
            log.info("UserLogin :" + user.getUserName() + "  UserToken: " + jwt);
            return ResultUtils.success(map);
        }
        return ResultUtils.error(ControllerEnum.USER_NAME_OR_PASSWORD_ERROR);
    }

}
