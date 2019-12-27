package com.laonsys.smartchurch.utiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchMemberService;
import com.laonsys.smartchurch.service.ChurchService;

public class ChurchMemberPermission implements Permission {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private ChurchService churchService;
    @Autowired private ChurchMemberService churchMemberService;
    
    @Override
    public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
        boolean hasPermission = false;
        if (isAuthenticated(authentication) && isChurchId(targetDomainObject)) {
            OurChurch ourChurch = churchService.selectOne((Integer) targetDomainObject);
            
            if(ourChurch == null) {
                if(logger.isDebugEnabled()) {
                    logger.debug("{}", (Integer) targetDomainObject);
                }
            } else {
                User login = getLogin(authentication);
                if(login != null) {
                    hasPermission = churchMemberService.isMember(ourChurch.getId(), login.getId());
                }
            }
        }
        return hasPermission;
    }
    
    public void setChurchService(ChurchService churchService) {
        this.churchService = churchService;
    }
    
    private User getLogin(Authentication authentication) {
        Object authenticationUserDetails = authentication.getPrincipal();
        return (User) authenticationUserDetails;
    }
    
    private boolean isChurchId(Object targetDomainObject) {
        return targetDomainObject instanceof Integer && (Integer) targetDomainObject > 0;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() instanceof UserDetails;
    }
}
