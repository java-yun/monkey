package com.monkey.common.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.monkey.common.code.BizCode;

/**
 * http 接口响应实体
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/13 14:10
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Response<T>(String code, String msg, T data) {

    public static <T> Response<T> ok(T data) {
        return new Response<>(BizCode.SUCCESS.code(), BizCode.SUCCESS.msg(), data);
    }

    public static <T> Response<T> error(String code, String msg) {
        return new Response<>(code, msg, null);
    }

    public static <T> Response<T> error(BizCode bizCode) {
        return new Response<>(bizCode.code(), bizCode.msg(), null);
    }
}
