package com.laonsys.smartchurch.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.Zipcode;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.smartchurch.service.ZipcodeService;
import com.laonsys.smartchurch.utiles.SessionUtils;

@Controller
@RequestMapping("/ajax/*")
public class AjaxController {

    @Autowired
    private ZipcodeService zipcodeService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/zipcode/{key}", method=RequestMethod.GET)
    public @ResponseBody List<Zipcode> searchAddress(@PathVariable("key") String key) {
        return zipcodeService.serachByDong(key);
    }
    
    @RequestMapping(value="/checkAuth", method=RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody Map<String, Boolean> checkAuth() {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        
        result.put("result", SessionUtils.hasAuthority("ROLE_USER"));
        
        return result;
    }
    
    @RequestMapping(value="/isAvailableMail", method=RequestMethod.POST)
    public @ResponseBody Map<String, Boolean> isAvailableMail(@RequestParam String email) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        
        UserDetails user = userService.loadUserByUsername(email);

        result.put("result", (user == null) ? true : false);
        
        return result;
    }
}
