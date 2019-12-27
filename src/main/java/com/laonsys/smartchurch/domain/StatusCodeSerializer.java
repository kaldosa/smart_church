package com.laonsys.smartchurch.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;


public class StatusCodeSerializer extends JsonSerializer<StatusCode> {

    @Override
    public void serialize(StatusCode value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("code");
        jgen.writeNumber(value.value());
        jgen.writeFieldName("description");
        jgen.writeString(value.getDescription());
        jgen.writeEndObject();
    }    
}
