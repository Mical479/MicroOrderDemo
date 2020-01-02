package com.test.orderuser.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息实体类
 *
 * @author MicalJ
 * @create 2019/11/26 14:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods {

    private Integer goodsId;

    private String goodsName;

    private Double goodsPrice;

    private Integer goodsStock;
}
