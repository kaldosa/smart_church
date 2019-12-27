package com.laonsys.springmvc.extensions.validation.support;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.validation.constraints.File;

public class FileListValidator extends AbstractFileValidator implements ConstraintValidator<File, List<MultipartFile>> {
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
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {

        if (files == null) {
            context.buildConstraintViolationWithTemplate("{required.file}").addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return !required;
        }

        trim(files);

        if (files.isEmpty()) {
            context.buildConstraintViolationWithTemplate("{required.file}").addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return !required;
        }

        boolean isValid = true;
        for (MultipartFile file : files) {
            if (!validateContentType(file, contentTypes)) {
                context.buildConstraintViolationWithTemplate(file.getOriginalFilename() + "{invalid.file.mediatype}")
                        .addConstraintViolation().disableDefaultConstraintViolation();
                isValid = false;
            }

            if (!validateSize(file, limit)) {
                context.buildConstraintViolationWithTemplate(file.getOriginalFilename() + "{invalid.file.size}")
                        .addConstraintViolation().disableDefaultConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
