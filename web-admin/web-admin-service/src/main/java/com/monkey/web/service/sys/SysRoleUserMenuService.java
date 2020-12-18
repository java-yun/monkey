package com.monkey.web.service.sys;

import com.monkey.web.entity.SysMenu;
import com.monkey.web.entity.SysRole;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 13:49
 */
public interface SysRoleUserMenuService {

    /**
     * 根据 username查询 角色信息
     * @param username username
     * @return List<SysRole>
     */
    List<SysRole> getRolesByUsername(String username);

    /**
     * 根据 username 查询 菜单信息
     * @param username username
     * @return List<SysMenu>
     */
    List<SysMenu> getButtonsByUsername(String username);
}
