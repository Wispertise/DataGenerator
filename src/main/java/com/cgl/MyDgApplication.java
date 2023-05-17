package com.cgl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cgl.mapper")
public class MyDgApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyDgApplication.class, args);
    }
}
