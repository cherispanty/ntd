package com.qgwy.template.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.qgwy.template.util.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bstek.ureport.definition.datasource.BuildinDatasource;



@Component
@Slf4j
public class UreportDataSource implements BuildinDatasource {
    private static final String NAME = "MyDataSource";

    @Autowired
    private DynamicDataSource dynamicDataSource;


    /**
     * 数据源名称
     */
    @Override
    public String name() {
        return NAME;
    }


    /**
     * 获取连接
     *         
     **/
    @Override
    public Connection getConnection() {
        try {
            return dynamicDataSource.getConnection();
        } catch (SQLException e) {
            log.error("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        }
        return null;
    }

}
