package com.laonsys.smartchurch.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ResponseSerializer extends JsonSerializer<Response> {

    @Override
    public void serialize(Response value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("code");
        jgen.writeNumber(value.getStatusCode().value());
        jgen.writeFieldName("description");
        jgen.writeString(value.getStatusCode().getDescription());
        if(!value.getDataMap().isEmpty()) {
            jgen.writeObjectField("data", value.getDataMap());
        }
        jgen.writeEndObject();
    }

}
