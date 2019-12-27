package com.laonsys.smartchurch.domain.church;

import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Attach;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@ToString(callSuper = true, includeFieldNames = true, exclude = {"thumbnail", "attachments", "comments", "uploads"})
@EqualsAndHashCode(callSuper=false)
public @Data class Album extends AbstractEntity<Album> {
    private int churchId;
    
    private String path;

    @NotEmpty(groups = {Create.class, Update.class})
    @Size(max=50, groups = {Create.class, Update.class})
    private String subject;

    @NotEmpty(groups = {Create.class, Update.class})
    @Size(max=140, groups = {Create.class, Update.class})
    private String contents;

    private long hits;

    private int commentsCount;
    
    private Attachment thumbnail;

    private List<Attachment> attachments;

    private List<BaseComments> comments;

    @File(contentType = {"image/jpeg", "image/gif", "image/png"}, limit = 1048576, groups = {Attach.class})
    private List<MultipartFile> uploads;
    
    private User author;
}
