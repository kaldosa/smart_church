package com.laonsys.springmvc.extensions.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.laonsys.springmvc.extensions.validation.support.DateFormValidator;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFormValidator.class)
public @interface DateForm {
    
    String message() default "잘못된 날짜 형식입니다.";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
