package com.monkey.common.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/19 14:42
 */
public class StringUtils {

    public static final Pattern PATTERN = Pattern.compile("[A-Z]");

    public static final char START_UNDER_LINE = '_';

    /**
     * 字符串 大写转小写加下划线
     * @param str 被转换的字符串
     * @return String
     */
    public static String upperCharToUnderLine(String str) {
        if(Objects.nonNull(str) && !str.isBlank()){
            StringBuilder builder = new StringBuilder(str);
            Matcher mc = PATTERN.matcher(str);
            int i = 0;
            while (mc.find()) {
                builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
                i++;
            }
            if(START_UNDER_LINE == builder.charAt(0)){
                builder.deleteCharAt(0);
            }
            return builder.toString();
        }
        return str;
    }
}
