package com.monkey.product.repository;

import com.monkey.common.repository.RedisRepository;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.constants.RedisKeyConstants;
import org.springframework.stereotype.Repository;

/**
 * 商品redis
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/16 9:25
 */
@Repository
public class ProductRedisRepository {

    private final RedisRepository redisRepository;

    public ProductRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    /**
     * 获取 商品编码
     * @return String
     */
    public String getProductCode() {
        if (!this.redisRepository.setNx(RedisKeyConstants.PRODUCT_CODE, BusinessConstants.START_PRODUCT_CODE)) {
            return String.valueOf(redisRepository.incrementOne(RedisKeyConstants.PRODUCT_CODE));
        }
        return BusinessConstants.START_PRODUCT_CODE;
    }
}
