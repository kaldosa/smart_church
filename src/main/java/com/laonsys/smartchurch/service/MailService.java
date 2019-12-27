package com.laonsys.smartchurch.service;

import com.laonsys.smartchurch.domain.ContactUs;
import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.VerifyEmail;
import com.laonsys.smartchurch.domain.church.OurChurch;

public interface MailService {
    void sendPassword(final User user);
    void sendWelcome(final String email, final String name);
    void sendVerifyEmail(final JoinFormBean user, final VerifyEmail confirmData);
    void transferApprovalMail(final OurChurch ourChurch);
    void sendContactUs(final ContactUs contactUs);
}
