package com.laonsys.springmvc.extensions.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;

/**
 * {@link PostInterceptor}에 대한 아답터 클래스
 * 
 * @author kaldosa
 * @param <T>
 *        entity 제너릭 타입
 * @see PostInterceptor
 */
@Component
public class PostInterceptorAdapter<T extends Entity<? super T>> implements PostInterceptor<T> {

    @Override
    public void postList(int page, Model model) {}
    
    @Override
    public void postList(Paginate paginate, Model model) {}
    
    @Override
    public void postList(QueryParam searchCriteria, Model model) {}
    
    @Override
    public void postView(int entityId, Model model) {}
    
    @Override
    public void postView(int entityId, T entity, Model model) {}
    
    @Override
    public void postCreate(T entity, Model model) {}
    
    @Override
    public void postUpdate(T entity, Model model) {}
}
