package com.stu.hello_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 移除了 AuthInterceptor 相关代码，保留其他 MVC 配置（如有需要可继续添加）
}