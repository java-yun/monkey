package com.monkey.common.repository;

import com.google.common.collect.Lists;
import com.monkey.common.code.BizCode;
import com.monkey.common.exception.SystemException;
import com.monkey.common.utils.JpaAnnotationUtils;
import com.monkey.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品批量操作
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/13 11:17
 */
@Slf4j
@Repository
public class BatchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(rollbackOn = Exception.class)
    public int batchInsert(List<?> list) {
        if (Objects.nonNull(list) && list.size() > 0) {
            String tableName;
            List<Map<String, Object>> records;
            String identity;
            try {
                //获取表名
                tableName = ObjectUtils.getClassAnnotationAttr(list.get(0).getClass(), Table.class, "name");
                //获取表字段以及字段值
                records = ObjectUtils.getListElementAttrValue(list, Column.class, "name");
                //获取自增主键字段的key
                identity = JpaAnnotationUtils.getIdentityPrimaryKey(list.get(0).getClass());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("get table structure exception : ", e);
                throw SystemException.throwException(BizCode.DATA_INSERT_ERROR);
            }
            if (Objects.isNull(tableName)) {
                log.error("can not get table name, po name: {}", list.get(0).getClass().getName());
                throw SystemException.throwException(BizCode.DATA_INSERT_ERROR);
            }
            var builder = new StringBuilder("INSERT INTO " + tableName + "(");
            var record = records.get(0);
            LinkedList<String> insertColumns = Lists.newLinkedList();
            for(Map.Entry<String, Object> entry: record.entrySet()) {
                //主键不是自增
                if (!entry.getKey().equals(identity)) {
                    String key = entry.getKey();
                    insertColumns.add(key);
                    builder.append(entry.getKey()).append(", ");
                }
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append(") VALUES ");
            //拼接占位符 ?
            for (int i = 0; i < records.size(); i++) {
                builder.append("(");
                //根据要插入的字段
                builder.append("?, ".repeat(insertColumns.size()));
                builder.deleteCharAt(builder.lastIndexOf(","));
                builder.append("),");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
            var sql = builder.toString();
            log.info("batch insert sql : {}", sql);
            var query = entityManager.createNativeQuery(sql);
            //设置值
            int paramIndex = 1;
            for (Map<String, Object> map: records) {
                //根据要插入的字段
                for (String column: insertColumns) {
                    query.setParameter(paramIndex++, map.get(column));
                }
                builder.deleteCharAt(builder.lastIndexOf(","));
                builder.append("),");
            }
            return query.executeUpdate();
        }
        return 0;
    }
}
