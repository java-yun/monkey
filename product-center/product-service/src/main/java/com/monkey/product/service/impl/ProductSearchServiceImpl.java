package com.monkey.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.monkey.product.bo.ProductSearchRequest;
import com.monkey.product.constants.BusinessConstants;
import com.monkey.product.exception.ProductErrorCode;
import com.monkey.product.exception.ProductException;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import com.monkey.product.service.ProductSearchService;
import com.monkey.product.utils.SearchAfter;
import com.monkey.product.vo.ProductSearch;
import com.monkey.product.vo.ProductSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
        var searchSourceBuilder = this.getSearchSourceBuilder(productSearchRequest);
        List<ProductSearch> esProducts = Lists.newArrayList();
        String afterKey = null;
        try {
            var response = elasticsearchOperateRepository.doSearch(new String[]{BusinessConstants.PRODUCT_INDEX_ALIAS}, searchSourceBuilder, BusinessConstants.PRODUCT_ES_QUERY_SIZE);
            var searchHits = response.getHits().getHits();
            if (searchHits.length > 0) {
                afterKey = SearchAfter.toAfterKey(searchHits[searchHits.length - 1].getSortValues());
                for (SearchHit searchHit: searchHits) {
                    var productSearch = JSONObject.parseObject(searchHit.getSourceAsString(), ProductSearch.class);
                    esProducts.add(productSearch);
                }
            }
        } catch (IOException e) {
            log.error("search product from elasticsearch exception, request:{}", JSON.toJSONString(productSearchRequest), e);
            throw ProductException.throwException(ProductErrorCode.PRODUCT_SEARCH_ERROR);
        }
        return new ProductSearchResult(esProducts, afterKey);
    }

    private SearchSourceBuilder getSearchSourceBuilder(ProductSearchRequest request) {
        var sourceBuilder = new SearchSourceBuilder();
        var boolQueryBuilder = QueryBuilders.boolQuery();
        //价格区间
        //termQuery和matchQuery、queryStringQuery的区别在于，后两者会对查询条件进行分词匹配
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
        if (StringUtils.isNotEmpty(request.getSearchKeyword())) {
            String searchKeyword = request.getSearchKeyword().trim();
            //searchKeyword大于指定长度的，对searchKeyword进行分词匹配查询，否者不分词
            if (searchKeyword.length() > BusinessConstants.SEARCH_KEYWORD_NOT_PARTICIPLE_MAX_LENGTH) {
                boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchKeyword, "name", "brief", "description"));
            } else {
                boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.termQuery("name", searchKeyword))
                        .should(QueryBuilders.termQuery("brief", searchKeyword))
                        .should(QueryBuilders.termQuery("description", searchKeyword)));
            }
        }
        //排序
        if (StringUtils.isNotEmpty(request.getOrderByColumn())) {
            sourceBuilder.sort(SortBuilders.fieldSort(request.getOrderByColumn()).missing(0).order(SortOrder.fromString(request.getOrderByType())));
        } else {
            sourceBuilder.sort(SortBuilders.fieldSort("onSaleTime").missing(0).order(SortOrder.DESC))
                .sort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        sourceBuilder.query(boolQueryBuilder);
        //设置search after 解决深度分页问题
        if (StringUtils.isNotEmpty(request.getAfterKey())) {
            sourceBuilder.searchAfter(SearchAfter.toSearchAfter(request.getAfterKey()));
        }

        return sourceBuilder;
    }
}
