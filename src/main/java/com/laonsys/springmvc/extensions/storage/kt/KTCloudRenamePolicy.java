package com.laonsys.springmvc.extensions.storage.kt;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Setter;

import com.laonsys.springmvc.extensions.exception.UnsupportedException;
import com.laonsys.springmvc.extensions.storage.RenamePolicy;

@Named("ktRenamePolicy")
public class KTCloudRenamePolicy implements RenamePolicy {

    @Setter @Inject KTCloudStorageApis ktCloudStorageApis;
    
    @Override
    public String getRename(String parent, String child) {
        int offset = child.lastIndexOf(".");
        String ext = "";
        if(offset != -1) {
            ext = (child.substring(offset)).toLowerCase();
        }
        
        String folder = "";
        int lastSlash = child.lastIndexOf("/");
        if(lastSlash != -1) {
            folder = (child.substring(0, lastSlash));
        }
        
        String rename = child.substring(lastSlash + 1, offset);
        
        // FIXME 영문 대,소문자, 숫자, _, -, \, . 이외 일경우 rename
        if(!rename.matches("[_A-Za-z0-9-\\.]+")) {
            rename = UUID.randomUUID().toString();
        }
        
        while(ktCloudStorageApis.exists(parent, folder + "/" + rename + ext)) {
            rename = UUID.randomUUID().toString();
        }
        
        return folder + "/" + rename + ext;
    }
    
    @Override
    public String getRename(String path) {
        return null;
    }
    
    @Override
    public File getRenameFile(String path) {
        throw new UnsupportedException();
    }

    @Override
    public File getRenameFile(String parent, String child) {
        throw new UnsupportedException();
    }

}
