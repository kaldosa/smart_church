package com.laonsys.springmvc.extensions.validation.support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.springmvc.extensions.validation.constraints.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Autowired UserService userService;
    
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User user = userService.findByEmail(value, null);
        return (user == null) ? true : false;
    }
}
