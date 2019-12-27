package com.laonsys.springmvc.extensions.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;


public class VelocityMerger implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(VelocityMerger.class);

    private VelocityEngine engine;

    private Resource toolBoxConfigLocation;

    private String toolBoxConfigurationPath;

    public void setToolBoxConfigLocation(Resource toolBoxConfigLocation) {
        this.toolBoxConfigLocation = toolBoxConfigLocation;
    }

    public void setEngine(VelocityEngine engine) {
        this.engine = engine;
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        toolBoxConfigurationPath = toolBoxConfigLocation.getFile().getAbsolutePath();
        if (logger.isInfoEnabled()) {
            logger.info("Resource loader path '{}' resolved to file '{}'", toolBoxConfigLocation.getURI(), toolBoxConfigurationPath);
        }
    }

    /**
     * Velocity 템플릿 매개 변수를 병합 한 결과를 반환한다. 템플릿 인코딩은 설정 파일에 정의 된 기본값을 사용한다.
     * 
     * @param templateName
     *        템플릿 이름
     * @param model
     *        매개 변수 Map
     * @return 병합 결과
     */
    public String merge(String templateName, Map<String, Object> model) {
        return VelocityEngineUtils.mergeTemplateIntoString(engine, templateName, model, toolBoxConfigurationPath);
    }

    /**
     * Velocity 템플릿 매개 변수를 병합 한 결과를 반환한다. 템플릿 인코딩은 설정 파일에 정의 된 기본값을 사용한다.
     * 
     * @param templateName
     *        템플릿 이름
     * @param templateEncoding
     *        템플릿 인코딩
     * @param model
     *        매개 변수 Map
     * @return 병합 결과
     */
    public String merge(String templateName, String templateEncoding, Map<String, Object> model) {
        return VelocityEngineUtils.mergeTemplateIntoString(engine, templateName, templateEncoding, model, toolBoxConfigurationPath);
    }

    /**
     * Velocity 템플릿 매개 변수를 병합 한 결과를 Writer 에 쓴다. 템플릿 인코딩은 설정 파일에 정의 된 기본값을 사용한다.
     * 
     * @param templateName
     *        템플릿 이름
     * @param model
     *        매개 변수 Map
     * @param writer
     *        병합 결과를 내보낼 Writer
     */
    public void mergeToWriter(String templateName, Map<String, Object> model, Writer writer) {
        VelocityEngineUtils.mergeTemplate(engine, templateName, model, writer, toolBoxConfigurationPath);
    }

    /**
     * Velocity 템플릿 매개 변수를 병합 한 결과를 Writer 에 쓴다. 템플릿 인코딩은 설정 파일에 정의 된 기본값을 사용한다.
     * 
     * @param templateName
     *        템플릿 이름
     * @param templateEncoding
     *        템플릿 인코딩
     * @param model
     *        매개 변수 Map
     * @param writer
     *        병합 결과를 내보낼 Writer
     */
    public void mergeToWriter(String templateName, String templateEncoding, Map<String, Object> model, Writer writer) {
        VelocityEngineUtils.mergeTemplate(engine, templateName, templateEncoding, model, writer, toolBoxConfigurationPath);
    }
}
