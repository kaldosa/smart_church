package com.laonsys.springmvc.extensions.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.Email;

import com.laonsys.springmvc.extensions.validation.support.EmailValidator;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Email
@Documented
public @interface ValidEmail {
    String message() default "{com.laonsys.validation.constraint.ValidEmail.message}";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    /**
     * Defines several <code>@ValidEmail</code> annotations on the same element
     */
    @Target({ METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidEmail[] value();
    }
}
