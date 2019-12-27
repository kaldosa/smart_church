package com.laonsys.smartchurch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.Notice;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.mapper.NoticeMapper;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;

@Service("noticeEntityService")
@Transactional
public class NoticeEntityServiceImpl extends AbstractGenericService<Notice> implements UpdateHitsService<Notice> {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    protected Mapper<Notice> getMapper() {
        return noticeMapper;
    }

    @Override
    public void updateHits(int hits, int id) {
        noticeMapper.updateHits(hits, id);
    }

    @Override
    public void updateHits(Notice model) {
        noticeMapper.updateHits(model);
    }
}
