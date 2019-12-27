package com.laonsys.smartchurch.domain;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = ResponseSerializer.class)
public class Response {
    private StatusCode statusCode;

    private Map<String, Object> dataMap;

    public Response() {
        dataMap = new HashMap<String, Object>();
        statusCode = StatusCode.OK;
    }
    
    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
    
    public void putData(String key, Object value) {
        dataMap.put(key, value);
    }
    
    public Object getData(String key) {
        return dataMap.get(key);
    }
}
