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
import com.laonsys.smartchurch.domain.church.Sermon;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchSermonApiController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("churchSermonEntityService") private GenericService<Sermon> churchSermonEntityService;
    
    @RequestMapping(value = "/sermon", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListSermon(@PathVariable int churchId, @WebQuery QueryParam queryParam) {
        Response response = new Response();

        queryParam.addParam("id", churchId);
        List<Sermon> list = churchSermonEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/sermon/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getSermon(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        Sermon sermon = churchSermonEntityService.selectOne(entityId);
        
        if(sermon == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("sermon", sermon);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteSermon(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        churchSermonEntityService.delete(entityId);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createSermon(@PathVariable int churchId, 
                                                @Validated({Create.class}) Sermon sermon, 
                                                BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }

        sermon.setChurchId(churchId);
        churchSermonEntityService.create(sermon);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/sermon/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editSermon(@PathVariable int churchId,
                                             @PathVariable int entityId,
                                             @Validated({Update.class}) Sermon sermon, 
                                             BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        sermon.setId(entityId);
        sermon.setChurchId(churchId);
        churchSermonEntityService.update(sermon);
        
        return response;
    }
}
