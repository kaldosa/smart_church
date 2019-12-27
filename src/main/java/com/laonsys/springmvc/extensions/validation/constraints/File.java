package com.laonsys.springmvc.extensions.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;

import com.laonsys.springmvc.extensions.validation.support.FileValidator;
import com.laonsys.springmvc.extensions.validation.support.FileListValidator;

@Target({ METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FileValidator.class, FileListValidator.class})
public @interface File {
    String message() default "";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 검사할 content type
     */
    String[] contentType() default {};

    /**
     * @return 검사할 file 사이즈
     */
    int limit() default Integer.MAX_VALUE;

    /**
     * @return 첨부파일의 필수유무 (기본값 false)
     */
    boolean required() default false;
    
    /**
     * Defines several <code>@File</code> annotations on the same element
     * @see Size
     *
     * @author Emmanuel Bernard
     */
    @Target({ METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        File[] value();
    }
}
