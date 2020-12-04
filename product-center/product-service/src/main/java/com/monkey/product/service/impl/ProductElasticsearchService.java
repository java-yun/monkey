package com.monkey.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.monkey.common.utils.NumberUtils;
import com.monkey.product.bo.ProductIndex;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.entity.Product;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import com.monkey.product.service.BaseElasticsearchService;
import com.monkey.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 商品 es 操作service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/20 17:48
 */
@Slf4j
@Service
public class ProductElasticsearchService extends BaseElasticsearchService {

    private final ProductService productService;

    public ProductElasticsearchService(ElasticsearchOperateRepository elasticsearchOperateRepository, ProductService productService) {
        super(elasticsearchOperateRepository);
        this.productService = productService;
    }

    @Override
    protected Class<?> getClazz() {
        return ProductIndex.class;
    }

    @Override
    protected List<String> getIndexNames() {
        return BusinessConstants.PRODUCT_INDEXES;
    }

    @Override
    protected String getIndexAlias() {
        return BusinessConstants.PRODUCT_INDEX_ALIAS;
    }

    @Override
    protected void syncDataToEs(String indexName) {
        //查询上架商品的总记录数
        long count = this.productService.getOnSaleTotalCount();
        var cursor = 0;
        var times = NumberUtils.getDivUpValue((int) count, BusinessConstants.PRODUCT_BATCH_QUERY_SIZE);
        var bulkProcessor = super.elasticsearchOperateRepository.initBulkProcessor(BusinessConstants.PRODUCT_BULK_SIZE);
        try {
            for (int i = 0; i < times; i++) {
                List<Product> products = this.productService.getOnSaleProductWithLimit(cursor, BusinessConstants.PRODUCT_BATCH_QUERY_SIZE);
                if (Objects.isNull(products) || products.size() < 1) {
                    break;
                }
                products.forEach(product -> {
                    ProductIndex productIndex = ProductIndex.builder().build();
                    BeanUtils.copyProperties(product, productIndex);
                    IndexRequest request = new IndexRequest(indexName).id(product.getCode() + "_" + product.getId()).source(JSONObject.toJSONString(productIndex), XContentType.JSON).opType(DocWriteRequest.OpType.CREATE);
                    bulkProcessor.add(request);
                });
                cursor = products.get(products.size() - 1).getId();
            }
            bulkProcessor.flush();
        } catch (Exception e) {
            log.error("product bulk sync es exception", e);
        } finally {
            try {
                bulkProcessor.awaitClose(100L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("bulkProcessor awaitClose exception", e);
            }
        }
    }
}
