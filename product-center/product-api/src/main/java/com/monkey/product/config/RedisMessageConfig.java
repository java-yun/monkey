package com.monkey.product.config;

import com.monkey.product.listener.RedisMessageTestListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * redis 订阅发布 监听配置
 * @author jiangyun
 * @version 0.0.1
 * @date 2021/1/7 15:31
 */
@Slf4j
@Configuration
public class RedisMessageConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter testAdapter) {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫chat 的通道
        container.addMessageListener(testAdapter, new PatternTopic("channel.test"));
        //这个container 可以添加多个 messageListener
//        container.addMessageListener(CatAdapter, new PatternTopic("cat"));
        log.info("redis message listener created...............................");
        return container;
    }

    @Bean
    MessageListenerAdapter testAdapter() {
        return new MessageListenerAdapter(new RedisMessageTestListener());
    }
}
