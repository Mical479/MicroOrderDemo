package com.test.orderuser.service.impl;

import com.test.orderuser.beans.User;
import com.test.orderuser.dao.UserDao;
import com.test.orderuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MicalJ
 * @create 2019/12/6 9:55
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名检索用户数据
     * @param userName
     * @return
     */
    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByName(userName);
    }
}
