package com.laonsys.smartchurch.controller.client;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.smartchurch.service.ChurchMetaService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.validation.groups.Create;

@Controller
@RequestMapping("/application-service")
@SessionAttributes("churchMeta")
@Slf4j
public class RegisterChurchController {
    
    @Autowired private ChurchMetaService churchMetaService;

    @ExceptionHandler(HttpSessionRequiredException.class)
    public String handleException(HttpSessionRequiredException ex) {
        return "redirect:/application-service";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterForm(Model model) {
        model.addAttribute("churchMeta", new ChurchMeta());
        return "/main/registerChurch";
    }
    
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegisterForm(@Validated(Create.class) @ModelAttribute ChurchMeta churchMeta, 
                                   BindingResult result, SessionStatus status) {

        if(result.hasErrors()) {
            return "/main/registerChurch";
        }
        
        log.debug("{}", churchMeta);
        try {
            churchMetaService.insert(churchMeta);
        } catch (ServiceException e) {
            result.reject(null, "동일한 정보를 가진 교회가 이미 존재합니다.");
            return "/main/registerChurch";
        }
        
        status.setComplete();
        return "redirect:/application-service";
    }
}
