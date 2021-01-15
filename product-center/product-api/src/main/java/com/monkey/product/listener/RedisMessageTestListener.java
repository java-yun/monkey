package com.monkey.product.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2021/1/7 15:27
 */
@Slf4j
@Component
public class RedisMessageTestListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("redis test recevied message: {}", new String(message.getBody()));
    }
}
