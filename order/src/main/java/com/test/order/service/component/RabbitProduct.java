package com.test.order.service.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RabbitMQ发送消息
 *
 * @author MicalJ
 * @create 2019/12/13 15:02
 **/
@Component
@Slf4j
public class RabbitProduct {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMessage(String orderId){
        log.info("插入订单到消息队列：{}", orderId);
        rabbitTemplate.convertAndSend(
                "delay_exchange",
                "delay_key",
                orderId,
                message -> {
                    message.getMessageProperties().setExpiration("3000000");
                    return message;
                }
        );
    }
}
