package com.laonsys.smartchurch.controller.client;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchMetaService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.utils.AjaxUtils;

@Controller
@RequestMapping("/application-service")
@SessionAttributes("entity")
@Slf4j
public class SignUpChurchController {

    @Autowired private ChurchService churchService;
    @Autowired private ChurchMetaService churchMetaService;
    
    @ModelAttribute
    public void ajaxAttribute(WebRequest request, Model model) {
        model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    public String handleException(HttpSessionRequiredException ex) {
        return "redirect:/application-service";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupForm(@RequestParam int id, Model model) {
        log.debug("{}", id);
        
        ChurchMeta churchMeta = churchMetaService.selectOne(id);
        
        OurChurch ourChurch = new OurChurch();
        ourChurch.setChurchMeta(churchMeta);
        model.addAttribute("entity", ourChurch);
        
        return "/main/signupChurch";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postSignupForm(@ModelAttribute("entity") OurChurch ourChurch,BindingResult result, @LogonUser User loginUser, SessionStatus status) {

        ourChurch.setApplicant(loginUser);
        churchService.application(ourChurch, result);
        
        if(result.hasErrors()) {
            return "/main/signupChurch";
        }
        
        status.setComplete();
        
        return "redirect:/application-service/complete";
    }
    
    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    public String completeSignup() {
        return "/main/completeSignupChurch";
    }
}
