package com.monkey.web.entity;

import com.monkey.web.id.RoleUserId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:57
 */
@Data
@Entity
@IdClass(RoleUserId.class)
@Table(name = "sys_role_user")
public class SysRoleUser implements Serializable {

    @Id
    private Integer roleId;

    @Id
    private Integer userId;
}
