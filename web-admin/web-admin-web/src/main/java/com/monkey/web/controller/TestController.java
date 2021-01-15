package com.monkey.web.controller;

import com.monkey.common.repository.RedisRepository;
import com.monkey.common.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:24
 */
@RestController
public class TestController {


    private final RedisRepository redisRepository;

    public TestController(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @RequestMapping(value = "/publishMessage", method = RequestMethod.GET)
    public Response<String> publishMessageToRedis() {
        this.redisRepository.convertAndSend("channel.test", "hello moto!");
        return Response.ok();
    }

}
