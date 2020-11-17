package com.monkey.product.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 仓库
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 18:02
 */
@Component
public class RedisRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 字符串存储
     *
     * @param key key
     * @param value value
     */
    public void setString(final String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 字符串存储带有超时时间的数据
     *
     * @param key key
     * @param value value
     * @param expire expire time
     */
    public void setString(final String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 获取字符串值
     *
     * @param key key
     * @return String
     */
    public String getString(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 自增长,
     * @param key key
     * @param delta value
     * @return Long
     */
    public Long increment(final String key, long delta) {
        return this.incrementExpire(key,delta,0);
    }

    /**
     * 带过期时间的自增长
     * @param key key
     * @param delta delta
     * @param expire expire time
     * @return Long
     */
    public Long incrementExpire(final String key, long delta, long expire){
        var value = stringRedisTemplate.opsForValue().increment(key, delta);
        if(expire != 0){
            stringRedisTemplate.expire(key,expire,TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 一次默认只增长1
     * @param key key
     * @return Long
     */
    public Long incrementOne(final String key){
        return increment(key,1);
    }

    /**
     * 一次默认只增长1
     * @param key key
     * @param expire expire time
     * @return Long
     */
    public Long incrementOneExpire(final String key, long expire){
        return incrementExpire(key,1,expire);
    }

    /**
     * set if not exists
     * @param key key
     * @param value time
     * @return true 设值成功， false 设值失败
     */
    public boolean setNx(String key, String value) {
        return this.stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }
}
