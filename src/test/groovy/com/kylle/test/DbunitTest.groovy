package com.kylle.test

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DbunitTest extends Specification {


    @Dbunit(content = {
        t_user(user_id: 1, user_name: "bob")
        t_order(order_id: 1, price: 10, user_id: 1)
    })
    def test() {
        given:
        println("test")
        Thread.sleep(100000L)
    }


}
