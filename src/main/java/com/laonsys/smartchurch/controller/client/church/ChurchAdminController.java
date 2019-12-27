package com.laonsys.smartchurch.controller.client.church;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.laonsys.smartchurch.domain.PhotoFormBean;
import com.laonsys.smartchurch.domain.church.ChurchMember;
import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchMemberService;
import com.laonsys.smartchurch.service.ChurchMetaService;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@SessionAttributes("updateBean")
public class ChurchAdminController {
    private transient Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired private ChurchService churchService;
    @Autowired private ChurchOrgService churchOrgService;
    @Autowired private ChurchMetaService churchMetaService;
    @Autowired private ChurchMemberService churchMemberService;
    
    @ModelAttribute
    public void getChurch(@PathVariable int churchId, ModelMap model) {
        
        OurChurch ourChurch = churchService.selectOne(churchId);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        int memberCount = churchMemberService.count(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
        model.addAttribute("memberCount", memberCount);
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin", method = RequestMethod.GET)
    public String main(@PathVariable int churchId, ModelMap model) {

        OurChurch ourChurch = (OurChurch) model.get("ourChurch");
        
        Long total = churchService.totalAttachSize(ourChurch.getPath(), false);
        Long media = churchService.totalAttachSize(ourChurch.getPath(), true);
        
        
        total = (total != null) ? total/(1024*1024) : 0;
        media = (media != null) ? media/(1024*1024) : 0;
        
        model.put("totalSize", total);
        model.put("mediaSize", media);
        
        return "/church/admin/main";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/editChurch", method = RequestMethod.GET)
    public String editChurch(@PathVariable int churchId, ModelMap model) {

        OurChurch ourChurch = (OurChurch) model.get("ourChurch");
        
        model.put("updateBean", ourChurch.getChurchMeta());
        return "/church/admin/editChurch";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/editChurch", method = RequestMethod.PUT)
    public String putChurch(@PathVariable int churchId, @Validated(Update.class) @ModelAttribute("updateBean") ChurchMeta updateBean, BindingResult result, SessionStatus status) {
        
        if(result.hasErrors()) {
            return "/church/admin/editChurch";
        }
        
        churchMetaService.update(updateBean);
        status.setComplete();
        
        return "redirect:/church/" + churchId + "/admin";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/editMain", method = RequestMethod.GET)
    public String editMainImage(@PathVariable int churchId, ModelMap model) {
        List<Attachment> mainImages = churchService.selectMainImages(churchId);
        model.addAttribute("mainImages", mainImages);
        model.addAttribute("logoFormBean", new PhotoFormBean());
        return "/church/admin/editMain";
    }
    
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/editLogo", method = RequestMethod.POST)
    public String editLogo(@PathVariable int churchId, 
                           @Validated(Update.class) @ModelAttribute("logoFormBean") PhotoFormBean photoFormBean, 
                           BindingResult result,
                           ModelMap model) {
        
        if(result.hasErrors()) {
            return "/church/admin/editMain";
        }
        
        OurChurch ourChurch = (OurChurch) model.get("ourChurch");
        
        churchService.updateLogo(ourChurch, photoFormBean);
        
        return "redirect:/church/" + churchId + "/admin/editMain";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/editMain", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public @ResponseBody String ajaxPostMainImage(@PathVariable int churchId,
                                                  @Validated PhotoFormBean photoFormBean, 
                                                  BindingResult result,
                                                  ModelMap model) {
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
            OurChurch ourChurch = (OurChurch) model.get("ourChurch");
            
            Attachment attach = churchService.insertMainImage(ourChurch.getPath(), churchId, photoFormBean.getFile());

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
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/editMain/{mainPhotoId}", method = RequestMethod.DELETE)
    public @ResponseBody Map<String, Boolean> deleteAttach(@PathVariable int churchId, @PathVariable int mainPhotoId) {
        
        logger.debug("{}", "delete id : " + mainPhotoId);
        
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        try {
            churchService.deleteMainImage(mainPhotoId);
            result.put("result", true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
        }
        
        return result;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/members", method = RequestMethod.GET)
    public String getManageMember(@PathVariable int churchId, @WebQuery QueryParam queryParam, ModelMap model) {
        queryParam.addParam("churchId", churchId);
        List<ChurchMember> list = churchMemberService.selectList(queryParam);
        model.put("list", list);
        model.put("paginate", queryParam.getPaginate());
        model.put("queryParam", queryParam);
        return "/church/admin/listMember";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/church/{churchId}/admin/members/delete", method = RequestMethod.POST)
    public String postDeleteMembers(@PathVariable int churchId,  @RequestParam("deleteItems") int[] ids) {
        
        churchMemberService.delete(churchId, ids);
        
        return "redirect:/church/" + churchId + "/admin/members";
    }
}
