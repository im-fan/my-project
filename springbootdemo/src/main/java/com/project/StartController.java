package com.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.project.mapper")
public class StartController {

    public static void main(String[] args){
        SpringApplication.run(StartController.class,args);
    }

}
