package com.laonsys.smartchurch.service;

import java.sql.Timestamp;
import java.util.List;

import com.laonsys.smartchurch.domain.church.Event;

public interface ChurchScheduleService {

    public List<Event> select(Timestamp startRange, Timestamp endRange);
    
    public List<Event> selectEvents(int id, Timestamp startRange, Timestamp endRange);
    
    public List<Event> selectEndlessRepeatEvents(int id, Timestamp startRange, Timestamp endRange);
    
    public List<Event> selectEndRepeatEvents(int id, Timestamp startRange, Timestamp endRange);
    
    public Event selectById(int eventId);
    
    public void add(Event event);
    
    public void update(Event event);
    
    public void delete(int eventId);
}
