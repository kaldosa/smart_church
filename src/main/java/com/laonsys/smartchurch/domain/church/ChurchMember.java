package com.laonsys.smartchurch.domain.church;

import lombok.Data;
import lombok.ToString;

import com.laonsys.smartchurch.domain.User;

@ToString(includeFieldNames = true)
public @Data class ChurchMember {
    private int church;
    private User member;
}
