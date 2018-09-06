package com.dpermi.core;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.Configuration;

/**
 * Created by val620@126.com on 2017/12/4.
 */

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration ir = registry.addInterceptor(authInterceptor());
        // 配置拦截的路径
        ir.addPathPatterns("/**");

        // 配置不拦截的路径
        ir.excludePathPatterns("/**.html");
        ir.excludePathPatterns("/**.js");
        ir.excludePathPatterns("/**.css");
        ir.excludePathPatterns("/**.jpg");
        ir.excludePathPatterns("/**.png");
        ir.excludePathPatterns("/user/login");
        ir.excludePathPatterns("/user/currentUser");
        ir.excludePathPatterns("/message/getMessagesByUser");
        ir.excludePathPatterns("/user/code");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
