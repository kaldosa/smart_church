package com.laonsys.springmvc.extensions.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;

/**
 * {@link PreInterceptor}에 대한 아답터 클래스
 * 
 * @author kaldosa
 * @param <T>
 *        entity 제너릭 타입
 * @see PreInterceptor
 */
@Component
public class PreInterceptorAdapter<T extends Entity<? super T>> implements PreInterceptor<T> {

    @Override
    public void preList(int page, Model model) {}
    
    @Override
    public void preList(Paginate paginate, Model model) {}
    
    @Override
    public void preList(QueryParam searchCriteria, Model model) {}
    
    @Override
    public void preView(int entityId, Model model) {}
    
    @Override
    public void preUpdate(T entity, Model model) {}
    
    @Override
    public void preCreate(T entity, org.springframework.ui.Model model) {}
}
