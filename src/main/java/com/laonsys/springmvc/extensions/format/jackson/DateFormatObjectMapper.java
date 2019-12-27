package com.laonsys.springmvc.extensions.format.jackson;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class DateFormatObjectMapper extends ObjectMapper {

    public DateFormatObjectMapper() {
        configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        setDateFormat(new SimpleDateFormat("yyyy.MM.dd"));
    }
}
