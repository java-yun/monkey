package com.monkey.product.config;

import com.monkey.product.interceptor.MdcLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义web配置
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/23 15:03
 */
@Slf4j
@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MdcLogInterceptor mdcLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcLogInterceptor).addPathPatterns("/product-list-rec");
        log.info("MdcLogInterceptor register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
