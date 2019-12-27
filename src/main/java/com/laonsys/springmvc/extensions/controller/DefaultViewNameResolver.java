package com.laonsys.springmvc.extensions.controller;

import org.springframework.stereotype.Component;

import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.ViewType;

/**
 * {@link ViewNameResolver}의 기본 구현 클래스
 * 
 * @author kaldosa
 * @param <T>
 *        entity 제너릭 타입
 * @see ViewNameResolver
 */
@Component
public class DefaultViewNameResolver<T extends Entity<T>> implements ViewNameResolver<T> {
    @Override
    public String viewNameResolver(String viewRoot, ViewType type, Class<T> clazz, String url) {

        String viewName = "";

        switch (type) {
        case LIST:
        case CREATE:
        case EDIT:
        case VIEW:
            viewName = viewRoot + "/" + type.getValue() + clazz.getSimpleName();
            break;
        case REDIRECT:
            viewName = "redirect:" + url;
            break;
        case FORWARD:
            viewName = "forward:" + url;
            break;
        }
        return viewName;
    }
}