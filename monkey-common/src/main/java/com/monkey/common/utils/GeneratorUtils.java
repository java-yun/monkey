package com.monkey.common.utils;

import java.util.UUID;

/**
 * generator 工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 10:00
 */
public class GeneratorUtils {

    /**
     * 获取uuid
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
