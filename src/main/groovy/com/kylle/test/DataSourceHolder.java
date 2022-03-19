package com.kylle.test;

import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceHolder {

    static volatile DataSource datasource;
    static ApplicationContext context;
    public static void main(String[] args) throws SQLException {
        getConnection();
    }

    private static synchronized DataSource getDataSource() throws SQLException {
        if (datasource == null) {
//            DruidDataSource druidDataSource = new DruidDataSource();
//            druidDataSource.setUrl("jdbc:h2:mem:goods;INIT=CREATE SCHEMA IF NOT EXISTS goods\\;SET SCHEMA goods");
//            druidDataSource.setUsername("sa");
//            druidDataSource.setPassword("");
//            druidDataSource.init();
//            datasource = druidDataSource;
            datasource = context.getBean(DataSource.class);
        }
        return datasource;
    }

    public static synchronized void setDataSource(DataSource dataSource) {
        DataSourceHolder.datasource = dataSource;
    }



    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
