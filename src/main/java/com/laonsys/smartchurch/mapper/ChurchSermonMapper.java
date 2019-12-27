package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.church.Sermon;
import com.laonsys.springmvc.extensions.model.Attachment;

public interface ChurchSermonMapper extends Mapper<Sermon>{
    public Sermon lastOne(int id);
    public void addAttachRefTable(@Param("id") int id, @Param("attachments") List<Attachment> attachments);
    public void deleteAttachRefTable(int id);
}
