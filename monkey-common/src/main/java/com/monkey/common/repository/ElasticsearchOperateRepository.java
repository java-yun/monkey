package com.monkey.common.repository;

import com.google.common.collect.Lists;
import com.monkey.common.code.BizCode;
import com.monkey.common.exception.SystemException;
import com.monkey.common.utils.ElasticsearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

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
              //连续两次用同一个clazz创建索引，存在index_uuid相同异常，有待研究解决
//            this.restTemplate.createIndex(clazz);
//            this.restTemplate.putMapping(clazz);
            log.info("index create success, indexName:{}, index clazz: {}", indexName, clazz.getName());
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            log.error("index create error, indexName:{}, index class :{}", indexName, clazz.getName(), e);
            throw SystemException.throwException(BizCode.INDEX_CREATE_ERROR);
        }



    }

    /**
     * 查询别名下的索引
     * @param indexAlias 别名
     * @return 索引列表
     * @throws IOException IOException
     */
    public List<String> getIndexByAlias(String indexAlias) throws IOException {
        List<String> list = Lists.newArrayList();
        var request = new GetAliasesRequest();
        request.aliases(indexAlias);
        var response = this.client.indices().getAlias(request, RequestOptions.DEFAULT);
        var aliases = response.getAliases();
        aliases.forEach((indexName, aliasMetaData) -> list.add(indexName));
        return list;
    }

    /**
     * 判断索引是否存在
     * @param indexName 索引名
     * @return true false
     * @throws IOException IOException
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
     * @throws IOException IOException
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
     * @throws IOException IOException
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
                    bulkResponse.forEach(itemResponse -> log.error("fail message: {}", itemResponse.getFailureMessage()));
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable throwable) {
                log.error("insert {} pieces of data fail, executionId: {}----", bulkRequest.numberOfActions(), executionId, throwable);
            }
        };
        return BulkProcessor.builder((request, bulkListener) -> this.client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener)
                //达到刷新的条数
                .setBulkActions(bulkSize)
                //达到刷新的大小
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
                //固定刷新的时间频率
                .setFlushInterval(TimeValue.timeValueSeconds(5L))
                //并发线程数
                .setConcurrentRequests(4)
                //重试补偿策略
                .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3))
                .build();
    }

    /**
     * 查询
     * @param searchSourceBuilder searchSourceBuilder
     * @param size 查询记录数
     * @param indices 索引数组
     * @return SearchResponse
     * @throws IOException IOException
     */
    public SearchResponse doSearch(SearchSourceBuilder searchSourceBuilder, int size, String... indices) throws IOException {
        searchSourceBuilder.fetchSource(true);
        searchSourceBuilder.timeout(new TimeValue(500));
        searchSourceBuilder.size(size);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices(indices);
        return this.client.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 刷新索引
     * @param indices 索引名称
     * @return  RestStatus
     * @throws IOException IOException
     */
    public RestStatus refreshIndex(String... indices) throws IOException {
        RefreshRequest request = new RefreshRequest(indices);
        RefreshResponse response = this.client.indices().refresh(request, RequestOptions.DEFAULT);
        return response.getStatus();
    }

    /**
     * 重置索引settings
     * @param settings settings
     * @param indexName indexName
     */
    public void setSettings(Settings settings, String indexName) {
        UpdateSettingsRequest request = new UpdateSettingsRequest(indexName);
        request.settings(settings);
        this.client.indices().putSettingsAsync(request, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(AcknowledgedResponse response) {
                log.info("reset index settings success? {}", response.isAcknowledged());
            }

            @Override
            public void onFailure(Exception e) {
                log.error("reset index settings fail", e);
            }
        });
    }
}
