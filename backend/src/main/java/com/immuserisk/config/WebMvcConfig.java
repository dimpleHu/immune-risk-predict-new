package com.immuserisk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加日期时间格式化器，支持 yyyy-MM-dd HH:mm:ss 格式
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

}