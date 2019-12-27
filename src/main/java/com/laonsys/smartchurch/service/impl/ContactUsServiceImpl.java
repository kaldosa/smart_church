package com.laonsys.smartchurch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.ContactUs;
import com.laonsys.smartchurch.mapper.ContactUsMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.MailService;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;

@Service("contactUsEntityService")
@Transactional
public class ContactUsServiceImpl extends AbstractGenericService<ContactUs> {
    @Autowired private ContactUsMapper contactUsMapper;

    @Autowired MailService mailService;
    
    @Override
    protected Mapper<ContactUs> getMapper() {
        return contactUsMapper;
    }
    
    @Override
    public void create(ContactUs model) {
        super.create(model);
        mailService.sendContactUs(model);
    }
}
