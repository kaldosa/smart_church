package com.laonsys.smartchurch.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.springmvc.extensions.utils.EncodingUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AttachService attachService;

    @RequestMapping(value = "/make-folder", method = RequestMethod.POST)
    public String makedirectory(@RequestParam String path) {
        attachService.makeDirectory(path);
        return "/admin/main";
    }

    @RequestMapping(value = "/del-folder", method = RequestMethod.POST)
    public String deletedirectory(@RequestParam String path) {
        attachService.delDirectory(path);
        return "/admin/main";
    }
    
    @RequestMapping(value = "/pw-encoding", method = RequestMethod.POST)
    public @ResponseBody String ajaxPasswordEncode(@RequestParam String email, @RequestParam String password) {
        return "{result : \"" + EncodingUtils.encodePassword(password, email) + "\"}";
    }
}
