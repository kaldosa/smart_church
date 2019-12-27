package com.laonsys.springmvc.extensions.bind.support;

import java.lang.annotation.Annotation;
import java.security.Principal;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.springmvc.extensions.bind.annotation.LogonUser;

public class LogonUserArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        Annotation[] annotations = methodParameter.getParameterAnnotations();
        
        if(methodParameter.getParameterType().equals(User.class))
        {
            for(Annotation annotation : annotations)
            {
                if(LogonUser.class.isInstance(annotation))
                {
                    Principal principal = webRequest.getUserPrincipal();
                    
                    if(principal == null) return null;
                    
                    return (User)((Authentication) principal).getPrincipal();
                }
            }
        }
        
        return WebArgumentResolver.UNRESOLVED;
    }
}
