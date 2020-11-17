package com.monkey.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * 对象工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/13 11:27
 */
public class ObjectUtils {

    /**
     * 获取 list 中每个元素的属性以及属性值
     * @param list list
     * @param keyAnnotation 取代原字段名作为map key的注解
     * @param keyAnnotationField 注解的属性
     * @return List<Map<String, Object>>
     * @throws IllegalAccessException IllegalAccessException
     * @throws NoSuchFieldException NoSuchFieldException
     */
    public static List<Map<String, Object>> getListElementAttrValue(List<?> list, Class keyAnnotation, String keyAnnotationField) throws IllegalAccessException, NoSuchFieldException {
        List<Map<String, Object>> result = new ArrayList<>();
        if (Objects.nonNull(list) || list.size() > 0) {
            for (Object obj : list) {
                Map<String, Object> map = getObjectAttrValue(obj, keyAnnotation, keyAnnotationField);
                if (Objects.nonNull(map)) {
                    result.add(map);
                }
            }
        }
        return result;
    }

    /**
     * 获取对象的属性以及属性值
     * @param obj obj
     * @param keyAnnotation 取代原字段名作为map key的注解
     * @param keyAnnotationField 注解的属性
     * @return Map
     * @throws IllegalAccessException IllegalAccessException
     * @throws NoSuchFieldException NoSuchFieldException
     */
    public static Map<String, Object> getObjectAttrValue(Object obj, Class keyAnnotation, String keyAnnotationField) throws IllegalAccessException, NoSuchFieldException {
        Map<String, Object> map = null;
        if (Objects.nonNull(obj)) {
            map = new HashMap<>(16);
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                //打开私有访问权限
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(obj);
                Annotation annotation = field.getDeclaredAnnotation(keyAnnotation);
                if (Objects.nonNull(annotation) && Objects.nonNull(keyAnnotation) && Objects.nonNull(keyAnnotationField)) {
                    name = getAnnotationValue(annotation, keyAnnotationField).toString();
                }
                map.put(name, value);
            }
        }
        return map;
    }

    /**
     * 获取类  指定注解的指定属性值
     * @param obj 类的实例
     * @param clazz 注解的class
     * @param fieldName 注解的字段名
     * @return String
     * @throws NoSuchFieldException NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static String getClassAnnotationAttr(Object obj, Class clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Annotation annotation = obj.getClass().getDeclaredAnnotation(clazz);
        Object value = getAnnotationValue(annotation, fieldName);
        return Objects.nonNull(value) ? value.toString() : null;
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
