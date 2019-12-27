package com.laonsys.smartchurch.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper=false)
public @Data class ContactUs extends Postings {
    private boolean isAnswer;
    private ContactReply reply;
}
