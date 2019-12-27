package com.laonsys.smartchurch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.church.ChurchMeta;
import com.laonsys.smartchurch.mapper.ChurchMetaMapper;
import com.laonsys.smartchurch.service.ChurchMetaService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.QueryParam;

@Service("churchMetaService")
@Transactional
public class ChurchMetaServiceImpl implements ChurchMetaService {

    @Autowired private ChurchMetaMapper churchMetaMapper;
    
    @Override
    public int count(QueryParam queryParam) {
        return churchMetaMapper.count(queryParam);
    }

    @Override
    public List<ChurchMeta> selectList(QueryParam queryParam) {
        List<ChurchMeta> list = churchMetaMapper.selectList(queryParam);
        int totalCount = count(queryParam);
        queryParam.getPaginate().paging(totalCount);
        return list;
    }

    @Override
    public ChurchMeta selectOne(int id) {
        return churchMetaMapper.selectOne(id);
    }

    @Override
    public void insert(ChurchMeta churchMeta) {
        if(!churchMetaMapper.isExist(churchMeta)) {
            churchMetaMapper.insert(churchMeta);
        } else {
            throw new ServiceException("Duplicated church meta information.");
        }
    }

    @Override
    public void update(ChurchMeta churchMeta) {
        churchMetaMapper.update(churchMeta);
    }

    @Override
    public void delete(int id) {
        churchMetaMapper.delete(id);
    }
}
