package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface ChurchMetaMapper {
    int count(QueryParam searchCriteria);
    List<ChurchMeta> selectList(QueryParam queryParam);
    ChurchMeta selectOne(int id);
    void insert(ChurchMeta churchMeta);
    void update(ChurchMeta churchMeta);
    void delete(int id);
    
    boolean isExist(ChurchMeta churchMeta);
}
