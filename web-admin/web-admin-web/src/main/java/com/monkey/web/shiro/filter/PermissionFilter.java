package com.monkey.web.shiro.filter;

import com.monkey.web.bo.CurrentUser;
import com.monkey.web.utils.CmsSysUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 登录权限 拦截器
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:57
 */
@Slf4j
public class PermissionFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        var roles = (String[]) o;

        var sub = getSubject(servletRequest, servletResponse);
        var session = sub.getSession();
        var user = (CurrentUser) session.getAttribute(CmsSysUserUtil.SHIRO_CURRENT_USER);
        log.info("user:{}", user);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        saveRequest(request);
        WebUtils.issueRedirect(request, response, "/goLogin");
        return false;
    }
}
