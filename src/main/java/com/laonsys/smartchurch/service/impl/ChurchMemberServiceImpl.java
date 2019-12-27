package com.laonsys.smartchurch.service.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.church.ChurchMember;
import com.laonsys.smartchurch.mapper.ChurchMemberMapper;
import com.laonsys.smartchurch.service.ChurchMemberService;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Service("churchMemberService")
@Transactional
@Slf4j
public class ChurchMemberServiceImpl implements ChurchMemberService {
    @Autowired ChurchMemberMapper churchMemberMapper;
    
    @Override
    public int count(QueryParam queryParam) {
        log.info("church member count query params : {}", queryParam);
        int count = churchMemberMapper.count(queryParam);
        if(log.isDebugEnabled()) {
            log.debug("church {} members count {}", queryParam.getParams().get("churchId"), count);
        }
        return count;
    }

    @Override
    public List<ChurchMember> selectList(QueryParam queryParam) {
        log.info("church member select query params : {}", queryParam);
        
        List<ChurchMember> list = churchMemberMapper.selectList(queryParam);
        int totalCount = count(queryParam);
        queryParam.getPaginate().paging(totalCount);
        return list;
    }

    @Override
    public boolean isMember(int church, int member) {
        boolean result = churchMemberMapper.isMember(church, member);
        if(log.isDebugEnabled()) {
            log.debug("[{}] user is a member of the church [{}]", member, church);
            log.debug("{}", ( (result) ? "yes" : "no" ) );
        }
        return result;
    }

    @Override
    public void create(ChurchMember churchMember) {
        churchMemberMapper.create(churchMember);
        if(log.isDebugEnabled()) {
            log.debug("joined member of the Church successfully. {}", churchMember);
        }
    }

    @Override
    public void delete(int church, int[] members) {
        for(int member : members) {
            log.info("withdraw member [{}] of a church [{}]", member, church);
            churchMemberMapper.delete(church, member);
        }
    }
}
