package com.laonsys.smartchurch.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.Authority;
import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.VerifyEmail;
import com.laonsys.smartchurch.mapper.UserMapper;
import com.laonsys.smartchurch.mapper.VerifyEmailMapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.JoinService;
import com.laonsys.smartchurch.service.MailService;
import com.laonsys.springmvc.extensions.utils.EncodingUtils;

@Service("joinService")
@Transactional
public class JoinServiceImpl implements JoinService {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private UserMapper userMapper;
    @Autowired private VerifyEmailMapper verifyEmailMapper;
    @Autowired private MailService mailService;
    @Autowired private AttachService attachService;
    
    @Override
    public StatusCode isAvailableEMail(JoinFormBean formBean) {
        
        User user = new User();
        user.setEmail(formBean.getEmail());
        User result = userMapper.selectUser(user);
        
        if(result != null) {
            return StatusCode.JOIN_ALREADY_USED;
        }
        
        return StatusCode.OK;
    }
    
    @Override
    public void sendMail(JoinFormBean formBean) {
        notNull(formBean);

        List<VerifyEmail> list = verifyEmailMapper.selectByEmail(formBean.getEmail());
        
        if(list != null && !list.isEmpty()) {
            for(VerifyEmail old : list) {
                verifyEmailMapper.delete(old.getId());
            }
        }
        
        VerifyEmail verifyEmail = new VerifyEmail();
        
        String code = RandomStringUtils.randomNumeric(8);
        
        verifyEmail.setCode(code);
        verifyEmail.setEmail(formBean.getEmail());

        verifyEmailMapper.insert(verifyEmail);

        if(logger.isDebugEnabled()) {
            logger.debug("email [{}], code [{}]", formBean.getEmail(), code);
        }

        mailService.sendVerifyEmail(formBean, verifyEmail);
    }
    
    @Override
    public StatusCode verifyCode(JoinFormBean formBean) {
        if(formBean == null) {
            throw new IllegalArgumentException("JoinFormBean must not null.");
        }

        String code = formBean.getCode();
        String email = formBean.getEmail();
        
        VerifyEmail that = verifyEmailMapper.select(code);
        
        if(that == null) {
            if(logger.isDebugEnabled()) {
                logger.debug("verifyEmail not found. : email [{}], code [{}]", email, code);
            }

            return StatusCode.JOIN_INVALID_CODE;
        }
        
        String thatCode = that.getCode();
        String thatEmail = that.getEmail();

        if(logger.isDebugEnabled()) {
            logger.debug("THIS :email [{}], code [{}]", email, code);
            logger.debug("THAT :email [{}], code [{}]", thatEmail, thatCode);
        }
        
//        long sentTime = that.getSentDate().getTime();
//        long currentTime = Calendar.getInstance().getTimeInMillis();
//        long spent = currentTime - sentTime;
//        
//        if(VerifyEmail.EXPIRED < spent) {
//            if(logger.isDebugEnabled()) {
//                logger.debug("Verify mail transfer time : {}", sentTime);
//                logger.debug("Current Time : {}", currentTime);
//                logger.debug("Expired Time {} > [{}]", spent, VerifyEmail.EXPIRED);
//            }
//
//            return StatusCode.JOIN_INVALID_CODE;
//        }
        
        if(!code.equals(thatCode) || !email.equals(thatEmail)) {
            return StatusCode.JOIN_INVALID_CODE;
        }
        
        verifyEmailMapper.delete(that.getId());
        
        return StatusCode.OK;
    }

    @Override
    public void joinUser(JoinFormBean formBean) {
        User user = new User();
        
        String email = formBean.getEmail();

        user.setEmail(email);
        user.setName(formBean.getName());
        user.setPassword(EncodingUtils.encodePassword(formBean.getPassword(), email));
        user.setContact(formBean.getContact());
        user.addAuthority(new Authority("ROLE_USER"));
        user.setEnabled(true);

        userMapper.addUser(user);
        userMapper.addAuthority(new Authority(user.getId(), "ROLE_USER"));
        
        // 사용자 클라우드 폴더 생성
        attachService.makeDirectory("user/" + user.getEmail());
        
        // 가입 환영 안내 메일 발송
        mailService.sendWelcome(user.getEmail(), user.getName());

        if (logger.isDebugEnabled()) {
            logger.debug("User register has been successful. {}", user);
        }
    }
}
