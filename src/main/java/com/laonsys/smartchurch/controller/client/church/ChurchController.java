package com.laonsys.smartchurch.controller.client.church;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.Sermon;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchSermonService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Controller
public class ChurchController {
    
    private transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired private ChurchSermonService churchSermonService;
    @Autowired private ChurchOrgService churchOrgService;
    @Autowired private ChurchService churchService;
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String exceptionHandle(IllegalArgumentException ie) {
    
        return "redirect:/";
    }
    
    @RequestMapping(value = "/church/denied", method = RequestMethod.GET)
    public String getDenied() {
        return "/church/denied";
    }
    
    @RequestMapping(value = "/church/{churchId}", method = RequestMethod.GET)
    public String main(@PathVariable int churchId, Model model) throws IllegalArgumentException {
        
        OurChurch ourChurch = churchService.selectOne(churchId);
        List<Attachment> mainImages = churchService.selectMainImages(churchId);
        model.addAttribute("ourChurch", ourChurch);
        model.addAttribute("mainImages", mainImages);
        
        if(ourChurch == null) {
            logger.debug("{}", "==============================> occur IOException");
            throw new IllegalArgumentException();
        }
        
        Sermon sermon = churchSermonService.lastOne(churchId);
        model.addAttribute("sermon", sermon);
        
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        model.addAttribute("listOrg", listOrg);
        
        return "/church/main";
    }
}
