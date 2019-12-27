package com.laonsys.smartchurch.controller.client.church;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.context.request.WebRequest;

import com.laonsys.smartchurch.domain.church.ChurchIntro;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.History;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.Server;
import com.laonsys.smartchurch.domain.church.Worship;
import com.laonsys.smartchurch.service.ChurchInformationService;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.utils.AjaxUtils;
import com.laonsys.springmvc.extensions.utils.StringUtils;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@Controller
@RequestMapping("/church/{churchId}")
@SessionAttributes({"churchIntro"})
public class ChurchIntroController {
    
    @Autowired private ChurchService churchService;
    @Autowired private ChurchInformationService churchInformationService;
    @Autowired private ChurchOrgService churchOrgService;

    @ModelAttribute
    public void ajaxAttribute(WebRequest request, Model model) {
        model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
    }
    
    @ModelAttribute
    public void getChurch(@PathVariable int churchId, Model model) {
        
        OurChurch ourChurch = churchService.selectOne(churchId);
        QueryParam queryParam = new QueryParam();
        queryParam.addParam("churchId", churchId);
        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
        
        model.addAttribute("listOrg", listOrg);
        model.addAttribute("ourChurch", ourChurch);
    }
    
    @RequestMapping(value = "/intro", method = RequestMethod.GET)
    public String intro(@PathVariable int churchId, Model model) {
        
        ChurchIntro intro = churchInformationService.getIntro(churchId);
        model.addAttribute("intro", intro);
        
        return "/church/info/viewIntro";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/intro/new", method = RequestMethod.GET)
    public String createIntro(@PathVariable int churchId, Model model) {
        
        model.addAttribute("churchIntro", new ChurchIntro());

        return "/church/info/createIntro";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/intro", method = RequestMethod.POST)
    public String postIntro(@PathVariable int churchId, 
                            @Validated(Create.class) @ModelAttribute("churchIntro") ChurchIntro intro, 
                            BindingResult result,
                            SessionStatus sessionStatus) {
        
        if(result.hasErrors()) {
            return "/church/info/createIntro";
        }

        churchInformationService.addIntro(intro);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/intro";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/intro/edit", method = RequestMethod.GET)
    public String editIntro(@PathVariable int churchId, Model model) {
        
        ChurchIntro intro = churchInformationService.getIntro(churchId);
        model.addAttribute("churchIntro", intro);
        
        return "/church/info/editIntro";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/intro", method = RequestMethod.PUT)
    public String editIntro(@PathVariable int churchId,
                            @Validated(Update.class) @ModelAttribute("churchIntro") ChurchIntro intro, 
                            BindingResult result,
                            SessionStatus sessionStatus) {
        
        if(result.hasErrors()) {
            return "/church/info/editIntro";
        }
        
        churchInformationService.updateIntro(intro);
        sessionStatus.setComplete();
        
        return "redirect:/church/" + churchId + "/intro";
    }
    
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String history(@PathVariable int churchId, Model model) {
       
        List<History> histories = churchInformationService.getHistories(churchId);
        
        TreeSet<Integer> years = new TreeSet<Integer>();

        for(History history : histories) {
            years.add(history.getDate().getYear());
        }
        
        model.addAttribute("years", years.descendingSet());
        model.addAttribute("histories", histories);
        
        return "/church/info/viewHistory";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/history/edit", method = RequestMethod.GET)
    public String editHistory(@PathVariable int churchId, @ModelAttribute History history, Model model) {
        
        List<History> histories = churchInformationService.getHistories(churchId);
        model.addAttribute("histories", histories);
        
        return "/church/info/editHistory";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> ajaxAddHistory(@PathVariable int churchId, 
                                                 @Validated @ModelAttribute History history, 
                                                 BindingResult result, 
                                                 ModelMap model) {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(result.hasErrors()) {
            return null;
        }
        
        history.setChurchId(churchId);
        churchInformationService.addHistory(history);
        
        response.put("id", history.getId());
        response.put("date", StringUtils.dateTimeToString(history.getDate(), "yyyy.MM.dd"));
        response.put("content", history.getContents());
        
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/history/{historyId}", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteHistory(@PathVariable int churchId, @PathVariable("historyId") int historyId) {
        churchInformationService.deleteHistory(historyId);
    }
    
    @RequestMapping(value = "/worship", method = RequestMethod.GET)
    public String worship(@PathVariable int churchId, Model model) {
        
        List<Worship> worships = churchInformationService.getWorships(churchId);
        
        model.addAttribute("worships", worships);
        
        return "/church/info/viewWorship";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/worship/edit", method = RequestMethod.GET)
    public String editWorship(@PathVariable int churchId, @ModelAttribute Worship worship, Model model) {
        
        List<Worship> worships = churchInformationService.getWorships(churchId);
        model.addAttribute("worships", worships);
        
        return "/church/info/editWorship";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/worship", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> ajaxAddWorship(@PathVariable int churchId, 
                                                 @Validated @ModelAttribute Worship worship, 
                                                 BindingResult result, 
                                                 ModelMap model) {
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(result.hasErrors()) {
            return null;
        }
        
        worship.setChurchId(churchId);
        churchInformationService.addWorship(worship);
        
        response.put("id", worship.getId());
        response.put("title", worship.getTitle());
        response.put("dayOfWeek", worship.getDayOfWeek().getValue());
        response.put("time", worship.getTime());
        response.put("place", worship.getPlace());
        return response;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/worship", method = RequestMethod.PUT)
    public String updateWorship(@PathVariable int churchId, @RequestParam("order") List<Integer> order) {

        if(!order.isEmpty()) {
            int size = order.size();
            List<Worship> worships = new ArrayList<Worship>();
            for(int i = 0; i < size; i++) {
                Worship worship = new Worship();
                worship.setId(order.get(i));
                worship.setSort(i);
                worships.add(worship);
            }
            
            churchInformationService.updateWorships(worships);
        }
        
        return "redirect:/church/" + churchId + "/worship";
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/worship/{worshipId}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody String deleteWorship(@PathVariable int churchId, @PathVariable("worshipId") int worshipId) {
        churchInformationService.deleteWorship(worshipId);
        return null;
    }
    
    @RequestMapping(value = "/serve", method = RequestMethod.GET)
    public String serve(@PathVariable int churchId, Model model) {
        
        List<Server> servers = churchInformationService.getServers(churchId);
        model.addAttribute("servers", servers);
        return "/church/info/viewServe";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/serve/edit", method = RequestMethod.GET)
    public String editServe(@PathVariable int churchId, @ModelAttribute Server server, Model model) {
        List<Server> servers = churchInformationService.getServers(churchId);
        model.addAttribute("servers", servers);
        
        return "/church/info/editServe";
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/serve", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Server ajaxAddServer(@PathVariable int churchId, 
                              @Validated @ModelAttribute Server server, 
                              BindingResult result) throws IOException {

        if(result.hasErrors()) {
            return null;
        }
        
        server.setChurchId(churchId);
        churchInformationService.addServer(server);
        
        return server;
    }

    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/serve/{serverId}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody String deleteServer(@PathVariable int churchId, @PathVariable("serverId") int serverId) {
        churchInformationService.deleteServer(serverId);
        return null;
    }
    
    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
    @RequestMapping(value = "/serve", method = RequestMethod.PUT)
    public String updateServer(@PathVariable int churchId, @RequestParam("order") List<Integer> order) {
        
        if(!order.isEmpty()) {
            int size = order.size();
            List<Server> servers = new ArrayList<Server>();
            for(int i = 0; i < size; i++) {
                Server server = new Server();
                server.setId(order.get(i));
                server.setSort(i);
                servers.add(server);
            }
            
            churchInformationService.updateServers(servers);
        }
        
        return "redirect:/church/" + churchId + "/serve";
    }    
    
    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String map(@PathVariable int churchId, Model model) {
        return "/church/info/viewMap";
    }
}