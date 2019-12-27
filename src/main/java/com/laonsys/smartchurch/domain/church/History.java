package com.laonsys.smartchurch.domain.church;

import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.laonsys.springmvc.extensions.model.JodaTimeSerializer;

public class History {
    private int id;

    private int churchId;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonSerialize(using = JodaTimeSerializer.class)
    private DateTime date;
    
    @NotEmpty
    @Size(max = 128)
    private String contents;

    private int sort;

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

    public DateTime getDate() {
        return date;
    }
    
    public void setDate(DateTime date) {
        this.date = date;
    }
    
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getSort() {
        return sort;
    }
    
    public void setSort(int sort) {
        this.sort = sort;
    }
}
