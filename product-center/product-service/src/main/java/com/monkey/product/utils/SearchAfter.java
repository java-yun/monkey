package com.monkey.product.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.monkey.common.utils.Des3Utils;

/**
 * search_after 工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/2 10:44
 */
public class SearchAfter {

    public static String toAfterKey(Object[] searchAfter) {
        return Des3Utils.encrypt(JSON.toJSONString(searchAfter));
    }

    public static Object[] toSearchAfter(String afterKey) {
        return JSON.parseObject(Des3Utils.decrypt(afterKey), Object[].class, JSON.DEFAULT_PARSER_FEATURE & ~Feature.UseBigDecimal.getMask() ,Feature.OrderedField);
    }
}
