package com.monkey.web.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * shiro 使用freemarker 配置
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/23 10:42
 */
@Component
public class FreemarkerShiroConfig implements InitializingBean {

    private final Configuration configuration;

    public FreemarkerShiroConfig(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
