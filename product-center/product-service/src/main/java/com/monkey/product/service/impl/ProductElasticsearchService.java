package com.monkey.product.service.impl;

import com.monkey.product.bo.ProductIndex;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import com.monkey.product.service.BaseElasticsearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品 es 操作service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/20 17:48
 */
@Service
public class ProductElasticsearchService extends BaseElasticsearchService {

    public ProductElasticsearchService(ElasticsearchOperateRepository elasticsearchOperateRepository) {
        super(elasticsearchOperateRepository);
    }

    @Override
    protected Class<?> getClazz() {
        return ProductIndex.class;
    }

    @Override
    protected List<String> getIndexNames() {
        return null;
    }

    @Override
    protected String getIndexAlias() {
        return null;
    }

    @Override
    protected void syncDataToEs() {

    }
}
