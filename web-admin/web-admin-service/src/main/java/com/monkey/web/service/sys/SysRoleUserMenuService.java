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

    /**
     * 查询用户的一级菜单
     * @param username username
     * @return List<SysMenu>
     */
    List<SysMenu> getOneLevel(String username);

    /**
     * 查询用户某个菜单的子菜单
     * @param username username
     * @param pCode pCode
     * @param isButton isButton
     * @return List<SysMenu>
     */
    List<SysMenu> getUserMenusByParent(String username, String pCode, Byte isButton);
}
