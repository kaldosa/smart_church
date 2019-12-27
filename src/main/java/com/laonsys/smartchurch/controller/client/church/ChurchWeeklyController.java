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

import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.Weekly;
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
public class ChurchWeeklyController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired @Qualifier("churchWeeklyEntityService") private GenericService<Weekly> churchWeeklyEntityService;
    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int id, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(id);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", id);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "/weekly", method = RequestMethod.GET)
    public String getList(@PathVariable int id, @WebQuery QueryParam queryParam, Model model) {

        queryParam.addParam("id", id);
        List<Weekly> list = churchWeeklyEntityService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/news/listWeekly";
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/weekly/{weeklyId}", method = RequestMethod.GET)
    public String getView(@PathVariable int id, @PathVariable("weeklyId") int weeklyId, @WebQuery QueryParam queryParam, Model model) {

        Weekly weekly = churchWeeklyEntityService.selectOne(weeklyId);

        if(weekly == null) {
            return "redirect:/church/" + id + "/weekly";
        }
        
        ((UpdateHitsService) churchWeeklyEntityService).updateHits(weekly.getHits() + 1, weeklyId);
        model.addAttribute("weekly", weekly);
        model.addAttribute("queryParam", queryParam);
        return "/church/news/viewWeekly";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int id, Model model) {
        model.addAttribute("entity", new Weekly());
        return "/church/news/createWeekly";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int id, 
                                 @Validated({Create.class}) @ModelAttribute("entity") Weekly weekly, 
                                 BindingResult result,
                                 SessionStatus sessionStatus,
                                 Model model) {
        
        if(result.hasErrors()) {
            return "/church/news/createWeekly";
        }
        
        weekly.setChurchId(id);
        churchWeeklyEntityService.create(weekly);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/weekly";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int id, @PathVariable("entityId") int entityId, Model model) {

        Weekly weekly = churchWeeklyEntityService.selectOne(entityId);
        
        model.addAttribute("entity", weekly);
        return "/church/news/editWeekly";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int id, @PathVariable("entityId") int entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") Weekly weekly,
                              BindingResult result,
                              SessionStatus sessionStatus) {
        
        if(result.hasErrors()) {
            return "/church/news/editWeekly";
        }
        
        weekly.setId(entityId);
        weekly.setChurchId(id);
        churchWeeklyEntityService.update(weekly);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/weekly";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable("entityId") int entityId) {
        
        churchWeeklyEntityService.delete(entityId);
        
        return "redirect:/church/" + id + "/weekly";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int id, @RequestParam("deleteItems") int[] ids) {

        churchWeeklyEntityService.delete(ids);
        
        return "redirect:/church/" + id + "/weekly";
    }
}