package com.test.order;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.order.beans.Goods;
import com.test.order.beans.Order;
import com.test.order.beans.PagePayRequestContent;
import com.test.order.config.AlipayConfig;
import com.test.order.dao.GoodsDao;
import com.test.order.dao.OrderDao;
import com.test.order.service.AlipayService;
import com.test.order.service.OrderService;
import com.test.order.utils.OrderCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.List;

@SpringBootTest
@Slf4j
class OrderApplicationTests {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderService goodsDao;

	@Autowired
	private AlipayService alipayService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {

		List<Order> orderListByUser = orderDao.getOrderListByUser(1);
		System.out.println(orderListByUser);
	}

	public static void main(String[] args) {
		String orderCode = OrderCodeUtil.getOrderCode(5);
		System.out.println(orderCode);
	}

	@Test
	void testGoods(){
		goodsDao.updateGoodsStock(Goods.builder().goodsId(1).goodsStock(-2).build());
	}

	@Test
	void testPay() throws Exception {
		DefaultAlipayClient alipayClient = new DefaultAlipayClient(
				AlipayConfig.GATEWAYURL, AlipayConfig.APP_ID, AlipayConfig.MERCHANT_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
		model.setBillDate("2018-05-13");
		model.setBillType("signcustomer");
//		request.setBizContent("{" +
//				"\"bill_type\":\"trade\"," +
//				"\"bill_date\":\"2016-04-05\"" +
//				"  }");
		request.setBizModel(model);
		AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}

		System.out.println("=========getSubCode==============" +response.getSubCode());
		System.out.println("=========getCode==============" +response.getCode());
		System.out.println("==========getMsg=============" +response.getMsg());
		System.out.println("==========getParams=============" +response.getParams());
		System.out.println("==========getBody=============" +response.getBody());
		System.out.println("==========isSuccess=============" +response.isSuccess());
	}
}
