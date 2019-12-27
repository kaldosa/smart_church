package com.laonsys.smartchurch.utiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.service.ReplyService;

public class ReplyPermission implements Permission {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReplyService replyService;

    @Override
    public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
        boolean hasPermission = false;
        if (isAuthenticated(authentication) && isOurChurchId(targetDomainObject)) {
            BaseComments reply = replyService.selectOne((Integer) targetDomainObject, null);

            if (reply == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{}", (Integer) targetDomainObject);
                }
            }

            User login = getLogin(authentication);

            hasPermission = (login.getId() == reply.getUser().getId());
        }
        return hasPermission;
    }

    public void setReplyService(ReplyService replyService) {
        this.replyService = replyService;
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
