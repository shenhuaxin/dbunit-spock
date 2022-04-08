package com.kylle.test


import org.springframework.context.ApplicationContext

import javax.sql.DataSource

class DbunitDataSourceInit {

    ApplicationContext context;

    DbunitDataSourceInit(ApplicationContext context) {
        this.context = context
    }

    public void init() throws Exception {
        DataSourceHolder.setDataSource(context.getBean(DataSource.class));
    }


}
