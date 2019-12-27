package com.laonsys.smartchurch.service;

import java.util.List;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;

public interface ReplyService {
    BaseComments selectOne(int id, String refTable);

    List<BaseComments> selectList(int refId, String refTable);

    void insert(BaseComments reply, String refTable);

    void update(BaseComments reply, User activeUser);

    void delete(int id, String refTable, User activeUser);
    
    void deleteAll(int refId, String refTable);
    
    void deleteAll(int churchId);
    
    boolean hasPermission(int id, User user);
}
