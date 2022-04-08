package com.kylle.test

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DbunitConfiguration {


    @Bean(initMethod = "init")
    public DbunitDataSourceInit dataSourceInit(ApplicationContext context) {
        return new DbunitDataSourceInit(context);
    }

}
