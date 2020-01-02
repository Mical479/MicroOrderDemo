package com.test.order.dao;

import com.test.order.beans.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MicalJ
 * @create 2019/12/5 9:09
 **/
@Mapper
public interface UserDao {
    /**
     * 根据用户名和用户密码获取用户
     * @param userName 用户姓名
     * @return
     */
    User getUserByName(String userName);
}
