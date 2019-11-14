package com.project.config;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.project.es.web.dao.main",
        sqlSessionFactoryRef = "mainSqlSessionFactory")
public class DruidDataSourceConfig {

    @Value("${dataSource.main.type}")
    private Class<? extends DataSource> type;

    @Bean(name = "dataSourceProperties")
    @ConfigurationProperties("dataSource.main")
    public Map<String,String> mutablePropertyValues(){
        return Maps.newHashMap();
    }


    @Bean(name = "mainDataSource")
    @Primary
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create().type(type).build();
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues(mutablePropertyValues());
        new RelaxedDataBinder(dataSource).bind(mutablePropertyValues);
        return dataSource;
    }

    /** 多个数据源时需要指定事务管理器，否则会默认主事务管理器，导致事务失败 **/
    @Bean(name = "mainTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("mainDataSource") DataSource mainDataSource) {
        return new DataSourceTransactionManager(mainDataSource);
    }

    @Bean(name = "mainSqlSessionFactory")
    @Primary
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource mainDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mainDataSource);
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("param", "pageNum=start;pageSize=limit");
        pageHelper.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});
        return sessionFactory.getObject();
    }

}
