package com.laonsys.smartchurch.controller.client.apis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.church.Event;
import com.laonsys.smartchurch.service.ChurchScheduleService;

//@Controller
//@RequestMapping("/apis/church/service/{churchId}")
//public class ChurchScheduleApiController {
//    protected transient Logger logger = LoggerFactory.getLogger(getClass());
//    
//    @Autowired private ChurchScheduleService scheduleService;
//    
//    // calendar의 특정 기간에 해당하는 일정이벤트 ajax 요청에 대한 json 응답 처리
//    @RequestMapping(value = "/schedule", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody Response getEvents(@PathVariable int churchId,
//                                            @RequestParam(value="start") long startRangeLong, 
//                                            @RequestParam(value="end") long endRangeLong) {
//        Response response = new Response();
//        
//        
//        final long DAYMILLISEC = 60 * 60 * 24 * 1000; // 하루 (24시간) -> millisecond
//
//        Calendar calendar = Calendar.getInstance();
//        Timestamp startRange = new Timestamp(startRangeLong * 1000); // calendar 뷰의 기간 시작일
//        Timestamp endRange = new Timestamp(endRangeLong * 1000); // calendar 뷰의 기간 종료일
//
//        List<Event> eventsList = scheduleService.selectEvents(churchId, startRange, endRange);
//        List<Event> repeatEventsList = scheduleService.selectEndlessRepeatEvents(churchId, startRange, endRange);
//        repeatEventsList.addAll(scheduleService.selectEndRepeatEvents(churchId, startRange, endRange));
//        List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
//
//        for (Event event : eventsList) { // 반복이 아닌 일정이벤트
//            makeEventList(jsonList, event, event.getStart(), event.getEnd());
//        }
//
//        for (Event event : repeatEventsList) { // 반복 일정이벤트
//
//            Timestamp range; // 해당 달의 반복해야할 범위
//            if (event.isRepeatEnd()) { // 반복 종료 설정 시 종료일을 범위로 지정
//                range = new Timestamp(event.getRepeatEndDate().getTime());
//            }
//            else { // 계속 반복일 경우 calendar 뷰 종료범위를 범위로 지정
//                range = endRange;
//            }
//
//            long dateGap = ((startRange.getTime() - event.getStart().getTime()) / DAYMILLISEC); // 이벤트 시작 날과 뷰의 첫 날까지의 차 (day/날 수)
//            if (((startRange.getTime() - event.getStart().getTime()) % DAYMILLISEC) != 0) {
//                dateGap += 1;
//            }
//            long startEndGap = (event.getEnd().getTime() - event.getStart().getTime()); // 이벤트 시작날짜와 이벤트 종료날짜의 차 (시작날짜에 해당 변수 더하여 종료날짜 구함)
//
//            Timestamp eventStart = event.getStart();
//
//            // 반복 일정이벤트 객체의 반복빈도에 따른 처리
//            switch (event.getRepeatFreq()) {
//            case DAY: // 매일
//
//                int i = 0;
//                while (range.after(eventStart)) {
//                    if (event.getStart().after(startRange)) { // 일정이벤트가 calendar 뷰 범위내에 등록되었다면 반복주기만큼 더하여 시작날짜 구함 
//                        eventStart = new Timestamp(event.getStart().getTime() + (event.getRepeatCycle() * i * DAYMILLISEC));
//                    }
//                    else { // 일정이벤트가 calendar 뷰 범위 이전에 등록되었다면 일정과 뷰의 차를 더하고 뷰 범위내에 첫 반복 일정을 구한 후 반복주기만큼 더하여 시작날짜 구함
//                        eventStart = new Timestamp(event.getStart().getTime()
//                                + ((dateGap + (event.getRepeatCycle() * i) + (event.getRepeatCycle() - (dateGap % event.getRepeatCycle()))) * DAYMILLISEC));
//                    }
//                    if (eventStart.getTime() >= startRangeLong && eventStart.before(range)) {
//                        makeEventList(jsonList, event, eventStart, new Timestamp(eventStart.getTime() + startEndGap));
//                    }
//                    i++;
//                }
//
//                break;
//
//            case WEEK: // 매주
//
//                String daysOfWeek[] = event.getRepeatDays().split(","); // 반복요일 값을 구분자로 잘라 배열에 담음
//
//                calendar.setTimeInMillis(event.getStart().getTime());
//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int minute = calendar.get(Calendar.MINUTE);
//                calendar.set(Calendar.HOUR, 0);
//                calendar.set(Calendar.MINUTE, 0);
//
//                if (event.getStart().after(startRange)) { // 반복 일정이벤트의 시작날짜가 calendar 뷰 시작범위 이후인 경우
//                    // 일정 시작날짜부터 뷰 종료범위까지 하루씩 늘리면서 옵션 요일과 같을 경우 이벤트 지정
//                    for (long d = calendar.getTimeInMillis(); d < endRangeLong * 1000; d += DAYMILLISEC) {
//                        calendar.setTimeInMillis(d);
//                        for (int j = 0; j < daysOfWeek.length; j++) {
//                            // Calendar.DAY_OF_WEEK : 일 = 1 , daysOfWeek : 일 = 0
//                            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 == Integer.parseInt(daysOfWeek[j])) {
//                                calendar.set(Calendar.HOUR, hour);
//                                calendar.set(Calendar.MINUTE, minute);
//
//                                if (eventStart.getTime() >= startRangeLong && eventStart.before(range)) {
//                                    makeEventList(jsonList, event, new Timestamp(calendar.getTimeInMillis()), new Timestamp(calendar.getTimeInMillis() + startEndGap));
//                                }
//                            }
//                        }
//                    }
//                }
//                else { // 반복 일정이벤트의 시작날짜가 calendar 뷰 시작범위 이전인 경우
//                    for (i = 0; i < 6; i++) { // calendar 뷰에서 display 되는 주 = 6주
//                        for (int j = 0; j < daysOfWeek.length; j++) { // 매주 반복요일 지정값만큼 더하여 일정구하기
//                            eventStart = new Timestamp(event.getStart().getTime() + ((dateGap + Long.parseLong(daysOfWeek[j]) + (7 * i)) * DAYMILLISEC));
//
//                            if (eventStart.getTime() >= startRangeLong && eventStart.before(range)) {
//                                makeEventList(jsonList, event, eventStart, new Timestamp(eventStart.getTime() + startEndGap));
//                            }
//                        }
//                    }
//                }
//
//                break;
//
//            case MONTH: // 매월
//
//                calendar.setTimeInMillis(startRangeLong * 1000);
//                int year = calendar.get(Calendar.YEAR); // calendar 뷰 범위의 시작 년 값 할당
//                int month = calendar.get(Calendar.MONTH); // calendar 뷰 범위의 시작 월 값 할당
//
//                calendar.setTimeInMillis(event.getStart().getTime());
//                if (event.getStart().after(startRange)) { // calendar 뷰 범위 내에 등록된 이벤트일 경우 이벤트의 월 값 할당
//                    month = calendar.get(Calendar.MONTH);
//                }
//
//                int weekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
//                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//
//                i = 0;
//                while (range.after(eventStart)) {
//                    calendar.set(Calendar.YEAR, year); // calendar 뷰 범위의 시작 년 값 지정
//                    calendar.set(Calendar.MONTH, month + i);
//                    if (event.getRepeatDate().equals("day")) { // 일정이벤트 반복날짜 옵션이 "요일"인 경우에만
//                        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekInMonth);
//                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
//                    }
//                    eventStart = new Timestamp(calendar.getTimeInMillis());
//
//                    if (eventStart.getTime() >= startRangeLong && eventStart.before(range)) {
//                        makeEventList(jsonList, event, eventStart, new Timestamp(eventStart.getTime() + startEndGap));
//                    }
//                    i++;
//                }
//
//                break;
//
//            case YEAR: // 매년
//
//                calendar.setTimeInMillis(startRangeLong * 1000);
//                year = calendar.get(Calendar.YEAR); // calendar 뷰의 해당 년 값
//
//                calendar.setTimeInMillis(event.getStart().getTime());
//                weekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH); // 일정이벤트 시작날짜가 몇째주인지 반환
//                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 일정이벤트 시작날짜의 요일 반환
//
//                calendar.set(Calendar.YEAR, year);
//                if (event.getRepeatDate().equals("day")) { // 일정이벤트 반복날짜 옵션이 "요일"인 경우에만
//                    calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekInMonth);
//                    calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
//                }
//                eventStart = new Timestamp(calendar.getTimeInMillis());
//
//                if (eventStart.getTime() >= startRangeLong && eventStart.before(range)) {
//                    makeEventList(jsonList, event, eventStart, new Timestamp(eventStart.getTime() + startEndGap));
//                }
//
//                break;
//            }
//        }
//        
//        if(jsonList.isEmpty()) {
//            response.setStatusCode(StatusCode.NO_CONTENTS);
//            return response;
//        } else {
//            response.putData("list", jsonList);
//        }
//
//        return response;
//    }
//    
//    // 가공된 일정이벤트 객체 리스트에서 fullcalendar 플러그인의 event 객체 형태로 변환하는 메소드
//    public void makeEventList (List<Map<String, Object>> jsonList, Event event, Timestamp start, Timestamp end) {
//        
//        Map<String, Object> eventMap = new HashMap<String, Object>();
//        eventMap.put("id", event.getId());
//        eventMap.put("title", event.getTitle());
//        eventMap.put("start", start);
//        eventMap.put("end", end);
//        eventMap.put("allDay", event.isAllday());
//        jsonList.add(eventMap);
//        eventMap = null;
//    }
//}
