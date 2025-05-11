package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đảm bảo đường dẫn /static/** trỏ đến classpath:/static/
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
                
        // Đảm bảo đường dẫn /images/** trỏ đến classpath:/static/images/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
} 