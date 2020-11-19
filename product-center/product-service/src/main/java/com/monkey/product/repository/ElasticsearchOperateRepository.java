package com.monkey.product.repository;

import com.monkey.common.utils.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * elasticsearch 仓库操作
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/19 9:55
 */
@Repository
public class ElasticsearchOperateRepository {

    private final RestHighLevelClient client;

    private final ElasticsearchRestTemplate restTemplate;

    ElasticsearchOperateRepository(RestHighLevelClient client, ElasticsearchRestTemplate restTemplate) {
        this.client = client;
        this.restTemplate = restTemplate;
    }

    public void createIndex(Class<?> clazz){
        try {
            Settings settings = this.getSettings(clazz);
            XContentBuilder mappings = this.getMappings(clazz);
            Document document = clazz.getDeclaredAnnotation(Document.class);
            String indexName = document.indexName();
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            request.settings(settings);
            request.mapping(mappings);
            this.client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //this.restTemplate.createIndex(clazz);
    }

    private XContentBuilder getMappings(Class<?> clazz) throws IOException, NoSuchFieldException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject().field("properties")
                    .startObject();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                builder.field(field.getName()).startObject();
                field.setAccessible(true);
                org.springframework.data.elasticsearch.annotations.Field fieldAnnotation = field.getDeclaredAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
                InvocationHandler handler = Proxy.getInvocationHandler(fieldAnnotation);
                Field annotationField = handler.getClass().getDeclaredField("memberValues");
                annotationField.setAccessible(true);
                Map<String, Object> memberValues = (Map<String, Object>) annotationField.get(handler);
                for (Map.Entry<String, Object> entry: memberValues.entrySet()) {
                    String key = StringUtils.upperCharToUnderLine(entry.getKey());
                    if (entry.getValue() instanceof Boolean v) {
                        builder.field(key, v);
                    } else if (entry.getValue() instanceof FieldType fieldType) {
                        builder.field(key, getTypeValue(fieldType));
                    } else if (entry.getValue() instanceof DateFormat dateFormat) {
                        builder.field(key, getDateFormatValue(dateFormat));
                    } else if (entry.getValue() instanceof String s) {
                        builder.field(key, s);
                    }
                }
                builder.endObject();
            }
            builder.endObject().endObject();
            return builder;
        } else {
            return null;
        }
    }

    private String getDateFormatValue(DateFormat dateFormat) {
        return switch (dateFormat) {
            case none -> "none";
            case custom -> "custom";
            case basic_date -> "basic_date";
            case basic_date_time -> "basic_date_time";
            case basic_date_time_no_millis-> "basic_date_time_no_millis";
            case basic_ordinal_date -> "basic_ordinal_date";
            case basic_ordinal_date_time -> "basic_ordinal_date_time";
            case basic_ordinal_date_time_no_millis-> "basic_ordinal_date_time_no_millis";
            case basic_time -> "basic_time";
            case basic_time_no_millis -> "basic_time_no_millis";
            case basic_t_time -> "basic_t_time";
            case basic_t_time_no_millis -> "basic_t_time_no_millis";
            case basic_week_date -> "basic_week_date";
            case basic_week_date_time -> "basic_week_date_time";
            case basic_week_date_time_no_millis -> "basic_week_date_time_no_millis";
            case date -> "date";
            case date_hour -> "date_hour";
            case date_hour_minute -> "date_hour_minute";
            case date_hour_minute_second -> "date_hour_minute_second";
            case date_hour_minute_second_fraction -> "date_hour_minute_second_fraction";
            case date_hour_minute_second_millis -> "date_hour_minute_second_millis";
            case date_optional_time -> "date_optional_time";
            case date_time -> "date_time";
            case date_time_no_millis -> "date_time_no_millis";
            case hour -> "hour";
            case hour_minute -> "hour_minute";
            case hour_minute_second -> "hour_minute_second";
            case hour_minute_second_fraction -> "hour_minute_second_fraction";
            case hour_minute_second_millis -> "hour_minute_second_millis";
            case ordinal_date -> "ordinal_date";
            case ordinal_date_time -> "ordinal_date_time";
            case ordinal_date_time_no_millis -> "ordinal_date_time_no_millis";
            case time -> "time";
            case time_no_millis -> "time_no_millis";
            case t_time -> "t_time";
            case t_time_no_millis -> "t_time_no_millis";
            case week_date -> "week_date";
            case week_date_time -> "week_date_time";
            case weekDateTimeNoMillis -> "weekDateTimeNoMillis";
            case week_year -> "week_year";
            case weekyearWeek -> "weekyearWeek";
            case weekyearWeekDay -> "weekyearWeekDay";
            case year -> "year";
            case year_month -> "year_month";
            case year_month_day -> "year_month_day";
        };
    }

    private String getTypeValue(FieldType fieldType) {
        return switch (fieldType) {
            case Keyword -> "keyword";
            case Text -> "text";
            case Byte -> "byte";
            case Short -> "short";
            case Integer -> "integer";
            case Long -> "long";
            case Double -> "double";
            case Float -> "float";
            case Boolean -> "boolean";
            case Date -> "date";
            case Object -> "object";
            case Ip -> "ip";
            case Binary -> "binary";
            case Nested -> "nested";
            default -> "";
        };
    }

    private Settings getSettings(Class<?> clazz) {
        Document document = clazz.getDeclaredAnnotation(Document.class);
        short replicas = document.replicas();
        short shards = document.shards();
        String refreshInterval = document.refreshInterval();
        return Settings.builder().put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .put("index.refresh_interval", refreshInterval)
                .build();
    }

}
