package com.laonsys.smartchurch.service;

import java.util.List;

import com.laonsys.smartchurch.domain.church.ChurchMember;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface ChurchMemberService {
    int count(QueryParam queryParam);
    List<ChurchMember> selectList(QueryParam queryParam);
    boolean isMember(int church, int member);
    void create(ChurchMember churchMember);
    void delete(int church, int[] members);
}
