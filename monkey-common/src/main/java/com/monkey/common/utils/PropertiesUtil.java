package com.monkey.common.utils;

import org.springframework.core.env.Environment;

/**
 * 属性工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2021/1/4 16:57
 */
public class PropertiesUtil {

    private static Environment env = null;

    public static void setEnvironment(Environment env) {
        PropertiesUtil.env = env;
    }

    public static String getProperty(String key) {
        return PropertiesUtil.env.getProperty(key);
    }

}
