package com.laonsys.smartchurch.domain.church;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;

import com.laonsys.springmvc.extensions.validation.constraints.DateForm;

public class Event {

    private Long id;

    private int churchId;
    
    @NotEmpty(message = "내용을 입력해주세요.")
    private String title; // 내용

    @DateForm
    @NotEmpty(message = "시작일을 선택해주세요.")
    private String startDate; // 시작날짜 (form)

    @DateForm
    @NotEmpty(message = "종료일을 선택해주세요.")
    private String endDate; // 종료날짜 (form)

    private String startTime; // 시작시간 (form)

    private String endTime; // 종료시간 (form)

    private String originStartDate; // 반복일정일 경우 원래 시작날짜 (form / = DB_start)

    private boolean dateEditable; // 날짜 수정 가능 여부 (반복이었던 경우, 등록날짜(시작날짜)에서만 날짜 수정 가능)

    private Timestamp start; // 시작일시 (DB)

    private Timestamp end; // 종료일시 (DB)

    private boolean allday; // 종일 여부

    private boolean repeatable; // 반복 여부


    private EventRepeatType repeatFreq; // 반복 빈도 (value = day,week,month,year)
    
    private int repeatCycle; // 반복 주기 (빈도 : day - 몇일마다)

    private String repeatDays; // 반복 요일 (DB / 빈도 : week - 매주 요일마다)

    private int repeatDay[]; // 반복 요일 (form)


    private String repeatDate; // 반복일 (빈도 : month,year / value = date,day / 매월,매년 날짜 혹은 몇째주 요일)

    private boolean repeatEnd; // 반복 종료 여부

    
    private String repeatEndDateStr; // 반복종료일 (form)

    private Timestamp repeatEndDate; // 반복종료일 (DB)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getChurchId() {
        return churchId;
    }
    
    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOriginStartDate() {
        return originStartDate;
    }

    public void setOriginStartDate(String originStartDate) {
        this.originStartDate = originStartDate;
    }

    public boolean isDateEditable() {
        return dateEditable;
    }

    public void setDateEditable(boolean dateEditable) {
        this.dateEditable = dateEditable;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public boolean isAllday() {
        return allday;
    }

    public void setAllday(boolean allday) {
        this.allday = allday;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public EventRepeatType getRepeatFreq() {
        return repeatFreq;
    }

    public void setRepeatFreq(EventRepeatType repeatFreq) {
        this.repeatFreq = repeatFreq;
    }

    public int getRepeatCycle() {
        return repeatCycle;
    }

    public void setRepeatCycle(int repeatCycle) {
        this.repeatCycle = repeatCycle;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    public int[] getRepeatDay() {
        return repeatDay;
    }

    public void setRepeatDay(int[] repeatDay) {
        this.repeatDay = repeatDay;
    }

    public String getRepeatDate() {
        return repeatDate;
    }

    public void setRepeatDate(String repeatDate) {
        this.repeatDate = repeatDate;
    }

    public boolean isRepeatEnd() {
        return repeatEnd;
    }

    public void setRepeatEnd(boolean repeatEnd) {
        this.repeatEnd = repeatEnd;
    }

    public String getRepeatEndDateStr() {
        return repeatEndDateStr;
    }

    public void setRepeatEndDateStr(String repeatEndDateStr) {
        this.repeatEndDateStr = repeatEndDateStr;
    }

    public Timestamp getRepeatEndDate() {
        return repeatEndDate;
    }

    public void setRepeatEndDate(Timestamp repeatEndDate) {
        this.repeatEndDate = repeatEndDate;
    }
}
