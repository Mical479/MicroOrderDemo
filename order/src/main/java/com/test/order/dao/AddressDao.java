package com.test.order.dao;

import com.test.order.beans.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地址持久层接口
 *
 * @author MicalJ
 * @create 2019/11/29 11:18
 **/
@Mapper
public interface AddressDao {

    /**
     * 获取用户默认地址
     * @param userId 用户ID
     * @return
     */
    Address getDefaultAddress(Integer userId);
}
