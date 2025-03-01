package com.zhang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/images/**").addResourceLocations("file:D:/github/pornMagnetSpringBoot/searchModeul/src/main/resources/images/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:D:/images/");
    }
}
