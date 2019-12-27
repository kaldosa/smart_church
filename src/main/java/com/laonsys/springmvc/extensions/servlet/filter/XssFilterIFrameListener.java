package com.laonsys.springmvc.extensions.servlet.filter;

import com.nhncorp.lucy.security.xss.markup.Element;

public class XssFilterIFrameListener extends XssFilterListener {

    @Override
    public void handleElement(Element e) {
        if (!isWhiteUrl(e.getAttributeValue("src"))) {
            logger.debug("IFrame src attribute is not WHITE URL [ {} ]", e.getAttributeValue("src"));
            e.setEnabled(false);
            logger.debug("IFrame Tag not allowed by XssFilter");
        }
    }
}
