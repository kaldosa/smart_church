package com.laonsys.springmvc.extensions.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.nhncorp.lucy.security.xss.XssFilter;

public class XssFilteredRequest extends HttpServletRequestWrapper {

    private XssFilter xssFilter = XssFilter.getInstance();

    public XssFilteredRequest(HttpServletRequest request) {
        super(request);
    }

    private String xssFilter(String value) {
        return xssFilter.doFilter(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return xssFilter(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return xssFilter(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);

        if (values == null)
            return null;

        int length = values.length;
        String[] cleanValues = new String[length];

        for (int i = 0; i < length; i++) {
            cleanValues[i] = xssFilter(values[i]);
        }
        return cleanValues;
    }
}
