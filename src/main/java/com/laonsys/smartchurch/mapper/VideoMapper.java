package com.laonsys.smartchurch.mapper;

import com.laonsys.springmvc.extensions.model.Attachment;

public interface VideoMapper {
    Attachment selectOne(int id);
    
    void insert(Attachment attachment);
    
    void delete(int id);
}
