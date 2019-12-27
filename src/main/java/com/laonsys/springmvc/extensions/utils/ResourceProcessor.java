package com.laonsys.springmvc.extensions.utils;

import java.io.InputStream;

/**
 * 특정 컨덴츠에 대한 리소스 처리를 하기 위한 processor interface.
 * 
 * @author kaldosa
 * 
 */
public interface ResourceProcessor {
    /**
     * 리소스에 대하여 특정 처리를 하기 위한 메소드.
     * 
     * @param fileName fileName
     * @param contentType 리소스의 컨덴츠 타입(mime type)
     * @param fileSize file size
     * @param inputStream 리소스에 대한 input stream
     * @return 리소스에 대한 특정 처리후의 결과를 Object로 반환한다.
     */
    Object processing(String fileName, String contentType, long fileSize, InputStream inputStream);
}
