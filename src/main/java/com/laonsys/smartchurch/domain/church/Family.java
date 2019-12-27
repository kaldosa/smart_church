package com.laonsys.smartchurch.domain.church;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

public class Family extends AbstractEntity<Family> {
    private int churchId;

    private String path;

    @NotEmpty(groups = { Create.class, Update.class })
    @Size(max = 10, groups = { Create.class, Update.class })
    private String name;

    @NotEmpty(groups = { Create.class, Update.class })
    @Size(max = 150, groups = { Create.class, Update.class })
    private String intro;

    private Attachment attachment;

    @File(contentType = { "image/jpeg", "image/png", "image/gif", "image/x-png", "image/x-citrix-png",
            "image/x-citrix-jpeg", "image/pjpeg" }, limit = 1048576, groups = { Create.class, Update.class })
    private transient MultipartFile upload;

    public int getChurchId() {
        return churchId;
    }

    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public MultipartFile getUpload() {
        return upload;
    }

    public void setUpload(MultipartFile upload) {
        this.upload = upload;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
