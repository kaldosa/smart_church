package com.laonsys.smartchurch.domain.church;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.laonsys.smartchurch.domain.BaseComments;

public class OrgPostsSerializer extends JsonSerializer<OrgPosts> {

    @Override
    public void serialize(OrgPosts value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getId());
        jgen.writeFieldName("orgId");
        jgen.writeNumber(value.getOrgId());        
        jgen.writeFieldName("subject");
        jgen.writeString(value.getSubject());
        jgen.writeFieldName("contents");
        jgen.writeString(value.getContents());
        jgen.writeFieldName("writtenDate");
        jgen.writeObject(value.getCreatedDate());
        jgen.writeFieldName("hits");
        jgen.writeNumber(value.getHits());
        jgen.writeFieldName("commentsCount");
        jgen.writeNumber(value.getCommentsCount());
        jgen.writeObjectField("attachment", value.getAttachment());
        jgen.writeFieldName("writerId");
        jgen.writeNumber(value.getUser().getId());
        jgen.writeFieldName("writerName");
        jgen.writeString(value.getUser().getName());
        jgen.writeObjectField("photo", value.getUser().getPhoto());
        
        List<BaseComments> comments = value.getComments();
        if(comments != null && !comments.isEmpty()) {
            jgen.writeFieldName("comments");
            jgen.writeObject(comments);
        }
        jgen.writeEndObject();
    }

}
