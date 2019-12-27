package com.laonsys.smartchurch.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Extended;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper=false)
public @Data class Postings extends AbstractEntity<Postings> {

    @NotEmpty (groups = {Create.class, Update.class}, message = "{NotEmpty.subject}")
    @Size(max=50, groups = {Create.class, Update.class})
    private String subject;

    @NotEmpty (groups = {Create.class, Update.class}, message = "{NotEmpty.contents}")
    private String contents;

    private long writerId;
    
    private User user;

    @NotNull(groups=Extended.class)
    @Size(min=2, max=10)
    private String writerName;

    private int hits;

    private int commentsCount;
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject; //StringUtils.replaceHTMLTag(subject);
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

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getCommentsCount() {
        return commentsCount;
    }
    
    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
