package com.laonsys.smartchurch.utiles;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.laonsys.smartchurch.domain.User;

public class SessionUtils {
	
	public static boolean hasAuthority(String role) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
	
		if(currentUser != null) {
			for(GrantedAuthority authority : currentUser.getAuthorities()) {
				if(role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null) return null;
		
		return (User) authentication.getPrincipal();
	}
	
	public static Authentication getAuthentication() {
	    return SecurityContextHolder.getContext().getAuthentication();
	}

}
