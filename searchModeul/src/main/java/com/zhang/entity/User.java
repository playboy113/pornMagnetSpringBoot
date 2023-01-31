package com.zhang.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Data
public class User {
    private String name;
    private int age;
}
