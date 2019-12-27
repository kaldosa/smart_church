package com.laonsys.smartchurch.utiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.Album;
import com.laonsys.springmvc.extensions.service.GenericService;

public class AlbumPermission implements Permission {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired @Qualifier("churchAlbumEntityService") GenericService<Album> churchAlbumEntityService;

    @Override
    public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
        boolean hasPermission = false;
        if (isAuthenticated(authentication) && isOurChurchId(targetDomainObject)) {
            Album album = churchAlbumEntityService.selectOne((Integer) targetDomainObject);

            if (album == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{}", (Integer) targetDomainObject);
                }
            }

            User login = getLogin(authentication);

            hasPermission = (login.getId() == album.getAuthor().getId());
        }
        return hasPermission;
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
