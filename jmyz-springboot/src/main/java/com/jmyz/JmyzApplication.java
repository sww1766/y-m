package com.jmyz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@MapperScan("com.jmyz.mapper*")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class JmyzApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmyzApplication.class, args);
    }
}