package com.monkey.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.monkey.product.bo.ProductSearchRequest;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.exception.ProductErrorCode;
import com.monkey.product.exception.ProductException;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import com.monkey.product.service.ProductSearchService;
import com.monkey.product.vo.ProductSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * 商品 搜索 service 实现
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 14:36
 */
@Slf4j
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ElasticsearchOperateRepository elasticsearchOperateRepository;

    ProductSearchServiceImpl(ElasticsearchOperateRepository elasticsearchOperateRepository) {
        this.elasticsearchOperateRepository = elasticsearchOperateRepository;
    }

    @Override
    public ProductSearchResult searchProduct(ProductSearchRequest productSearchRequest) {
        SearchSourceBuilder searchSourceBuilder = this.getSearchSourceBuilder(productSearchRequest);
        try {
            SearchResponse searchResponse = elasticsearchOperateRepository.doSearch(new String[]{BusinessConstants.PRODUCT_INDEX_ALIAS}, searchSourceBuilder, BusinessConstants.PRODUCT_ES_QUERY_SIZE);
        } catch (IOException e) {
            log.error("search product from elasticsearch exception, request:{}", JSON.toJSONString(productSearchRequest), e);
            throw ProductException.throwException(ProductErrorCode.PRODUCT_SEARCH_ERROR);
        }
        return null;
    }

    private SearchSourceBuilder getSearchSourceBuilder(ProductSearchRequest request) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //价格区间
        if (Objects.nonNull(request.getMinPrice())) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(request.getMinPrice()));
        }
        if (Objects.nonNull(request.getMaxPrice())) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(request.getMaxPrice()));
        }
        if (StringUtils.isNotEmpty(request.getCode())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("code", request.getCode()));
        }
        if (Objects.nonNull(request.getCategoryId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("categoryId", request.getCategoryId()));
        }
        if (Objects.nonNull(request.getBrandId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("brandId", request.getBrandId()));
        }
        if (Objects.nonNull(request.getId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("id", request.getId()));
        }
        if (Objects.nonNull(request.getOnSaleTime())) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("onSaleTime").gte(request.getOnSaleTime().getTime()));
        }
        //在 name、brief、description中分词查找
        if (StringUtils.isNotEmpty(request.getQueryKey())) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(request.getQueryKey(), "name", "brief", "description"));
        }
        sourceBuilder.query(boolQueryBuilder);
//        sourceBuilder.searchAfter()
        return sourceBuilder;
    }
}
