package com.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CanalApplication {

    private static final Logger logger = LoggerFactory.getLogger(CanalApplication.class);

    public static void main(String[] args){
        logger.info("项目开始启动》》》");
        SpringApplication.run(CanalApplication.class,args);
        logger.info("项目启动成功》》》");
    }

}
