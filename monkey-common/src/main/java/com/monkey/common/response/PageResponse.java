package com.monkey.common.response;

import com.monkey.common.code.BizCode;

/**
 * http 接口响应实体  分页
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 10:19
 */
public record PageResponse<T>(String code, String msg, T data, Long total) {

    public static <T> PageResponse<T> ok(T data, Long total) {
        return new PageResponse<>(BizCode.SUCCESS.code(), BizCode.SUCCESS.msg(), data, total);
    }

    public static PageResponse<String> ok() {
        return new PageResponse<>(BizCode.SUCCESS.code(), BizCode.SUCCESS.msg(), BizCode.SUCCESS.msg(), null);
    }

    public static <T> PageResponse<T> error(String code, String msg) {
        return new PageResponse<>(code, msg, null, null);
    }

    public static <T> PageResponse<T> error(BizCode bizCode) {
        return new PageResponse<>(bizCode.code(), bizCode.msg(), null, null);
    }
}
