package com.monkey.web.entity;

import com.monkey.web.id.RoleMenuId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:57
 */
@Data
@Entity
@IdClass(RoleMenuId.class)
@Table(name = "sys_role_menu")
public class SysRoleMenu {

    @Id
    private Integer roleId;

    @Id
    private Integer menuId;
}
