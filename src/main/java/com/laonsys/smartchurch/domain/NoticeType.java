package com.laonsys.smartchurch.domain;

public enum NoticeType {
    COMMON("일반"), BUSINESS("비지니스");

    private String value;

    private NoticeType(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
