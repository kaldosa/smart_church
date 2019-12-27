package com.laonsys.smartchurch.utiles;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerMapping;

public class ChurchAccessDeniedHandler implements AccessDeniedHandler {

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);;
        String churchId = (String) pathVariables.get("churchId");
        if(churchId != null) {
            response.sendRedirect(request.getContextPath() + "/church/" + churchId + "/access-denied?url=" + path);
        } else {
            response.sendRedirect(request.getContextPath() + "/church/denied");
        }
    }
}
