package com.laonsys.smartchurch.domain;

import com.laonsys.springmvc.extensions.model.AbstractEntity;

public class Posts extends AbstractEntity<Posts> {

	private String subject;

	private String contents;

	private long writerId;

	private String writerName;

	private int viewCount;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public long getWriterId() {
		return writerId;
	}

	public void setWriterId(long writerId) {
		this.writerId = writerId;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
}
