package com.laonsys.smartchurch.service;

public interface RecaptchaService {
    boolean verify(String remoteip, String challenge, String response);
}
