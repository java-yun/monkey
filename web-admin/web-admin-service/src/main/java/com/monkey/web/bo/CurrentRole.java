package com.monkey.web.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色
 * @author jiangyun
 */
@Data
public class CurrentRole implements Serializable {
    private String id;

    private String roleName;

    private String remark;
}
