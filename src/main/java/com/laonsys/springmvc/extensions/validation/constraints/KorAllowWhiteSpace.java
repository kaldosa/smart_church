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

import com.laonsys.springmvc.extensions.validation.support.KorAllowWhiteSpaceValidator;


@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = KorAllowWhiteSpaceValidator.class)
@NotEmpty
@Documented
@ReportAsSingleViolation
public @interface KorAllowWhiteSpace {
    String message() default "{com.laonsys.validation.constraint.Kor.message}";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Defines several <code>@Kor</code> annotations on the same element
     */
    @Target({ METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNull[] value();
    }
}
