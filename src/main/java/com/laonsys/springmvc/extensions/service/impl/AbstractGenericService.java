package com.laonsys.springmvc.extensions.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.springmvc.extensions.exception.UnsupportedException;
import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.GenericService;

/**
 * CRUD service 를 구현한 추상 클래스
 * 
 * @author kaldosa
 * @param <T> entity 제너릭 타입
 */
@Transactional
@Slf4j
public abstract class AbstractGenericService<T extends Entity<? super T>> implements GenericService<T> {

    protected abstract Mapper<T> getMapper();
    
    public Logger getLog() {
        return log;
    }
    
    @Override
    public int count(QueryParam queryParam) {
        return getMapper().count(queryParam);
    }
    
    @Override
    public List<T> selectAll() {
        throw new UnsupportedException();
    }

    @Override
    public List<T> selectAll(QueryParam queryParam) {
        return getMapper().selectAll(queryParam);
    }
    
    @Override
    public List<T> selectList(QueryParam queryParam) {
        List<T> list = getMapper().selectList(queryParam);
        int totalCount = count(queryParam);
        queryParam.getPaginate().paging(totalCount);
        return list;
    }
    
    @Override
    public T selectOne(int id) {
        notNull(id);

        T model = getMapper().selectOne(id);
        if( model == null) {
            log.debug("No such");
        }
        return model;
    }

    @Override
    public T selectOne(QueryParam queryParam) {
        notNull(queryParam);
        
        T model = getMapper().selectOneByQueryParam(queryParam);
        if( model == null) {
            log.debug("No such");
        }        
        return model;
    }
    
    @Override
    public void create(T model) {
        notNull(model);
        model.setCreatedDate(new Date());
        getMapper().create(model);
    };
    
    @Override
    public void update(T model) {
        notNull(model);
        model.setModifiedDate(new Date());
        getMapper().update(model);
    };

    @Override
    public void delete(int id) {
        notNull(id);
        getMapper().delete(id);
    }
    
    @Override
    public void delete(int[] ids) {
        notNull(ids);
        for(int id : ids) {
            delete(id);
        }
    }

    @Override
    public void delete(T model) {
        notNull(model);
        delete(model.getId());
    };
}
