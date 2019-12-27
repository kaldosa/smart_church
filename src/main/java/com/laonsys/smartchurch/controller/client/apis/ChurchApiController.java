package com.laonsys.smartchurch.controller.client.apis;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.ChurchMember;
import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchMemberService;
import com.laonsys.smartchurch.service.ChurchMetaService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Controller
@RequestMapping("/apis/church")
@Slf4j
public class ChurchApiController {

    @Autowired private ChurchService churchService;
    @Autowired private ChurchMetaService churchMetaService;
    @Autowired private ChurchMemberService churchMemberService;
    
    @RequestMapping(value="/", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody Response listChurch(@WebQuery QueryParam queryParam) {
        Response response = new Response();
        
        List<ChurchMeta> list = churchMetaService.selectList(queryParam);
        
        if(list == null || list.isEmpty()) {
            log.warn("[listChurch] There are no results that match criteria. {}", queryParam);
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.setStatusCode(StatusCode.OK);
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    @RequestMapping(value = "/service", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody Response listService(@WebQuery QueryParam queryParam) {
        Response response = new Response();
        
        List<OurChurch> list = churchService.selectList(queryParam);
        
        if(list == null || list.isEmpty()) {
            log.warn("[listService] There are no results that match criteria. {}", queryParam);
            response.setStatusCode(StatusCode.NO_CONTENTS);
            return response;
        }
        
        response.setStatusCode(StatusCode.OK);
        response.putData("list", list);
        response.putData("paginate", queryParam.getPaginate());
        
        return response;
    }
    
    
    @RequestMapping(value = "/service/{churchId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Response getMap(@PathVariable int churchId) {
        Response response = new Response();

        OurChurch church = churchService.selectOne(churchId);
        
        if(church == null) {
            log.warn("There is no valid service ID. {}", churchId);
            response.setStatusCode(StatusCode.INVALID_SERVICE_ID);
            return response;
        }
        
        response.setStatusCode(StatusCode.OK);
        response.putData("church", church);
        
        return response;
    }
    
    @RequestMapping(value = "/service/{churchId}/join-member", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody StatusCode joinMember(@PathVariable int churchId, @LogonUser User logon) {
        StatusCode status = StatusCode.OK;

        OurChurch church = churchService.selectOne(churchId);
        
        if(church == null) {
            log.warn("There is no valid service ID. {}", churchId);
            return StatusCode.INVALID_SERVICE_ID;
        }
        
        if(logon == null) {
            log.warn("There is no login information. logonUser must not null.");
            status = StatusCode.USER_NOT_FOUND;
        } else {
            ChurchMember member = new ChurchMember();
            member.setChurch(churchId);
            member.setMember(logon);
            
            churchMemberService.create(member);
        }
        
        return status;
    }
}
