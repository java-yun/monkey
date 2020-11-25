package com.monkey.common.utils;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 检验
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/23 16:11
 */
public final class ValidUtils {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");

    /**
     * Don't let anyone instantiate this class.
     */
    private ValidUtils() {
    }

    /**
     * 判断元素是否为Null或者Empty
     * <p>
     * 目前可以判断一下元素
     * <ul>
     * <li>Collection,使用其isEmpty()</li>
     * <li>Map,使用其isEmpty()</li>
     * <li>Object[],判断length==0</li>
     * <li>String,使用.trim().length()效率高</li>
     * <li>Enumeration,使用hasMoreElements()</li>
     * <li>Iterator,使用hasNext()</li>
     * </ul>
     *
     * @param value 可以是Collection,Map,Object[],String,Enumeration,Iterator类型
     * @return 空返回true
     * @since 1.0
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object value) {
        if (null == value) {
            return true;
        }
        boolean flag = false;
        // 字符串
        if (value instanceof String s) {
            flag = s.trim().length() <= 0;
        }
        // Object[]object数组
        else if (value instanceof Object[] o) {
            flag = o.length == 0;
        }
        // ***********************************************************
        // 集合
        else if (value instanceof Collection c) {
            flag = c.isEmpty();
        }
        // map
        else if (value instanceof Map m) {
            flag = m.isEmpty();
        }
        // 枚举
        else if (value instanceof Enumeration e) {
            flag = !e.hasMoreElements();
        }
        // Iterator迭代器
        else if (value instanceof Iterator i) {
            flag = !i.hasNext();
        }
        return flag;
    }

    /**
     * 判断元素是否不为Null或者Empty,调用<code>!isNullOrEmpty</code>方法
     * <p>
     * 目前可以判断一下元素
     * <ul>
     * <li>Collection,使用其isEmpty()</li>
     * <li>Map,使用其isEmpty()</li>
     * <li>Object[],判断length==0</li>
     * <li>String,使用.trim().length()效率高</li>
     * <li>Enumeration,使用hasMoreElements()</li>
     * <li>Iterator,使用hasNext()</li>
     * </ul>
     *
     * @param value 可以是Collection,Map,Object[],String,Enumeration,Iterator类型
     * @return 不为空返回true
     * @since 1.0
     */
    public static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

    /**
     * 判断元素是否是数字,通过正则匹配实现
     *
     * @param str 传入字符
     * @return 是数字返回true
     * @since 1.0
     */
    public static boolean isNumeric(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

}
