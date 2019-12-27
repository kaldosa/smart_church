package com.laonsys.smartchurch.domain.church;

public enum EventRepeatType {
    DAY("매일"), WEEK("매주"), MONTH("매월"), YEAR("매년");
    
    private String value;
    
    private EventRepeatType(String value) {
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
