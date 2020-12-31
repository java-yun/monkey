package com.monkey.web.dao;

import com.monkey.web.constants.QueryColumnConstants;
import com.monkey.web.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:02
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Integer> {

    @Query(value = QueryColumnConstants.SYS_MENU_SELECT +
    """
        FROM sys_role_menu rm, sys_menu m
        WHERE rm.menu_id = m.id AND rm.role_id IN (?1)
    """, nativeQuery = true)
    List<SysMenu> getMenuByRoleIds(List<Integer> roleIds);

    @Query(value = QueryColumnConstants.SYS_MENU_SELECT +
    """
        FROM sys_role_menu rm, sys_menu m
        WHERE rm.menu_id = m.id AND rm.role_id IN (?1) AND m.p_code = ?2
        ORDER BY m.order_num
    """, nativeQuery = true)
    List<SysMenu> selectByRolesAndParentCode(List<Integer> roleIds, String pCode);

    @Query(value = QueryColumnConstants.SYS_MENU_SELECT +
    """
        FROM sys_role_menu rm, sys_menu m
        WHERE rm.menu_id = m.id AND rm.role_id IN (?1) AND m.p_code like CONCAT(?2,'%') AND m.menu_type = ?3
        ORDER BY m.`level` , m.order_num
    """, nativeQuery = true)
    List<SysMenu> selectMenusByRolesAndParentCode(List<Integer> roleIds, String pCode, Byte isButton);

    @Query(value = QueryColumnConstants.SYS_MENU_SELECT +
            """
                FROM sys_menu m
                ORDER BY m.`level` , m.order_num
            """, nativeQuery = true)
    List<SysMenu> getAllMenus();

    @Query(value = QueryColumnConstants.SYS_MENU_SELECT +
            """
                FROM sys_menu m
                WHERE m.menu_type = 0
                ORDER BY m.`level` , m.order_num
            """, nativeQuery = true)
    List<SysMenu> getAllMenusExcludeButton();

    SysMenu findByCode(String code);

    @Query(value = "select max(code) from sys_menu where p_code = ?1", nativeQuery = true)
    String getMaxCodeByPCode(String pCode);

}
