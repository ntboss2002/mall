package com.zyxx.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @Author zxy
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 所有请求都会处理跨域
        registry.addMapping("/**")
                // 允许谁访问
                .allowedOrigins("*")
                // 允许通过的请求数
                .allowedMethods("*")
                // 允许的请求头
                .allowedHeaders("*");
    }
}

