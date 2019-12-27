package com.laonsys.smartchurch.domain.church;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

public class ChurchIntro {
    private int id;

    private int churchId;

    @NotEmpty
    private String path;

    @NotEmpty(groups = { Create.class, Update.class })
    @Size(max = 50, message = "{Size.churchIntro.slogan}", groups = { Create.class, Update.class })
    private String slogan;

    @NotEmpty(message = "{NotEmpty.contents}", groups = { Create.class, Update.class })
    private String intro;

    private Attachment introImage;

    @File(contentType = { "image/jpeg", "image/png", "image/gif", "image/x-png", "image/x-citrix-png",
            "image/x-citrix-jpeg", "image/pjpeg" }, limit = 1048576, groups = { Create.class, Update.class })
    private MultipartFile file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Attachment getIntroImage() {
        return introImage;
    }

    public void setIntroImage(Attachment introImage) {
        this.introImage = introImage;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
