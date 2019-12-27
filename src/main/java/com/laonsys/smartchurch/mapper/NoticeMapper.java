package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.Notice;
import com.laonsys.springmvc.extensions.model.Attachment;

public interface NoticeMapper extends Mapper<Notice>{
	public int attachCount(int id);
	
	public Attachment selectAttachById(int id);

	public void saveNoticeAttach(@Param("attachments") List<Attachment> attachments);
	
	public void deleteNoticeAttachById(int id);
}
