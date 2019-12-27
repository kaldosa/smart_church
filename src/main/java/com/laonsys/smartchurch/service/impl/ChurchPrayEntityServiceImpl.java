package com.laonsys.smartchurch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.church.Pray;
import com.laonsys.smartchurch.mapper.ChurchPrayMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.ChurchPrayService;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;

@Service("churchPrayEntityService")
@Transactional
public class ChurchPrayEntityServiceImpl extends AbstractGenericService<Pray> implements ChurchPrayService, UpdateHitsService<Pray> {

    @Autowired private ChurchPrayMapper churchPrayMapper;
    @Autowired private ReplyService replyService;
    
    @Override
    protected Mapper<Pray> getMapper() {
        return churchPrayMapper;
    }
    
    @Override
    public Pray selectOne(int id) {
        Pray pray = super.selectOne(id);
        
        if(pray != null) {
            List<BaseComments> comments = replyService.selectList(pray.getId(), "church_pray_comments");
            pray.setComments(comments);
        }
        
        return pray;
    }
    
    @Override
    public void delete(int id) {
        replyService.deleteAll(id, "church_pray_comments");
        super.delete(id);
    }
    
    @Override
    public void addComment(BaseComments comments) {
        replyService.insert(comments, "church_pray_comments");
    }
    
    @Override
    public void deleteComment(int id) {
        replyService.delete(id, "church_pray_comments", null);
    }

    @Override
    public void updateHits(int hits, int id) {
        churchPrayMapper.updateHits(hits, id);
    }

    @Override
    public void updateHits(Pray model) {
        churchPrayMapper.updateHits(model);
    }
}
