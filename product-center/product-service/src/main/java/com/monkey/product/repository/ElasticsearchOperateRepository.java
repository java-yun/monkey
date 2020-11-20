package com.monkey.product.repository;

import com.google.common.collect.Lists;
import com.monkey.common.utils.ObjectUtils;
import com.monkey.product.exception.ProductErrorCode;
import com.monkey.product.exception.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.data.elasticsearch.annotations.Document;
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
//            Settings settings = ElasticsearchUtils.getSettings(clazz);
//            Map<String, Object> mappings = ElasticsearchUtils.getMappings(clazz);
//            Document document = clazz.getDeclaredAnnotation(Document.class);
//            String indexName = document.indexName();
//            CreateIndexRequest request = new CreateIndexRequest(indexName);
//            request.settings(settings);
//            request.mapping(mappings);
//            this.client.indices().create(request, RequestOptions.DEFAULT);
            ObjectUtils.changeAnnotationFieldValue(clazz, Document.class, "indexName", indexName);
            this.restTemplate.createIndex(clazz);
            this.restTemplate.putMapping(clazz);
            log.info("index create success, index clazz: {}", clazz.getName());
        } catch (NoSuchFieldException | IllegalAccessException e) {
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
        GetAliasesResponse response = this.client.indices().getAlias(request, RequestOptions.DEFAULT);
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
        IndicesAliasesRequest request = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasActions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(indexName).alias(indexAlias);
        request.addAliasAction(aliasActions);
        AcknowledgedResponse response = this.client.indices().updateAliases(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     * @param indexName 索引名
     * @return true false
     */
    public boolean deleteIndex(String indexName) throws IOException {
        var request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse response = this.client.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }
}
