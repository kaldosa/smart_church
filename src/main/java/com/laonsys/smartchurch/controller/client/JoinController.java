package com.laonsys.smartchurch.controller.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.JoinFormBean.Agree;
import com.laonsys.smartchurch.domain.JoinFormBean.Join;
import com.laonsys.smartchurch.domain.JoinFormBean.Verify;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.service.JoinService;
import com.laonsys.smartchurch.service.UserService;

@Controller
@RequestMapping("/join")
@SessionAttributes("joinFormBean")
public class JoinController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired UserService userService;
    @Autowired JoinService joinService;
    
    @ExceptionHandler(HttpSessionRequiredException.class)
    public String handleException(HttpSessionRequiredException ex) {
        return "redirect:/join?_step=init";
    }
    
    @RequestMapping(value="/isAvailableMail", method=RequestMethod.POST)
    public @ResponseBody Map<String, Boolean> isAvailableMail(@RequestParam String email) {
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        
        UserDetails user = userService.loadUserByUsername(email);

        result.put("result", (user == null) ? true : false);
        
        return result;
    }
    
    @RequestMapping(params="_step=init", method = RequestMethod.GET)
    public String getStep1(Model model) throws Exception {
        model.addAttribute("joinFormBean", new JoinFormBean());
        return "/main/joinStep1";
    }
 
    @RequestMapping(params="_step=agree", method = RequestMethod.POST)
    public String postStep1(@Validated(Agree.class) @ModelAttribute("joinFormBean") JoinFormBean joinFormBean, 
                            BindingResult result) throws Exception {
        
        if(result.hasErrors()) {
            return "/main/joinStep1";
        }

        joinService.sendMail(joinFormBean);

        return "/main/joinStep2";
    }
    
    @RequestMapping(params="_step=verify", method = RequestMethod.POST)
    public String postStep2(@Validated(Verify.class) @ModelAttribute("joinFormBean") JoinFormBean joinFormBean, BindingResult result, SessionStatus status) {
        boolean isValid = !result.hasErrors();
        
        isValid = joinService.verifyCode(joinFormBean) == StatusCode.OK;

        if(!isValid) {
            status.setComplete();
            return "redirect:/join?_step=init";
        }
        
        return "/main/joinStep3";
    }
    
    @RequestMapping(params="_step=join", method = RequestMethod.POST)
    public String postStep3(@Validated(Join.class) @ModelAttribute("joinFormBean") JoinFormBean joinFormBean, BindingResult result, SessionStatus status) {
        if(result.hasErrors()) {
            return "/main/joinStep3";
        }

        logger.debug("{}", joinFormBean);
        try{
            joinService.joinUser(joinFormBean);
        } catch(Exception e) {
            logger.debug("Failed to join user. {}", joinFormBean);
            return "redirect:/";
        } finally {
            status.setComplete();
        }
        
        return "/main/successJoin";
    }
    
    @RequestMapping(params="_step=cancel", method = RequestMethod.GET)
    public String getCancel(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
    
//    @RequestMapping(value = "/joinUser/notEnable", method = RequestMethod.GET)
//    public String getNotEnableUser() {
//        return "/auth/notEnableUser";
//    }
//
//    @RequestMapping(value = "/joinUser", method = RequestMethod.GET)
//    public String getJoin(@ModelAttribute User user) {
//        return "/main/join";
//    }
//    
//    @RequestMapping(value = "/joinUser", method = RequestMethod.POST)
//    public String postJoinForm(@Validated({JoinUser.class}) @ModelAttribute User user, BindingResult result, RedirectAttributesModelMap map, HttpSession session) throws Exception {
//        logger.debug("{}", user);
//        
//        if(result.hasErrors()) {
//            return "/main/join";
//        }
//        
//        user.addAuthority(new UserAuthority("ROLE_USER"));
//        User clone = userService.addUser(user);
//        
//        userService.confirmEmail(clone, false);
//
//        logger.debug("post join form sessionId : {}", session.getId());
//        session.setAttribute("join_user_mail", user.getEmail());
//        map.put("q_j", session.getId());
//        
//        return "redirect:/joinUser/sendMail";
//    }
//    
//    @RequestMapping(value = "/joinUser/sendMail", method = RequestMethod.GET)
//    public String getSendConfirmMail(@RequestParam("q_j") String encrypt, ModelMap map, HttpSession session) throws Exception {
//        logger.debug("send confirm mail sessionId : {}", encrypt);
//        if(session.getId().equals(encrypt)) {
//            String email = (String) session.getAttribute("join_user_mail");
//            session.removeAttribute("join_user_mail");
//            map.put("email", email);
//        } else {
//            return "redirect:/errors/500";
//        }
//        
//        return "/main/sendMail";
//    }
//    
//    @RequestMapping(value = "/joinUser/confirmEmail", method = RequestMethod.GET)
//    public String checkConfirmEmail(@RequestParam("q_c") String code, ModelMap model) throws Exception {
//        logger.debug("Web join user authentication code : {}", code);
//        
//        Response response = userService.enableUser(code);
//        
//        String view = "/";
//        switch(response.getStatusCode()) {
//        case JOIN_INVALID_USER:
//            view = "/main/failedConfirmEmail";
//            break;
//        case JOIN_EXPIRED_MAIL:
//            view = "redirect:/joinUser/notEnable";
//            break;
//        case OK:
//            view = "/main/successJoin";
//            break;
//        }
//        
//        model.put("status", response.getStatusCode());
//        model.put("email", response.getData("email"));
//        
//        return view;
//    }
//    
//    @RequestMapping(value = "/joinUser/resendMail", method = RequestMethod.POST)
//    public @ResponseBody StatusCode sendConfirmEmail(User user, BindingResult result) throws Exception {
//        
//        if(result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            List<String> errorMsg = new ArrayList<String>();
//            StringBuffer buffer = new StringBuffer();
//            for(FieldError error : fieldErrors) {
//                buffer.append(error.getDefaultMessage() + "\n");
//            }
//            errorMsg.add(buffer.toString());
//
//            return StatusCode.DATA_BINDING_ERROR.setAnother(buffer.toString());
//        }
//        
//        User finded = userService.selectUser(user);
//        
//        if(finded == null) {
//            return StatusCode.USER_NOT_FOUND;
//        }
//        
//        userService.confirmEmail(finded, false);
//        
//        return StatusCode.OK.setAnother("확인 메일을 재발송하였습니다.");
//    }

}
