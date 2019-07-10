package com.qgwy.template.config;

import com.qgwy.template.constants.DataSourceNames;
import com.qgwy.template.util.DynamicDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    /*@Autowired
    DataSource firstDataSource;
    @Autowired
    DataSource secondDataSource;
    @Autowired
    DataSource testDataSource;*/

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){

            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }

    /*//重点在这个方法
    @Bean
    @Primary
    public DynamicDataSource dataSource() {



        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        targetDataSources.put(DataSourceNames.Test, testDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }*/


    /**
     * 根据数据源创建SqlSessionFactory
      * @param dataSource
     * @return
     * @throws Exception
     */

    /*@Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }*/


   /* @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/


    /**
     *
     * 配置事务管理器
     * @param dataSource
     * @return
     */
    /*@Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/

    /*@Bean
    public TransactionTemplate txTemplate(DataSourceTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }*/


}
