package com.laonsys.springmvc.extensions.model;

import java.util.Date;

/**
 * CRUD controller 와 service 에서 사용할 엔티티에 대한 인터페이스
 * @author   kaldosa
 * @param  < T  >  entity 제너릭 타입
 */
public interface Entity<T extends Entity<T>> extends Comparable<T> {

    /**
     * entity 의 id 를 반환한다.
     * @return   entity 의 id
     */
    int getId();

    /**
     * entity 의 id 를 설정한다.
     * @param id   entity 의 id
     */
    void setId(int id);

    /**
     * entity 가 DB 에 저장될 때의 시간을 반환한다.
     * @return   entity 가 DB 에 저장될 때의 시간
     */
    Date getCreatedDate();

    /**
     * entity 가 DB 에 저장될 때의 시간을 설정한다.
     * @param createdDate   entity 가 DB 에 저장될 때의 시간
     */
    void setCreatedDate(Date createdDate);

    /**
     * entity 가 DB 에 업데이트될 때의 시간을 반환한다.
     * @return   entity 가 DB 에 업데이트될 때의 시간
     */
    Date getModifiedDate();

    /**
     * entity 가 DB 에 업데이트될 때의 시간을 설정한다.
     * @param modifiedDate   entity 가 DB 에 업데이트될 때의 시간
     */
    void setModifiedDate(Date modifiedDate);
}
