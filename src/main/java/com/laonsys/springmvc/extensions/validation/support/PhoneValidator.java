package com.laonsys.springmvc.extensions.validation.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.laonsys.springmvc.extensions.validation.constraints.Phone;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private Pattern pattern = Pattern.compile("^(01(0|1|[6-9])-\\d{3,4}-\\d{4})$");

    @Override
    public void initialize(Phone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}
