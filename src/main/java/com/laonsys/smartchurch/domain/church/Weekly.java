package com.laonsys.smartchurch.domain.church;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.Postings;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

public class Weekly extends Postings {
    private int churchId;
    
    private String path;
    
    private Attachment attach;

    @File(limit = 5242880, groups = {Create.class, Update.class})
    private MultipartFile upload;

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
    
    public Attachment getAttach() {
        return attach;
    }

    public void setAttach(Attachment attach) {
        this.attach = attach;
    }

    public MultipartFile getUpload() {
        return upload;
    }

    public void setUpload(MultipartFile upload) {
        this.upload = upload;
    }
}
