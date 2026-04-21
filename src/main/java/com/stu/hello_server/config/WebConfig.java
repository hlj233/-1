package com.stu.hello_server.config;

import com.stu.hello_server.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                // 放行所有实验需要的接口
                .excludePathPatterns(
                        "/api/users/login",
                        "/api/users/register",
                        "/api/users/*/detail",
                        "/api/users/*"  // 重点：放行删除接口
                );
    }
}