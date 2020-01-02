package com.test.orderuser.service;

import com.test.orderuser.beans.Address;

import java.util.List;

/**
 * 地址的逻辑处理接口
 *
 * @author MicalJ
 * @create 2019/11/29 11:25
 **/
public interface AddressService {

    void insertAddress(Address address);

    void deleteAddress(Integer addressId, Integer userId);

    void updateAddress(Address address);

    List<Address> listAddress(Address address);
}
