package com.laonsys.smartchurch.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.ServiceStatus;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Controller
@RequestMapping("/admin/services")
@SessionAttributes("entity")
public class ManageServiceController {

    @Autowired private ChurchService churchService;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getList(@WebQuery QueryParam queryParam, Model model) {

        List<OurChurch> list = churchService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/admin/listService";
    }
    
    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable("entityId") int entityId, @WebQuery QueryParam queryParam, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(entityId);

        if(ourChurch == null) {
            return "redirect:/admin/services";
        }
        
        model.addAttribute("entity", ourChurch);
        model.addAttribute("queryParam", queryParam);
        
        return "/admin/viewService";
    }
    
    @RequestMapping(value = "/{entityId}", method = RequestMethod.PUT)
    public String putService(@PathVariable("entityId") int entityId, 
                             @Validated @ModelAttribute("entity") OurChurch ourChurch, 
                             BindingResult result, 
                             SessionStatus status) {

        if(result.hasErrors()) {
            return "/admin/viewApplication";
        }

        if(ServiceStatus.WAITING == ourChurch.getStatus()) {
            churchService.approve(ourChurch);
        } else {
            churchService.update(ourChurch);
        }
        
        status.setComplete();
        
        return "redirect:/admin/services";
    }
    
    @RequestMapping(value = "/{entityId}", method = RequestMethod.DELETE)
    public String deleteService(@PathVariable("entityId") int entityId) {

        churchService.delete(entityId);
        
        return "redirect:/admin/services";
    }
    
    @RequestMapping(value = "/{entityId}/cancel", method = RequestMethod.DELETE)
    public String cancelService(@PathVariable("entityId") int entityId) {

        churchService.cancelApplication(entityId);
        
        return "redirect:/admin/services";
    }
}
