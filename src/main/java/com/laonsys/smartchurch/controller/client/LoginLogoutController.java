package com.laonsys.smartchurch.controller.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class LoginLogoutController {
    private @Value("#{envvars['http.scheme']}") String scheme;
    
    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestHeader(value = "referer", required = false) final String referer, 
                               @RequestParam(value = "error", required = false) boolean error,
                               HttpServletRequest request,
                               ModelMap model) {
        if (error == true) {
            model.put("error", "You have entered an invalid username or password!");
        }
        else {
            model.put("error", "");
        }
        
        if(referer == null || "".equals(referer)) {
            model.put("redirectUrl", "/");
        } else {
            model.put("redirectUrl", getRedirectUrl(request.getContextPath(), referer));
        }
        
        return "/auth/login";
    }

    @RequestMapping(value = "/auth/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/auth/denied";
    }
    
    @RequestMapping(value = "/popup/login", method = RequestMethod.GET)
    public String loginPopup() {
        return "/popup/login";
    }
    
    private String getRedirectUrl(String contextPath, String referer) {
        if(referer.startsWith("https") || referer.startsWith("HTTPS")) {
            return referer;
        }
        
        referer = referer.substring(referer.indexOf("://") + 3); // strip off scheme
     
        return scheme + referer;
    }    
}
