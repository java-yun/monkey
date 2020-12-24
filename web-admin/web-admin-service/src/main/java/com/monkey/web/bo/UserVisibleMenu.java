package com.monkey.web.bo;

import com.monkey.web.entity.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 10:43
 */
@Data
public class UserVisibleMenu implements Serializable {

    private List<SysMenu> topMenus;

    private List<MenuTree> leftMenus;
}
