package com.test.order.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MicalJ
 * @create 2019/12/5 9:23
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetailOrder {

    private Integer detailOrderId;

    private Goods goods;

    private Integer detailOrderCount;

    private Double detailOrderPrice;

    private String orderId;
}
