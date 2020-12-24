package com.monkey.web.service.sys;

import com.monkey.web.bo.UserVisibleMenu;

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
}
