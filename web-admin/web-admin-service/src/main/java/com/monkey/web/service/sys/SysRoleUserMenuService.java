package com.monkey.web.service.sys;

import com.monkey.web.entity.SysMenu;
import com.monkey.web.entity.SysRole;
import com.monkey.web.entity.SysRoleMenu;
import com.monkey.web.entity.SysRoleUser;

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

    /**
     * 根据roleId 查询 menuId列表
     * @param roleId roleId
     * @return List<Integer>
     */
    List<Integer> getMenuIdsByRoleId(Integer roleId);

    /**
     * 根据  menuId 删除
     * @param menuId menuId
     */
    void deleteRoleMenuByMenuId(Integer menuId);

    /**
     * 删除角色菜单  根据菜单ids
     * @param menuIds menuIds
     */
    void deleteRoleMenuByMenuIds(List<Integer> menuIds);

    /**
     * 批量添加 角色菜单
     * @param roleMenuList roleMenuList
     */
    void batchInsertRoleMenu(List<SysRoleMenu> roleMenuList);

    /**
     * 删除角色菜单  根据角色ids
     * @param roleIds roleIds
     */
    void deleteRoleMenuByRoleIds(List<Integer> roleIds);

    /**
     * 根据角色查询用户ids
     * @param roleId roleId
     * @return List<Integer>
     */
    List<Integer> getUserIdsByRoleId(String roleId);

    /**
     * 根据 userId 查询 roleIds
     * @param userId
     * @return List<Integer>
     */
    List<Integer> getRolesByUserId(Integer userId);

    /**
     * 删除 RoleUser 根据 roleIds
     * @param roleIds roleIds
     */
    void deleteRoleUserByRoleIds(List<Integer> roleIds);

    /**
     * 批量添加  RoleUser
     * @param roleUserList roleUserList
     */
    void batchInsertRoleUser(List<SysRoleUser> roleUserList);

    /**
     * 根据 userIds 删除 RoleUser
     * @param userIds userIds
     */
    void deleteRoleUserByUserIds(List<Integer> userIds);
}
