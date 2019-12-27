package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.church.Server;

public interface ChurchServerMapper {
    List<Server> selectList(int ourChurchId);
    void insert(Server server);
    void update(Server server);
    void delete(int id);
}
