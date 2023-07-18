package com.flowers;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.flowers.mapper")
public class flowersListApplication {
    public static void main(String[] args) {
        SpringApplication.run(flowersListApplication.class);
    }
}
