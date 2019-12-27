package com.laonsys.smartchurch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.Weekly;
import com.laonsys.smartchurch.mapper.ChurchWeeklyMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;

@Service("churchWeeklyEntityService")
@Transactional
public class ChurchWeeklyEntityServiceImpl extends AbstractGenericService<Weekly> implements UpdateHitsService<Weekly> {

    @Autowired private ChurchWeeklyMapper churchWeeklyMapper;
    @Autowired private AttachService attachService;
    
    @Override
    protected Mapper<Weekly> getMapper() {
        return churchWeeklyMapper;
    }
    
    @Override
    public void create(Weekly weekly) {
        super.create(weekly);
        MultipartFile file = weekly.getUpload();
        if(file != null && !file.isEmpty()) {
            attachService.insert("/" + weekly.getPath(), file, "church_weekly_attach", weekly.getId());
        }
    }
    
    @Override
    public void update(Weekly weekly) {
        super.update(weekly);

        MultipartFile file = weekly.getUpload();

        if (file != null && !file.isEmpty()) {
            if(weekly.getAttach() != null) {
                attachService.delete(weekly.getAttach().getId(), "church_weekly_attach");
            }
            attachService.insert("/" + weekly.getPath(), file, "church_weekly_attach", weekly.getId());
        }
    }
    
    @Override
    public void delete(int id) {
        attachService.deleteAll(id, "church_weekly_attach");
        super.delete(id);
    }

    @Override
    public void updateHits(int hits, int id) {
        churchWeeklyMapper.updateHits(hits, id);
    }

    @Override
    public void updateHits(Weekly model) {
        churchWeeklyMapper.updateHits(model);
    }
}
