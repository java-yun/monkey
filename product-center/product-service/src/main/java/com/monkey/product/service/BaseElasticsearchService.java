package com.monkey.product.service;

import com.monkey.common.code.BizCode;
import com.monkey.common.exception.SystemException;
import com.monkey.common.repository.ElasticsearchOperateRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * elasticsearch 抽象类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/20 16:00
 */
@Slf4j
public abstract class BaseElasticsearchService {

    protected ElasticsearchOperateRepository elasticsearchOperateRepository;

    public BaseElasticsearchService(ElasticsearchOperateRepository elasticsearchOperateRepository) {
        this.elasticsearchOperateRepository = elasticsearchOperateRepository;
    }

    /**
     * 获取@Document 注解的实体类
     * @return Class
     */
    protected abstract Class<?> getClazz();

    /**
     * 获取索引列表
     * @return List
     */
    protected abstract List<String> getIndexNames();

    /**
     * 获取索引的别名
     * @return String
     */
    protected abstract String getIndexAlias();

    /**
     * es 同步数据
     * @param indexName 索引名称
     */
    protected abstract void syncDataToEs(String indexName);

    public final void execute() {
        log.info("sync data to es start.......................");
        var indexAlias = this.getIndexAlias();
        var indexNames = this.getIndexNames();
        try {
            //根据别名查询索引
            var aliasIndex = this.elasticsearchOperateRepository.getIndexByAlias(indexAlias);
            //别名任意时间只会有一个index，否则数据会有错误
            var indexName = indexNames.stream().filter(aliasIndex::contains).findFirst().orElse(null);
            //别名迁移到其他索引
            var hitIndex = this.changeAliasToAnotherIndex(indexAlias, indexNames, indexName);
            //删除原索引，创建新索引，然后同步数据
            indexNames.stream().filter((index) -> !index.equals(hitIndex)).forEach(this::execute);
            if (Objects.nonNull(hitIndex) && indexNames.contains(hitIndex)) {
                //别名迁移
                this.changeAliasToAnotherIndex(indexAlias, indexNames, hitIndex);
                //删除原索引，创建新索引，然后同步数据
                execute(hitIndex);
            } else {
                this.changeAliasToAnotherIndex(indexAlias, indexNames, null);
            }
            log.info("sync data to es finish.......................");
        } catch (IOException e) {
            log.error("elasticsearch data sync error, indexAlias: {}", indexAlias, e);
            throw SystemException.throwException(BizCode.DATA_SYNC_ERROR);
        }
    }

    /**
     * execute
     * @param indexName 索引名称
     */
    private void execute(String indexName) {
        try {
            //删除索引
            if (this.elasticsearchOperateRepository.isIndexExists(indexName)) {
                this.elasticsearchOperateRepository.deleteIndex(indexName);
            }
            //创建索引
            this.elasticsearchOperateRepository.createIndex(this.getClazz(), indexName);
            //数据同步
            this.syncDataToEs(indexName);
            /*
                由于index 的 refreshInterval设置的是  -1  即 不自动刷新索引，所以插入结束之后一定要手动刷新索引，否则无数据
                为什么设置成   -1 ？
                大数据量插入时，不自动刷新可以提高插入效率
             */
            this.elasticsearchOperateRepository.refreshIndex(indexName);
            //更新索引settings
            var settings = this.getUpdateSettings();
            this.elasticsearchOperateRepository.setSettings(settings, indexName);
        } catch (IOException e) {
            log.error("elasticsearch data sync exception", e);
            throw SystemException.throwException(BizCode.DATA_SYNC_ERROR);
        }
    }

    /**
     * 获取索引 settings
     * @return Settings
     */
    protected abstract Settings getUpdateSettings();

    /**
     * 迁移别名到其他索引，返回迁移后的索引名称
     * @param indexAlias 别名
     * @param indexNames 索引列表
     * @param indexName 原先别名所在的索引
     * @return String
     */
    private String changeAliasToAnotherIndex(String indexAlias, List<String> indexNames, String indexName) throws IOException {
        String hitIndex = null;
        if (Objects.nonNull(indexNames) && indexNames.size() > 0) {
            for (String str: indexNames) {
                if (str.equals(indexName)) {
                    continue;
                }
                //判断索引是否存在
                if (this.elasticsearchOperateRepository.isIndexExists(str)) {
                    //迁移别名
                    this.elasticsearchOperateRepository.addAliasToIndex(indexAlias, str);
                    hitIndex = str;
                    break;
                }
            }
        }
        return hitIndex;
    }
}
