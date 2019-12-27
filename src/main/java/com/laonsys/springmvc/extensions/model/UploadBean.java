package com.laonsys.springmvc.extensions.model;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.validation.constraints.File;

public class UploadBean {
    
    @File(contentType = {"image/jpeg", "image/gif", "image/png"}, limit = 1048576)
    private MultipartFile file;
    
    public MultipartFile getFile() {
        return file;
    }
    
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
