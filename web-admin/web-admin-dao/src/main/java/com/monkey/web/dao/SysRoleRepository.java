package com.monkey.web.dao;

import com.monkey.web.constants.QueryColumnConstants;
import com.monkey.web.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:01
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Integer> {

    @Query(value = QueryColumnConstants.SYS_ROLE_SELECT +
    """
        FROM sys_role_user ru, sys_user u, sys_role r
        WHERE ru.user_id = u.id AND r.id = ru.role_id AND u.username = ?1
    """, nativeQuery = true)
    List<SysRole> getRolesByUsername(String username);

    @Modifying
    @Query(value = "delete from sys_role where id in (?1)", nativeQuery = true)
    void deleteByIds(List<Integer> ids);
}
