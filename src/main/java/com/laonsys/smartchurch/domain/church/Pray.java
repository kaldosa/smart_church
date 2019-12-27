package com.laonsys.smartchurch.domain.church;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.Postings;

@JsonSerialize(using = PraySerializer.class)
public class Pray extends Postings {
    private int churchId;
    
    private List<BaseComments> comments;
   
    public int getChurchId() {
        return churchId;
    }
    
    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }
    
    public List<BaseComments> getComments() {
        return comments;
    }
    
    public void setComments(List<BaseComments> comments) {
        this.comments = comments;
    }
}
