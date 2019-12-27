package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.springmvc.extensions.model.Attachment;

public interface AttachmentMapper {
    Attachment selectOne(int id);
    
    List<Attachment> selectList(@Param("refId") int refId, @Param("refTable") String refTable, @Param("page") int page, @Param("rowCount") int rowCount);
    
    List<Attachment> selectAllByParentPath(String parentPath);
    
    void insert(Attachment attachment);
    
    void insertList(@Param("attachments") List<Attachment> attachments);
    
    void insertRefTable(@Param("refTable") String refTable, @Param("id") int id, @Param("refId") int refId);
    
    void delete(int id);
    
    void deleteAll(@Param("refId") int refId, @Param("refTable") String refTable);
    
    void deleteAllByParentPath(String parentPath);
    
    void deleteRefTable(@Param("refTable") String refTable, @Param("id") int id);
    
    void deleteRefAll(@Param("refId") int refId, @Param("refTable") String refTable);
    
    Long totalAttachSize(@Param("path") String path, @Param("type") boolean type);
}
