package com.monkey.web.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 复选框实体
 * @author jiangyun
 */
@Data
public class CheckBox implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否选中 默认false
     */
    private boolean check;
}
