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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.PhotoFormBean;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.Album;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchAlbumService;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{churchId}")
@SessionAttributes("entity")
public class ChurchAlbumController {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired @Qualifier("churchAlbumEntityService") GenericService<Album> churchAlbumEntityService;
    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int churchId, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(churchId);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "/album", method = RequestMethod.GET)
    public String getList(@PathVariable int churchId, @WebQuery QueryParam queryParam, Model model) {
        
        queryParam.addParam("churchId", churchId);
        queryParam.getPaginate().setItemPerPage(9);
        
        List<Album> list = churchAlbumEntityService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/album/listAlbum";
    }
    
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int churchId, 
                          @PathVariable int entityId, 
                          @WebQuery QueryParam queryParam, 
                          Model model) {
        Album album = churchAlbumEntityService.selectOne(entityId);

        if(album == null) {
            return "redirect:/church/" + churchId + "/album";
        }
        
        model.addAttribute("entity", album);
        model.addAttribute("comment", new BaseComments());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/album/viewAlbum";
    }

    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.POST)
    public String addComment(@PathVariable int churchId,
                             @PathVariable int entityId,
                             @ModelAttribute("comment") BaseComments comment,
                             BindingResult result,
                             @LogonUser User activeUser) {
        
        if(result.hasErrors()) {
            return "/church/album/viewAlbum";
        }
        
        comment.setChurchId(churchId);
        comment.setUser(activeUser);
        comment.setPostingsId(entityId);
        ((ChurchAlbumService) churchAlbumEntityService).addComment(comment);
        
        return "redirect:/church/" + churchId + "/album/" + entityId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#commentId, 'isReplyUser'))")
    @RequestMapping(value = "/album/{entityId}/{commentId}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable int churchId,
                                @PathVariable int entityId,
                                @PathVariable int commentId,
                                @LogonUser User activeUser) {
        
        ((ChurchAlbumService) churchAlbumEntityService).deleteComment(commentId, activeUser);
        
        return "redirect:/church/" + churchId + "/album/" + entityId;
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/album/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int churchId, Model model) {
        model.addAttribute("entity", new Album());
        return "/church/album/createAlbum";
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/album", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int churchId, 
                                 @Validated({Create.class}) @ModelAttribute("entity") Album album, 
                                 BindingResult result,
                                 SessionStatus status,
                                 @LogonUser User logon,
                                 Model model) {
        
        if(result.hasErrors()) {
            return "/church/album/createAlbum";
        }

        album.setChurchId(churchId);
        album.setAuthor(logon);
        churchAlbumEntityService.create(album);
        status.setComplete();
        
        return "redirect:/church/" + churchId + "/album/" + album.getId() + "/photo";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}/photo", method = RequestMethod.GET)
    public String getAddAlbum(@PathVariable int churchId, @PathVariable int entityId, Model model) {
        Album album = churchAlbumEntityService.selectOne(entityId);
        if(album == null) {
            return "redirect:/church/" + churchId;
        }
        
        model.addAttribute("photos", album.getAttachments());
        
        return "/church/album/addAlbum";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}/photo", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public @ResponseBody String ajaxPostAttachment(@PathVariable int churchId, 
                                                   @PathVariable int entityId,
                                                   @Validated PhotoFormBean photoFormBean,
                                                   BindingResult result,
                                                   @RequestParam("url") String url) {
        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<String, Object>();

        if(result.hasErrors()) {
                response.put("error", "허용되지 않는 미디어타입 또는 파일크기입니다.");
                response.put("name", photoFormBean.getFile().getOriginalFilename());
                response.put("size", photoFormBean.getFile().getSize());
                response.put("url", "");
                response.put("thumbnail_url", "");
                response.put("delete_url", "");
                response.put("delete_type", "DELETE");

            return "[" + gson.toJson(response) + "]";
        }

        try {
            Attachment attach = ((ChurchAlbumService) churchAlbumEntityService).insertAttach(url, entityId, photoFormBean.getFile());

            response.put("name", photoFormBean.getFile().getOriginalFilename());
            response.put("size", photoFormBean.getFile().getSize());
            response.put("url", attach.getPath());
            response.put("thumbnail_url", attach.getPath());
            String deleteUrl = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURL() + "/" + attach.getId();
            response.put("delete_url", deleteUrl);
            response.put("delete_type", "DELETE");
            
        } catch (ServiceException e) {
            response.put("error", "업로드를 하지 못하였습니다.");
            response.put("name", photoFormBean.getFile().getOriginalFilename());
            response.put("size", photoFormBean.getFile().getSize());
            response.put("url", "");
            response.put("thumbnail_url", "");
            response.put("delete_url", "");
            response.put("delete_type", "DELETE");
            
            return "[" + gson.toJson(response) + "]";
        }
        
        return "[" + gson.toJson(response) + "]";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int churchId, @PathVariable int entityId, Model model) {

        Album album = churchAlbumEntityService.selectOne(entityId);
        
        model.addAttribute("entity", album);
        
        return "/church/album/editAlbum";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int churchId, @PathVariable int entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") Album album, 
                              BindingResult result,
                              SessionStatus status) {
        
        if(result.hasErrors()) {
            logger.debug("{}", result);
            return "/church/album/editAlbum";
        }
        
        album.setId(entityId);
        churchAlbumEntityService.update(album);
        
        return "redirect:/church/" + churchId + "/album";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}/photo/{attachId}", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Boolean> deleteAttach(@PathVariable int churchId, @PathVariable int entityId, @PathVariable("attachId") int attachId) {
        
        logger.debug("{}", "delete id : " + entityId);
        logger.debug("{}", "delete attach id : " + attachId);
        
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        try {
            ((ChurchAlbumService) churchAlbumEntityService).deleteAttach(entityId, attachId);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
        }
        
        return result;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isAlbumAuthor'))")
    @RequestMapping(value = "/album/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int churchId, @PathVariable int entityId) {
        
        logger.debug("{}", "delete id : " + entityId);
        
        try {
            churchAlbumEntityService.delete(entityId);
        } catch (ServiceException se) {
            logger.debug("{}", se.getMessage());
        }
        
        return "redirect:/church/" + churchId + "/album";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/album/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int churchId, @RequestParam("deleteItems") int[] ids) {
        
        logger.debug("{}", "delete ids : " + ids);
        
        churchAlbumEntityService.delete(ids);
        
        return "redirect:/church/" + churchId + "/album";
    }
}
