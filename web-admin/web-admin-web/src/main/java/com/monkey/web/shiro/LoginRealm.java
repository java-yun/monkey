package com.monkey.web.shiro;


import com.monkey.common.utils.IsValid;
import com.monkey.web.dao.SysUserRepository;
import com.monkey.web.bo.CurrentPermitButton;
import com.monkey.web.bo.CurrentRole;
import com.monkey.web.bo.CurrentUser;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.utils.CmsSysUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 登陆认证器
 * @author jiangyun
 */
@Slf4j
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleUserMenuService sysRoleUserMenuService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("(doGetAuthorizationInfo)获取当前用户用shiro控制的shiro权限");
        var info = new SimpleAuthorizationInfo();
        var cUser = CmsSysUserUtil.getCurrentUser();
        cUser.getRoles().forEach( role -> {
            if(StringUtils.isNotEmpty(role.getId())){
                info.addRole(role.getId());
            }
        } );
        cUser.getPermitButtons().forEach( button -> {
            if(StringUtils.isNotEmpty(button.getPermission())){
                info.addStringPermission(button.getPermission());
            }
        });
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("(doGetAuthenticationInfo)获取认证");
        var username = (String) authenticationToken.getPrincipal();
        var sysUser = sysUserRepository.findByUsernameAndDelFlag(username, Byte.parseByte(IsValid.valid.getValue()));
        if(Objects.isNull(sysUser)){
            throw new UnknownAccountException("账户密码不正确");
        }
        var currentUser = new CurrentUser(sysUser.getId(), sysUser.getUsername(), sysUser.getAge(), sysUser.getEmail(), sysUser.getPhoto(), sysUser.getRealName());
        currentUser.setType(sysUser.getType());
        var subject = CmsSysUserUtil.getSubject();
        var session = subject.getSession();
        //封装角色信息
        var roles =  sysRoleUserMenuService.getRolesByUsername(username);
        //将 SysRole 转换 CurrentRole
        List<CurrentRole> currentRoles= roles.stream().filter(Objects::nonNull).map(role -> {
            var currentRole = new CurrentRole();
            BeanUtils.copyProperties(role, currentRole);
            return currentRole;
        }).collect(Collectors.toList());
        currentUser.setRoles(currentRoles);
        //封装按钮权限列表
        var buttonsByUserNames = sysRoleUserMenuService.getButtonsByUsername(username);
        //将 SysMenu 转换成 CurrentPermitButton
        List<CurrentPermitButton> permitButtons = buttonsByUserNames.stream().filter(Objects::nonNull).map(button -> {
            var currentPermitButton = new CurrentPermitButton();
            BeanUtils.copyProperties(button, currentPermitButton);
            return currentPermitButton;
        }).collect(Collectors.toList());
        currentUser.setPermitButtons(permitButtons);
        session.setAttribute("currentUser", currentUser);

        //设置session有效时间为1小时
        session.setTimeout(60 * 60 * 1000);
        var byteSource = ByteSource.Util.bytes(username);
        return new SimpleAuthenticationInfo(username, sysUser.getPassword(), byteSource, getName());
    }

}
