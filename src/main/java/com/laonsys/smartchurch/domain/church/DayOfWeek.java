package com.laonsys.smartchurch.domain.church;

public enum DayOfWeek {
    EVERYDAY("매일"), SUNDAY("주일"), MONDAY("월요일"), TUESDAY("화요일"), WEDNESDAY("수요일"), THURSDAY("목요일"), FRIDAY("금요일"), SATURDAY("토요일");
    
    private String value;
    
    private DayOfWeek(String value) {
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
