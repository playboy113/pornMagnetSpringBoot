package com.play;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class playMenuApplication {
    public static void main(String[] args) {
        SpringApplication.run(playMenuApplication.class);
    }
}
