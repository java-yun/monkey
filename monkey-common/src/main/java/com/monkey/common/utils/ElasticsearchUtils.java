package com.monkey.common.utils;

import com.google.common.collect.Maps;
import com.monkey.common.code.BizCode;
import com.monkey.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.settings.Settings;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * elasticsearch 工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/20 14:22
 */
@Slf4j
public class ElasticsearchUtils {

    private final static List<String> ES_FIELD_ATTR = List.of("type", "index", "format", "store", "analyzer", "searchAnalyzer");


    public static Map<String, Object> getMappings(Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            Map<String, Object> mappings = Maps.newHashMap();
            Map<String, Object> fieldMap = Maps.newHashMap();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                org.springframework.data.elasticsearch.annotations.Field fieldAnnotation = field.getDeclaredAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
                if (Objects.nonNull(fieldAnnotation)) {
                    InvocationHandler handler = Proxy.getInvocationHandler(fieldAnnotation);
                    Field annotationField = handler.getClass().getDeclaredField("memberValues");
                    annotationField.setAccessible(true);
                    Map<String, Object> fieldAnnotationAttrMap = Maps.newHashMap();
                    Map<String, Object> memberValues = (Map<String, Object>) annotationField.get(handler);
                    for (Map.Entry<String, Object> entry: memberValues.entrySet()) {
                        if (!ES_FIELD_ATTR.contains(entry.getKey())) {
                            continue;
                        }
                        String key = StringUtils.upperCharToUnderLine(entry.getKey());
                        if (entry.getValue() instanceof Boolean b) {
                            fieldAnnotationAttrMap.put(key, b);
                        } else if (entry.getValue() instanceof FieldType fieldType) {
                            fieldAnnotationAttrMap.put(key, getTypeValue(fieldType));
                        } else if (entry.getValue() instanceof DateFormat dateFormat) {
                            fieldAnnotationAttrMap.put(key, getDateFormatValue(dateFormat));
                        } else if (entry.getValue() instanceof String s) {
                            fieldAnnotationAttrMap.put(key, s);
                        }
                    }
                    if (!"date".equals(fieldAnnotationAttrMap.get("type").toString())) {
                        fieldAnnotationAttrMap.remove("format");
                    }
                    boolean indexed = "false".equals(fieldAnnotationAttrMap.get("index").toString());
                    boolean removeAnalyzer = !"text".equals(fieldAnnotationAttrMap.get("type").toString())
                            || ("text".equals(fieldAnnotationAttrMap.get("type").toString()) && indexed);
                    if (removeAnalyzer) {
                        fieldAnnotationAttrMap.remove("analyzer");
                        fieldAnnotationAttrMap.remove("search_analyzer");
                    }
                    fieldMap.put(field.getName(), fieldAnnotationAttrMap);
                }
            }
            mappings.put("properties", fieldMap);
            return mappings;
        } else {
            log.error("index mappings create error, index clazz : {}", clazz.getName());
            throw SystemException.throwException(BizCode.INDEX_CREATE_ERROR);
        }
    }

    public static String getDateFormatValue(DateFormat dateFormat) {
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

    public static String getTypeValue(FieldType fieldType) {
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

    public static Settings getSettings(Class<?> clazz) {
        var document = clazz.getDeclaredAnnotation(Document.class);
        var replicas = document.replicas();
        var shards = document.shards();
        var refreshInterval = document.refreshInterval();
        return Settings.builder().put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .put("index.refresh_interval", refreshInterval)
                .build();
    }
}
