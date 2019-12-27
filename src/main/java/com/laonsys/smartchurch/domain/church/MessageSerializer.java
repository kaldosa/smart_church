package com.laonsys.smartchurch.domain.church;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class MessageSerializer extends JsonSerializer<Message> {

    @Override
    public void serialize(Message value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getId());
        jgen.writeFieldName("churchId");
        jgen.writeNumber(value.getChurchId());
        jgen.writeFieldName("subject");
        jgen.writeString(value.getSubject());
        jgen.writeFieldName("subTitle");
        jgen.writeString(value.getSubTitle());
        jgen.writeFieldName("contents");
        jgen.writeString(value.getContents());
        jgen.writeFieldName("writtenDate");
        jgen.writeObject(value.getCreatedDate());
        jgen.writeEndObject();
    }

}
