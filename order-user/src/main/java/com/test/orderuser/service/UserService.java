package com.test.orderuser.service;

import com.test.orderuser.beans.User;

/**
 * @author MicalJ
 * @create 2019/12/6 9:54
 **/
public interface UserService {

    User getUserByUserName(String userName);
}
