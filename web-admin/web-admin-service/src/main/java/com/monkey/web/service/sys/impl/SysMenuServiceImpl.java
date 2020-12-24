package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.Detect;
import com.monkey.common.utils.IsValid;
import com.monkey.web.bo.UserVisibleMenu;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.service.sys.SysMenuService;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.utils.CmsSysUserUtil;
import com.monkey.web.utils.MenuUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * menu service impl
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 10:36
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    private final SysRoleUserMenuService sysRoleUserMenuService;

    public SysMenuServiceImpl(SysRoleUserMenuService sysRoleUserMenuService) {
        this.sysRoleUserMenuService = sysRoleUserMenuService;
    }

    @Override
    public UserVisibleMenu getUserVisibleMenu() {
        var userVisibleMenu = new UserVisibleMenu();
        var username = CmsSysUserUtil.getCurrentUser().getUsername();
        //当前用户一级菜单
        var topMenus = this.sysRoleUserMenuService.getOneLevel(username);
        if (Detect.isNotNullOrEmpty(topMenus)) {
            //第一个一级菜单所有子菜单
            List<SysMenu> lefts = sysRoleUserMenuService.getUserMenusByParent(username, topMenus.get(0).getCode(), Byte.valueOf(IsValid.invalid.getValue()));
            //转换成 树状结构
            var leftMenus = MenuUtil.convertMenu2Tree(lefts);
            userVisibleMenu.setTopMenus(topMenus);
            userVisibleMenu.setLeftMenus(leftMenus);
        }
        return userVisibleMenu;
    }
}
