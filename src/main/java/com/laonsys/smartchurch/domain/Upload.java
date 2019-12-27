package com.laonsys.smartchurch.domain;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.validation.constraints.File;

public class Upload {
    @File(contentType = {"video/mp4", "application/octet-stream"}, limit = 104857600, required = true)
    private MultipartFile file;

    public Upload() {
    }
    
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }
}
