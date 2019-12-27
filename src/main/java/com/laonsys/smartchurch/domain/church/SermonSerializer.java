package com.laonsys.smartchurch.domain.church;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.laonsys.springmvc.extensions.model.Attachment;

public class SermonSerializer extends JsonSerializer<Sermon> {

    @Override
    public void serialize(Sermon value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getId());
        jgen.writeFieldName("churchId");
        jgen.writeNumber(value.getChurchId());
        jgen.writeFieldName("path");
        jgen.writeString(value.getPath());
        jgen.writeFieldName("subject");
        jgen.writeString(value.getSubject());
        jgen.writeFieldName("preacher");
        jgen.writeString(value.getPreacher());
        jgen.writeFieldName("bible");
        jgen.writeString(value.getBible());

        jgen.writeFieldName("sermonDate");
        DateTimeFormatter formatter =  DateTimeFormat.forPattern("yyyy.MM.dd");
        jgen.writeObject(formatter.print(value.getSermonDate()));
        
        List<Attachment> attachments = value.getAttachments();
        if(attachments != null && !attachments.isEmpty()) {
            Attachment thumbnail = Sermon.getMedia(attachments, "image/png");
            if(thumbnail != null) {
                jgen.writeObjectField("thumbnail", thumbnail);
            }
            
            Attachment mp4 = Sermon.getMedia(attachments, "video/mp4");
            
            if(mp4 != null) {
                jgen.writeObjectField("mp4", mp4);
            }
            
            Attachment flv = Sermon.getMedia(attachments, "video/x-flv");
            
            if(flv != null) {
                jgen.writeObjectField("flv", flv);
            }
            
            Attachment mp3 = Sermon.getMedia(attachments, "audio/mpeg");
            
            if(mp3 != null) {
                jgen.writeObjectField("mp3", mp3);
            }
            
            Attachment note = Sermon.getNote(attachments);
            
            if(note != null) {
                jgen.writeObjectField("note", note);
            }
        }
        jgen.writeEndObject();        
    }

}
