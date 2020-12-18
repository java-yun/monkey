package com.monkey.web.id;

import java.io.Serializable;
import lombok.Data;

/**
 * 角色 菜单 联合主键
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 14:12
 */
@Data
public class RoleMenuId implements Serializable {

    private Integer roleId;

    private Integer menuId;
}
