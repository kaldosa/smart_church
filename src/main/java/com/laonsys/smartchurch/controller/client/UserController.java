package com.laonsys.smartchurch.controller.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.smartchurch.utiles.CommonUtils;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.utils.EncodingUtils;

@Controller
@RequestMapping("/auth")
public class UserController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired UserService userService;
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/confirmPw", method = RequestMethod.GET)
    public String getConfirmPassword(@RequestParam(value = "error", required = false) boolean error, 
                                     ModelMap model, 
                                     @LogonUser User activeUser) {
        
        logger.debug("login user id [{}]", activeUser.getId());
        
        if (error == true) {
        
            model.put("error", "You have entered an invalid username or password!");
        }
        else {
            model.put("error", "");
        }

        User clone = CommonUtils.cloneBean(activeUser);
        clone.setPassword(null);
        
        model.put("user", clone);
        
        return "/auth/confirmPw";
    }
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/confirmPw", method = RequestMethod.POST)
    public String postConfirmPassword(@ModelAttribute User user,
                                      @LogonUser User activeUser) {
        
        String logonUserId = activeUser.getEmail();
        String loginUserPw = activeUser.getPassword();
        String formUserId = user.getEmail();
        String formUserPw = user.getPassword();
        String encodePw = EncodingUtils.encodePassword(formUserPw, formUserId);

        boolean valid = true;
        
        valid = formUserId.equals(logonUserId);
        valid = encodePw.equals(loginUserPw);
        
        logger.debug("{}", "activeUser PW: " + loginUserPw);
        logger.debug("{}", "form PW: " + encodePw);
        
        if(!valid) {
            return "redirect:/auth/confirmPw?error=true";
        }
        
        return "redirect:/auth/modifyUser";
    }
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/modifyUser", method = RequestMethod.GET)
    public String getMyInfo(Model model, @LogonUser User activeUser) {
        User user = userService.findById(activeUser.getId());
        model.addAttribute("user", user);
        return "/auth/modifyUser";
    }
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/modifyUser", method = RequestMethod.PUT)
    public String putMyInfo(@ModelAttribute User user, BindingResult result, @LogonUser User activeUser) throws IOException {

        logger.debug("login user id [{}]", activeUser.getId());
        
        if(result.hasErrors()) {
            return "/auth/modifyUser";
        }
        
        userService.updateUser(user, activeUser);
        
        return "redirect:/";
    }
}
