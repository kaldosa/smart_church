package com.laonsys.smartchurch.controller.client.church;

import java.util.List;

import javax.validation.groups.Default;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.Pray;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchPrayService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{churchId}")
@SessionAttributes("entity")
public class ChurchPrayController {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired @Qualifier("churchPrayEntityService") GenericService<Pray> churchPrayEntityService;
    @Autowired private ReplyService replyService;
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
    
    @RequestMapping(value = "/pray", method = RequestMethod.GET)
    public String getList(@PathVariable int churchId, @WebQuery QueryParam queryParam, Model model) {

        queryParam.addParam("id", churchId);
        List<Pray> list = churchPrayEntityService.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        return "/church/pray/listPray";
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int churchId, @PathVariable("entityId") int entityId, @WebQuery QueryParam queryParam, Model model) {
        
        Pray pray = churchPrayEntityService.selectOne(entityId);

        if(pray == null) {
            return "redirect:/church/" + churchId + "/pray";
        }
        
        model.addAttribute("entity", pray);
        model.addAttribute("comment", new BaseComments());
        model.addAttribute("queryParam", queryParam);

        ((UpdateHitsService)churchPrayEntityService).updateHits(pray.getHits() + 1, entityId);
        
        return "/church/pray/viewPray";
    }

    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.POST)
    public String addComment(@PathVariable int churchId,
                             @PathVariable int entityId,
                             @ModelAttribute("comment") BaseComments comment,
                             BindingResult result,
                             @LogonUser User activeUser) {
        
        if(result.hasErrors()) {
            return "/church/pray/viewPray";
        }
        
        comment.setChurchId(churchId);
        comment.setUser(activeUser);
        comment.setPostingsId(entityId);
        ((ChurchPrayService) churchPrayEntityService).addComment(comment);
        
        return "redirect:/church/" + churchId + "/pray/" + entityId;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#commentId, 'isReplyUser'))")
    @RequestMapping(value = "/pray/{entityId}/{commentId}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable int churchId,
                                @PathVariable int entityId,
                                @PathVariable int commentId,
                                @LogonUser User activeUser) {
        
        if(activeUser != null) {
            replyService.delete(commentId, "church_pray_comments", activeUser);
        }
        
        return "redirect:/church/" + churchId + "/pray/" + entityId;
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/pray/new", method = RequestMethod.GET)
    public String getCreateForm(@PathVariable int churchId, Model model) {
        model.addAttribute("entity", new Pray());
        return "/church/pray/createPray";
    }
    
    @PreAuthorize("isAuthenticated() and (hasPermission(#churchId, 'isChurchMember') or hasPermission(#churchId, 'isOurChurchAdmin'))")
    @RequestMapping(value = "/pray", method = RequestMethod.POST)
    public String postCreateForm(@PathVariable int churchId, 
                                 @Validated({Default.class}) @ModelAttribute("entity") Pray pray, 
                                 BindingResult result, 
                                 Model model,
                                 SessionStatus sessionStatus,
                                 @LogonUser User activeUser) {

        if(result.hasErrors()) {
            return "/church/pray/createPray";
        }
        
        pray.setChurchId(churchId);
        pray.setUser(activeUser);
        
        churchPrayEntityService.create(pray);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/pray";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isPrayWriter'))")
    @RequestMapping(value = "/pray/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int churchId, @PathVariable int entityId, Model model, @LogonUser User activeUser) {

        Pray pray = churchPrayEntityService.selectOne(entityId);
        model.addAttribute("entity", pray);
        
        return "/church/pray/editPray";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isPrayWriter'))")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable int churchId, @PathVariable int entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") Pray pray,
                              BindingResult result,
                              SessionStatus sessionStatus) {
        
        if(result.hasErrors()) {
            return "/church/pray/editPray";
        }
        
        pray.setId(entityId);
        churchPrayEntityService.update(pray);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/pray";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin') or (hasPermission(#churchId, 'isChurchMember') and hasPermission(#entityId, 'isPrayWriter'))")
    @RequestMapping(value = "/pray/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int churchId, @PathVariable int entityId) {

        churchPrayEntityService.delete(entityId);
        
        return "redirect:/church/" + churchId + "/pray";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/pray/delete", method = RequestMethod.POST)
    public String deletes(@PathVariable int churchId, @RequestParam("deleteItems") int[] ids) {
        
        logger.debug("{}", "delete ids : " + ids);
        
        churchPrayEntityService.delete(ids);
        
        return "redirect:/church/" + churchId + "/pray";
    }
}