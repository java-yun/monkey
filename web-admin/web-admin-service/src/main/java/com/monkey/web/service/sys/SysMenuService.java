package com.monkey.web.service.sys;

import com.monkey.web.bo.UserVisibleMenu;
import com.monkey.web.entity.SysMenu;

import java.util.List;

/**
 * menu service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 10:38
 */
public interface SysMenuService {

    /**
     * 获取当前用户 可见菜单
     * @return UserVisibleMenu
     */
    UserVisibleMenu getUserVisibleMenu();

    List<SysMenu> changeMode(String pCode);

    /**
     * 查询所有菜单
     * @return List<SysMenu>
     */
    List<SysMenu> getAllMenus();

    /**
     * 查询所有菜单 排除按钮
     * @return List<SysMenu>
     */
    List<SysMenu> getAllMenusExcludeButton();

    /**
     * 根据条件查询
     * @param sysMenu sysMenu
     * @return List<SysMenu>
     */
    List<SysMenu> getMenuByCondition(SysMenu sysMenu);

    /**
     * 根据主键查询
     * @param id id
     * @return SysMenu
     */
    SysMenu selectById(Integer id);

    /**
     * 根据 code 查询
     * @param code code
     * @return SysMenu
     */
    SysMenu getByCode(String code);

    /**
     * 添加菜单
     * @param sysMenu sysMenu
     */
    void addMenu(SysMenu sysMenu);

    /**
     * 根据主键更新菜单
     * @param sysMenu sysMenu
     */
    void updateMenu(SysMenu sysMenu);

    /**
     * 根据主键  删除
     * @param id id
     */
    void deleteMenu(Integer id);
}
