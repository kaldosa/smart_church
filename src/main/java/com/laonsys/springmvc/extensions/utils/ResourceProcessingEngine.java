package com.laonsys.springmvc.extensions.utils;

import java.io.InputStream;

/**
 * 사용자가 올리는 첨부파일에 대하여 어떤 처리(이미지 resizing, 동영상을 변환 등)를 수행하는
 * {@link ResourceProcessor}를 실행시키는 엔진.
 * 
 * @author kaldosa
 * 
 */
public interface ResourceProcessingEngine {
    /**
     * 컨덴츠 타입에 따라 등록된 {@link ResourceProcessor}에게 리소스 처리를 실행시킨다.
     * 
     * @param fileName fileName
     * @param contentType 리소스의 컨덴츠 타입(mime type)
     * @param fileSize file size
     * @param inputStream 리소스에 대한 input stream
     * @return {@link ResourceProcessor}가 처리한 결과를 Object로 반환한다.
     */
    Object processing(String fileName, String contentType, long fileSize, InputStream inputStream);
}