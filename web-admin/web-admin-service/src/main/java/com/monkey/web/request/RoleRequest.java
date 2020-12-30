package com.monkey.web.request;

import lombok.Data;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/30 14:06
 */
@Data
public class RoleRequest {

    private String roleName;

    private String description;

    private Integer page;

    private Integer limit = 20;
}
