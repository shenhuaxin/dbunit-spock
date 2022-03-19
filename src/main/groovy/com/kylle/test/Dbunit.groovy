package com.kylle.test

import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtensionAnnotation(DbunitExtension)
@interface Dbunit {

    /**
     * <pre>
     *  @Dbunit(content = {
     *       t_user(user_id: 1, user_name: "bob")
     *       t_order(order_id: 1, price: 10, user_id: 1)
     *   })
     *  </pre>
     */
    Class<? extends Closure> content() default Closure.class;

    String schema() default "";
}