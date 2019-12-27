package com.laonsys.smartchurch.controller.client.apis;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.PhotoFormBean;
import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.Album;
import com.laonsys.smartchurch.service.ChurchAlbumService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchAlbumApiController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired @Qualifier("churchAlbumEntityService") private GenericService<Album> churchAlbumEntityService;
    
    @RequestMapping(value = "/album", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getListAlbum(@PathVariable int churchId,  @WebQuery QueryParam queryParam) {
        Response response = new Response();
        
        queryParam.addParam("churchId", churchId);
        List<Album> list = churchAlbumEntityService.selectList(queryParam);

        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }

        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.GET, produces = "application/json")
    public  @ResponseBody Response getAlbum(@PathVariable int churchId, @PathVariable int entityId) {
        Response response = new Response();
        
        Album album = churchAlbumEntityService.selectOne(entityId);
        
        if(album == null ) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("album", album);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.DELETE, produces = "application/json")
    public  @ResponseBody Response deleteAlbum(@PathVariable int churchId, @PathVariable int entityId, Principal principal) {
        Response response = new Response();
        churchAlbumEntityService.delete(entityId);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/album", method = RequestMethod.POST, produces = "application/json")
    public  @ResponseBody Response createAlbum(@PathVariable int churchId,
                                               @Validated({Create.class}) @ModelAttribute Album album, 
                                               BindingResult result,
                                               @LogonUser User logon) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        album.setChurchId(churchId);
        album.setAuthor(logon);
        churchAlbumEntityService.create(album);

        response.putData("albumId", album.getId());
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody Response editAlbum(@PathVariable int churchId,
                                            @PathVariable int entityId,
                                            @Validated({Update.class}) @ModelAttribute Album album, 
                                            BindingResult result, 
                                            Principal principal) {
        Response response = new Response();
        
        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.DATA_BINDING_ERROR);
            return response;
        }
        
        album.setId(entityId);
        album.setChurchId(churchId);
        churchAlbumEntityService.update(album);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.POST, produces = "application/json")
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
        
        reply.setPostingsId(entityId);
        reply.setChurchId(churchId);
        reply.setUser(activeUser);
        
        ((ChurchAlbumService) churchAlbumEntityService).addComment(reply);

        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#replyId, 'isReplyUser'))")
    @RequestMapping(value = "/album/{entityId}/{replyId}", method = RequestMethod.DELETE)
    public @ResponseBody Response deleteComment(@PathVariable int churchId,
                                                @PathVariable int entityId,
                                                @PathVariable int replyId,
                                                @LogonUser User activeUser) {
        Response response = new Response();
        
        ((ChurchAlbumService) churchAlbumEntityService).deleteComment(replyId, activeUser);
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}/photo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public @ResponseBody Response postPostAttachment(@PathVariable int churchId, 
                                                     @PathVariable int entityId,
                                                     @Validated PhotoFormBean photoFormBean,
                                                     BindingResult result,
                                                     @RequestParam("url") String url) {
        Response response = new Response();

        if(result.hasErrors()) {
            response.setStatusCode(StatusCode.NOT_ALLOWED_MEDIATYPE_FILESIZE);
            return response;
        }

        try {
            Attachment attach = ((ChurchAlbumService) churchAlbumEntityService).insertAttach(url, entityId, photoFormBean.getFile());
            response.putData("attachment", attach);
            
        } catch (ServiceException e) {
            response.setStatusCode(StatusCode.FAILED_CREATE_CONTENTS);
            return response;
        }
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}/photo/{attachId}", method = RequestMethod.DELETE)
    public @ResponseBody StatusCode deleteAttach(@PathVariable int churchId, 
                                                 @PathVariable int entityId, 
                                                 @PathVariable int attachId) {
        
        StatusCode statusCode = StatusCode.OK;
        
        logger.debug("{}", "delete id : " + entityId);
        logger.debug("{}", "delete attach id : " + attachId);
        try {
            ((ChurchAlbumService) churchAlbumEntityService).deleteAttach(entityId, attachId);
        }
        catch (Exception e) {
            logger.error("occurred exception during attachment delete.");
            statusCode = StatusCode.FAILED_DELETE_CONTENTS;
        }
        
        return statusCode;
    }
}
