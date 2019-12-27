package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.church.Worship;

public interface ChurchWorshipMapper {
    List<Worship> selectList(int ourChurchId);
    void insert(Worship worship);
    void update(Worship worship);
    void delete(int id);
}
