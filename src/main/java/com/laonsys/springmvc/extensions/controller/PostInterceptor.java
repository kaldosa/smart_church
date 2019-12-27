package com.laonsys.springmvc.extensions.controller;

import org.springframework.ui.Model;

import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;

/**
 * CRUD method 실행 후 해야할 작업이 있을 경우 사용하는 인터셉터
 * 
 * @author kaldosa
 * @param <T>
 *        entity 제너릭 타입
 * @see PostInterceptorAdapter
 */
public interface PostInterceptor<T extends Entity<? super T>> {
    /**
     * 목록보기에서 추가적인 작업이 필요할 경우 사용
     * 
     * @param page
     *        현재 페이지
     * @param model
     *        view 에서 사용하는 model map
     */
    public void postList(int page, Model model);

    public void postList(Paginate paginate, Model model);
    
    public void postList(QueryParam searchCriteria, Model model);
    
    /**
     * 디테일정보 보기에서 추가적인 작업이 필요할 경우 사용
     * 
     * @param entityId
     *        entity 의 id
     * @param model
     *        view 에서 사용하는 model map
     */
    public void postView(int entityId, Model model);

    /**
     * 디테일정보 보기에서 추가적인 작업이 필요할 경우 사용
     * 
     * @param entityId
     *        entity 의 id
     * @param entity
     *        {@link Entity}
     * @param model
     *        view 에서 사용하는 model map
     */
    public void postView(int entityId, T entity, Model model);

    /**
     * create 폼 submit 에서 추가적인 작업이 필요할 경우 사용
     * 
     * @param entity
     *        {@link Entity}
     * @param model
     *        view 에서 사용하는 model map
     */
    public void postCreate(T entity, Model model);

    /**
     * edit 폼 submit 에서 추가적인 작업이 필요할 경우 사용
     * 
     * @param entity
     *        {@link Entity}
     * @param model
     *        view 에서 사용하는 model map
     */
    public void postUpdate(T entity, Model model);
}
