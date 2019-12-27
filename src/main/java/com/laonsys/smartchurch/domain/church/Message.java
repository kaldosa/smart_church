package com.laonsys.smartchurch.domain.church;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import com.laonsys.smartchurch.domain.Postings;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@JsonSerialize(using=MessageSerializer.class)
public class Message extends Postings {
    private int churchId;

    @NotEmpty(groups={Create.class, Update.class})
    @Size(max = 128, groups={Create.class, Update.class})
    private String subTitle;
    
    public int getChurchId() {
        return churchId;
    }
    
    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }
    
    public String getSubTitle() {
        return subTitle;
    }
    
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
