package com.laonsys.smartchurch.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.smartchurch.service.ChurchMetaService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Controller
@RequestMapping("/application-service")
public class FindForSignupController {

    @Autowired private ChurchMetaService churchMetaService;
    
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = "text/html;charset=utf-8")
    public String ajaxFindResult(@WebQuery QueryParam queryParam, Model model) {
        List<ChurchMeta> list = churchMetaService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        return "/ajax/listChurch";
    }
}
