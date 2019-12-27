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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.church.Weekly;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchWeeklyApiController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("churchWeeklyEntityService") private GenericService<Weekly> churchWeeklyEntityService;
    
    @RequestMapping(value = "/weekly", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListWeekly(@PathVariable int churchId, @WebQuery QueryParam queryParam) {
        Response response = new Response();

        queryParam.addParam("id", churchId);
        List<Weekly> list = churchWeeklyEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
   
    @RequestMapping(value = "/weekly/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getWeekly(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        Weekly weekly = churchWeeklyEntityService.selectOne(entityId);
        
        if(weekly == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;            
        }

        response.putData("weekly", weekly);
        return response;
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteWeekly(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        churchWeeklyEntityService.delete(entityId);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createWeekly(@PathVariable int churchId, 
                                              @Validated({Create.class}) Weekly weekly, 
                                              BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        weekly.setChurchId(churchId);
        churchWeeklyEntityService.create(weekly);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/weekly/{id}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editWeekly(@PathVariable int churchId,
                                             @PathVariable int entityId,
                                           @Validated({Update.class}) Weekly weekly, 
                                           BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        weekly.setId(entityId);
        weekly.setChurchId(churchId);
        churchWeeklyEntityService.update(weekly);

        return response;
    }
}
