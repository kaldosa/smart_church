<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/jquery/cal/fullcalendar.css" />" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery/cal/fullcalendar.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/cal/jquery.qtip-1.0.0-rc3.min.js" />"></script>
<style type="text/css">
#mask {
    opacity: 0.5;
    background-color: black;
    filter: alpha(opacity=50);
    display: none;
    left: 0;
    top: 0;
    position: absolute;
    z-index: 998
}
#calendar_content {
    float: left;
    padding: 10px 2px;
    position: relative;
    width: 670px;
}
#form-layer {
    display: none;
    position: absolute;
    width: 400px; 
    top: 35%;
    left: 15%;
    border: 1px solid #777; 
    background-color: white; 
    color: black; 
    font-size: 12px; 
    z-index: 999
}

.event-form-box {margin: 10px 0; width: 380px; padding: 10px 10px;}
.event-form-field {margin: 5px 0;}
.event-form-field label {float: left; width: 50px; font-weight: bold;}
.fc-event-inner {max-height: 25px;}

</style>
<title>Insert title here</title>
</head>
<body>
<h2>교회소식</h2>
<div class="intro">

<div class="church_content">

<div id="titleWrap">
<h3>교회일정</h3>
<p class="home">Home &gt; 교회소식 &gt; <span>교회일정</span></p>
</div>

<div id="calendar_content">

        
<p class="pd30"></p>

</div><!-- calendar_content -->

</div>

<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">
<sec:authorize ifAnyGranted="ROLE_USER">
<div class="profile-wrap">

<div class="profile">
<sec:authentication property="principal" var="user" />
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="프로필 사진" width="50" height="50" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/commons/img_default_profile.gif" />" alt="댓글 썸네일" width="50" height="50" /></c:otherwise>
</c:choose>

<div class="profile-info">
<p class="user-name"><span><strong>${user.name}</strong> 님</span></p>
<a href="<c:url value="/auth/confirmPw"/>">내정보</a>
</div>

<div class="logout-form">

<s:eval var="logoutUrl" expression="@server['logoutUrl']" />
<form name="logoutForm" method="post" action="${logoutUrl}">
<input type="hidden" name="spring-security-redirect" value="/church/${ourChurch.id}" />
<input type="image" src="<c:url value="/resources/images/church/common/btn_logout2.gif" />" alt="로그아웃" class="logout" />
</form>

</div>

</div>

</div>
</sec:authorize>

<div class="rnb">
<ul>
<li><a href="<c:url value="/church/${ourChurch.id}/news" />">새소식</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/weekly" />">우리주보</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/schedule" />">교회일정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/family" />">새가족 소개</a></li>
</ul>
</div>
<!-- rnb -->
</div>
<!--// 오른쪽 프로필 및 서브메뉴 -->

</div>
<!-- intro -->

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div id="form-layer" class="fc-transparent">
    <div style="padding: 10px 20px 5px 20px; border-bottom: 1px solid #777; ">
        <img src="<c:url value="/resources/images/church/news/btn_list.png" />" /> <strong style="font-size: 14px">일정 등록</strong>
    </div>
    <form:form id="event-form" method="post" commandName="event">
        <fieldset>
        <div>
        <form:errors path="*"></form:errors>
        </div>
        <div class="event-form-box">
            <div class="event-form-field">
                <label for="startDate">날짜</label><input type="text" name="startDate" id="startDate" size="10" readonly="readonly" class="input_text input_w100 datepicker" />
                - <input type="text" name="endDate" id="endDate" size="10" readonly="readonly" class="input_text input_w100 datepicker" />
            </div>
            <div class="event-form-field">
                <label for="title">내용</label><textarea name="title" id="title" rows="3" cols="35" id="title" class="input_textarea input_w300"></textarea>
            </div>
        </div>
        <div align="center" style="margin: 10px 0;">
            <input id="addEvent" type="image" src="<c:url value="/resources/images/church/news/btn_add.png" />" />
            <a><img src="<c:url value="/resources/images/church/news/btn_cancle.png" />" id="cancel" /></a>
        </div>
        </fieldset>
    </form:form>
</div>


