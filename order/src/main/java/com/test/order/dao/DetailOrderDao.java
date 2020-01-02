package com.test.order.dao;

import com.test.order.beans.DetailOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/12/5 9:21
 **/
@Mapper
public interface DetailOrderDao {

    /**
     * 插入订单详情
     * @param detailOrder
     * @return
     */
    int insertDetailOrder(DetailOrder detailOrder);

    /**
     * 插入订单详情列表
     * @param list
     * @return
     */
    int insertOrderList(List<DetailOrder> list);

}
