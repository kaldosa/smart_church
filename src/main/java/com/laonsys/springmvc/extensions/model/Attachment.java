package com.laonsys.springmvc.extensions.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;


@ToString(callSuper = true, includeFieldNames = true)
public @Data class Attachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5609923713379073230L;

    private int id;

    private int fkId;

    private String fileName;

    private String realFileName;

    private String contentType;

    private String path;
    
    private String parentPath;

    private long fileSize;
}
