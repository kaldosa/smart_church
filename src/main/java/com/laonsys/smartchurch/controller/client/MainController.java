package com.laonsys.smartchurch.controller.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.laonsys.smartchurch.domain.Notice;
import com.laonsys.smartchurch.domain.TodayWords;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.smartchurch.service.TodayWordsService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;


@Controller
public class MainController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
   
    @Autowired @Qualifier("noticeEntityService") GenericService<Notice> noticeEntityService;
    @Autowired ChurchService churchService;
    @Autowired TodayWordsService todayWordsService;
    
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getMain(Model model) {
        
        QueryParam queryParam = new QueryParam();
        Paginate paginate = new Paginate(7, 10);
        queryParam.setPaginate(paginate);
        
        List<Notice> listNotice = noticeEntityService.selectAll(queryParam);

        model.addAttribute("listNotice", listNotice);
        
        List<OurChurch> listChurch = churchService.selectRecent();
        
        model.addAttribute("listChurch", listChurch);

        TodayWords words = todayWordsService.getTodayWords();
        
        model.addAttribute("todayWords", words);
        
        return "/main/main";
    }

    @RequestMapping(value = "/listChurch", method = RequestMethod.GET)
    public String listChurch(@WebQuery QueryParam queryParam, Model model) {
        
        queryParam.addParam("status", "SERVICE");
        List<OurChurch> listChurch = churchService.selectList(queryParam);
        
        model.addAttribute("list", listChurch);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/main/listChurch";
    }
}