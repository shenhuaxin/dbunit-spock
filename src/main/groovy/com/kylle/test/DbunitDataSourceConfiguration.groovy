package com.kylle.test


import org.springframework.beans.BeansException
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Service

import javax.sql.DataSource

@Service
class DbunitDataSourceConfiguration implements InitializingBean, ApplicationContextAware {

    ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        DataSourceHolder.setDataSource(context.getBean(DataSource.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
