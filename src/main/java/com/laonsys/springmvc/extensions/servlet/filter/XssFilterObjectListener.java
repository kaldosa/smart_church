package com.laonsys.springmvc.extensions.servlet.filter;

import java.util.regex.Pattern;

import com.nhncorp.lucy.security.xss.markup.Element;

public class XssFilterObjectListener extends XssFilterListener {

    private final Pattern[] URLNAMES = { Pattern.compile("['\"]?\\s*(?i:url)\\s*['\"]?"),
        Pattern.compile("['\"]?\\s*(?i:href)\\s*['\"]?"),
        Pattern.compile("['\"]?\\s*(?i:src)\\s*['\"]?"),
        Pattern.compile("['\"]?\\s*(?i:movie)\\s*['\"]?") };
    
    @Override
    public void handleElement(Element e) {

        if(!isAllow(e)) {
            logger.debug("Object Tag param attribute (or Embed src attribute of child tag) is not WHITE URL.");
            e.setEnabled(false);
            logger.debug("Object Tag not allowed by XssFilter");
        }
    }

    private boolean isAllow(Element e) {

        for (Element param : e.getElements()) {
            if ("param".equalsIgnoreCase(param.getName()) && !checkParamUrl(param)) {
                logger.debug("Param attribute URL is not WHITE URL [ {} ]", param.getAttributeValue("value"));
                return false;
            } 
            
            if("embed".equalsIgnoreCase(param.getName())) {
                if(isWhiteUrl(param.getAttributeValue("src"))) {
                    param.setEnabled(true);
                } else {
                    logger.debug("Embed src attribute is not WHITE URL [ {} ]", param.getAttributeValue("src"));
                }
            }
        }
        
        return true;
    }
    
    private boolean checkParamUrl(Element param) {
        String name = param.getAttributeValue("name");
        String value = param.getAttributeValue("value");
        
        if(containsURLName(name)) {
            return isWhiteUrl(value);
        }
        
        return true;
    }
    
    private boolean containsURLName(String name) {
        for (Pattern p : URLNAMES) {
            if (p.matcher(name).matches()) {
                return true;
            }
        }
        return false;
    }
}
