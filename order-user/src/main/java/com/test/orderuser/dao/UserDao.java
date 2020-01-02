package com.test.orderuser.dao;

import com.test.orderuser.beans.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户持久层操作接口
 *
 * @author MicalJ
 * @create 2019/11/27 9:28
 **/
@Mapper
public interface UserDao {

    /**
     * 根据用户名和用户密码获取用户
     * @param userName 用户实体
     * @return
     */
    User getUserByName(String userName);
}
