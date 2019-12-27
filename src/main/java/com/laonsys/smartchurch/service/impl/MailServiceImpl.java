package com.laonsys.smartchurch.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.laonsys.smartchurch.domain.ContactUs;
import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.VerifyEmail;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.MailService;
import com.laonsys.springmvc.extensions.utils.VelocityMerger;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityMerger velocityMerger;

    private @Value("#{envvars['mail.username']}") String from;
    
    private transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void sendPassword(final User user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setTo(user.getEmail());
                message.setFrom("Smart Church <" + from + ">");
                message.setSubject("[Smart Church] 비밀번호 찾기");
                
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("user", user);
                
                String text = velocityMerger.merge("passwordEmail.vm", model); //VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "passwordEmail.vm", model);
                
                message.setText(text, true);
            }
        };
        
        try {
            mailSender.send(preparator);
        }
        catch (MailException e) {
            logger.info("Failed to send confirm email : {}", e.getMessage());
        }
    }
    
    @Override
    public void sendWelcome(final String email, final String name) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setTo(email);
                message.setFrom("Smart Church <" + from + ">");
                message.setSubject("[Smart Church] Smart Church 가입을 환영합니다.");

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("name", name);
                model.put("email", email);

                String text = velocityMerger.merge("welcomeEmail.vm", model);

                message.setText(text, true);
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException e) {
            logger.error("Failed to send welcome email : {}", e.getMessage());
        }
    }
    
    @Override
    public void sendVerifyEmail(final JoinFormBean user, final VerifyEmail verifyEmail) {
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setTo(user.getEmail());
                message.setFrom("Smart Church <" + from + ">");
                message.setSubject("[Smart Church] 이메일 확인 메일입니다.");
                
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("user", user);
                model.put("verifyEmail", verifyEmail);
                
                String text = velocityMerger.merge("verifyEmail.vm", model);//VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "verifyEmail.vm", model);
                
                message.setText(text, true);
            }
        };
        
        try {
            mailSender.send(preparator);
        }
        catch (MailException e) {
            logger.info("Failed to send confirm email : {}", e.getMessage());
        }
    }
    
    @Override
    public void transferApprovalMail(final OurChurch ourChurch) {
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setTo(ourChurch.getApplicant().getEmail());
                message.setFrom("Smart Church <" + from + ">");
                message.setSubject("[Smart Church] 우리교회 서비스 승인 메일.");
                
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("ourChurch", ourChurch);
                
                String text = velocityMerger.merge("approvalEmail.vm", model);
                message.setText(text.toString(), true);
            }
        };
        
        try {
            mailSender.send(preparator);
        }
        catch (MailException e) {
            logger.info("Failed to transfer approval email. : {}", e.getMessage());
        }
    }

    @Override
    public void sendContactUs(final ContactUs contactUs) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setTo(from);
                message.setFrom("Smart Church <" + from + ">");
                message.setSubject("[Smart Church] " + contactUs.getUser().getEmail() + "님의 문의 메일.");
                
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("contactUs", contactUs);
                
                String text = velocityMerger.merge("contactUs.vm", model);
                message.setText(text.toString(), true);
            }
        };
        
        try {
            mailSender.send(preparator);
        }
        catch (MailException e) {
            logger.info("Failed to transfer contact us email. : {}", e.getMessage());
        }
    }
}
