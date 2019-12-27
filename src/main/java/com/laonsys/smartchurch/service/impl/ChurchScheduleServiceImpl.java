package com.laonsys.smartchurch.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.church.Event;
import com.laonsys.smartchurch.mapper.ChurchScheduleMapper;
import com.laonsys.smartchurch.service.ChurchScheduleService;

@Service("scheduleService")
@Transactional
public class ChurchScheduleServiceImpl implements ChurchScheduleService {

    @Autowired
    private ChurchScheduleMapper churchSchedulerMapper;

    @Override
    public List<Event> select(Timestamp startRange, Timestamp endRange) {
        return churchSchedulerMapper.select(startRange, endRange);
    }
    
    @Override
    public List<Event> selectEvents(int id, Timestamp startRange, Timestamp endRange) {
        return churchSchedulerMapper.selectEvents(id, startRange, endRange);
    }

    @Override
    public List<Event> selectEndlessRepeatEvents(int id, Timestamp startRange, Timestamp endRange) {
        return churchSchedulerMapper.selectEndlessRepeatEvents(id, startRange, endRange);
    }

    @Override
    public List<Event> selectEndRepeatEvents(int id, Timestamp startRange, Timestamp endRange) {
        return churchSchedulerMapper.selectEndRepeatEvents(id, startRange, endRange);
    }
    
    @Override
    public Event selectById(int eventId) {
        return churchSchedulerMapper.selectById(eventId);
    }
    
    @Override
    public void add(Event event) {
        churchSchedulerMapper.add(event);
    }

    @Override
    public void update(Event event) {
        churchSchedulerMapper.update(event);
    }

    @Override
    public void delete(int eventId) {
        churchSchedulerMapper.delete(eventId);
    }
}
