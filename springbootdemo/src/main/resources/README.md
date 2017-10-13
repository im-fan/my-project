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
    com.project
        StartApplication
        com.project.cotroller
        com.project.service
        com.project.xxx

#二、整合Mybatis
##1.增加数据库和mybatis-spring相关maven配置
    <!--数据库-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.39</version>
    </dependency>

    <!--mybatis-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.1.1</version>
    </dependency>
    
##2.配置数据库链接和Mybatis相关配置(未使用数据源)

    mybatis:
      mapperLocations: classpath:mapper/*.xml
      typeAliasesPackage: com.project.entity
    
    #MySQL
    spring:
      datasource:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8
        username: root
        password: 1234
    
##3.启动类增加 
    @MapperScan(basePackages = "com.project.mapper") 扫描mapper方法所在文件夹
    
#三、单元测试
##1.增加maven配置
    <!--test-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
##2.增加测试类注解
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest
    //@SpringApplicationConfiguration(classes = StartController.class)// 1.4.0 前版本
    