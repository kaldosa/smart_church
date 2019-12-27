package com.laonsys.smartchurch.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.church.Event;

public interface ChurchScheduleMapper {

    public List<Event> select(@Param("startRange") Timestamp startRange, @Param("endRange") Timestamp endRange);
    
    public List<Event> selectEvents(@Param("id") int id, @Param("startRange") Timestamp startRange, @Param("endRange") Timestamp endRange);
    
    public List<Event> selectEndlessRepeatEvents(@Param("id") int id, @Param("startRange") Timestamp startRange, @Param("endRange") Timestamp endRange);
    
    public List<Event> selectEndRepeatEvents(@Param("id") int id, @Param("startRange") Timestamp startRange, @Param("endRange") Timestamp endRange);
    
    public Event selectById(int eventId);
    
    public void add(Event event);
    
    public void update(Event event);
    
    public void delete(int eventId);
}
