package com.laonsys.smartchurch.domain;

import java.util.Calendar;
import java.util.Date;

import com.laonsys.springmvc.extensions.model.BaseDomain;

public class Comments extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2344311571813144284L;

	private int id;

	private int postsId;

	private int parentId;

	private String comments;

	private String sortKey = "0"; // 정렬 계산을 위한 default 값

	private int level;

	private long writerId;

	private Date writtenDate = Calendar.getInstance().getTime();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostsId() {
		return postsId;
	}

	public void setPostsId(int postsId) {
		this.postsId = postsId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getWriterId() {
		return writerId;
	}

	public void setWriterId(long writerId) {
		this.writerId = writerId;
	}

	public Date getWrittenDate() {
		return writtenDate;
	}

	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}
}