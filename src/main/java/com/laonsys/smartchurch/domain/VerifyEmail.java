package com.laonsys.smartchurch.domain;

import java.util.Date;

public class VerifyEmail {
    public static long EXPIRED = 10 * 60 * 1000; // 10분
    
    private int id;

    private String code;

    private String email;

    private Date sentDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}
