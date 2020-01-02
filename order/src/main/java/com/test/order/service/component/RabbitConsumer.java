package com.test.order.service.component;

import com.alipay.api.AlipayApiException;
import com.rabbitmq.client.Channel;
import com.test.order.beans.Order;
import com.test.order.enums.OrderStatus;
import com.test.order.service.AlipayService;
import com.test.order.service.OrderService;
import com.test.order.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ消费者
 *
 * @author MicalJ
 * @create 2019/12/13 15:07
 **/
@Component
@Slf4j
public class RabbitConsumer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AlipayService alipayService;

    @RabbitListener(queues = "receive_queue")
    public void receiveDelay(String orderId, Message message, Channel channel) throws IOException, AlipayApiException {
        Boolean aBoolean = redisTemplate.hasKey(OrderServiceImpl.ORDER_PREFIX + orderId);
        Order order = new Order();
        order.setOrderId(orderId);
        log.info("接收队列收到消息：" + orderId + ", 是否存在key：" + aBoolean);
        if (aBoolean != null && aBoolean){
            order.setOrderStatus(OrderStatus.ORDER_CANCELED);
            //更新订单状态
            orderService.updateOrder(order);
            //向支付宝发起关闭支付请求
            alipayService.close(orderId);
            log.error("订单取消了：" + orderId);
        }
        //通知MQ消息已被接收，可以ACK（从队列中删除）了
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
