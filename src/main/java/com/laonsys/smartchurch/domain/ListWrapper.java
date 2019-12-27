package com.laonsys.smartchurch.domain;

import java.util.List;

public interface ListWrapper<T> {
    
    public void setList(List<T> list);
    
    public List<T> getList();
}
