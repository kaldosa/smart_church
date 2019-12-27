package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface ChurchServiceMapper {
    int count(QueryParam searchCriteria);
    List<OurChurch> selectList(QueryParam queryParam);
    List<OurChurch> selectRecent();
    OurChurch selectOne(int id);
    
    void insert(OurChurch ourChurch);
    void update(OurChurch ourChurch);
    void delete(int id);
    
    boolean isAvailablePath(String path);
    boolean isAvailableApplicant(int id);
}
