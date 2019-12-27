package com.laonsys.smartchurch.controller.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.springmvc.extensions.validation.groups.FindPw;

@Controller
@RequestMapping("/findUser")
public class FindUserController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired UserService userService;
    

    @RequestMapping(value = "/findPw", method = RequestMethod.GET)
    public String findIdPassword() {
        return "/auth/findPassword";
    }
    
    @RequestMapping(value = "/findPw", method = RequestMethod.POST)
    public String findIdPassword(@Validated({FindPw.class}) User user, BindingResult result, ModelMap model) {
        
        if(result.hasErrors()) {
            model.put("statusCode", StatusCode.DATA_BINDING_ERROR);
            return "/auth/findPassword";
        }
        
        User findUser = userService.findByEmail(user.getEmail(), user.getName());
        
        if(findUser == null) {
            model.put("statusCode", StatusCode.USER_NOT_FOUND);
            return "/auth/findPassword";
        }
        
        model.put("email", user.getEmail());
        model.put("name", user.getName());
        return "/auth/sendPwMail";
    }
    
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public @ResponseBody StatusCode sendMail(String email, String name, ModelMap model) {
        return userService.sendPasswordEmail(email, name);
    }
}
