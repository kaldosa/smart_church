package com.laonsys.springmvc.extensions.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextHelper {
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
    
    public static String getContextPath() {
        return getCurrentRequest().getContextPath();
    }
    
    public static String getRealPath(String path) {
        return getCurrentRequest().getSession().getServletContext().getRealPath(path);
    }
}
