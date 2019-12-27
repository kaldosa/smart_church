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
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OrgPosts;
import com.laonsys.smartchurch.service.ChurchOrgPostsService;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchOrgApiController {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired private ChurchOrgService churchOrgService;
    @Autowired @Qualifier("churchOrgPostsEntityService") private GenericService<OrgPosts> churchOrgPostsEntityService;
    
    @RequestMapping(value = "/org", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListOrg(@PathVariable int churchId) {
        Response response = new Response();
        
        QueryParam searchCriteria = new QueryParam();
        searchCriteria.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(searchCriteria);

        if(listOrg == null || listOrg.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("list", listOrg);
        
        return response;
    }
    
    @RequestMapping(value = "/org/{orgId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListOrgPosts(@PathVariable int churchId,
                                                  @PathVariable int orgId,
                                                  @WebQuery QueryParam queryParam) {
        Response response = new Response();
        
        queryParam.addParam("orgId", orgId);
        List<OrgPosts> list = churchOrgPostsEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/org/{orgId}/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response viewOrgPosts(@PathVariable int churchId,
                                               @PathVariable int orgId,
                                               @PathVariable int entityId) {
        Response response = new Response();
        
        OrgPosts orgPosts = churchOrgPostsEntityService.selectOne(entityId);
        
        if(orgPosts == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("OrgPosts", orgPosts);
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isOrgPostsWriter'))")
    @RequestMapping(value = "/org/{orgId}/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteOrgPosts(@PathVariable int churchId,
                                                  @PathVariable int orgId,
                                                  @PathVariable int entityId) {
        Response response = new Response();
        churchOrgPostsEntityService.delete(entityId);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/org/{orgId}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Response createOrgPosts(@PathVariable int churchId,
                                                 @PathVariable int orgId,
                                                 @Validated({Create.class}) OrgPosts orgPosts, 
                                                 BindingResult result,
                                                 @LogonUser User user) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        orgPosts.setOrgId(orgId);
        orgPosts.setUser(user);
        churchOrgPostsEntityService.create(orgPosts);
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isOrgPostsWriter'))")
    @RequestMapping(value = "/org/{orgId}/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editOrgPosts(@PathVariable int churchId,
                                               @PathVariable int orgId,
                                               @PathVariable int entityId,
                                               @Validated({Update.class}) OrgPosts orgPosts,
                                               BindingResult result,
                                               @LogonUser User activeUser) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        orgPosts.setId(entityId);
        orgPosts.setOrgId(orgId);
        churchOrgPostsEntityService.update(orgPosts);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/org/{orgId}/{entityId}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Response addComment(@PathVariable int churchId,
                                             @PathVariable int orgId,
                                             @PathVariable int entityId,
                                             @Validated BaseComments reply,
                                             BindingResult result,
                                             @LogonUser User activeUser) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        reply.setPostingsId(entityId);
        reply.setUser(activeUser);
        ((ChurchOrgPostsService) churchOrgPostsEntityService).addComment(reply);
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#commentId, 'isReplyUser'))")
    @RequestMapping(value = "/org/{orgId}/{entityId}/{commentId}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody Response deleteComment(@PathVariable int churchId,
                                                @PathVariable int orgId,
                                                @PathVariable int entityId,
                                                @PathVariable int commentId,
                                                @LogonUser User activeUser) {
        Response response = new Response();
        
        ((ChurchOrgPostsService) churchOrgPostsEntityService).deleteComment(commentId, activeUser);
        
        return response;
    }
}
