package com.monkey.common.response;

import com.monkey.common.code.BizCode;
import lombok.Data;

/**
 * lay ui table response
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 9:33
 */
@Data
public class LayUiTableResponse<T> {

    private final int code = 0;

    private String msg = BizCode.SUCCESS.msg();

    private final long count;

    private final T data;

    public static <T> LayUiTableResponse<T> ok(T data, long count) {
        return new LayUiTableResponse<T>(count, data);
    }

    public LayUiTableResponse(long count, T data) {
        this.count = count;
        this.data = data;
    }
}
