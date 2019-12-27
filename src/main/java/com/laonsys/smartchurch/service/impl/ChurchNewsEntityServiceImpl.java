package com.laonsys.smartchurch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.ChurchNews;
import com.laonsys.smartchurch.mapper.ChurchNewsMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;

@Service("churchNewsEntityService")
@Transactional
public class ChurchNewsEntityServiceImpl extends AbstractGenericService<ChurchNews> implements UpdateHitsService<ChurchNews> {

    @Autowired private ChurchNewsMapper churchNewsMapper;
    @Autowired private AttachService attachService;
    
    @Override
    protected Mapper<ChurchNews> getMapper() {
        return churchNewsMapper;
    }
    
    @Override
    public void create(ChurchNews news) {
        super.create(news);
        MultipartFile file = news.getUpload();
        
        if(file != null && !file.isEmpty()) {
            attachService.insert("/" + news.getPath(), file, "church_news_attach", news.getId());
        }
    }
    
    @Override
    public void update(ChurchNews news) {
        super.update(news);

        MultipartFile file = news.getUpload();

        if (file != null && !file.isEmpty()) {
            if(news.getAttach() != null) {
                attachService.delete(news.getAttach().getId(), "church_news_attach");
            }
            attachService.insert("/" + news.getPath(), file, "church_news_attach", news.getId());
        }
    }
    
    @Override
    public void delete(int id) {
        attachService.deleteAll(id, "church_news_attach");
        super.delete(id);
    }

    @Override
    public void updateHits(int hits, int id) {
        churchNewsMapper.updateHits(hits, id);
    }

    @Override
    public void updateHits(ChurchNews model) {
        churchNewsMapper.updateHits(model);
    }
}
