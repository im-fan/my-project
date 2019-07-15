#笔记
> 搭建springcloud框架并集成一些东西

##一、搭建简单框架

###1.新建maven项目，引入springboot相关包；
```text
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
```

###2.增加配置application.yml配置文件，添加项目配置
```text
server:
  port: 8080
  tomcat:
    uri-encoding: utf-8
```

        
###3.启动类
```text
    配置 @SpringBootApplication 注解
    增加方法
    public static void main(String[] args){
        SpringApplication.run(StartController.class,args);
    }   
```

    
###4.注意项目结构，使用以上配置时，默认扫描启动类所在包下的文件夹，所以启动类要放在最顶层的文件夹中
```text
    com.project
        StartApplication
        com.project.cotroller
        com.project.web.service
        com.project.xxx
```

##二、整合Mybatis
###1.增加数据库和mybatis-spring相关maven配置
```text
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
```
    
###2.配置数据库链接和Mybatis相关配置(未使用数据源)
```text

    mybatis:
      mapperLocations: classpath:mapper/*.xml
      typeAliasesPackage: com.project.web.entity
    
    #MySQL
    spring:
      datasource:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8
        username: root
        password: 1234
```
    
###3.启动类增加 
```text
    @MapperScan(basePackages = "com.project.mapper") 扫描mapper方法所在文件夹
    
```
##三、单元测试
###1.增加maven配置
```text
    <!--test-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
```
    
###2.增加测试类注解
```text
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringBootTest
    //@SpringApplicationConfiguration(classes = StartController.class)// 1.4.0 前版本
    
```
    
##四、增加Druid数据源
### 多数源问题
```text
多个数据源需在config类中指定主数据源
@Primary

配置多个数据源后，代码中事务事务时，需要指定事务管理器名称，否则默认主事务管理器，会导致事务不生效
例：@Transactional(rollbackFor = Exception.class,timeout = 3,transactionManager = "mainTransactionManager")
```
###1.增加maven配置
```text
<!--druid数据源-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.0.26</version>
</dependency>
```
    
    
###2.增加配置文件配置
```text
spring:
      datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        username: root
        password: 1234
        driver-class-name: com.mysql.jdbc.Driver
        filters: stat
        maxActive: 20
        initialSize: 1
        maxWait: 60000
        minIdle: 1
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQueryTimeout: 900000
        validationQuery: SELECT 1
        testWhileIdle: true
        testOnBorrow: true
        testOnReturn: false
        poolPreparedStatements: true
        maxOpenPreparedStatements: 20
```
    
        
###3.增加相关类
```text
    主要：DruidDataSourceConfig.java
    配合druid监控的使用：DruidStatFilter.java
    配置监控界面：DruidStatViewServlet.java
```
    
    
###4.启动类增加注解
```text
增加：@ServletComponentScan  使servlet生效
```
    
###5.访问
```text
启动项目，输入 http://localhost:8080/druid/index.html 访问druid控制台
```
    
    
##五、配置Logback

###1.新建日志配置文件
    详情见logback-boot.xml

###2.加载配置文件
    application.yml中增加配置
    #配置日志
    logging:
      config: classpath:logback-boot.xml

##六、更换启动banner图

###1.新建banner-xx.txt
    文件里就是启动使用的banner
###2.加载
    application.yml中增加配置
    #banner配置
    banner:
      location: banner-xx.txt
      
      
##七、整合Quartz定时任务

###1.增加maven配置
```text
<!--定时任务-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
</dependency>
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.2.1</version>
</dependency>
```

    
###2.新增相关类
```text
    1.MyScheduler.java 定义任务配置
    2.SchedulerListener.java 定义任务监听器
    3.ScheduledJob.java 业务逻辑
```

###3.相关
```text
XXLJob-轻量级分布式任务调度平台
http://www.xuxueli.com/xxl-job
```

## 八、整合EC-未验证
### docker-es
> 文件需要提前建好，否则会自动生成一个文件夹导致启动失败
```text
#停止容器
docker stop myes01;
docker rm -f myes01;

#启动es集群
docker run -m 2g -d --name myes01 -p 9200:9200 -p 9300:9300 -v ~/work/docker/software/elasticsearch/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v ~/work/docker/software/elasticsearch/data/data-01:/usr/share/elasticsearch/data -v ~/work/docker/software/elasticsearch/plugins:/plugins -v ~/work/docker/software/elasticsearch/es.yml:/usr/share/elasticsearch/config/jvm.options:/usr/share/elasticsearch/config/jvm.options elasticsearch:5.6.4

#停止head集群管理工具
docker stop elasticsearch-head-5
docker rm -f elasticsearch-head-5

#启动head，集群管理
docker run --name elasticsearch-head-5 -p 9100:9100 mobz/elasticsearch-head:5
```

### 1.增加jar包
```text
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    <version>1.5.9.RELEASE</version>
</dependency>
<dependency>
    <groupId>io.searchbox</groupId>
    <artifactId>jest</artifactId>
    <version>6.3.1</version>
</dependency>
<dependency>
    <groupId>net.java.dev.jna</groupId>
    <artifactId>jna</artifactId>
    <version>5.2.0</version>
</dependency>

```

### 2.增加配置项
```text
#es(docker-es搭建)
spring:
  data:
    elasticsearch:
      cluster-name: my-es # 集群名
      #连接节点,注意在集群中通信都是9300端口，否则会报错无法连接上！
      cluster-nodes: 0.0.0.0:9300
      local: true #是否本地连接
      repositories:
        enabled: true #仓库中数据存储
```

### 3.简单案例
```text
见 EsServiceImpl
```

## 八、MapStruct对象转换工具
```text
1.新建具体转换规则类UserMapper-必须
2.新建特殊转换逻辑HandWritten-非必须

注意事项
    pom.xml中需要增加相关包和插件

```

## 九、整合RocketMQ
###1.增加jar包
```text
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client</artifactId>
    <version>4.5.1</version>
</dependency>
```

###2.增加配置
```text
rocketmq:
  consumer:
    groupName: ConsumerGroupOne # 消费者的组名
    consumeThreadMin: 2
    consumeThreadMax: 5
    consumeMessageBatchMaxSize: 10
    topics: TopicOne
  producer:
    groupName: ProducerGroupOne # 生产者的组名
    maxMessageSize: 100
    sendMsgTimeout: 1000
    retryTimesWhenSendFailed: 3
  namesrvAddr: 127.0.0.1:9876 # NameServer地址
```

###3.代码
```text
1.consumer&producer类增加注解@Service,实现CommandLineRunner
2.使用@Value("${xx}")读取配置
3.实现业务
```

## 十、Binlog
### 开启binlog
```text
mysql配置my.cnf中配置
#开启binlog
log-bin=mysql-bin

#binlog模式
binlog_format="ROW"

#配置server_id,否则项目启动可能会报错
server_id=1001

docker run --name mysql -v /Users/mac/work/docker/software/mysql5.6/conf/my.cnf:/etc/mysql/my.cnf -v /Users/mac/work/docker/software/mysql5.6/data:/var/lib/mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
```

### jar包
```text
<!--binlog-->
<dependency>
    <groupId>com.github.shyiko</groupId>
    <artifactId>mysql-binlog-connector-java</artifactId>
    <version>0.18.1</version>
</dependency>
```

### 简单示例
```text
见BinlogClientService
```
