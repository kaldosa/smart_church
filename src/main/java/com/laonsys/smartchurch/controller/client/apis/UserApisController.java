package com.laonsys.smartchurch.controller.client.apis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.JoinFormBean.Agree;
import com.laonsys.smartchurch.domain.JoinFormBean.Join;
import com.laonsys.smartchurch.domain.JoinFormBean.Verify;
import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.service.JoinService;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.validation.groups.FindPw;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis")
public class UserApisController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired UserService userService;
    @Autowired JoinService joinService;
    
    @RequestMapping(value = "/usersupport/checkMail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody StatusCode postCheckEmail(@Validated(Agree.class) @ModelAttribute JoinFormBean joinFormBean, 
                                                   BindingResult result, Device device) throws Exception {
        if(result.hasErrors()) {
            logger.debug("[/apis/usersupport/checkMail] data binding error.");
            logger.debug("{}", result);
            return StatusCode.DATA_BINDING_ERROR;
        }
        StatusCode statusCode = joinService.isAvailableEMail(joinFormBean);
        if(statusCode == StatusCode.OK) {
            joinService.sendMail(joinFormBean);
            return StatusCode.OK.setAnother("이메일 확인을 위한 메일을 발송하였습니다.\n 이메일을 확인하여 인증코드를 입력하세요.");
        }
        return statusCode;
    }
    
    @RequestMapping(value = "/usersupport/verifyCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody StatusCode postCheckCode(@Validated(Verify.class) @ModelAttribute JoinFormBean joinFormBean, 
                                                  BindingResult result, Device device) throws Exception {
        if(result.hasErrors()) {
            logger.debug("[/apis/usersupport/verifyCode] data binding error.");
            logger.debug("{}", result);            
            return StatusCode.DATA_BINDING_ERROR;
        }
        return joinService.verifyCode(joinFormBean);
    }
    
    @RequestMapping(value = "/usersupport/join", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody StatusCode postJoin(@Validated({Join.class}) @ModelAttribute JoinFormBean joinFormBean, BindingResult result, Device device) throws Exception {
        
        if(result.hasErrors()) {
            logger.debug("[/apis/usersupport/join] data binding error.");
            logger.debug("{}", result);            
            return StatusCode.DATA_BINDING_ERROR;
        }
        
        try{
            joinService.joinUser(joinFormBean);
        } catch(Exception e) {
            logger.debug("Failed to join user. {}", joinFormBean);
            return StatusCode.JOIN_FAILED;
        } 

        return StatusCode.OK;
    }
    
    @RequestMapping(value = "/usersupport/find/password", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody StatusCode findPassword(@Validated({FindPw.class}) @ModelAttribute User user, BindingResult result, Device device) throws Exception {
        
        if(result.hasErrors()) {
            logger.debug("[/apis/usersupport/find/password] data binding error.");
            logger.debug("{}", result);
            return StatusCode.DATA_BINDING_ERROR;
        }
        
        return userService.sendPasswordEmail(user.getEmail(), user.getName());
    }

    @RequestMapping(value = "/user/getuid", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody Response getUid(String email, @LogonUser User activeUser, Device device) throws Exception {
        Response response = new Response();
        
        if(activeUser == null || !email.equals(activeUser.getEmail())) {
            logger.debug("[/apis/user/getuid] permission denied. {} [{}]", email, activeUser.getEmail());
            response.setStatusCode(StatusCode.USER_PERMISSION_DENIED);
            return response;
        }
        
        response.putData("user", userService.findById(activeUser.getId()));
        return response;
    }
    
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public @ResponseBody StatusCode updateUser(@PathVariable int id,
                                               @Validated({Update.class}) @ModelAttribute User user, 
                                               BindingResult result, 
                                               Device device, 
                                               @LogonUser User activeUser) throws Exception {
        
        if(result.hasErrors()) {
            logger.debug("[/apis/user/{}] data binding error.", id);
            logger.debug("{}", result);            
            return StatusCode.DATA_BINDING_ERROR;
        }
        
        String activeUserEmail = activeUser.getEmail();
        String userEmail = user.getEmail();
        
        if(!userEmail.equals(activeUserEmail)) {
            return StatusCode.USER_PERMISSION_DENIED;
        }
        
        userService.updateUser(user, activeUser);
        
        return StatusCode.OK;
    }
}
