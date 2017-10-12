#笔记

###搭建spring框架笔记

#一、搭建简单框架

##1.新建maven项目，引入springboot相关包；
     <!--添加springboot依赖-->
        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.1.RELEASE</version>
        </parent>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-autoconfigure</artifactId>
                <version>1.4.3.RELEASE</version>
            </dependency>
        </dependencies>
##2.增加配置application.yml配置文件，添加项目配置
    server:
      port: 8080
      tomcat:
        uri-encoding: utf-8
        
##3.启动类
    配置 @SpringBootApplication 注解
    增加方法
    public static void main(String[] args){
        SpringApplication.run(StartController.class,args);
    }   
##4.注意项目结构，使用以上配置时，默认扫描启动类所在包下的文件夹，所以启动类要放在最顶层的文件夹中

#二、整合Mybatis
##1.增加mybatis-spring相关maven配置
##2.启动类增加 @MapperScan(basePackages = "com.project.mapper") 扫描mapper方法所在文件夹
    