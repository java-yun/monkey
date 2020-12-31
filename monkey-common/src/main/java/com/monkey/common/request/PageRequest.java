package com.monkey.common.request;

import lombok.Data;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 14:20
 */
@Data
public class PageRequest {

    private Integer page;

    private Integer limit = 20;
}
