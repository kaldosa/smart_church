package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.church.ChurchMember;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface ChurchMemberMapper {
    int count(QueryParam queryParam);
    List<ChurchMember> selectList(QueryParam queryParam);
    boolean isMember(@Param("church") int church, @Param("member") int member);
    void create(ChurchMember churchMember);
    void delete(@Param("church") int church, @Param("member") int member);
}
