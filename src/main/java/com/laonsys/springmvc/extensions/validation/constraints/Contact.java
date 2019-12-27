package com.laonsys.springmvc.extensions.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.laonsys.springmvc.extensions.validation.support.ContactValidator;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ContactValidator.class)
@Documented
@NotEmpty
@ReportAsSingleViolation
public @interface Contact {
    String message() default "{com.laonsys.validation.constraint.Phone.message}";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Defines several <code>@Phone</code> annotations on the same element
     */
    @Target({ METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNull[] value();
    }
}
