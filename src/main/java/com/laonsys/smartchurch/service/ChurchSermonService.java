package com.laonsys.smartchurch.service;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.Sermon;

public interface ChurchSermonService {
    public Sermon lastOne(int id);
    public int storeToTemporaryFile(MultipartFile file);
}
