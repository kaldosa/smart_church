package com.laonsys.smartchurch.domain.church;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.laonsys.springmvc.extensions.model.Attachment;

public class ChurchOrgSerializer extends JsonSerializer<ChurchOrg> {

    @Override
    public void serialize(ChurchOrg value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getId());
        jgen.writeFieldName("churchId");
        jgen.writeNumber(value.getChurchId());
        jgen.writeFieldName("path");
        jgen.writeString(value.getPath());
        jgen.writeFieldName("orgName");
        jgen.writeString(value.getName());
        jgen.writeFieldName("intro");
        jgen.writeString(value.getIntro());
        jgen.writeFieldName("manager");
        jgen.writeObject(value.getManager());
        Attachment orgImage = value.getAttachment();
        if(orgImage != null) {
            jgen.writeFieldName("orgImage");
            jgen.writeObject(orgImage);
        }
        jgen.writeEndObject();
    }

}
