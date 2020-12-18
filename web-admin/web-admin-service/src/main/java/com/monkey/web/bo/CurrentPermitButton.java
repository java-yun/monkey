package com.monkey.web.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户有权限的按钮对象
 * @author jiangyun
 */
@Data
public class CurrentPermitButton implements Serializable {
    /**
     * 菜单id
     */
    private Integer buttonId;

    /**
     * 权限名称
     */
    private String permission;
}
