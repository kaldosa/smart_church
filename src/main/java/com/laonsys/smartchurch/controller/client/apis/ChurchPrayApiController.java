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

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.Pray;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchPrayApiController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("churchPrayEntityService") GenericService<Pray> churchPrayEntityService;
    @Autowired private ReplyService replyService;
    
    @RequestMapping(value = "/pray", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListPray(@PathVariable int churchId, @WebQuery QueryParam queryParam) {
        Response response = new Response();

        queryParam.addParam("id", churchId);
        List<Pray> list = churchPrayEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getPray(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        Pray pray = churchPrayEntityService.selectOne(entityId);
        
        if(pray == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("pray", pray);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isPrayWriter'))")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deletePray(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();

        churchPrayEntityService.delete(entityId);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/pray", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createPray(@PathVariable int churchId,
                                              @Validated({Create.class}) Pray pray, 
                                              BindingResult result,
                                              @LogonUser User user) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        pray.setChurchId(churchId);
        pray.setUser(user);
        
        churchPrayEntityService.create(pray);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or hasPermission(#entityId, 'isPrayWriter')")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editPray(@PathVariable int churchId,
                                           @PathVariable int entityId,
                                           @Validated({Update.class}) Pray pray, 
                                           BindingResult result) {
        Response response = new Response();

        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }

        pray.setId(entityId);
        pray.setChurchId(churchId);
        churchPrayEntityService.update(pray);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Response addComment(@PathVariable int churchId,
                                             @PathVariable int entityId,
                                             @Validated BaseComments reply,
                                             BindingResult result,
                                             @LogonUser User activeUser) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        reply.setChurchId(churchId);
        reply.setPostingsId(entityId);
        reply.setUser(activeUser);
        
        replyService.insert(reply, "church_pray_comments");

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#replyId, 'isReplyUser'))")
    @RequestMapping(value = "/pray/{entityId}/{replyId}", method = RequestMethod.DELETE)
    public @ResponseBody Response deleteComment(@PathVariable int churchId,
                                                @PathVariable int entityId,
                                                @PathVariable int replyId,
                                                @LogonUser User activeUser) {
        Response response = new Response();
        
        replyService.delete(replyId, "church_pray_comments", activeUser);
        
        return response;
    }
}
