package com.laonsys.springmvc.extensions.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.laonsys.springmvc.extensions.model.QueryParam;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebQuery {
    String value() default "";
    boolean required() default true;
    Class<QueryParam> defaultValue() default QueryParam.class;
}
