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
import com.laonsys.smartchurch.domain.church.Family;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{id}")
@SessionAttributes("entity")
public class ChurchFamilyController {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired @Qualifier("churchFamilyEntityService") private GenericService<Family> churchFamilyEntityService;
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
    
    @RequestMapping(value = "/family", method = RequestMethod.GET)
    public String getList(@PathVariable int id, @WebQuery QueryParam queryParam, Model model) {
        
        queryParam.addParam("id", id);
        queryParam.getPaginate().setItemPerPage(9);
        
        List<Family> list = churchFamilyEntityService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/news/listFamily";
    }
    
    @RequestMapping(value = "/family/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int id, @PathVariable("entityId") int entityId, @WebQuery QueryParam queryParam, Model model) {
        Family family = churchFamilyEntityService.selectOne(entityId);

        if(family == null) {
            logger.debug("not found family object. \"{}\" [{}]", id, entityId);
            return "redirect:/church/" + id + "/family";
        }
        
        model.addAttribute("entity", family);
        model.addAttribute("queryParam", queryParam);
        return "/church/news/viewFamily";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int id, Model model) {
        model.addAttribute("entity", new Family());
        return "/church/news/createFamily";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int id, 
                                 @Validated({Create.class}) @ModelAttribute("entity") Family family, 
                                 BindingResult result,
                                 SessionStatus sessionStatus,
                                 Model model) {

        if(result.hasErrors()) {
            return "/church/news/createFamily";
        }
        
        family.setChurchId(id);
        churchFamilyEntityService.create(family);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/family";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int id, @PathVariable int entityId, Model model) {

        Family family = churchFamilyEntityService.selectOne(entityId);
        
        model.addAttribute("entity", family);
        return "/church/news/editFamily";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int id, @PathVariable int entityId,
                              @Validated(Update.class) @ModelAttribute("entity") Family family,
                              BindingResult result,
                              SessionStatus sessionStatus) {
        
        if(result.hasErrors()) {
            return "/church/news/editNews";
        }
        
        family.setId(entityId);
        family.setChurchId(id);
        churchFamilyEntityService.update(family);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/family";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable int entityId) {

        churchFamilyEntityService.delete(entityId);
        
        return "redirect:/church/" + id + "/family";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int id, @RequestParam("deleteItems") int[] ids) {

        churchFamilyEntityService.delete(ids);
        
        return "redirect:/church/" + id + "/family";
    }
}