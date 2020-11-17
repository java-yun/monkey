package com.monkey.common.utils;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author jiangyun
 * @version 0.0.1
 * @Description 集合工具类
 * @date 2020/11/11 9:48
 */
public class CollectionUtils {

    /**
     * 随机获取list的一个元素
     * @param list list
     * @return T
     */
    public static <T>T getListRandomElement(List<T> list) {
        if (Objects.isNull(list) || list.size() < 1) {
            return null;
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
 }
