package com.test.order.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MicalJ
 * @create 2019/12/23 14:06
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagePayRequestContent {

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("total_amount")
    private String totalAmount;

    private String subject;

    @JsonProperty("timeout_express")
    private String timeoutExpress = "90m";

    @JsonProperty("product_code")
    private String productCode = "FAST_INSTANT_TRADE_PAY";

    private String body;
}
