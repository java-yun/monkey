package com.monkey.common.config;

import com.monkey.common.utils.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 属性文件配置
 * @author jiangyun
 * @version 0.0.1
 * @date 2021/1/4 16:55
 */
@Configuration
public class PropertiesConfig {

    @Resource
    private Environment env;

    @PostConstruct
    public void setProperties() {
        PropertiesUtil.setEnvironment(env);
    }

}
