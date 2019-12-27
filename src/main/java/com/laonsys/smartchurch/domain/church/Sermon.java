package com.laonsys.smartchurch.domain.church;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.JodaTimeSerializer;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@JsonSerialize(using=SermonSerializer.class)
@ToString(callSuper = true, includeFieldNames = true)
public class Sermon extends AbstractEntity<Sermon> {
    @Getter @Setter
    private int churchId;
    
    @Getter @Setter
    private String path;

    @NotEmpty(groups={Create.class, Update.class})
    @Size(max = 10, groups={Create.class, Update.class})
    @Getter @Setter
    private String preacher;

    @Size(max = 30, groups={Create.class, Update.class})
    @Getter @Setter
    private String bible;

    @NotEmpty(message = "{NotEmpty.subject}", groups={Create.class, Update.class})
    @Size(max = 50, message = "{Size.subject}", groups={Create.class, Update.class})
    @Getter @Setter
    private String subject;

    @Getter @Setter
    private List<Attachment> attachments;

    @NotNull(message = "{NotNull.sermonDate}", groups={Create.class, Update.class})
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonSerialize(using = JodaTimeSerializer.class)
    @Getter @Setter
    private DateTime sermonDate;
    
    @Getter @Setter
    private int video;

    @File(contentType = {"text/plain", "application/plain", "application/pdf", "application/msword"}, limit = 5242880, groups={Create.class, Update.class})
    @Getter @Setter
    private MultipartFile note;
    
    @File(contentType = {"audio/mpeg"}, limit = 10485760, groups={Create.class, Update.class})
    @Getter @Setter
    private MultipartFile mp3;

    public static Attachment getMedia(List<Attachment> list, String mediaType) {
        if (list == null || list.isEmpty())
            return null;

        for (Attachment attach : list) {

            if (mediaType.equals(attach.getContentType())) {
                return attach;
            }
        }

        return null;
    }

    public static Attachment getNote(List<Attachment> list) {
        if(list == null || list.isEmpty()) return null;
        
        for(Attachment attach : list) {
            String contentType = attach.getContentType();
            if("text/plain".equalsIgnoreCase(contentType) || 
               "application/plain".equalsIgnoreCase(contentType) ||
               "application/pdf".equalsIgnoreCase(contentType) ||
               "application/msword".equalsIgnoreCase(contentType)) {
                return attach;
            }
        }
        
        return null;
    }
}
