package com.qgwy.template;

import com.qgwy.template.config.DruidConfig;
import com.qgwy.template.config.DynamicDataSourceConfig;
import com.qgwy.template.config.MyBatisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Import(DynamicDataSourceConfig.class)
@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
@MapperScan(value = "com.qgwy.template.mapper")
public class QgwyApplication {
    public static void main(String[] args) {
        SpringApplication.run(QgwyApplication.class, args);
    }

}


