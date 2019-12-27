package com.laonsys.springmvc.extensions.controller;

import org.springframework.ui.Model;

import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;

/**
 * CRUD method 실행 시 사전 작업이 필요한 경우 사용하는 인터셉터
 * 
 * @author kaldosa
 * @param <T>
 *        entity 제너릭 타입
 * @see PreInterceptorAdapter
 */
public interface PreInterceptor<T extends Entity<? super T>> {

    /**
     * 목록보기에서 사전 작업이 필요한 경우 사용
     * 
     * @param page
     *        현재 페이지
     * @param model
     *        view 에서 사용하는 model map
     */
    public void preList(int page, Model model);
    public void preList(Paginate paginate, Model model);
    public void preList(QueryParam searchCriteria, Model model);
    
    /**
     * 디테일보기에서 사전 작업이 필요한 경우 사용
     * 
     * @param entityId
     *        entity 의 id
     * @param model
     *        view 에서 사용하는 model map
     */
    public void preView(int entityId, Model model);

    /**
     * create 폼에서 사전 작업이 필요한 경우 사용
     * 
     * @param entity
     *        {@link Entity}
     * @param model
     *        view 에서 사용하는 model map
     */
    public void preCreate(T entity, Model model);

    /**
     * edit 폼에서 사전 작업이 필요한 경우 사용
     * 
     * @param entity
     *        {@link Entity}
     * @param model
     *        view 에서 사용하는 model map
     */
    public void preUpdate(T entity, Model model);
}
