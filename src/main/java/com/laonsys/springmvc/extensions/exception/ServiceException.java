package com.laonsys.springmvc.extensions.exception;

import org.springframework.core.NestedRuntimeException;

public class ServiceException extends NestedRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -6619729070755593958L;
    
    public ServiceException(String msg) {
        super(msg);
    }
    
    public ServiceException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
