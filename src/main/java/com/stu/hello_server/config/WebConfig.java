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
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/users/login",
                        "/api/users",
                        "/api/users/*",
                        // 放行分页接口
                        "/api/users/page"
                );
    }
}