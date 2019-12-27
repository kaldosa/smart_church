package com.laonsys.smartchurch.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.laonsys.springmvc.extensions.model.Attachment;

public class CommentSerializer extends JsonSerializer<BaseComments> {

    @Override
    public void serialize(BaseComments value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getId());
        jgen.writeFieldName("comment");
        jgen.writeString(value.getComments());
        jgen.writeFieldName("writedDate");
        jgen.writeObject(value.getCreatedDate());
        jgen.writeFieldName("writerId");
        jgen.writeNumber(value.getUser().getId());
        jgen.writeFieldName("writerName");
        jgen.writeString(value.getUser().getName());
        
        Attachment photo = value.getUser().getPhoto();
        if(photo != null && photo.getId() > 0) {
            jgen.writeFieldName("photo");
            jgen.writeObject(photo);
        }
        jgen.writeEndObject();
    }

}
