package com.laonsys.springmvc.extensions.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class QueryParam {
    private String criteria;

    private String keyword;

    private Paginate paginate;

    private Map<String, Object> params;
    
    public QueryParam() {
        params = new HashMap<String, Object>();
    }
    
    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Paginate getPaginate() {
        return paginate;
    }
    
    public void setPaginate(Paginate paginate) {
        this.paginate = paginate;
    }
    
    public Map<String, Object> getParams() {
        return params;
    }
    
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
    public void addParam(String key, Object value) {
        params.put(key, value);
    }
    
    public Object removeParam(String key) {
        return params.remove(key);
    }
    
    @Override
    public String toString() {
        String tostring = "criteria: " + ((criteria == null) ? "":criteria) 
                        + ", keyword: " + ((keyword == null) ? "":keyword)
                        + "[paginate: " + paginate.toString() + "]\r\n";
        
        StringBuffer buffer = new StringBuffer(tostring);
        
        if(!params.isEmpty()) {
            Set<Entry<String, Object>> sets = params.entrySet();
            for(Map.Entry<String, Object> entry : sets) {
                buffer.append("[" + entry.getKey() + ": " + entry.getValue().toString() + "]\r\n");
            }
        }
        return buffer.toString();
    }
}
