package com.monkey.common.utils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;

/**
 * jpa 注解工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 17:16
 */
public class JpaAnnotationUtils {
    /**
     * 获取jpa主键
     * @param clazz clazz
     * @return String
     * @throws NoSuchFieldException NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static String getIdentityPrimaryKey(Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        String identity = null;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            //打开私有访问权限
            field.setAccessible(true);
            String name = field.getName();
            Id id = field.getDeclaredAnnotation(Id.class);
            Column column = field.getDeclaredAnnotation(Column.class);
            GeneratedValue generate = field.getDeclaredAnnotation(GeneratedValue.class);
            if (Objects.nonNull(generate) && Objects.nonNull(id)) {
                Object fieldValue = getAnnotationValue(generate, "strategy");
                if (GenerationType.IDENTITY.equals(fieldValue)) {
                    identity = name;
                    if (Objects.nonNull(column)) {
                        Object value = getAnnotationValue(column, "name");
                        if (Objects.nonNull(value)) {
                            identity = value.toString();
                        }
                    }
                }
                break;
            }
        }
        return identity;
    }

    /**
     * 获取注解的某个字段值
     * @param annotation 注解
     * @param fieldName 字段
     * @return Object
     * @throws NoSuchFieldException NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static Object getAnnotationValue(Annotation annotation, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        //获取 注解 这个代理实例所持有的 InvocationHandler
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field annotationField = handler.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        annotationField.setAccessible(true);
        Map memberValues = (Map) annotationField.get(handler);
        return memberValues.get(fieldName);
    }
}
