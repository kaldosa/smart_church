package com.laonsys.smartchurch.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;


public class UserSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("uid", value.getId());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("contact", value.getContact());
        jgen.writeBooleanField("enable", value.isEnabled());
        jgen.writeObjectField("photo", value.getPhoto());
        jgen.writeEndObject();        
    }

}
