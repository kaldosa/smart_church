package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.springmvc.extensions.model.QueryParam;

public interface Mapper<T> {
    
    int count(QueryParam queryParam);
    
    List<T> selectAll(QueryParam queryParam);
    
    public List<T> selectList(QueryParam queryParam);
    
    public T selectOne(int id);

    public T selectOneByQueryParam(QueryParam queryParam);
    
    public void create(T t);
    
    public void update(T t);
    
    public void delete(int id);
    
    public void updateHits(@Param("hits") int hits, @Param("id") int id);

    public void updateHits(T t);
}
