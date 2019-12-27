package com.laonsys.smartchurch.controller.client.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.church.ChurchIntro;
import com.laonsys.smartchurch.domain.church.History;
import com.laonsys.smartchurch.domain.church.Server;
import com.laonsys.smartchurch.domain.church.Worship;
import com.laonsys.smartchurch.service.ChurchInformationService;

@Controller
@RequestMapping("/apis/church/service/{churchId}")
public class ChurchIntroApiController {

    @Autowired private ChurchInformationService churchInformationService;
    
    @RequestMapping(value = "/intro", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getIntro(@PathVariable int churchId) {
        Response response = new Response();
        ChurchIntro intro = churchInformationService.getIntro(churchId);
        if(intro == null) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.putData("intro", intro);
        return response;
    }
    
    @RequestMapping(value = "/history", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getHistory(@PathVariable int churchId) {
        Response response = new Response();
        List<History> list = churchInformationService.getHistories(churchId);
        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        response.putData("list", list);
        return response;
    }
    
    
    @RequestMapping(value = "/worship", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getWorship(@PathVariable int churchId) {
        Response response = new Response();
        List<Worship> list = churchInformationService.getWorships(churchId);
        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        response.putData("list", list);
        return response;
    }
    
    @RequestMapping(value = "/server", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getServe(@PathVariable int churchId) {
        Response response = new Response();
        List<Server> list = churchInformationService.getServers(churchId);
        if(list == null || list.isEmpty()) {
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        response.putData("list", list);
        return response;
    }
}
