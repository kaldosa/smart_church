package com.laonsys.smartchurch.controller.client.apis;

import java.util.List;

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
import com.laonsys.smartchurch.domain.church.ChurchNews;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchNewsApiController {
    @Autowired @Qualifier("churchNewsEntityService") GenericService<ChurchNews> churchNewsEntityService;
    
    @RequestMapping(value = "/news", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListNews(@PathVariable int churchId, @WebQuery QueryParam queryParam) {
        Response response = new Response();

        queryParam.addParam("id", churchId);
        List<ChurchNews> list = churchNewsEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }

        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/news/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getNews(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        ChurchNews churchNews = churchNewsEntityService.selectOne(entityId);
        if(churchNews == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }

        response.putData("news", churchNews);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteNews(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        churchNewsEntityService.delete(entityId);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createNews(@PathVariable int churchId,
                                              @Validated({Create.class}) ChurchNews churchNews, 
                                              BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        churchNews.setChurchId(churchId);
        churchNewsEntityService.create(churchNews);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/news/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public  @ResponseBody Response editNews(@PathVariable int churchId,
                                            @PathVariable int entityId,
                                            @Validated({Update.class}) ChurchNews churchNews, 
                                            BindingResult result) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }

        churchNews.setId(entityId);
        churchNews.setChurchId(churchId);
        churchNewsEntityService.update(churchNews);

        return response;
    }
}
