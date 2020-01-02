package com.test.orderuser.service.impl;

import com.test.orderuser.beans.Goods;
import com.test.orderuser.dao.GoodsDao;
import com.test.orderuser.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品业务层
 *
 * @author MicalJ
 * @create 2019/11/27 15:49
 **/
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> getGoodsList() {
        return goodsDao.getGoodsList();
    }

    @Override
    public Goods getGoodsById(Integer goodsId) {
        return goodsDao.getGoodsById(goodsId);
    }
}
