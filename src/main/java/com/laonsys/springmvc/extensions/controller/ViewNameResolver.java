package com.laonsys.springmvc.extensions.controller;

import com.laonsys.springmvc.extensions.model.ViewType;

/**
 * CRUD method 실행후 뷰 타입 {@link ViewType}에 따라 뷰 이름을 결정하기 위한 인터페이스
 * 
 * <p>{@link ViewType}이 LIST, VIEW, CREATE, EDIT 인 경우는 다음의 조합으로 뷰 이름을 결정한다.
 * <p>뷰 root 폴더 + 뷰 타입 접두사 + entity 이름
 * <p> 예로, 뷰 이름이 /business/notice/listNotice 로 가정할 경우 다음과 같다.
 * <ul>
 * <li>/business/notice : 뷰 root 폴더 
 * <li>/list : 뷰 타입 접두사
 * <li>Notice : entity 이름
 * </ul>
 * 
 * <p>주의 : 뷰 root 폴더는 디폴트로 controller 의 클래스레벨의 {@link RequestMapping}을 사용한다.
 * 이를 변경하고자 할 때에는, {@link AbstractCrudController}의 {@link AbstractCrudController#setUp()}에서
 * {@link AbstractCrudController#setViewRoot(String)}로 변경해야 한다.
 * 
 * <pre>
 * {@code
 * void setUp() {
 *      
 *      setViewRoot("/test"); // view 파일을 test 폴더에서 찾는다.
 * }
 * </pre>
 * 
 * <p>{@link ViewType}이 REDIRECT, FORWARD 인 경우 리다이렉트 또는 포워드를 하기 위하여 url 를 이용하여
 * 리다이렉트(또는 포워드) 뷰 이름을 만든다. 기본적으로 url은 controller 의 클래스레벨의 {@link RequestMapping}을 사용한다.
 * 
 * @author kaldosa
 * @param <T> entity 제너릭 타입
 * @see DefaultViewNameResolver
 */
public interface ViewNameResolver<T> {
    
    /**
     * 
     * @param viewRoot view 파일들의 root 폴더
     * @param type view 의 type {@link ViewType}
     * @param clazz entity class 로 뷰 파일의 접미사 역할을 한다. class 의 simpleName 을 사용한다.
     * @param url {@link ViewType}이 REDIRECT 나 FORWARD 일 경우 사용하는 URL
     * @return 뷰 이름 또는 리다이렉트(혹은 포워드) URL
     */
    public String viewNameResolver(String viewRoot, ViewType type, Class<T> clazz, String url);
}
