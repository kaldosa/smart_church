package com.laonsys.springmvc.extensions.utils;

import java.io.InputStream;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ResourceProcessingEngine}을 구현한 기본 구현체.
 * <p>
 * content type 과 content type 에 대한 {@link ResourceProcessor}를
 * {@code resourceProcessors} 맵에 등록하여 사용한다.
 * <p>
 * {@code resourceProcessors} 맵에 등록된 처리기가 없을 경우
 * {@code defaultProcessor}가 있을 경우, {@code defaultProcessor}가 수행된다.
 * 
 * @author kaldosa
 * 
 */
@Slf4j
public class ResourceProcessingEngineImpl implements ResourceProcessingEngine {
    private @Setter
    Map<String, ResourceProcessor> resourceProcessors;

    private @Setter
    ResourceProcessor defaultProcessor;

    @Override
    public Object processing(String fileName, String contentType, long fileSize, InputStream inputStream) {

        ResourceProcessor processor = resourceProcessors.get(contentType);
        processor = (processor != null) ? processor : defaultProcessor;
        Object result = null;
        if (processor != null) {
            try {
                result = processor.processing(fileName, contentType, fileSize, inputStream);
                if (log.isDebugEnabled()) {
                    log.debug("Processing resource for: " + inputStream + " in " + fileName + " for type "
                            + contentType);
                }
            }
            catch (Exception e) {
                log.error("Error processing resource for: " + inputStream + " in " + fileName + " for type "
                        + contentType, e);
            }
        }
        else {
            log.warn("Resource processor not found for content type: " + contentType
                    + " and no default processor was provided");
        }
        return result;
    }
}