package com.lanxin.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
        // 拦截所有请求，通过判断是否有 @UserLoginToken 注解 决定是否需要登录
        //不加注解的话默认不验证
    }

    @Bean
    public TokenInterceptor authenticationInterceptor() {
        return new TokenInterceptor();
    }

}
