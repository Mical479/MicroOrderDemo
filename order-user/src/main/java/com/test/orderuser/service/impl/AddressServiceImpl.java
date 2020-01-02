package com.test.orderuser.service.impl;

import com.test.orderuser.beans.Address;
import com.test.orderuser.dao.AddressDao;
import com.test.orderuser.enums.ServiceEnum;
import com.test.orderuser.exception.CustomException;
import com.test.orderuser.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/11/29 11:25
 **/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    /**
     * 用户插入地址
     *
     * @param address 地址实体
     * @return
     */
    @Override
    public void insertAddress(Address address) {
        //将插入的地址设置为默认地址,需要判断是否已存在默认地址，如存在，就取消默认
        if (address.getIsDefault() > 0) {
            cancelDefaultAddress(address);
        }
        int i = addressDao.insertAddress(address);
        if (i <= 0) {
            throw new CustomException(ServiceEnum.INSERT_INTO_ADDRESS_ERROR);
        }
    }

    /**
     * 用户删除某个地址
     *
     * @param addressId
     * @return
     */
    @Override
    public void deleteAddress(Integer addressId, Integer userId) {
        int i = addressDao.delAddress(addressId, userId);
        if (i <= 0) {
            throw new CustomException(ServiceEnum.DELETE_ADDRESS_ERROR);
        }
    }

    /**
     * 用户更新地址
     * @param address
     */
    @Override
    public void updateAddress(Address address) {
        if (address.getIsDefault() > 0) {
            cancelDefaultAddress(address);
        }
        int i = addressDao.updateAddress(address);
        if (i <= 0) {
            throw new CustomException(ServiceEnum.UPDATE_ADDRESS_NOT_SUCCESS);
        }
    }

    /**
     * 用户查询地址
     *
     * @param address
     * @return
     */
    @Override
    public List<Address> listAddress(Address address) {
        return addressDao.selectUserAddress(address);
    }

    /**
     * 取消默认地址，需要在address中带入 userID，is_default = 1
     *
     * @param address
     */
    private void cancelDefaultAddress(Address address) {
        List<Address> addressList = addressDao.selectUserAddress(address);
        //表示已有默认地址
        if (addressList != null && addressList.size() == 1) {
            Address address1 = addressList.get(0);
            address1.setIsDefault(0);
            if (addressDao.updateAddress(address1) <= 0) {
                throw new CustomException(ServiceEnum.UPDATE_ADDRESS_NOT_SUCCESS);
            }
        }
    }
}
