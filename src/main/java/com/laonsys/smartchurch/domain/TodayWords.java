package com.laonsys.smartchurch.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.laonsys.springmvc.extensions.model.AbstractEntity;

@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper=false)
public @Data class TodayWords extends AbstractEntity<TodayWords> {
    private String words;
    private String verse;
}
