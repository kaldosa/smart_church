package com.laonsys.springmvc.extensions.model;

import com.laonsys.springmvc.extensions.controller.ViewNameResolver;

/**
 * {@link ViewNameResolver}   에서 사용할 뷰의 타입
 * @author   kaldosa
 * @see  ViewNameResolver
 */
public enum ViewType {
    /**
     * @uml.property  name="lIST"
     * @uml.associationEnd  
     */
    LIST("list"), /**
     * @uml.property  name="cREATE"
     * @uml.associationEnd  
     */
    CREATE("create"), /**
     * @uml.property  name="eDIT"
     * @uml.associationEnd  
     */
    EDIT("edit"), /**
     * @uml.property  name="vIEW"
     * @uml.associationEnd  
     */
    VIEW("view"), /**
     * @uml.property  name="rEDIRECT"
     * @uml.associationEnd  
     */
    REDIRECT(""), /**
     * @uml.property  name="fORWARD"
     * @uml.associationEnd  
     */
    FORWARD("");
    
    /**
     * @uml.property  name="value"
     */
    private String value;
    
    private ViewType(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    /**
     * @return
     * @uml.property  name="value"
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     * @uml.property  name="value"
     */
    public void setValue(String value) {
        this.value = value;
    }    
}
