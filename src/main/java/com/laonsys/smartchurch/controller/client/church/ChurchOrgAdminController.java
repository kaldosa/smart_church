package com.laonsys.smartchurch.controller.client.church;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{churchId}")
@SessionAttributes("entity")
public class ChurchOrgAdminController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int churchId, Model model) {
        OurChurch ourChurch = churchService.selectOne(churchId);
        
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg); 
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin", method = RequestMethod.GET)
    public String getList(@PathVariable int churchId, Model model) {

        return "/church/org/listOrg";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int churchId, Model model) {
        model.addAttribute("entity", new ChurchOrg());
        return "/church/org/createOrg";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int churchId,
                                 @Validated({Create.class}) @ModelAttribute("entity") ChurchOrg churchOrg, 
                                 BindingResult result,
                                 SessionStatus sessionStatus,
                                 Model model) {
        
        if(result.hasErrors()) {
            return "/church/org/createOrg";
        }
        
        churchOrg.setChurchId(churchId);
        churchOrgService.create(churchOrg);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/orgadmin";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int churchId, @PathVariable int entityId, Model model) {
        
        ChurchOrg churchOrg = churchOrgService.selectOne(entityId);
        model.addAttribute("entity", churchOrg);
        
        return "/church/org/editOrg";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int churchId, @PathVariable int entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") ChurchOrg churchOrg,
                              BindingResult result,
                              SessionStatus sessionStatus) {

        if(result.hasErrors()) {
            return "/church/org/editOrg";
        }
        
        churchOrg.setId(entityId);
        churchOrg.setChurchId(churchId);
        churchOrgService.update(churchOrg);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/orgadmin";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin", method = RequestMethod.PUT)
    public String sortOrg(@PathVariable int churchId, @RequestParam("order") List<Integer> order) {

        if(!order.isEmpty()) {
            int size = order.size();
            List<ChurchOrg> churchOrgs = new ArrayList<ChurchOrg>();
            for(int i = 0; i < size; i++) {
                ChurchOrg churchOrg = new ChurchOrg();
                churchOrg.setId(order.get(i));
                churchOrg.setSort(i);
                churchOrgs.add(churchOrg);
            }
            
            churchOrgService.update(churchOrgs);
        }
        
        return "redirect:/church/" + churchId + "/org";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/orgadmin/{entityId}", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Boolean> delete(@PathVariable int churchId, @PathVariable int entityId) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        
        churchOrgService.delete(entityId);
        
        response.put("result", true);
        return response;
    }
}