package com.laonsys.smartchurch.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


@JsonSerialize(using = CommentSerializer.class)
public class BaseComments implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6663622925023779236L;

    private int id;
    
    private int churchId;
    
    private int postingsId;

    @Length(min=1, max=140)
    private String comments;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date createdDate = Calendar.getInstance().getTime();

    private User user;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChurchId() {
        return churchId;
    }
    
    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }
    
    public int getPostingsId() {
        return postingsId;
    }

    public void setPostingsId(int postingsId) {
        this.postingsId = postingsId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
