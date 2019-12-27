package com.laonsys.springmvc.extensions.exception;

import org.springframework.core.NestedRuntimeException;

public class PermissionNotDefinedException extends NestedRuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -4916184896246641768L;

    public PermissionNotDefinedException(String msg) {
        super(msg);
    }
    
    public PermissionNotDefinedException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
