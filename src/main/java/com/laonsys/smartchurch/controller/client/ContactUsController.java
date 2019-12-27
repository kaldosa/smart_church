package com.laonsys.smartchurch.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.ContactUs;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;

@Controller
@SessionAttributes("entity")
public class ContactUsController {
    
    @Autowired GenericService<ContactUs> contactUsEntityService;
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/contactUs", method = RequestMethod.GET)
    public String getContactUs(Model model) {
        model.addAttribute("entity", new ContactUs());
        return "/mypage/contactUs";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/contactUs", method=RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody Map<String, Object> ajaxPost(@Validated({Create.class}) @ModelAttribute ContactUs entity,
                                        BindingResult result,
                                        @LogonUser User logon,
                                        SessionStatus status) {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuffer buffer = new StringBuffer();
            for(ObjectError error : errors) {
                buffer.append(error.getDefaultMessage() + "\r\n");
            }
            
            response.put("result", false);
            response.put("msg", buffer.toString());
        } else {
            entity.setUser(logon);
            contactUsEntityService.create(entity);
            status.setComplete();
            
            response.put("result", true);
            response.put("msg", "문의 메일이 발송되었습니다.");
        }
        return response;
    }
}
