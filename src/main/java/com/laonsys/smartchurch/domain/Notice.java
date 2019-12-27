package com.laonsys.smartchurch.domain;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Attachment;

public class Notice extends AbstractEntity<Notice> {
	
    @NotEmpty
    @Size(max=50)
	private String subject;
	
    @NotEmpty
	private String content;
	
	private NoticeType noticeType;
	
	private List<Attachment> attachments;
	
	private List<MultipartFile> multipartFiles;
	
	private long hits;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NoticeType getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }
	
	public void setMultipartFiles(List<MultipartFile> multipartFiles) {
        this.multipartFiles = multipartFiles;
    }
	
	public long getHits() {
		return hits;
	}
	
	public void setHits(long hits) {
		this.hits = hits;
	}
}
