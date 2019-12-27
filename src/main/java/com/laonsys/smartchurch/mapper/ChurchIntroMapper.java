package com.laonsys.smartchurch.mapper;

import com.laonsys.smartchurch.domain.church.ChurchIntro;

public interface ChurchIntroMapper {
    ChurchIntro selectOne(int churchId);
    void insert(ChurchIntro churchIntro);
    void update(ChurchIntro churchIntro);
    void delete(String url);
}
