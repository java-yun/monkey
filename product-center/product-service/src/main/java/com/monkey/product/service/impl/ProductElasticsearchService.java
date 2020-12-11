package com.monkey.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.monkey.common.utils.NumberUtils;
import com.monkey.product.bo.ProductIndex;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.entity.Product;
import com.monkey.product.exception.ProductErrorCode;
import com.monkey.product.exception.ProductException;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import com.monkey.product.service.BaseElasticsearchService;
import com.monkey.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            log.info("data add to bulk processor finished, waiting commit to es................................");
        } catch (Exception e) {
            log.error("product bulk sync es exception", e);
            throw ProductException.throwException(ProductErrorCode.BULK_OPERATOR_ERROR);
        } finally {
            try {
                var awaitClose = bulkProcessor.awaitClose(100L, TimeUnit.SECONDS);
                log.info("whether the insertion was completed in time: {}", awaitClose);
                /*
                    由于index 的 refreshInterval设置的是  -1  即 不自动刷新索引，所以插入结束之后一定要手动刷新索引，否则无数据
                    为什么设置成   -1 ？
                    大数据量插入时，不自动刷新可以提高插入效率
                 */
                this.elasticsearchOperateRepository.refreshIndex(indexName);
            } catch (InterruptedException | IOException e) {
                log.error("bulkProcessor awaitClose or refresh index exception", e);
            }
        }
    }
}
