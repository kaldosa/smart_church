package com.laonsys.smartchurch.utiles;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.laonsys.smartchurch.domain.Authority;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchService;

public class OurChurchAdminPermission implements Permission {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired private ChurchService churchService;
    
    @Override
    public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
        boolean hasPermission = false;
        if (isAuthenticated(authentication) && isOurChurchId(targetDomainObject)) {
            OurChurch ourChurch = churchService.selectOne((Integer) targetDomainObject);
            
            if(ourChurch == null) {
                if(logger.isDebugEnabled()) {
                    logger.debug("{}", (Integer) targetDomainObject);
                }
            }
            
            String churchAdminRole = "ROLE_" + ourChurch.getPath();
            
            User login = getLogin(authentication);

            @SuppressWarnings("unchecked")
            Collection<Authority> userAuthorities = (Collection<Authority>) login.getAuthorities();
            for(Authority authority : userAuthorities) {
                String role = authority.getRole();
                if(role.equalsIgnoreCase("ROLE_ADMIN") || role.equalsIgnoreCase(churchAdminRole)) {
                    hasPermission = true;
                    break;
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
    
    private boolean isOurChurchId(Object targetDomainObject) {
        return targetDomainObject instanceof Integer && (Integer) targetDomainObject > 0;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() instanceof UserDetails;
    }
}
