package com.laonsys.springmvc.extensions.validation.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.laonsys.springmvc.extensions.validation.constraints.KorAllowWhiteSpace;


public class KorAllowWhiteSpaceValidator implements ConstraintValidator<KorAllowWhiteSpace, String> {

    private Pattern pattern = Pattern.compile("^([가-힣]+\\s*[가-힣 ]*)[^\\s]$");

    @Override
    public void initialize(KorAllowWhiteSpace constraintAnnotation) {
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
