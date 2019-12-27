package com.laonsys.smartchurch.controller.client.church;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.smartchurch.domain.church.Event;
import com.laonsys.smartchurch.domain.church.EventRepeatType;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.service.ChurchOrgService;
import com.laonsys.smartchurch.service.ChurchScheduleService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.springmvc.extensions.model.QueryParam;


//@Controller
//@RequestMapping("/church/{churchId}")
public class ChurchSchedulerController {
//    
//    protected transient Logger logger = LoggerFactory.getLogger(getClass());
//    
//    @Autowired private ChurchScheduleService scheduleService;
//    @Autowired private ChurchService churchService;
//    @Autowired private ChurchOrgService churchOrgService;
//    
//    @ModelAttribute
//    public void getChurch(@PathVariable int churchId, Model model) {
//        
//        OurChurch ourChurch = churchService.selectOne(churchId);
//        QueryParam queryParam = new QueryParam();
//        queryParam.addParam("churchId", churchId);
//        List<ChurchOrg> listOrg = churchOrgService.selectAll(queryParam);
//        
//        model.addAttribute("listOrg", listOrg);
//        model.addAttribute("ourChurch", ourChurch);
//    }
//    
//    // scheduler view 페이지 요청 처리
//    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
//    public String calendar(@PathVariable int churchId) {
//        return "/church/news/viewSchedule";
//    }
//    
//    // calendar의 특정 기간에 해당하는 일정이벤트 ajax 요청에 대한 json 응답 처리
//    @RequestMapping(value = "/schedule/getEvents", method = RequestMethod.GET)
//    public @ResponseBody void getEvents(@PathVariable int churchId,
//                                        @RequestParam(value="start", required=false) long startRangeLong, 
//                                        @RequestParam(value="end", required=false) long endRangeLong, 
//                                        HttpServletResponse response) throws IOException {
//        
//        final long DAYMILLISEC = 60 * 60 * 24 * 1000; // 하루 (24시간) -> millisecond
//        Gson gson = new Gson();
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
//        // 일정이벤트를 담은 list를 json 형태로 변환하여 response body에 담아 응답
//        String json = gson.toJson(jsonList);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        Writer writer = response.getWriter();
//        writer.write(json);
//        writer.flush();
//        writer.close();
//        System.out.println(json);
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
//    
//    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
//    // 일정이벤트 등록 요청된 데이터를 DB에 insert
//    @RequestMapping(value = "/schedule/addEvent", method = RequestMethod.POST)
//    public String addEvent(@PathVariable int churchId,@ModelAttribute Event event) {
//
//        if (event.getStartTime() == null) {
//            event.setStart(Timestamp.valueOf(event.getStartDate() + " 00:00:00"));
//            event.setEnd(Timestamp.valueOf(event.getEndDate() + " 00:00:00"));
//            event.setAllday(true);
//            event.setRepeatable(false);
//        }
//        else {
//            event.setStart(Timestamp.valueOf(event.getStartDate() + " " + event.getStartTime()));
//            event.setEnd(Timestamp.valueOf(event.getEndDate() + " " + event.getEndTime()));
//            event.setAllday(false);
//            event.setRepeatable(false);
//        }
//        
//        event.setChurchId(churchId);
//        
//        // 태그 방지
///*        String title = event.getTitle();
//        title = title.replaceAll("<", "&lt");
//        title = title.replaceAll(">", "&gt");
//        event.setTitle(title);*/
//
//        scheduleService.add(event);
//
//        return "/church/news/viewSchedule";
//    }
//    
//    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
//    // 일정이벤트 수정 폼 페이지 요청 처리
//    @RequestMapping(value = "/schedule/update", method = RequestMethod.GET)
//    public String updateEvent(@PathVariable int churchId, @RequestParam("eventId") int eventId, @RequestParam("eventStart") long start, @RequestParam("eventEnd") long end, ModelMap model) {
//        
//        Event originEvent = scheduleService.selectById(eventId);
//
//        // Timestamp 형식의 날짜 데이터를 날짜와 시간을 분리하여 set
//        String startDate[] = new Timestamp(start * 1000).toString().split(" ");
//        originEvent.setStartDate(startDate[0]);
//        String startTimeArr[] = startDate[1].split(":", 3);
//        String startTime = startTimeArr[0] + ":" + startTimeArr[1] + ":00";
//        originEvent.setStartTime(startTime);
//        String endDate[] = new Timestamp(end * 1000).toString().split(" ");
//        originEvent.setEndDate(endDate[0]);
//        String endTimeArr[] = endDate[1].split(":", 3);
//        String endTime = endTimeArr[0] + ":" + endTimeArr[1] + ":00";
//        originEvent.setEndTime(endTime);
//
//        if (originEvent.isRepeatable()) { // 반복일정일 경우
//            
//            String[] originStart = originEvent.getStart().toString().split(" ");
//            originEvent.setOriginStartDate(originStart[0]);
//            
//            // 수정하려는 대상 이벤트의 시작일이 DB에 등록된 일정 시작일과 같을 시에만 날짜 수정 가능
//            if (startDate[0].equals(originStart[0])) {
//                originEvent.setDateEditable(true);
//            } else {
//                originEvent.setDateEditable(false);
//            }
//            
//            // ','를 구분자로 하여 string 형식의 반복 요일을 잘라서 배열에 담아 set
//            if (originEvent.getRepeatDays() != null) {
//                String[] repeatDayStr = originEvent.getRepeatDays().split(",");
//                int[] repeatDay = new int[repeatDayStr.length];
//                for (int i = 0; i < repeatDayStr.length; i++) {
//                    repeatDay[i] = Integer.parseInt(repeatDayStr[i]);
//                }
//                originEvent.setRepeatDay(repeatDay);
//            }
//            if (originEvent.getRepeatEndDate() != null) {
//                String[] repeatEndDate = originEvent.getRepeatEndDate().toString().split(" ");
//                originEvent.setRepeatEndDateStr(repeatEndDate[0]);
//            }
//        }
//
//        Map<String, String> startTimeSelect = makeTimeSelectOption();
//        Map<String, String> endTimeSelect = startTimeSelect;
//
//        if (startDate[0].equals(endDate[0]) && !startTime.equals("00:00:00")) { // 종료시간 select option 범위를 한정해야 하는 경우
//            Map<String, String> temp = makeTimeSelectOption();
//            List<String> keySet = new ArrayList<String>(temp.keySet());
//
//            for (int i = 0; i < keySet.size(); i++) { // 종료시간 select option을 시작시간 이후 범위로 한정
//                String key = keySet.get(i);
//                temp.remove(key);
//                if (key.equals(startTime)) {
//                    break;
//                }
//            }
//            endTimeSelect = temp;
//        }
//
//        model.addAttribute("startTimeSelect", startTimeSelect);
//        model.addAttribute("endTimeSelect", endTimeSelect);
//        model.addAttribute("event", originEvent);
//
//        return "/church/news/updateSchedule";
//    }
//    
//    // 일정이벤트 수정 뷰에서 시작시간의 값을 변경할 때 ajax로 요청된 종료시간 범위를 한정시켜 응답 처리
//    @RequestMapping(value = "/schedule/getEndTimeOptions", method = RequestMethod.GET)
//    public @ResponseBody void getEndTimeOptions(@PathVariable int churchId, 
//                                                @RequestParam("startDate") String startDate,
//                                                @RequestParam("endDate") String endDate,
//                                                @RequestParam("startTime") String startTime, HttpServletResponse response) throws IOException {
//        
//        Gson gson = new Gson();
//        Map<String, String> timeSelect = makeTimeSelectOption();
//        List<String> keySet = new ArrayList<String>(timeSelect.keySet());
//
//        List<Map<String, String>> rangeTimeSelect = new ArrayList<Map<String, String>>();
//
//        int range = keySet.size();
//        for (int i = 0; i < keySet.size(); i++) {
//
//            String key = keySet.get(i);
//            if (key.equals(startTime)) {
//                range = i + 1;
//            }
//            if (!startDate.equals(endDate)) {
//                range = 0;
//            }
//            if (range <= i) {
//                Map<String, String> rangeOption = new LinkedHashMap<String, String>();
//                rangeOption.put("value", key);
//                rangeOption.put("label", timeSelect.get(key));
//                rangeTimeSelect.add(rangeOption);
//            }
//        }
//
//        String json = gson.toJson(rangeTimeSelect);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        Writer writer = response.getWriter();
//        writer.write(json);
//        writer.flush();
//        writer.close();
//    }
//    
//    // 시간 select 태그 option 값 생성 메소드
//    public Map<String, String> makeTimeSelectOption() {
//        Map<String, String> timeSelect = new LinkedHashMap<String, String>();
//        for (int i = 0; i < 24; i++) {
//            if (i < 12) {
//                if (i == 0) {
//                    timeSelect.put("00:00:00", "오전 12:00");
//                    timeSelect.put("00:30:00", "오전 12:30");
//                }
//                else if (i > 0 && i < 10) {
//                    timeSelect.put("0" + i + ":00:00", "오전 0" + i + ":00");
//                    timeSelect.put("0" + i + ":30:00", "오전 0" + i + ":30");
//                }
//                else {
//                    timeSelect.put(i + ":00:00", "오전 " + i + ":00");
//                    timeSelect.put(i + ":30:00", "오전 " + i + ":30");
//                }
//            }
//            else {
//                if (i == 12) {
//                    timeSelect.put("12:00:00", "오후 12:00");
//                    timeSelect.put("12:30:00", "오후 12:30");
//                }
//                else {
//                    timeSelect.put(i + ":00:00", "오후 " + (i - 12) + ":00");
//                    timeSelect.put(i + ":30:00", "오후 " + (i - 12) + ":30");
//                }
//            }
//        }
//
//        return timeSelect;
//    }
//    
//    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
//    // 일정이벤트 수정 폼에서 변경된 데이터를 DB에 update
//    @RequestMapping(value = "/schedule/update", method = RequestMethod.POST)
//    public String updateEvent(@PathVariable int churchId, @ModelAttribute Event event, BindingResult result) {
//        
//        String startDate = event.getStartDate();
//        String endDate = event.getEndDate();
//        
//        if (!event.isDateEditable()) {
//            String[] startDateArr = event.getStart().toString().split(" ");
//            String[] endDateArr = event.getEnd().toString().split(" ");
//            startDate = startDateArr[0];
//            endDate = endDateArr[0];
//        }
//        
//        if (event.isRepeatable()) {
//            
//            // 반복 종료일이 일정이벤트 시작날짜보다 이전일 경우 반복 false
//            if (event.isRepeatEnd() && Date.valueOf(event.getRepeatEndDateStr()).before(Date.valueOf(event.getOriginStartDate()))) {
//                event.setRepeatable(false);
//            }
//            else {
//                if (event.getRepeatFreq() == EventRepeatType.WEEK) {
//                    String repeatDays = "";
//                    if (event.getRepeatDay().length < 1) { // 매주 반복일 경우 반복요일 값이 없다면 반복 false
//                        event.setRepeatable(false);
//                    }
//                    else { // 반복요일 배열 값을 ","을 구분자로 하는 문자열로 생성
//                        for (int i = 0; i < event.getRepeatDay().length; i++) {
//                            repeatDays += Integer.toString(event.getRepeatDay()[i]) + ",";
//                        }
//                        event.setRepeatDays(repeatDays);
//                    }
//                }
//
//                // 반복 일정 종료일 지정한 경우 종료일에 최대 시간 범위 지정
//                if (event.isRepeatEnd() && event.getRepeatEndDateStr() != null && event.getRepeatEndDateStr() != "") {
//                    event.setRepeatEndDate(Timestamp.valueOf(event.getRepeatEndDateStr() + " 23:59:59"));
//                }
//            }
//        }
//
//        if (event.isAllday()) {
//            event.setStart(Timestamp.valueOf(startDate + " 00:00:00"));
//            event.setEnd(Timestamp.valueOf(endDate + " 00:00:00"));
//        }
//        else {
//            event.setStart(Timestamp.valueOf(startDate + " " + event.getStartTime()));
//            event.setEnd(Timestamp.valueOf(endDate + " " + event.getEndTime()));
//        }
//
//        // 태그 방지
///*        String title = event.getTitle();
//        title = title.replaceAll("<", "&lt");
//        title = title.replaceAll(">", "&gt");
//        event.setTitle(title);*/
//        
//        scheduleService.update(event);
//        return "redirect:/church/" + churchId + "/schedule";
//    }
//    
//    @PreAuthorize("isAuthenticated() and hasPermission(#churchId, 'isOurChurchAdmin')")
//    @RequestMapping("/schedule/delete")
//    public String deleteEvent(@PathVariable int churchId, @RequestParam("eventId") int eventId) {
//        scheduleService.delete(eventId);
//        return "/church/news/viewSchedule";
//    }
}
