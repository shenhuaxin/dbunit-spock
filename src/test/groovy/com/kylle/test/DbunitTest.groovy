package com.kylle.test

import groovy.sql.Sql
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest// (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DbunitTest extends Specification {


    @Dbunit(content = {
        t_user(user_id:1 , user_name: 'kylle')
        t_user(user_id:2 , user_name: 'kya')
    })
    def testTransaction() {
        given:
        def value = new Sql(DataSourceHolder.getConnection())
                .firstRow("select count(*) from t_user")
                .values().first()

        println(value)
        assert value == 2
    }

}
