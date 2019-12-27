package com.laonsys.smartchurch.service;

import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.StatusCode;

public interface JoinService {
    StatusCode isAvailableEMail(JoinFormBean formBean);
    void sendMail(JoinFormBean formBean);
    StatusCode verifyCode(JoinFormBean formBean);
    void joinUser(JoinFormBean formBean);
}
