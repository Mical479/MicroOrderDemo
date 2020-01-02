package com.test.orderuser.service;

import com.test.orderuser.beans.Goods;

import java.util.List;

/**
 * 商品业务层处理
 *
 * @author MicalJ
 * @create 2019/11/27 15:49
 **/
public interface GoodsService {

    List<Goods> getGoodsList();

    Goods getGoodsById(Integer goodsId);
}
