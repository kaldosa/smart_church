package com.laonsys.smartchurch.service;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.PhotoFormBean;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface ChurchService {
    int count(QueryParam queryParam);
    
    List<OurChurch> selectList(QueryParam queryParam);
    List<OurChurch> selectRecent();
    OurChurch selectOne(int id);
    List<Attachment> selectMainImages(int id);
    
    void insert(OurChurch ourChurch);
    void update(OurChurch ourChurch);
    void delete(int id);
    
    boolean isAvailablePath(String path);
    
    public void updateLogo(OurChurch ourChurch, PhotoFormBean photoFormBean);
    public Attachment insertMainImage(String path, int refId, MultipartFile file);
    public void deleteMainImage(int attachId);
    Long totalAttachSize(String path, boolean type);

    void application(OurChurch ourChurch, BindingResult result);
    void cancelApplication(int id);
    void approve(OurChurch ourChurch);
}
