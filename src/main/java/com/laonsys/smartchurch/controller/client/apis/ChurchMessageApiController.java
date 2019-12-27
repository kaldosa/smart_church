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
import com.laonsys.smartchurch.domain.church.Message;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchMessageApiController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("churchMessageEntityService") private GenericService<Message> churchMessageEntityService;
    
    @RequestMapping(value = "/message", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListMessage(@PathVariable int churchId, @WebQuery QueryParam queryParam) {
        Response response = new Response();

        queryParam.addParam("id", churchId);
        List<Message> list = churchMessageEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }

        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/message/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getMessage(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        Message message = churchMessageEntityService.selectOne(entityId);
        
        if(message == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("message", message);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteMessage(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        churchMessageEntityService.delete(entityId);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createMessage(@PathVariable int churchId,
                                                 @Validated({Create.class}) @ModelAttribute Message message, 
                                                 BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        message.setChurchId(churchId);
        churchMessageEntityService.create(message);
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/message/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editMessage(@PathVariable int churchId,
                                              @PathVariable int entityId,
                                              @Validated({Update.class}) @ModelAttribute Message message, 
                                              BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        message.setId(entityId);
        message.setChurchId(churchId);
        churchMessageEntityService.update(message);
        
        return response;
    }
}
