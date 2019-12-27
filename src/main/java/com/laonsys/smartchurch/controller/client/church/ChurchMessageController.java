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
import com.laonsys.smartchurch.domain.church.Message;
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
public class ChurchMessageController {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    @Autowired @Qualifier("churchMessageEntityService") private GenericService<Message> churchMessageEntityService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int id, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(id);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", id);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String getList(@PathVariable int id, @WebQuery QueryParam queryParam, Model model) {
        
        queryParam.addParam("id", id);
        queryParam.getPaginate().setItemPerPage(5);
        
        List<Message> list = churchMessageEntityService.selectList(queryParam);
        
        if(list.size() > 0) {
            model.addAttribute("message", list.get(0));
        }
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/sermon/listMessage";
    }
    
    @RequestMapping(value = "/message/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int id, 
                          @PathVariable int entityId, 
                          @WebQuery QueryParam queryParam,
                          Model model) {
        
        queryParam.addParam("id", id);
        queryParam.getPaginate().setItemPerPage(5);
        
        List<Message> list = churchMessageEntityService.selectList(queryParam);
        
        Message message = churchMessageEntityService.selectOne(entityId);

        model.addAttribute("list", list);
        model.addAttribute("message", message);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/sermon/listMessage";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int id, Model model) {
        model.addAttribute("entity", new Message());
        return "/church/sermon/createMessage";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int id, 
                                 @Validated({Create.class}) @ModelAttribute("entity") Message message, 
                                 BindingResult result,
                                 SessionStatus sessionStatus,
                                 Model model) {
        
        if(result.hasErrors()) {
            return "/church/sermon/createMessage";
        }
        
        message.setChurchId(id);
        churchMessageEntityService.create(message);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/message";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int id, @PathVariable int entityId, Model model) {

        Message message = churchMessageEntityService.selectOne(entityId);
        model.addAttribute("entity", message);
        
        return "/church/sermon/editMessage";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int id, 
                              @PathVariable int entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") Message message,
                              BindingResult result,
                              SessionStatus sessionStatus) {

        if(result.hasErrors()) {
            return "/church/sermon/editMessage";
        }
        
        message.setId(entityId);
        message.setChurchId(id);
        churchMessageEntityService.update(message);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + id + "/message";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable int entityId) {
        
        logger.debug("{}", "delete id : " + entityId);
        
        churchMessageEntityService.delete(entityId);
        
        return "redirect:/church/" + id + "/message";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int id, @RequestParam("deleteItems") int[] ids) {
        
        logger.debug("{}", "delete ids : " + ids);
        
        churchMessageEntityService.delete(ids);
        
        return "redirect:/church/" + id + "/message";
    }    
}
