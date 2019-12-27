package com.laonsys.smartchurch.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laonsys.smartchurch.domain.TodayWords;
import com.laonsys.smartchurch.mapper.TodayWordsMapper;
import com.laonsys.smartchurch.service.TodayWordsService;

@Service
@Transactional
public class TodayWordsServiceImpl implements TodayWordsService {
    
    @Autowired
    private TodayWordsMapper todayWordsMapper;
    
    private static TodayWords words;
    
    @Override
    public TodayWords getTodayWords() {
        if(words != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(words.getCreatedDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, 1);
            
            Date nextDay = cal.getTime();
            Date current = new Date();
            
            if(nextDay.before(current)) {
            //if(nextDay.after(current)) {                
                int id = words.getId() + 1;
                int next = (id < 64) ? id : 1;

                words = getNext(next);
            }

        } else {
            words = getNext(1);
        }
        return words;
    }
    
    private TodayWords getNext(int id) {
        TodayWords words = todayWordsMapper.selectOne(id);
        words.setCreatedDate(new Date());
        return words;
    }
}
