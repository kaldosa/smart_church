package com.laonsys.springmvc.extensions.validation.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.laonsys.springmvc.extensions.validation.constraints.DateForm;

public class DateFormValidator implements ConstraintValidator<DateForm, String> {

    @Override
    public void initialize(DateForm constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        
        try {
            Date testDate = simpleDateFormat.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(testDate.getTime());
            
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) + 1;
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            
            if(1 > m || m > 12) {
                return false;
            }
            if(1 > d || d > 31) {
                return false;
            }
            if((m==4 || m==6 || m==9 || m==11) && d==31) {
                return false;
            }
            if(m==2) {
                boolean isleap = (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0));
                
                if(d > 29 || (d == 29 && !isleap)) {
                    return false;
                }
            }
            return true;
        }
        catch(ParseException e) {
            return false;
        }
    }
}