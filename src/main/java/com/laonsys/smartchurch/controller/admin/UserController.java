package com.laonsys.smartchurch.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Controller("adminUserController")
@RequestMapping("/admin")
public class UserController {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired UserService userService;
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(@WebQuery QueryParam queryParam, Model model) {

        List<User> list = userService.list(queryParam);
        
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("userList", list);
        
        return "/admin/listUser";
    }
    
    @RequestMapping(value = "/users/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("deleteItems") int[] deleteUsers) {
        logger.debug("{}", deleteUsers);
        
        return "redirect:/admin/users";
    }
}
