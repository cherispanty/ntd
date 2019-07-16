package com.qgwy.ntd;

import com.qgwy.ntd.config.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Import(DynamicDataSourceConfig.class)
@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
@MapperScan(value = "com.qgwy.ntd.mapper")
@EnableSwagger2
public class NtdApplication {
    public static void main(String[] args) {
        SpringApplication.run(NtdApplication.class, args);
    }

}


