## 集成ES

### REST Clinet
- 不受es版本影响
- 9200端口直接访问

```text

1.引入包
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-client</artifactId>
    <version>6.4.0</version>
</dependency>

2.配置EsRestConfig

```

### Spring-data-es方式
- pom版本号需要与es版本号对应


|spring data elasticsearch|	elasticsearch|
|--|--|
|3.1.x|	6.2.2|
|3.0.x|	5.5.0|
|2.1.x|	2.4.0|
|2.0.x|	2.2.0|
|1.3.x|	1.5.2|

```text

1.导包
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-elasticsearch</artifactId>
    <version>3.0.10.RELEASE</version>
</dependency>

2.配置链接
spring:
  data:
    elasticsearch:
      cluster-name: esCluster # 集群名
      #连接节点,注意在集群中通信都是9300端口，否则会报错无法连接上！
      cluster-nodes: 117.0.0.1.129:9300
      local: true #是否本地连接
      repositories:
        enabled: true #仓库中数据存储


```

### Java API方式
- ES7.0版本中将会移除
