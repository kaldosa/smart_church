package com.laonsys.smartchurch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.church.Message;
import com.laonsys.smartchurch.mapper.ChurchMessageMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;

@Service("churchMessageEntityService")
@Transactional
public class ChurchMessageEntityServiceImpl extends AbstractGenericService<Message> {

    @Autowired private ChurchMessageMapper churchMessageMapper;
    
    @Override
    protected Mapper<Message> getMapper() {
        return churchMessageMapper;
    }
}
