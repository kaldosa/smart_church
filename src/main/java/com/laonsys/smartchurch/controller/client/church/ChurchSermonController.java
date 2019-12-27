package com.laonsys.smartchurch.controller.client.church;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

import com.google.gson.Gson;
import com.laonsys.smartchurch.domain.Upload;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.Sermon;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchSermonService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;


@Controller
@RequestMapping("/church/{id}")
@SessionAttributes("entity")
public class ChurchSermonController {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    @Autowired @Qualifier("churchSermonEntityService") private GenericService<Sermon> churchSermonEntityService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int id, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(id);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", id);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "/sermon", method = RequestMethod.GET)
    public String getList(@PathVariable int id, @WebQuery QueryParam queryParam, Model model) {

        queryParam.addParam("id", id);
        queryParam.getPaginate().setItemPerPage(5);
        
        List<Sermon> list = churchSermonEntityService.selectList(queryParam);
        
        if(list.size() > 0) {
            model.addAttribute("sermon", list.get(0));
        }
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/sermon/listSermon";
    }
    
    @RequestMapping(value = "/sermon/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int id,
                          @PathVariable int entityId,
                          @WebQuery QueryParam queryParam,
                          Model model) {
        
        queryParam.getPaginate().setItemPerPage(5);
        queryParam.addParam("id", id);
        
        List<Sermon> list = churchSermonEntityService.selectList(queryParam);
        
        Sermon sermon = churchSermonEntityService.selectOne(entityId);
        
        model.addAttribute("list", list);
        model.addAttribute("sermon", sermon);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/sermon/listSermon";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int id, Model model) {
        model.addAttribute("entity", new Sermon());
        return "/church/sermon/createSermon";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int id,
                                 @RequestParam("video") int video,
                                 @Validated({Create.class}) @ModelAttribute("entity") Sermon sermon, 
                                 BindingResult result,
                                 SessionStatus status,
                                 ModelMap model) {
        
        if(result.hasErrors()) {
            return "/church/sermon/createSermon";
        }
        
        sermon.setChurchId(id);
        churchSermonEntityService.create(sermon);
        status.setComplete();
        
        return "redirect:/church/" + id + "/sermon";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/video", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public @ResponseBody String ajaxPostAttachment(@PathVariable int id, @Validated Upload file, BindingResult result) {
        Map<String, Object> response = new HashMap<String, Object>();
        Gson gson = new Gson();
        logger.debug("file {}", file);

        if(result.hasErrors()) {
            response.put("error", "허용되지 않는 미디어타입 또는 파일크기입니다.");
            return gson.toJson(response);
        }
        
        int video = 0;
        try {
            video = ((ChurchSermonService) churchSermonEntityService).storeToTemporaryFile(file.getFile());
        } catch (ServiceException e) {
            response.put("error", "Failed to upload video.");
            return gson.toJson(response);
        }
        
        response.put("success", true);
        response.put("video", video);
        
        return gson.toJson(response);
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int id, @PathVariable int entityId, Model model) {

        Sermon sermon = churchSermonEntityService.selectOne(entityId);
        
        model.addAttribute("entity", sermon);
        
        return "/church/sermon/editSermon";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int id, 
                              @PathVariable int entityId,
                              @Validated(Update.class) @ModelAttribute("entity") Sermon sermon,
                              BindingResult result,
                              SessionStatus status) {

        if(result.hasErrors()) {
            return "/church/sermon/editSermon";
        }
        
        sermon.setId(entityId);
        sermon.setChurchId(id);
        churchSermonEntityService.update(sermon);
        status.setComplete();
        
        return "redirect:/church/" + id + "/sermon";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id, @PathVariable int entityId) {
        
        logger.debug("{}", "delete id : " + entityId);
        
        churchSermonEntityService.delete(entityId);
        
        return "redirect:/church/" + id + "/sermon";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int id, @RequestParam("deleteItems") int[] ids) {
        
        logger.debug("{}", "delete ids : " + ids);
        
        churchSermonEntityService.delete(ids);
        
        return "redirect:/church/" + id + "/sermon";
    }
}