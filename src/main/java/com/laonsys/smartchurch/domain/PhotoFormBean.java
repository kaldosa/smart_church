package com.laonsys.smartchurch.domain;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.validation.constraints.File;

public class PhotoFormBean {
    @File(contentType = { "image/jpeg", "image/png", "image/gif", "image/x-png", "image/x-citrix-png",
            "image/x-citrix-jpeg", "image/pjpeg" }, limit = 10485760) // 10Mb
    private MultipartFile file;

    public PhotoFormBean() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
