package com.laonsys.smartchurch.domain;

public enum BoardType {
    NOTICE("공지사항"), QNA("질문과답변");
    
    private String value;
    
    private BoardType(String value) {
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
