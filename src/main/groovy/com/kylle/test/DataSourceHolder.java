package com.kylle.test;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

public class DataSourceHolder {

    static volatile DataSource datasource;
    static ApplicationContext context;

    public static synchronized DataSource getDataSource() {
        if (datasource == null) {
            datasource = context.getBean(DataSource.class);
        }
        return datasource;
    }

    public static synchronized void setDataSource(DataSource dataSource) {
        DataSourceHolder.datasource = dataSource;
    }

    public static Connection getConnection() {
        return DataSourceUtils.getConnection(getDataSource());
    }

}
