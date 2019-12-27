package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.church.History;

public interface ChurchHistoryMapper {
    List<History> selectList(int ourChurchId);
    void insert(History history);
    void update(History history);
    void delete(int id);
}
