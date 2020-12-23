package com.monkey.web.dao;

import com.monkey.web.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(value = """
        SELECT srole.id AS id, srole.CODE AS CODE, srole.p_code AS pcode, srole.role_name AS roleName
        FROM sys_role_user roleu, sys_user u, sys_role srole
        WHERE roleu.user_id = u.id AND srole.id = roleu.role_id AND u.username = ?1
     """, nativeQuery = true)
    List<SysRole> getRolesByUsername(String username);
}
