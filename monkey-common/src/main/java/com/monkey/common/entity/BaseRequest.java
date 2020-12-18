package com.monkey.common.entity;

import lombok.Data;

/**
 * base request
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 9:46
 */
@Data
public class BaseRequest {

    private Integer pageNum;

    private Integer size;
}
