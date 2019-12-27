package com.laonsys.springmvc.extensions.validation.support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.validation.constraints.File;

public class FileValidator extends AbstractFileValidator implements ConstraintValidator<File, MultipartFile> {
    private int limit;

    private String[] contentTypes;

    private boolean required;

    @Override
    public void initialize(File constraintAnnotation) {
        limit = constraintAnnotation.limit();
        contentTypes = constraintAnnotation.contentType();
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (file == null) {
            context.buildConstraintViolationWithTemplate("{required.file}").addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return !required;
        }

        if (file.isEmpty()) {
            context.buildConstraintViolationWithTemplate("{required.file}").addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return !required;
        }

        if (!validateContentType(file, contentTypes)) {
            context.buildConstraintViolationWithTemplate(file.getOriginalFilename() + "{invalid.file.mediatype}")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            return false;
        }

        if (!validateSize(file, limit)) {
            context.buildConstraintViolationWithTemplate(file.getOriginalFilename() + "{invalid.file.size}")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }
}
