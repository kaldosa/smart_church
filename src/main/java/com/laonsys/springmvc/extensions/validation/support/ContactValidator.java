package com.laonsys.springmvc.extensions.validation.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.laonsys.springmvc.extensions.validation.constraints.Contact;

public class ContactValidator implements ConstraintValidator<Contact, String> {

    private Pattern pattern = Pattern.compile("^(\\d{2,4}\\-\\d{3,4}\\-\\d{4})$");

    @Override
    public void initialize(Contact constraintAnnotation) {
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
