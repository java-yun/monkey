package com.monkey.web.id;

import lombok.Data;

import java.io.Serializable;

/**
 *  角色  用户  主键
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 14:15
 */
@Data
public class RoleUserId implements Serializable {

    private Integer roleId;

    private Integer userId;
}
