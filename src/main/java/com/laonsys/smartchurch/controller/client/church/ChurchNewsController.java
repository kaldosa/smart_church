package com.laonsys.smartchurch.controller.client.church;

import java.util.List;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.church.ChurchNews;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{id}")
@SessionAttributes("entity")
public class ChurchNewsController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    
    @Autowired @Qualifier("churchNewsEntityService") GenericService<ChurchNews> churchNewsEntityService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int id, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(id);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", id);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String getList(@PathVariable int id, @WebQuery QueryParam queryParam, Model model) {

        queryParam.addParam("id", id);
        List<ChurchNews> list = churchNewsEntityService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/news/listNews";
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.GET)
    public String getView(@PathVariable int id, @PathVariable("newsId") int newsId, @WebQuery QueryParam queryParam, Model model) {

        ChurchNews churchNews = churchNewsEntityService.selectOne(newsId);

        if(churchNews == null) {
            return "redirect:/church/" + id + "/news";
        }
        
        ((UpdateHitsService)churchNewsEntityService).updateHits(churchNews.getHits() + 1, newsId);
        model.addAttribute("news", churchNews);
        model.addAttribute("queryParam", queryParam);
        return "/church/news/viewNews";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int id, Model model) {
        model.addAttribute("entity", new ChurchNews());
        return "/church/news/createNews";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int id, 
                                 @Validated({Create.class}) @ModelAttribute("entity") ChurchNews churchNews, 
                                 BindingResult result,
                                 SessionStatus sessionStatus,
                                 Model model) {
        
        if(result.hasErrors()) {
            return "/church/news/createNews";
        }

        churchNews.setChurchId(id);
        churchNewsEntityService.create(churchNews);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/news";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/{newsId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int id, @PathVariable("newsId") int newsId, Model model) {
        
        ChurchNews churchNews = churchNewsEntityService.selectOne(newsId);
        model.addAttribute("entity", churchNews);
        
        return "/church/news/editNews";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int id,
                              @PathVariable("newsId") int newsId,
                              @Validated({Update.class}) @ModelAttribute("entity") ChurchNews churchNews,
                              BindingResult result,
                              SessionStatus sessionStatus) {
        
        if(result.hasErrors()) {
            return "/church/news/editNews";
        }
        
        churchNews.setId(newsId);
        churchNews.setChurchId(id);
        churchNewsEntityService.update(churchNews);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/news";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/{newsId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable("newsId") int newsId) {

        churchNewsEntityService.delete(newsId);
        
        return "redirect:/church/" + id + "/news";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int id, @RequestParam("deleteItems") int[] ids) {
        
        churchNewsEntityService.delete(ids);
        
        return "redirect:/church/" + id + "/news";
    }    
}