package com.car.data.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {

    @Bean
    public VisitInterceptor visitInterceptor(){
        return new VisitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(visitInterceptor()).addPathPatterns("/**").excludePathPatterns("/**/*.js","/**/*.js.map");
    }
}
