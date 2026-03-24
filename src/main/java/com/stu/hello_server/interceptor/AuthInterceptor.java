package com.stu.hello_server.interceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 自定义鉴权拦截器，校验请求头中的Authorization令牌
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 尝试从HTTP请求头中截获名为"Authorization"的隐藏令牌信息
        String token = request.getHeader("Authorization");

        // 如果没有携带Token，直接拦截，不放行到Controller
        if (token == null || token.isEmpty()) {
            // 构造401报错的JSON字符串返回给前端
            response.setContentType("application/json;charset=UTF-8");
            String errorJson = "{\"code\": 401,\"msg\":\"登录凭证已缺失,请重新登录\"}";
            response.getWriter().write(errorJson);
            return false; // 返回false表示拦截打回
        }
        return true; // 令牌存在，返回true予以放行
    }
}