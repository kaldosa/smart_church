package com.laonsys.smartchurch.controller.client;

import java.util.List;

import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.laonsys.smartchurch.domain.Notice;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;

@Controller
@RequestMapping("/notices")
public class NoticeController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired @Qualifier("noticeEntityService") GenericService<Notice> noticeEntityService;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getList(@WebQuery QueryParam queryParam, Model model) {
        List<Notice> list = noticeEntityService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/main/listNotice";
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getView(@PathVariable("id") int id, @WebQuery QueryParam queryParam, Model model) {
        Notice notice = noticeEntityService.selectOne(id);

        if(notice == null) {
            return "redirect:/notices";
        }
        
        model.addAttribute("entity", notice);
        
        queryParam.addParam("id", id);
        queryParam.addParam("pager", "next");
        Notice next = noticeEntityService.selectOne(queryParam);
        model.addAttribute("next", next);
        
        queryParam.addParam("pager", "prev");
        Notice prev = noticeEntityService.selectOne(queryParam);
        model.addAttribute("prev", prev);
        
        notice.setHits(notice.getHits() + 1);
        ((UpdateHitsService<Notice>) noticeEntityService).updateHits(notice);
        
        model.addAttribute("queryParam", queryParam);
        
        return "/main/viewNotice";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getCreateForm(@ModelAttribute("entity") Notice notice) {
        return "/main/createNotice";
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postCreateForm(@Validated({Default.class}) @ModelAttribute("entity") Notice notice, 
                                 BindingResult result, 
                                 Model model) {

        if(result.hasErrors()) {
            return "/main/createNotice";
        }

        noticeEntityService.create(notice);
        
        return "redirect:/notices";
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int id, Model model) {

        Notice notice = noticeEntityService.selectOne(id);
        model.addAttribute("entity", notice);
        
        return "/main/editNotice";
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int id,
                              @Validated({Default.class}) @ModelAttribute Notice notice,
                              BindingResult result) {
        
        if(result.hasErrors()) {
            return "/main/editNotice";
        }
        
        notice.setId(id);
        noticeEntityService.update(notice);
        
        return "redirect:/notices";
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") int id) {
        
        logger.debug("{}", "delete id : " + id);
        
        noticeEntityService.delete(id);
        
        return "redirect:/notices";
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletes(@RequestParam("deleteItems") int[] ids) {
        
        logger.debug("{}", "delete ids : " + ids);
        
        noticeEntityService.delete(ids);
        
        return "redirect:/notices";
    }
}