package com.laonsys.springmvc.extensions.bind.support;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.laonsys.springmvc.extensions.bind.annotation.SessionParam;

public class SessionParamArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        Annotation[] paramAnns = methodParameter.getParameterAnnotations();
        Class<?> paramType = methodParameter.getParameterType();

        for (Annotation paramAnn : paramAnns) {
            if (SessionParam.class.isInstance(paramAnn)) {

                SessionParam sessionParam = (SessionParam) paramAnn;
                String paramName = sessionParam.value();
                boolean required = sessionParam.required();
                String defaultValue = sessionParam.defaultValue();
                HttpServletRequest httprequest = (HttpServletRequest) webRequest.getNativeRequest();
                HttpSession session = httprequest.getSession(false);

                Object result = null;
                if (session != null) {
                    result = session.getAttribute(paramName);
                }
                if (result == null)
                    result = defaultValue;
                if (result == null && required && session == null)
                    raiseSessionRequiredException(paramName, paramType);
                if (result == null && required)
                    raiseMissingParameterException(paramName, paramType);

                return result;
            }
        }

        return WebArgumentResolver.UNRESOLVED;
    }

    protected void raiseMissingParameterException(String paramName, Class<?> paramType) throws Exception {
        throw new IllegalStateException("Missing parameter '" + paramName + "' of type [" + paramType.getName() + "]");
    }

    protected void raiseSessionRequiredException(String paramName, Class<?> paramType) throws Exception {
        throw new HttpSessionRequiredException("No HttpSession found for resolving parameter '" + paramName + "' of type [" + paramType.getName() + "]");
    }
}
