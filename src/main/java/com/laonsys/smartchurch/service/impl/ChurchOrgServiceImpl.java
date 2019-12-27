package com.laonsys.smartchurch.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.mapper.ChurchOrgMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.ChurchOrgPostsService;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchOrgService")
@Transactional
public class ChurchOrgServiceImpl extends AbstractGenericService<ChurchOrg> implements ChurchOrgService {

    @Autowired private ChurchOrgMapper churchOrgMapper;    
    @Autowired private ChurchOrgPostsService churchOrgPostsService;
    @Autowired private AttachService attachService;
    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
    @Override
    protected Mapper<ChurchOrg> getMapper() {
        return churchOrgMapper;
    }
    
    @Override
    public void create(ChurchOrg churchOrg) {
        super.create(churchOrg);
        MultipartFile file = churchOrg.getUpload();
        
        if(file != null && !file.isEmpty()) {
            saveAttach(churchOrg.getId(), churchOrg.getPath(), file, "church_org_attach");
        }
    }
    
    @Override
    public void update(ChurchOrg churchOrg) {
        super.update(churchOrg);
        
        MultipartFile file = churchOrg.getUpload();
        
        if (file != null && !file.isEmpty()) {
            attachService.deleteAll(churchOrg.getId(), "church_org_attach");
            saveAttach(churchOrg.getId(), churchOrg.getPath(), file, "church_org_attach");
        }
    }
    
    @Override
    public void update(List<ChurchOrg> churchOrgs) {
        for(ChurchOrg org : churchOrgs) {
            churchOrgMapper.update(org);
        }
    }
    
    @Override
    public void delete(int id) {
        churchOrgPostsService.deleteAll(id);
        attachService.deleteAll(id, "church_org_attach");
        super.delete(id);
    }
    
    private Attachment saveAttach(int refId, String path, MultipartFile file, String tableName) {
        Attachment attachment = null;
        try {
            File output = (File) resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream());
            
            if(output != null) {
                attachment = attachService.insert("/" + path, output, tableName, refId);
            }
        }
        catch (IOException e) {
            throw new ServiceException("failed to save image in [" + tableName + "].");
        }
        return attachment;
    }
}
