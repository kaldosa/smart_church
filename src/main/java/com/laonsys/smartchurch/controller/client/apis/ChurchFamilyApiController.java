package com.laonsys.smartchurch.controller.client.apis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.church.Family;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchFamilyApiController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("churchFamilyEntityService") private GenericService<Family> churchFamilyEntityService;
    
    @RequestMapping(value = "/family", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListFamily(@PathVariable int churchId, @WebQuery QueryParam queryParam) {
        Response response = new Response();
        
        queryParam.addParam("id", churchId);
        List<Family> list = churchFamilyEntityService.selectList(queryParam);
        
        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/family/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getFamily(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        Family family = churchFamilyEntityService.selectOne(entityId);
        
        if(family == null ) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("family", family);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteFamily(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();

        churchFamilyEntityService.delete(entityId);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createFamily(@PathVariable int churchId,
                                                @Validated({Create.class}) @ModelAttribute Family family, 
                                                BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        family.setChurchId(churchId);
        churchFamilyEntityService.create(family);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/family/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editFamily(@PathVariable int churchId,
                                             @PathVariable int entityId,
                                             @Validated({Update.class}) @ModelAttribute Family family, 
                                             BindingResult result) {
        
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        family.setId(entityId);
        family.setChurchId(churchId);
        churchFamilyEntityService.update(family);

        return response;
    }
}
