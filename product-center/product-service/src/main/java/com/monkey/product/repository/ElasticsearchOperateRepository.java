package com.monkey.product.repository;

import com.google.common.collect.Lists;
import com.monkey.common.utils.ObjectUtils;
import com.monkey.product.exception.ProductErrorCode;
import com.monkey.product.exception.ProductException;
import com.monkey.product.utils.ElasticsearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch 仓库操作
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/19 9:55
 */
@Slf4j
@Repository
public class ElasticsearchOperateRepository {

    private final RestHighLevelClient client;

    private final ElasticsearchRestTemplate restTemplate;

    ElasticsearchOperateRepository(RestHighLevelClient client, ElasticsearchRestTemplate restTemplate) {
        this.client = client;
        this.restTemplate = restTemplate;
    }

    /**
     * 创建索引
     * @param clazz @Document注解的类
     * @param indexName 索引名称
     */
    public void createIndex(Class<?> clazz, String indexName){
        try {
            var settings = ElasticsearchUtils.getSettings(clazz);
            var mappings = ElasticsearchUtils.getMappings(clazz);
            var request = new CreateIndexRequest(indexName);
            request.settings(settings);
            request.mapping(mappings);
            this.client.indices().create(request, RequestOptions.DEFAULT);
//            ObjectUtils.changeAnnotationFieldValue(clazz, Document.class, "indexName", indexName);
//            this.restTemplate.createIndex(clazz);
//            this.restTemplate.putMapping(clazz);
            log.info("index create success, index clazz: {}", clazz.getName());
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            log.error("index create error, index class :{}", clazz.getName(), e);
            throw ProductException.throwException(ProductErrorCode.INDEX_CREATE_ERROR);
        }



    }

    /**
     * 查询别名下的索引
     * @param indexAlias 别名
     * @return 索引列表
     */
    public List<String> getIndexByAlias(String indexAlias) throws IOException {
        List<String> list = Lists.newArrayList();
        var request = new GetAliasesRequest();
        request.aliases(indexAlias);
        var response = this.client.indices().getAlias(request, RequestOptions.DEFAULT);
        var aliases = response.getAliases();
        aliases.forEach((indexName, aliasMetaData) -> {
            list.add(indexName);
        });
        return list;
    }

    /**
     * 判断索引是否存在
     * @param indexName 索引名
     * @return true false
     */
    public boolean isIndexExists(String indexName) throws IOException {
        var request = new GetIndexRequest(indexName);
        return this.client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 索引添加别名
     * @param indexAlias 别名
     * @param indexName 索引名
     * @return true false
     */
    public boolean addAliasToIndex(String indexAlias, String indexName) throws IOException {
        var request = new IndicesAliasesRequest();
        var aliasActions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(indexName).alias(indexAlias);
        request.addAliasAction(aliasActions);
        var response = this.client.indices().updateAliases(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     * @param indexName 索引名
     * @return true false
     */
    public boolean deleteIndex(String indexName) throws IOException {
        var request = new DeleteIndexRequest(indexName);
        var response = this.client.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 获取批量操作的  BulkProcessor
     * @param bulkSize 批量大小
     * @return BulkProcessor
     */
    public BulkProcessor initBulkProcessor(int bulkSize) {
        var listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                log.info("---attempting to insert {} pieces of data, executionId: {}----", bulkRequest.numberOfActions(), executionId);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                var hasFailures = bulkResponse.hasFailures();
                log.info("---insert {} pieces of data successfully, executionId: {}, hasFailures: {}----", bulkRequest.numberOfActions(), executionId, hasFailures);
                if (hasFailures) {
                    bulkResponse.forEach(itemResponse -> {
                        log.error("fail message: {}", itemResponse.getFailureMessage());
                    });
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable throwable) {
                log.error("insert {} pieces of data fail, executionId: {}----", bulkRequest.numberOfActions(), executionId, throwable);
            }
        };
        return BulkProcessor.builder((request, bulkListener) -> this.client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener)
                .setBulkActions(bulkSize)
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(2)
                .build();
    }

}