<script type="text/javascript">
$(function(){
    $('<div id="mask"></div>').appendTo('body');
    
    var index = 0;

    var calendar = $('#calendar_content').fullCalendar({
        monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ],
        dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
        header : {
            left : '',
            center : 'title',
            right : 'prev,next today'
        },
        editable : false,
        events : function getEvents(start, end, callback) {
            $.ajax({
                url: '<c:url value="/church/${ourChurch.id}/schedule/getEvents"/>',
                data: {
                    start: Math.round(start.getTime() / 1000),
                    end: Math.round(end.getTime() / 1000)
                },
                dataType: 'json',
                success: function(data) {
                    var events = data;
                    callback(events);
                }
            });
        },
        selectable : true,
        selectHelper : true,
        // 특정 날짜를 클릭할 때 일정이벤트 등록 폼 활성화
        select : function(startDate, endDate, allDay) {
            var start = dateFormat(startDate, "-");
            if (startDate != endDate) {
                var end = dateFormat(endDate, "-");
            }
            else {
                end = start;
            }
            
            $("#startDate").val(start);
            
            // 시작일 이후로 종료일 범위 제한
            var minDate = new Date(start);
            $("#endDate").datepicker("option", "minDate", minDate);
            
            $("#endDate").val(end);
            
            wrapWindowByMask();
            $("#form-layer").show();
            $("textarea[name=title]").focus();
            
            calendar.fullCalendar('unselect');
        },
        // 각각의 event 객체와 스크립트로 생성된 동적인 element를 렌더링
        eventRender : function(event, element) {
            index += 1;
            var startDate = dateFormat(event.start, ".") + "(" + dayName(event.start.getDay()) + ")";
            var endDate;            
            if(event.end==null){
                endDate = startDate;
            }
            else {
                endDate = dateFormat(event.end, ".") + "(" + dayName(event.end.getDay()) + ")";
            }
             
            var title = event.title.replace(new RegExp("<", "g"), "&lt");
            title = title.replace(new RegExp(">", "g"), "&gt");
            title = title.replace(new RegExp("\r\n", "g"), "<br/>");
            
            var eventLayer = "<div id='event-layer' style='width:308px; background-color: white; color: black; font-size: 12px; z-index:5'>"
                + "<p style='margin-right: 10px; margin-bottom: 1px; text-align: right; font-size: 11px; color: #555'>"
                + "<a id='edit"+ index +"'><img src='<c:url value='/resources/images/church/news/edit.png' />' />수정</a>"
                + "<a id='del"+ index +"' style='margin-left: 10px'><img src='<c:url value='/resources/images/church/news/del.png' />' />삭제</a></p>" 
                + "<fieldset style='border-top: 1px solid #777; margin-top: 3px'><p style='margin: 10px'><strong>"+startDate+"</strong>";
            if(!event.allDay){
                var startTime = timeFormat(event.start);
                eventLayer += " <span style='color: #007FCF; font-weight: bold'>"+startTime+"</span>";
            }
            if(startDate!=endDate || !event.allDay) {
                eventLayer += " - ";
            }
            if(startDate!=endDate) {
                eventLayer += "<strong>"+endDate+"</strong>";
            }
            if(!event.allDay) {
                var endTime = timeFormat(event.end);
                eventLayer += "<span style='color: #007FCF; font-weight: bold'>"+endTime+"</span>";
            }
            eventLayer += "</p><p style='width: 260px; margin: 10px; font-size: 13px; word-wrap: break-word;'>"+title+"</p></fieldset></div>";
            
            // event 객체에 mouseover 이벤트가 발생하면 qTip을 이용한 레이어 팝업창 띄움
            element.qtip({
                content: {
                    text: eventLayer,
                },
                hide: { when: { target: false }, fixed: true },
                onHide: {},
                position: { 
                    adjust: { x: -5, y: 15 },
                    corner: { target: 'topLeft', tooltip: 'topLeft' }
                },
                style: { 
                    width: 310,
                    
                    padding: 0,
                    border: { width: 1, radius: 0, color: '#777' }
                }
            });
            
            // event 정보를 담은 레이어팝업에서 삭제 버튼
            $("#del" + index).live('click', function() {
                $.ajax({
                    type: 'GET',
                    url: '<c:url value="/church/${ourChurch.id}/schedule/delete"/>',
                    data: ({"eventId" : event.id}),
                    contentType: 'application/x-www-form-urlencoded',
                    success: function(){
                        calendar.fullCalendar('refetchEvents');
                        
                        $(this).qtip({
                            onHide: function() {
                                $(this).qtip('destroy');      
                            }
                        });
                    }
                });
            });
            
            // event 객체에 click 이벤트가 발생하면 해당 일정이벤트를 수정하는 뷰 페이지 요청
            $("#edit" + index).live('click', function() {
                var start = Math.round(event.start.getTime() / 1000);
                if(event.end != null) {
                    var end = Math.round(event.end.getTime() / 1000);
                }
                else {
                    end = start;
                }
                
                document.location.href = "schedule/update?eventId=" + event.id + "&eventStart=" + start + "&eventEnd=" + end;
            });
        }
    });
    
    // 등록 폼 취소버튼 click 이벤트 핸들러
    $("#cancel").click(function(){
        $("#event-form")[0].reset();
        $("#mask").hide();
        $("#form-layer").hide();
    });
    
    // 등록 폼에서 내용 textarea 입력 시 line 수 체크하여 제한
    $("#title").keyup(function() {
        var text_byte = 0;
        var line = 1;
        var length = 0;
        var text = $("#title").val();
        
        for (var i=0; i<text.length; i++) {
            var one_char = text.charAt(i);
            
            text_byte += (one_char.charCodeAt(0) > 128) ? 2 : 1;
            length ++;
            
            if (one_char == "\n") {
                line += 1;
                if (line > this.rows) {
                    var display_text = text.substr(0, length-1);
                    $("#title").val(display_text);
                    alert(this.rows + "줄 이하로 입력해주세요.");
                    break;
                }
            }
        }
    });
    
    // 등록 폼 전송하기 전 validation 체크
    $("#event-form").submit(function(){
        // 내용 textarea 글자 수 제한
        var title = $("#title").val();
        var limitField = $("#title");
        var limitNum = 100;
        var text_byte = 0;
        var length = 0;
        
        for (var i=0; i<title.length; i++) {
            var one_char = title.charAt(i);
            
            text_byte += (one_char.charCodeAt(0) > 128) ? 2 : 1;
            
            if (text_byte <= limitNum) {
                length = i + 1;
            } 
            else {
                break;
            }
        }
        
        if (text_byte > limitNum) {
            var display_text = title.substr(0, length);
            limitField.val(display_text);
            var msg = "내용은 " + limitNum + "byte(한글 " + limitNum/2 + "자/영문,숫자 " + limitNum + "자) 이하로 입력해주세요.";
            alert(msg);
        }
        // 내용 textarea 값 유무 체크
        else if(title == '' || title == null) {
            alert("등록할 일정의 내용을 입력해주세요.");
        }
        // 등록 폼 값이 유효한 경우 ajax로 등록 요청 후 일정이벤트 refetch 요청
        else {
            $.ajax({
                type: 'POST',
                url: '<c:url value="/church/${ourChurch.id}/schedule/addEvent"/>',
                data: $("#event-form").serialize(),
                contentType : 'application/x-www-form-urlencoded',
                success: function(){
                    calendar.fullCalendar('refetchEvents');
                }
            });
            $("#event-form")[0].reset();
            $("#form-layer").hide();
            $("#mask").hide();
        }
        return false;
    });
    
    $(".datepicker").datepicker({
        dateFormat : "yy-mm-dd",
        monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ],
        dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
        onSelect : function(selectedDate) {
            // 시작일 선택 시 시작일 이후로 종료일 범위 제한
            if (this.id == "startDate") {
                var startDate = $("#startDate").datepicker("getDate");
                var minDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
                $("#endDate").datepicker('option', 'minDate', minDate);
            }
        }
    });

    // date 객체와 구분자를 받아서 format
    function dateFormat(date, sp) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        
        if(m < 10) {
            m = "0" + m;
        }
        if(d < 10) {
            d = "0" + d;
        }
        return y + sp + m + sp + d;
    }
    
    // date 객체를 받아서 시간 정보 format
    function timeFormat(date) {
        var h = date.getHours();
        var m = date.getMinutes();
        
        if(h<10){
            h = "0" + h;
        }
        if(m==0){
            m = "00";
        }
        return h + ":" + m;
    }
    
    // int 형으로 된 day 값을 string으로 변환
    function dayName(day){
        switch(day)
        {
            case 0: return "일"; break;
            case 1: return "월"; break;
            case 2: return "화"; break;
            case 3: return "수"; break;
            case 4: return "목"; break;
            case 5: return "금"; break;
            case 6: return "토"; break;
        };
    };
    
    // date 객체의 값이 유효한지 체크
    function checkDateFormat(date){
        
        var y = date.getFullYear();
        var m = date.getMonth();
        var d = date.getDate();
        
        if(1 > m || m > 12) {
            return false;
        }
        if(1 > d || d > 31) {
            return false;
        }
        if((m==4 || m==6 || m==9 || m==11) && d==31) {
            return false;
        }
        if(m==2) {
            var isleap = (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0));
            
            if(d > 29 || (d == 29 && !isleap)) {
                return false;
            }
        }
        return true;
    };
    
    // 레이어 팝업 마스크
    function wrapWindowByMask() {
        var maskWidth = $(document).width();
        var maskHeight = $(document).height();
        
        $("#mask").css({'width': maskWidth, 'height': maskHeight})
        $("#mask").show();
    }
});
</script>
</sec:authorize>
<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<script type="text/javascript">
$(function(){
    
    var index = 0;

    var calendar = $('#calendar_content').fullCalendar({
        monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
        dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ],
        dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
        header : {
            left : '',
            center : 'title',
            right : 'prev,next today'
        },
        editable : false,
        events : function getEvents(start, end, callback) {
            $.ajax({
                url: '<c:url value="/church/${ourChurch.id}/schedule/getEvents"/>',
                data: {
                    start: Math.round(start.getTime() / 1000),
                    end: Math.round(end.getTime() / 1000)
                },
                dataType: 'json',
                success: function(data) {
                    var events = data;
                    callback(events);
                }
            });
        },
        // 각각의 event 객체와 스크립트로 생성된 동적인 element를 렌더링
        eventRender : function(event, element) {
            index += 1;
            var startDate = dateFormat(event.start, ".") + "(" + dayName(event.start.getDay()) + ")";
            var endDate;            
            if(event.end==null){
                endDate = startDate;
            }
            else {
                endDate = dateFormat(event.end, ".") + "(" + dayName(event.end.getDay()) + ")";
            }
             
            var title = event.title.replace(new RegExp("<", "g"), "&lt");
            title = title.replace(new RegExp(">", "g"), "&gt");
            title = title.replace(new RegExp("\r\n", "g"), "<br/>");
            
            var eventLayer = "<div id='event-layer' style='width:308px; background-color: white; color: black; font-size: 12px; z-index:5'>"
                + "<fieldset style='margin-top: 3px'><p style='margin: 10px'><strong>"+startDate+"</strong>";
            if(!event.allDay){
                var startTime = timeFormat(event.start);
                eventLayer += " <span style='color: #007FCF; font-weight: bold'>"+startTime+"</span>";
            }
            if(startDate!=endDate || !event.allDay) {
                eventLayer += " - ";
            }
            if(startDate!=endDate) {
                eventLayer += "<strong>"+endDate+"</strong>";
            }
            if(!event.allDay) {
                var endTime = timeFormat(event.end);
                eventLayer += "<span style='color: #007FCF; font-weight: bold'>"+endTime+"</span>";
            }
            eventLayer += "</p><p style='width: 260px; margin: 10px; font-size: 13px; word-wrap: break-word;'>"+title+"</p></fieldset></div>";
            
            // event 객체에 mouseover 이벤트가 발생하면 qTip을 이용한 레이어 팝업창 띄움
            element.qtip({
                content: {
                    text: eventLayer,
                },
                hide: { when: { target: false }, fixed: true },
                onHide: {},
                position: { 
                    adjust: { x: -5, y: 15 },
                    corner: { target: 'topLeft', tooltip: 'topLeft' }
                },
                style: { 
                    width: 310,
                    
                    padding: 0,
                    border: { width: 1, radius: 0, color: '#777' }
                }
            });
        }
    });

    // date 객체와 구분자를 받아서 format
    function dateFormat(date, sp) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        
        if(m < 10) {
            m = "0" + m;
        }
        if(d < 10) {
            d = "0" + d;
        }
        return y + sp + m + sp + d;
    }
    
    // date 객체를 받아서 시간 정보 format
    function timeFormat(date) {
        var h = date.getHours();
        var m = date.getMinutes();
        
        if(h<10){
            h = "0" + h;
        }
        if(m==0){
            m = "00";
        }
        return h + ":" + m;
    }
    
    // int 형으로 된 day 값을 string으로 변환
    function dayName(day){
        switch(day)
        {
            case 0: return "일"; break;
            case 1: return "월"; break;
            case 2: return "화"; break;
            case 3: return "수"; break;
            case 4: return "목"; break;
            case 5: return "금"; break;
            case 6: return "토"; break;
        };
    };
});
</script>
</sec:authorize>
</body>
</html>