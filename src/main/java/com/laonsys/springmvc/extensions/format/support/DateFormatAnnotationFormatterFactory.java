package com.laonsys.springmvc.extensions.format.support;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.StringUtils;

public class DateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);
        fieldTypes.add(Date.class);
        return fieldTypes;
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<Date> configureFormatterFrom(DateTimeFormat annotation, Class<?> fieldType) {
        DateFormatter dateFormatter = new DateFormatter();

        if (StringUtils.hasLength(annotation.pattern())) {
            dateFormatter.setPattern(annotation.pattern());
        }
        return dateFormatter;
    }
}
