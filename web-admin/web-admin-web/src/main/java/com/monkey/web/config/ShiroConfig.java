package com.monkey.web.config;

import com.monkey.web.constants.WebConstants;
import com.monkey.web.shiro.LoginRealm;
import com.monkey.web.shiro.RetryLimitCredentialsMatcher;
import com.monkey.web.shiro.filter.PermissionFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * shiro 配置
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:53
 */
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.timeout:5000}")
    private int timeout;
    @Value("${spring.redis.database:0}")
    private int database;

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager) {
        var sfb = new ShiroFilterFactoryBean();
        sfb.setSecurityManager(securityManager);
        sfb.setLoginUrl("/login");
        sfb.setUnauthorizedUrl("/goLogin");
        var filters = new HashMap<String, Filter>(4);
        filters.put("per", getPermissionFilter());
        sfb.setFilters(filters);
        var filterMap = new LinkedHashMap<String, String>();
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

    @Bean("securityManager")
    public SessionsSecurityManager securityManager(@Qualifier("loginRealm") LoginRealm loginRealm) {
        var securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(loginRealm);
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        //自定义CustomSessionManager 继承 DefaultWebSessionManager
        var sessionManager = new DefaultWebSessionManager();
        //配置session持久化
        sessionManager.setSessionDAO(redisSessionDAO());
        //超时时间，默认 30分钟，会话超时；方法里面的单位是毫秒
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 30);
        return sessionManager;
    }

    /**
     * 配置具体cache实现类RedisCacheManager
     * 为什么要使用缓存:
     * 缓存组件位于SecurityManager中,在CustomRealm数据域中,由于授权方法中每次都要查询数据库,性能受影响,因此将数据缓存起来,提高查询效率
     * 除了使用Redis缓存,还能使用shiro-ehcache
     */
    public RedisCacheManager redisCacheManager() {
        var redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //设置过期时间，单位是秒，20s,
        redisCacheManager.setExpire(20);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     */
    @Bean
    public RedisManager redisManager() {
        var redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setDatabase(database);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * 自定义session持久化
     */
    public RedisSessionDAO redisSessionDAO() {
//      为啥session也要持久化？
//            重启应用，用户无感知，可以继续以原先的状态继续访问
//      注意点：
//            DO对象需要实现序列化接口 Serializable
//            logout接口和以前一样调用，请求logout后会删除redis里面的对应的key,即删除对应的token
        var redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    @Bean
    public PermissionFilter getPermissionFilter() {
        return new PermissionFilter();
    }


    @Bean
    public RetryLimitCredentialsMatcher hashedCredentialsMatcher(){
        var rm = new RetryLimitCredentialsMatcher();
        rm.setHashAlgorithmName("md5");
        rm.setHashIterations(WebConstants.CMS_PASSWORD_HASH_ITERATIONS);
        return rm;
    }

    @Bean(name = "loginRealm")
    public LoginRealm loginRealm() {
        var realm = new LoginRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        var advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager securityManager) {
        var as = new AuthorizationAttributeSourceAdvisor();
        as.setSecurityManager(securityManager);
        return as;
    }
}
