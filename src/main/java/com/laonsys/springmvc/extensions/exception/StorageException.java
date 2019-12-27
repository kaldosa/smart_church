package com.laonsys.springmvc.extensions.exception;

import org.springframework.core.NestedRuntimeException;

public class StorageException extends NestedRuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -4929773089561623255L;

    public StorageException(String msg) {
        super(msg);
    }
    
    public StorageException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
