package com.monkey.web.dao;

import com.monkey.web.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(value = """
        SELECT m.id AS id, m.permission AS permission, m.code AS code, m.p_code AS pCode, m.name AS name
        FROM sys_role_menu rolem, sys_menu m
        WHERE rolem.menu_id = m.id AND rolem.role_id IN (?1)
    """, nativeQuery = true)
    List<SysMenu> getMenuByRoleIds(List<Integer> roleIds);
}
