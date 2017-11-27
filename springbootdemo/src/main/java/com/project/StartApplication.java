package com.project;

import com.main.annotation.EnablePageUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.project.mapper")
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnablePageUtil
public class StartApplication {

    private static final Logger logger = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args){
        logger.info("项目开始启动》》》");
        SpringApplication.run(StartApplication.class,args);
        logger.info("项目启动成功》》》");
    }

}
