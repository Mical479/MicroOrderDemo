package com.test.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列相关配置
 *
 * @author MicalJ
 * @create 2019/12/13 14:46
 **/
@Configuration
public class RelayQueueConfig {

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange("delay_exchange");
    }

    /**
     * 私信队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        //设置要转发给死信接收交换机和队列的标签
        Map<String, Object> map = new HashMap<>(16);
        map.put("x-dead-letter-exchange", "receive_exchange");
        map.put("x-dead-letter-routing-key", "receive_key");
        return new Queue("delay_queue", true, false, false, map);
    }

    /**
     * 将死信队列绑定到死信交换机
     * @return
     */
    @Bean
    public Binding delayBinding(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay_key");
    }

    /**
     * 死信接收交换机
     * @return
     */
    @Bean
    public DirectExchange receiveExchange(){
        return new DirectExchange("receive_exchange");
    }

    /**
     * 死信接收队列
     * @return
     */
    @Bean
    public Queue receiveQueue(){
        return new Queue("receive_queue");
    }

    /**
     * 将死信接收队列绑定到死信接收交换机
     * @return
     */
    @Bean
    public Binding receiveBingding(){
        return BindingBuilder.bind(receiveQueue()).to(receiveExchange()).with("receive_key");
    }
}
