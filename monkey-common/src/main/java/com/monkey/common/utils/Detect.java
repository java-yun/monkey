package com.monkey.common.utils;

import java.util.*;

/**
 * 侦查  发现
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/3 10:13
 */
public class Detect {

    /**
     * 判断为空或null
     * @param value String、Object[]、Collection、Map、Enumeration、Iterator
     * @return boolean
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof String s) {
            return s.length() == 0;
        }
        if (value instanceof Object[] arr) {
            return arr.length == 0;
        }
        if (value instanceof Collection c) {
            return c.isEmpty();
        }
        if (value instanceof Map map) {
            return map.isEmpty();
        }
        if (value instanceof Enumeration e) {
            return !e.hasMoreElements();
        }
        if (value instanceof Iterator i) {
            return !i.hasNext();
        }
        return false;
    }

    /**
     * 判断是不为null、不为空
     * @param value String、Object[]、Collection、Map、Enumeration、Iterator
     * @return boolean
     */
    public static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

}
