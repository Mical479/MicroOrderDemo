package com.test.orderuser.dao;

import com.test.orderuser.beans.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/11/27 15:43
 **/
@Mapper
public interface GoodsDao {

    /**
     * 获取商品列表
     * @return
     */
    List<Goods> getGoodsList();

    /**
     * 根据商品的ID获取商品
     * @param goodsId
     * @return
     */
    Goods getGoodsById(Integer goodsId);
}
