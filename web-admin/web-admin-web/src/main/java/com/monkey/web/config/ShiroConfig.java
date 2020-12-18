package com.monkey.web.config;

import com.monkey.web.constants.WebConstants;
import com.monkey.web.shiro.LoginRealm;
import com.monkey.web.shiro.RetryLimitCredentialsMatcher;
import com.monkey.web.shiro.filter.PermissionFilter;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:53
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
        sfb.setSecurityManager(securityManager);
        sfb.setLoginUrl("/login");
        sfb.setUnauthorizedUrl("/goLogin");
        Map<String, Filter> filters = new HashMap<>(4);
        filters.put("per", getPermissionFilter());
        sfb.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login","anon");
        filterMap.put("/doLogin","anon");
        filterMap.put("/index","anon");
        // 服务监控接口
        filterMap.put("/monitor", "anon");
        filterMap.put("/actuator/**", "anon");
        filterMap.put("/actuator", "anon");
        filterMap.put("/getCode", "anon");
        filterMap.put("/sendSms", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/plugin/**", "anon");
        filterMap.put("/user/**", "per");
        filterMap.put("/**", "authc");
        sfb.setFilterChainDefinitionMap(filterMap);
        return sfb;
    }

    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager(@Qualifier("loginRealm") LoginRealm loginRealm) {
        DefaultWebSecurityManager dwm = new DefaultWebSecurityManager();
        dwm.setRealm(loginRealm);
        return dwm;
    }


    @Bean
    public PermissionFilter getPermissionFilter() {
        return new PermissionFilter();
    }


    @Bean
    public RetryLimitCredentialsMatcher hashedCredentialsMatcher(){
        RetryLimitCredentialsMatcher rm = new RetryLimitCredentialsMatcher();
        rm.setHashAlgorithmName("md5");
        rm.setHashIterations(WebConstants.CMS_PASSWORD_HASH_ITERATIONS);
        return rm;
    }


    /**
     * TODO 这段代码为何可以这么写 ?
     */
    @Bean(name = "loginRealm")
    public LoginRealm getLoginRealm() {
        LoginRealm realm = new LoginRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor as = new AuthorizationAttributeSourceAdvisor();
        as.setSecurityManager(securityManager);
        return as;
    }
}
