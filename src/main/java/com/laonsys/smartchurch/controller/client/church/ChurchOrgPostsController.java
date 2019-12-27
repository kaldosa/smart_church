package com.laonsys.smartchurch.controller.client.church;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OrgPosts;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchOrgPostsService;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{churchId}/org")
@SessionAttributes("entity")
public class ChurchOrgPostsController {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    @Autowired @Qualifier("churchOrgPostsEntityService") private GenericService<OrgPosts> churchOrgPostsEntityService;
    @Autowired private ReplyService replyService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int churchId, ModelMap model) {
        OurChurch ourChurch = churchService.selectOne(churchId);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getList(@PathVariable int churchId, @WebQuery QueryParam queryParam, ModelMap model) {

        List<OrgPosts> posts = null;
        ChurchOrg churchOrg = null;
        
        @SuppressWarnings("unchecked")
        List<ChurchOrg> listOrg = (List<ChurchOrg>) model.get("listOrg");
        if(listOrg.size() > 0) {
            churchOrg = listOrg.get(0);
            
            queryParam.addParam("orgId", churchOrg.getId());
            posts = churchOrgPostsEntityService.selectList(queryParam);
        }
        
        model.addAttribute("org", churchOrg);
        model.addAttribute("posts", posts);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/org/listPosts";
    }
    
    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    public String getList(@PathVariable int churchId, 
                          @PathVariable int orgId, 
                          @WebQuery QueryParam queryParam,
                          ModelMap model) {
        
        ChurchOrg churchOrg = churchOrgService.selectOne(orgId);
        
        queryParam.addParam("orgId", churchOrg.getId());
        List<OrgPosts> posts = churchOrgPostsEntityService.selectList(queryParam);
        
        model.addAttribute("org", churchOrg);
        model.addAttribute("posts", posts);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/org/listPosts";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{orgId}/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int churchId, 
                          @PathVariable int orgId, 
                          @PathVariable int entityId, 
                          @WebQuery QueryParam searchCriteria,
                          ModelMap model) {
        
        OrgPosts orgPosts = churchOrgPostsEntityService.selectOne(entityId);

        if(orgPosts == null) {
            return "redirect:/church/" + churchId + "/org";
        }

        ((UpdateHitsService) churchOrgPostsEntityService).updateHits(orgPosts.getHits() + 1, entityId);
        
        ChurchOrg churchOrg = churchOrgService.selectOne(orgId);
        
        model.addAttribute("org", churchOrg);
        model.addAttribute("orgPosts", orgPosts);
        model.addAttribute("comment", new BaseComments());
        model.addAttribute("searchCriteria", searchCriteria);
        
        return "/church/org/viewPosts";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/{orgId}/{entityId}", method = RequestMethod.POST)
    public String addComment(@PathVariable int churchId,
                             @PathVariable int orgId,
                             @PathVariable int entityId,
                             @ModelAttribute("comment") BaseComments comment,
                             BindingResult result,
                             @LogonUser User activeUser) {
        
        if(result.hasErrors()) {
            return "/church/org/viewPosts";
        }
        
        comment.setChurchId(churchId);
        comment.setUser(activeUser);        
        comment.setPostingsId(entityId);
        ((ChurchOrgPostsService) churchOrgPostsEntityService).addComment(comment);
        
        return "redirect:/church/" + churchId + "/org/" + orgId + "/" + entityId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#commentId, 'isReplyUser'))")
    @RequestMapping(value = "/{orgId}/{entityId}/{commentId}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable int churchId,
                                @PathVariable int orgId,
                                @PathVariable int entityId,
                                @PathVariable int commentId,
                                @LogonUser User activeUser) {
        
        replyService.delete(commentId, "church_org_posts_comments", activeUser);

        return "redirect:/church/" + churchId + "/org/" + orgId + "/" + entityId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/{orgId}/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int churchId, @PathVariable int orgId, ModelMap model) {

        ChurchOrg churchOrg = churchOrgService.selectOne(orgId);
        model.addAttribute("org", churchOrg);
        model.addAttribute("entity", new OrgPosts());
        
        return "/church/org/createPosts";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/{orgId}", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int churchId,
                                 @PathVariable int orgId,
                                 @Validated({Create.class}) @ModelAttribute("entity") OrgPosts orgPosts, 
                                 BindingResult result, 
                                 ModelMap model,
                                 SessionStatus sessionStatus,
                                 @LogonUser User activeUser) {

        if(result.hasErrors()) {
            return "/church/org/createPosts";
        }
        
        orgPosts.setUser(activeUser);        
        orgPosts.setOrgId(orgId);
        churchOrgPostsEntityService.create(orgPosts);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/org/" + orgId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isOrgPostsWriter'))")
    @RequestMapping(value = "/{orgId}/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int churchId, @PathVariable int orgId, @PathVariable int entityId, ModelMap model, @LogonUser User activeUser) {
        
        OrgPosts orgPosts = churchOrgPostsEntityService.selectOne(entityId);
        
        ChurchOrg churchOrg = churchOrgService.selectOne(orgId);
        
        model.addAttribute("org", churchOrg);
        model.addAttribute("entity", orgPosts);
        
        return "/church/org/editPosts";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isOrgPostsWriter'))")
    @RequestMapping(value = "/{orgId}/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int churchId, 
                              @PathVariable int orgId,
                              @PathVariable int entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") OrgPosts orgPosts,
                              BindingResult result) {
        
        if(result.hasErrors()) {
            return "/church/org/editPosts";
        }
        
        orgPosts.setId(entityId);
        orgPosts.setOrgId(orgId);
        churchOrgPostsEntityService.update(orgPosts);
        
        return "redirect:/church/" + churchId + "/org/" + orgId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isOrgPostsWriter'))")
    @RequestMapping(value = "/{orgId}/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int churchId,
                         @PathVariable int orgId,
                         @PathVariable int entityId, @LogonUser User activeUser) {
        
        logger.debug("{}", "delete id : " + entityId);
        
        churchOrgPostsEntityService.delete(entityId);
        
        return "redirect:/church/" + churchId + "/org/" + orgId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/{orgId}/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int churchId,
                          @PathVariable int orgId,
                          @RequestParam("deleteItems") int[] ids) {
        
        logger.debug("{}", "delete ids : " + ids);
        
        churchOrgPostsEntityService.delete(ids);
        
        return "redirect:/church/" + churchId + "/org/" + orgId;
    }
}