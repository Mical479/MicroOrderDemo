package com.test.order.dao;

import com.test.order.beans.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author MicalJ
 * @create 2019/11/27 15:43
 **/
@Mapper
public interface GoodsDao {

    /**
     * 更新物品库存
     * @param goods
     * @return
     */
    int updateGoodsStock(Goods goods);

    /**
     * 获取商品库存
     * @param goodsId
     * @return
     */
    Integer getGoodsStock(Integer goodsId);
}
