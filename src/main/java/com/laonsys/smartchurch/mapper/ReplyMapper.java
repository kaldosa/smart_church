package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.BaseComments;

public interface ReplyMapper {
    BaseComments selectOne(@Param("id") int id, @Param("refTable") String refTable);
    
    List<BaseComments> selectList(@Param("refId") int refId, @Param("refTable") String refTable);
    
    void insert(BaseComments reply);
    
    void insertRefTable(@Param("refTable") String refTable, @Param("id") int id, @Param("refId") int refId);
    
    void update(BaseComments reply);
    
    void delete(int id);
    
    void deleteAll(@Param("refId") int refId, @Param("refTable") String refTable);
    
    void deleteAllByChurchId(int id);
    
    void deleteRefTable(@Param("refTable") String refTable, @Param("id") int id);
    
    void deleteRefAll(@Param("refId") int refId, @Param("refTable") String refTable);
}
