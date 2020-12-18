package com.monkey.web.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树形结构展示
 * @author jiangyun
 */
@Data
public class MenuTree implements Serializable {
    private String id;

    private String name;

    private String code;

    private String pCode;

    private String url;

    private Short orderNum;

    private String icon;

    private String permission;

    private Byte menuType;

    private Byte level;

    private List<MenuTree> children = new ArrayList<>();

    /**
     * 是否展开
     */
    private boolean open  = true;

    /**
     * 是否选中
     */
    private boolean checked = false;

    private String title;
}
