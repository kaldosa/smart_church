package com.laonsys.springmvc.extensions.servlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhncorp.lucy.security.xss.event.ElementListener;
import com.nhncorp.lucy.security.xss.listener.WhiteUrlList;
import com.nhncorp.lucy.security.xss.markup.Element;

public class XssFilterListener implements ElementListener {
    
    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    private WhiteUrlList whiteUrlList;
    
    public XssFilterListener() {
        try {
            whiteUrlList = WhiteUrlList.getInstance();
        }
        catch (Exception e) {
            logger.debug("XssFilterListener : Fail to getInstance WhiteUrlList");
            throw new RuntimeException("XssFilterListener : Fail to getInstance WhiteUrlList");
        }
    }
    
    @Override
    public void handleElement(Element e) {
    }

    public boolean isWhiteUrl(String url) {
        logger.debug("XssFilterListener : Check white url [{}]", url);
        return whiteUrlList.contains(url);
    }
}
