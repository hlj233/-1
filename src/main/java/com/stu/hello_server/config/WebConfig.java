package com.stu.hello_server.config;


import com.stu.hello_server.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，挂载自定义拦截器，接管Spring MVC底层配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**") // 拦截/api下的所有请求路径
                .excludePathPatterns(
                        "/api/users/login", // 放行登录接口
                        "/api/users",       // 放行新增用户接口
                        "/api/users/*"      // 放行获取用户信息接口
                );
    }
}