package com.monkey.web.utils;

import com.monkey.web.bo.CheckBox;
import com.monkey.web.entity.SysRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 15:50
 */
public class RoleUtil {

    /**
     * 将 角色列表 转换成 checkbox列表
     * @param allRoles allRoles
     * @param roleIds roleIds
     * @return List<CheckBox>
     */
    public static List<CheckBox> convertRole2CheckBox(List<SysRole> allRoles, List<Integer> roleIds){
        if(allRoles == null || allRoles.size() == 0){
            return null;
        }
        return allRoles.stream().map(sysRole -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setId(sysRole.getId());
            checkBox.setName(sysRole.getRoleName());
            if(roleIds != null && roleIds.size() > 0 && roleIds.contains(sysRole.getId())){
                checkBox.setCheck(true);
            }
            return checkBox;
        }).collect(Collectors.toList());
    }
}
