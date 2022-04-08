package com.kylle.test

import groovy.sql.Sql
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DbunitTest extends Specification {


    @Dbunit(content = {
        t_configuration_attr(type: 1, configuration_id: 1, attr_code: "orgId", simple_value: "000T")
        t_configuration_attr(type: 1, configuration_id: 1, attr_code: "vehicleModelSeq", simple_value: "1")
        t_configuration_attr(type: 1, configuration_id: 1, attr_code: "channel", simple_value: "1")
        t_configuration_attr(type: 1, configuration_id: 1, attr_code: "weekDay",complex_value: "{\"fri\": 5.5, \"mon\": 1.1, \"sat\": 6.6, \"sun\": 7.7, \"tue\": 2.2, \"wed\": 3.3, \"thur\": 4.4}")
        t_configuration_attr(type: 1, configuration_id: 1, attr_code: "holidays", complex_value: "[{\"end\": 1609603200000, \"label\": \"元旦\", \"price\": 1, \"start\": 1609430400000}]")
        t_configuration_attr(type: 1, configuration_id: 1, attr_code: "specialDays", complex_value: "[{\"end\": 1609603200000, \"label\": \"活动节\", \"price\": 1, \"start\": 1609430400000}]")
    })
    def testTransaction() {
        given:
        def value = new Sql(DataSourceHolder.getConnection())
                .firstRow("select count(*) from store_goods.t_configuration_attr where type = 1 and configuration_id = 1 and status = 1")
                .values().first()

        println(value)
        assert value == 6
    }

}
