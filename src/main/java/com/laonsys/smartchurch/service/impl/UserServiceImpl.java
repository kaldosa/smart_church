package com.laonsys.smartchurch.service.impl;

import static org.springframework.util.Assert.notNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.Authority;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.VerifyEmail;
import com.laonsys.smartchurch.mapper.UserMapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.MailService;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.smartchurch.utiles.CommonUtils;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.utils.EncodingUtils;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AttachService attachService;

    @Autowired
    private MailService mailService;

    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
//    @Autowired
//    private ProviderManager authenticationManager;
    
    @Override
    public User findById(int userId) {
        return userMapper.findById(userId);
    }

    @Override
    public User findByEmail(String email, String name) {
        return userMapper.findByEmail(email, name, null);
    }

    @Override
    public User selectUser(int id, String email, String name, String password) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        if(password != null) {
            String encrypt = EncodingUtils.encodePassword(password, email);
            user.setPassword(encrypt);
        }
        return userMapper.selectUser(user);
    }
    
    @Override
    public User selectUser(User user) {
        if(user.getPassword() != null) {
            String encrypt = EncodingUtils.encodePassword(user.getPassword(), user.getEmail());
            user.setPassword(encrypt);
        }
        return userMapper.selectUser(user);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return selectUser(0, username, null, null);
    }

    @Override
    public void addAuthority(Authority authority) {
        userMapper.addAuthority(authority);
    }

    @Override
    public void delAuthority(Authority authority) {
        userMapper.delAuthority(authority);        
    }
    
    @Override
    public int addAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return userMapper.addAuthorities(authorities);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User addUser(User user) {
        User clone = CommonUtils.cloneBean(user);
        clone.setPassword(EncodingUtils.encodePassword(user.getPassword(), user.getEmail()));

        userMapper.addUser(clone);

        Collection<Authority> userAuthorities = (Collection<Authority>) clone.getAuthorities();

        for (Authority authority : userAuthorities) {
            authority.setUserId(clone.getId());
        }

        userMapper.addAuthorities(userAuthorities);

        return clone;
    }

    @Override
    public void updateUser(User user, User activeUser) throws IOException {

        user.setId(activeUser.getId());
        user.setEmail(activeUser.getEmail());

        String userId = user.getEmail();
        String userPw = user.getPassword();

        if (userPw != null && !"".equals(userPw)) {
            String encodingPw = EncodingUtils.encodePassword(userPw, userId);
            user.setPassword(encodingPw);
            activeUser.setPassword(encodingPw);
            logger.debug("{}", "User {" + user.getEmail() + "} change password : " + activeUser);
        }
        else {
            user.setPassword(activeUser.getPassword());
        }

//        if(user.getName() != null && !"".equals(user.getName())) {
//            activeUser.setName(user.getName());
//        }
//        
//        if(user.getContact() != null && !"".equals(user.getContact())) {
//            activeUser.setContact(user.getContact());
//        }
        
        userMapper.update(user);
        
        MultipartFile file = user.getUpload();

        if(file != null && !file.isEmpty()) {
            attachService.deleteAll(user.getId(), "user_photo");
            Attachment photo = null;
            try {
                File output = (File) resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                        file.getInputStream());
                
                if(output != null) {
                    photo = attachService.insert("/user/" + user.getEmail(), output, "user_photo", user.getId());
                }
            }
            catch (IOException e) {
                throw new ServiceException("failed to save image in [user_photo].");
            }
            
            activeUser.setPhoto(photo);
        }
        
        // update 갱신
        User updatedPrincipal = userMapper.findById(user.getId());
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(updatedPrincipal, updatedPrincipal.getPassword(), updatedPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userMapper.findByEmail(email, null, null);
        attachService.deleteAll(user.getId(), "user_photo");
        userMapper.deleteUserByEmail(email);
    }

    @Override
    public void deleteUsers(List<String> users) {
        if (users == null || users.isEmpty())
            return;

        for (String email : users) {
            deleteUserByEmail(email);
        }
    }

    @Override
    public int totalCount() {
        return userMapper.totalCount();
    }

    @Override
    public int count(QueryParam queryParam) {
        return userMapper.count(queryParam);
    }
    
    @Override
    public List<User> list(QueryParam queryParam) {
        List<User> list = userMapper.list(queryParam);
        int totalCount = count(queryParam);
        queryParam.getPaginate().paging(totalCount);
        return list;
    }

    @Override
    public void sendConfirmEmailCode(JoinFormBean user) throws Exception {
        notNull(user);

        VerifyEmail oldConfirmData = userMapper.selectConfirmDataByEmail(user.getEmail());
        
        if(oldConfirmData != null) {
            userMapper.deleteConfirmData(oldConfirmData.getId());
        }
        
        VerifyEmail confirmData = new VerifyEmail();
        
        String code = RandomStringUtils.randomAlphanumeric(8);
        
        confirmData.setCode(code);
        confirmData.setEmail(user.getEmail());

        userMapper.insertConfirmData(confirmData);

        if(logger.isDebugEnabled()) {
            logger.debug("email [{}], code [{}]", user.getEmail(), code);
        }

        mailService.sendVerifyEmail(user, confirmData);
    }
    
    @Override
    public void confirmEmail(User user, boolean isMobile) throws Exception {
//        notNull(user);
//        
//        ConfirmData oldConfirmData = userMapper.selectConfirmDataByUserId(user.getId());
//        
//        if(oldConfirmData != null) {
//            userMapper.deleteConfirmData(oldConfirmData.getId());
//        }
//        
//        String code = (isMobile) ? RandomStringUtils.randomAlphanumeric(8):RandomStringUtils.randomAlphabetic(32);
//
//        ConfirmData confirmData = new ConfirmData();
//        confirmData.setCode(code);
//        confirmData.setEmail(user.getId());
//        confirmData.setExpired(Calendar.getInstance().getTime());
//        
//        userMapper.insertConfirmData(confirmData);
//
//        String confirmUrl = (isMobile) ? code : hostUrl + code;
//        mailService.confirmEmail(user, confirmUrl, isMobile);
    }
    
    @Override
    public Response enableUser(String code) {
//        Response response = new Response();
//        
//        ConfirmData confirmData = userMapper.selectConfirmData(code);
//        
//        if(confirmData == null) {
//            response.setStatusCode(StatusCode.JOIN_INVALID_USER);
//            return response;
//        }
//        
//        long transferTime = confirmData.getExpired().getTime();
//        long currentTime = Calendar.getInstance().getTimeInMillis();
//        long remain = currentTime - transferTime;
//        long expiredTime = (10 * 60 * 1000) ; //(60 * 60 * 24) * 1000;
//        logger.debug("Authentication mail transfer time : {}", transferTime);
//        logger.debug("Current Time : {}", currentTime);
//        logger.debug("Expired Time {} > {}", currentTime - transferTime, expiredTime);
//        
//        if(expiredTime > remain) {
//            userMapper.deleteConfirmData(confirmData.getId());
//            response.setStatusCode(StatusCode.JOIN_EXPIRED_MAIL);
//            return response;            
//        }
//        
//        User user = userMapper.findById(confirmData.getEmail());
//        
//        // FIXME 회원가입후 이메일 인증을 하지 않은 상태에서 이메일 인증 URL를 클릭했는데 사용자 정보가 없을 수 있는가?
//        if(user == null) {
//            notNull(user);
//        }
//        
//        User update = new User();
//        update.setId(user.getId());
//        update.setEnabled(true);
//        
//        userMapper.update(update);
//        
//        userMapper.deleteConfirmData(confirmData.getId());
//        
//        response.putData("email", user.getEmail());
//        return response;
        return null;
    }
    
    @Override
    public StatusCode sendPasswordEmail(String email, String name) {
        User user = selectUser(0, email, name, null);
        if(user == null) {
            return StatusCode.USER_NOT_FOUND;
        }

        String password = RandomStringUtils.randomAlphanumeric(8);
        String encrypt = EncodingUtils.encodePassword(password, user.getEmail());
        
        User update = new User();
        update.setId(user.getId());
        update.setEmail(user.getEmail());
        update.setName(user.getName());
        update.setPassword(encrypt);

        userMapper.update(update);
        
        update.setPassword(password);
        mailService.sendPassword(update);

        return StatusCode.OK;
    }
}
