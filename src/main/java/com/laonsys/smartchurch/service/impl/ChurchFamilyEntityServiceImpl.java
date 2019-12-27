package com.laonsys.smartchurch.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.Family;
import com.laonsys.smartchurch.mapper.ChurchFamilyMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchFamilyEntityService")
@Transactional
public class ChurchFamilyEntityServiceImpl extends AbstractGenericService<Family> {

    @Autowired private ChurchFamilyMapper churchFamilyMapper;
    @Autowired private AttachService attachService;
    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
    @Override
    protected Mapper<Family> getMapper() {
        return churchFamilyMapper;
    }
    
    @Override
    public void create(Family family) {
        super.create(family);
        MultipartFile photo = family.getUpload();
        
        if(photo != null && !photo.isEmpty()) {
            saveAttach(family.getId(), family.getPath(), photo);
        }
    }
    
    @Override
    public void update(Family family) {
        super.update(family);

        MultipartFile photo = family.getUpload();

        if (photo != null && !photo.isEmpty()) {
            attachService.deleteAll(family.getId(), "church_family_attach");
            saveAttach(family.getId(), family.getPath(), photo);
        }
    }
    
    @Override
    public void delete(int id) {
        attachService.deleteAll(id, "church_family_attach");
        super.delete(id);
    }

    private void saveAttach(int refId, String path, MultipartFile file) {
        try {
            File output = (File) resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream());
            
            if(output != null) {
                attachService.insert("/" + path, output, "church_family_attach", refId);
            }
        }
        catch (IOException e) {
            throw new ServiceException("failed to save family photo.");
        }
    }
}
