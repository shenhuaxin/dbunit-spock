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
     *     content = {
     *
     *     }
     * </pre>
     */
    Class<? extends Closure> content() default Closure.class;

    String schema() default "";

    DatabaseOperation operation() default DatabaseOperation.INSERT;
}