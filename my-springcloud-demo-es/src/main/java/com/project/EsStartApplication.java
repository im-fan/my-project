package com.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class EsStartApplication {

    private static final Logger logger = LoggerFactory.getLogger(EsStartApplication.class);

    public static void main(String[] args){
        logger.info("项目开始启动》》》");
        SpringApplication.run(EsStartApplication.class,args);
        logger.info("项目启动成功》》》");
    }

}
