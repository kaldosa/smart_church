package com.laonsys.springmvc.extensions.service;

import com.laonsys.springmvc.extensions.model.Entity;

/**
 * CRUD 기능을 위한 service 인터페이스
 * 
 * @author kaldosa
 * @param <T> entity 제너릭 타입
 */
public interface UpdateHitsService<T extends Entity<? super T>> {
    /**
     * 조회수를 업데이트한다.
     * 
     * @param hits
     *        업데이트할 조회수
     * @param id
     *        업데이트할 entity id
     */
    void updateHits(int hits, int id);

    /**
     * 조회수를 업데이트한다.
     * 
     * @param model
     *        entity
     */
    void updateHits(T model);
}