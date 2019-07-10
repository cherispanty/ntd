package com.qgwy.template.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.qgwy.template.constants.DataSourceNames;
import com.qgwy.template.util.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源 
 */
@Configuration
public class DynamicDataSourceConfig {
 
    @Bean("firstDataSource")
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
 
    @Bean("secondDataSource")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("testDataSource")
    @ConfigurationProperties("spring.datasource.druid.test")
    public DataSource testDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("dataSource")
    @DependsOn({ "firstDataSource","secondDataSource","testDataSource"})
    @Primary
    public DynamicDataSource dataSource() {
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource());
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource());
        targetDataSources.put(DataSourceNames.Test, testDataSource());
        return new DynamicDataSource(firstDataSource(), targetDataSources);
    }
}