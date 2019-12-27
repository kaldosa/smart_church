package com.laonsys.springmvc.extensions.service;

import java.util.List;

import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.QueryParam;

/**
 * CRUD 기능을 위한 service 인터페이스
 * 
 * @author kaldosa
 * @param <T> entity 제너릭 타입
 */
public interface GenericService<T extends Entity<? super T>> {

    /**
     * 조건으로 검색한 결과 개수를 반환한다.
     * 
     * @param condition
     *        검색 조건
     * @return 조건으로 검색한 결과 개수
     */
    int count(QueryParam queryParam);
    
    /**
     * 모든 entity 를 가져온다.
     * 
     * @return entity 목록
     */
    List<T> selectAll();

    List<T> selectAll(QueryParam queryParam);
    
    /**
     * 조건에 해당하는 entity 목록을 가져온다.
     * 
     * @param condition
     *        검색 조건
     * @return 조건에 해당하는 entity 목록
     */
    List<T> selectList(QueryParam queryParam);
    
    /**
     * entity id 로 entity 정보를 가져온다.
     * 
     * @param id
     *        entity 의 id
     * @return entity
     */
    T selectOne(int id);

    /**
     * 조건을 만족하는 entity 정보를 가져온다.
     * 
     * @param condition
     *        검색 조건
     * @return entity
     */
    T selectOne(QueryParam queryParam);

    /**
     * DB에 entity 를 저장한다.
     * 
     * @param model
     *        entity
     */
    void create(T model);

    /**
     * entity 를 수정한다.
     * 
     * @param model
     *        수정할 entity
     */
    void update(T model);

    /**
     * 해당 entity 를 삭제한다.
     * 
     * @param model
     *        삭제할 entity
     */
    void delete(T model);

    /**
     * 해당 entity 를 삭제한다.
     * 
     * @param id
     *        삭제할 entity 의 id
     */
    void delete(int id);

    /**
     * 해당 entity 를 삭제한다.
     * 
     * @param ids
     *        삭제할 entity id 배열
     */
    void delete(int[] ids);
}