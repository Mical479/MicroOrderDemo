package com.test.orderuser.dao;

import com.test.orderuser.beans.Address;
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

    int insertAddress(Address address);

    /**
     * 用户删除地址
     * @param addressId
     * @param userId
     * @return
     */
    int delAddress(Integer addressId, Integer userId);

    List<Address> selectUserAddress(Address address);

    int updateAddress(Address address);
}
