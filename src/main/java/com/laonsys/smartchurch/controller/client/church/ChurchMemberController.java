package com.laonsys.smartchurch.controller.client.church;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.ChurchMember;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchMemberService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.smartchurch.service.RecaptchaService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;

@Controller
@RequestMapping("/church/{churchId}")
public class ChurchMemberController {
    
    @Autowired private ChurchService churchService;
    @Autowired private RecaptchaService recaptchaService;
    @Autowired private ChurchMemberService churchMemberService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int churchId, Model model) {
        OurChurch ourChurch = churchService.selectOne(churchId);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String getNonMember(@PathVariable int churchId, @RequestParam(required = false) String url, Model model) {
        model.addAttribute("url", url);
        return "/church/access-denied";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/join-member", method = RequestMethod.GET)
    public String getJoinMember(@PathVariable int churchId, @RequestParam(required = false) String url, Model model) {
        return "/church/join-member";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/join-member", method = RequestMethod.POST)
    public String postJoinMember(@RequestHeader(value = "referer") final String referer,
                                 @PathVariable final int churchId,
                                 @RequestParam final String challenge,
                                 @RequestParam final String response,
                                 @LogonUser User logon,
                                 final HttpServletRequest request,
                                 Model model) {

        boolean isValid = recaptchaService.verify(request.getRemoteAddr(), challenge, response);
        
        if(!isValid) {
            model.addAttribute("captcha", false);
            return "/church/join-member";
        }
        
        ChurchMember member = new ChurchMember();
        member.setChurch(churchId);
        member.setMember(logon);
        
        churchMemberService.create(member);
        
        return "redirect:/church/" + churchId;
    }
}
