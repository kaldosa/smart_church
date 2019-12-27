package com.laonsys.smartchurch.domain.church;

public enum ServiceStatus {
    WAITING("승인대기"), SERVICE("서비스"), STOP("서비스중지");

    private String value;

    private ServiceStatus(String value) {
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
