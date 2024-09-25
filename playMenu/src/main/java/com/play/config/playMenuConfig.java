package com.play.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class playMenuConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/images/**").addResourceLocations("file:D:/github/pornMagnetSpringBoot/searchModeul/src/main/resources/images/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:D:/images/");
        registry.addResourceHandler("/D/**").addResourceLocations("file:D:/");
        registry.addResourceHandler("/E/**").addResourceLocations("file:E:/");
        registry.addResourceHandler("/F/**").addResourceLocations("file:F:/");
        registry.addResourceHandler("/K/**").addResourceLocations("file:K:/");
        registry.addResourceHandler("/G/**").addResourceLocations("file:G:/");
        registry.addResourceHandler("/H/**").addResourceLocations("file:H:/");
        registry.addResourceHandler("/I/**").addResourceLocations("file:I:/");
    }
}
