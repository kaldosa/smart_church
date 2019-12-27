<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Insert title here</title>

<style type="text/css">
.event-update-box {margin: 10px 0; padding: 3px 10px; *background-color: #ECECEC;}
.event-update-field {margin: 10px 0; padding: 5px 0; border-bottom: 1px solid #ECECEC;}
.event-update-field span {float: left; width: 50px; font-weight: bold;}
.event-update-button {margin: 30px 0; text-align: center;}
.event-update-box #repeat_option {margin: 5px 50px; text-align: left;}
.event-update-box #repeat_option th {padding: 2px 5px 0 0;}
.event-update-box #repeat_option tr {height: 40px; padding: 2px 2px;}
.event-update-box #repeat_option td {padding: 2px 10px;}
</style>
</head>

<body>
<h2>교회소식</h2>
<div class="intro">
<div class="church_content">

<div id="titleWrap">
<h3>교회일정</h3>
<p class="home">Home &gt; 교회소식 &gt; <span>교회일정</span></p>
</div>

<%-- <h3 class="tit">
    <img src="<c:url value="/resources/images/church/news/btn_list.png" />" /> <strong style="font-size: 14px">일정 수정</strong>
</h3> --%>

<!-- 일정 수정 폼 -->
<c:url var="updateUrl" value="/church/${ourChurch.id}/schedule/update" />
<form:form id="update-form" method="post" commandName="event" action="${updateUrl}">
    <form:errors path="*"></form:errors>
    <fieldset>
        <form:hidden path="id" />
        <form:hidden path="start" />
        <form:hidden path="end" />
        <form:hidden path="dateEditable" />
        
<div class="event-update-box">
<div class="event-update-field">
<span>일시</span>
<form:input path="startDate" size="9" readOnly="readOnly" cssClass="input_text input_w100" />
<form:select path="startTime" cssClass="input_select select-time">
    <form:options items="${startTimeSelect}" />
</form:select>
 - <form:input path="endDate" size="9" readOnly="readOnly" cssClass="input_text input_w100" />
<form:select path="endTime" cssClass="input_select select-time">
    <form:options items="${endTimeSelect}" />
</form:select>
&nbsp;<form:checkbox path="allday" id="allday" label=" 종일" />
</div>

<div class="event-update-field">
<span>내용</span>
<form:textarea path="title" rows="3" cols="50" cssClass="input_textarea input_w350 input_h80"/>
</div>

<div class="event-update-field">
<span>반복</span>
<form:checkbox path="repeatable" id="repeatable" label="반복" />

<table id="repeat_option">
<tr>
    <th>반복빈도</th> 
    <td><form:select path="repeatFreq" id="repeat_freq" cssClass="input_select">
        <form:options items="${enumValues}" /></form:select></td>
        <th class="repeat_cycle">반복주기</th>
        <td><form:select path="repeatCycle" cssClass="input_select repeat_cycle">
        <c:forEach var="i" begin="1" end="30"><form:option value="${i}">${i}일</form:option></c:forEach></form:select></td>
</tr>
<tr id="repeat_day">
    <th>반복요일</th>
    <td colspan="3">
        <form:checkbox path="repeatDay" value="0" label=" 일" />
        <form:checkbox path="repeatDay" value="1" label=" 월" />
        <form:checkbox path="repeatDay" value="2" label=" 화" />
        <form:checkbox path="repeatDay" value="3" label=" 수" />
        <form:checkbox path="repeatDay" value="4" label=" 목" />
        <form:checkbox path="repeatDay" value="5" label=" 금" />
        <form:checkbox path="repeatDay" value="6" label=" 토" /></td>
</tr>
<tr id="repeat_date">
    <th>반복일</th>
    <td colspan="3"><form:radiobutton path="repeatDate" value="date" checked="checked" label=" 일" />
        <form:radiobutton path="repeatDate" value="day" style="margin-left: 10px" label=" 요일"/></td>
</tr>
<tr>
    <th>시작날짜</th>
    <td colspan="3"><form:input type="text" id="originStartDate" path="originStartDate" readOnly="readOnly" size="9" cssClass="input_text input_w100 datepicker" /></td>
</tr>
<tr>
    <th>반복종료</th>
    <td><form:radiobutton path="repeatEnd" value="false" label=" 계속 반복" /></td>
    <th><form:radiobutton path="repeatEnd" value="true" label=" 반복 종료일" style="margin-left: 10px; text-align: left" /></th>
    <td style="width: 76px;"><form:input path="repeatEndDateStr" id="repeatEndDate" size="9" cssClass="input_text input_w100 datepicker" /></td>
    </tr>
</table>
</div>

<div class="event-update-button">
    <input id="editEvent" type="image" src="<c:url value="/resources/images/church/news/btn_edit.png" />" class="save" />
    <a href="<c:url value="/church/${ourChurch.id}/schedule" />"><img src="<c:url value="/resources/images/church/news/btn_cancle.png" />" id="cancel" /></a>
</div>
</div>

</fieldset>
</form:form><!-- //일정 수정 폼 -->

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
</div>    
<script type="text/javascript">
$(function() {
    
    // 엘리먼트들이 load 될 때 필요한 속성 지정
    $(window).load(function() {
        $("#repeatable").trigger("change");
        var minDate = new Date($("#startDate").val());
        $("#endDate").datepicker("option", "minDate", minDate);
        
        $("#endDate").val($("#startDate").val());
        
        if ($("#dateEditable").val() == "false") {
            $("#startDate,#endDate").datepicker("destroy");
        }
        var textarea = $("textarea[name=title]"); 
        var text = textarea.val();
        textarea.val("");
        textarea.focus();
        textarea.val(text);
    });
    
    // 종일 유무에 따라 시간 select show/hide
    $("#allday").change(function() {
        if (this.checked) {
            $(".select-time").hide();
        } else {
            $(".select-time").show();
            if ($("#endTime").val() == "00:00:00") {
                $("#startTime").trigger("change");                
            }
        }
    }).trigger("change");
    
    // 시작시간 select 값 선택에 따른 종료시간 option 범위 ajax 요청 후 변경
    $("#startTime").change(function() {
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var startTime = $("#startTime").val();
        $.ajax({
            type: 'GET',
            url: 'getEndTimeOptions',
            data: ({"startDate" : startDate, "endDate" : endDate, "startTime" : startTime}),
            success: function(data) {
                var options = '';
                for (var i = 0; i < data.length; i++) {
                    options += '<option value="' + data[i].value + '">' + data[i].label + '</option>';
                }
                $("#endTime").html(options);
            }
        });
    });
    
    $("#endDate").change(function() {
        $("#startTime").trigger("change");
    });

    // 반복 유무에 따라 반복 옵션 show/hide
    $("#repeatable").change(function() {
        if (this.checked) {
            $("#repeat_option").show();
            
            $("#repeat_freq").trigger("change");
            $(":radio[name=repeatEnd]").trigger("change");
        } else {
            $("#repeat_option").hide();
        }
    }).trigger("change");

    // 반복 빈도 값에 따른 옵션 element 변경
    $("#repeat_freq").change(function() {
        if ($("#repeat_freq").val() == "DAY") {
            $(".repeat_cycle").show();
            $("#repeat_day").hide();
            $("#repeat_date").hide();
        }
        else if ($("#repeat_freq").val() == "WEEK") {
            
            var date = new Date($("#originStartDate").val());
            var day = date.getDay();
            $(":checkbox[value="+day+"]").attr("checked", "checked");
            
            $("#repeat_day").show();
            $(".repeat_cycle").hide();
            $("#repeat_date").hide();
        }
        else {
            $("#repeat_date").show();
            $(".repeat_cycle").hide();
            $("#repeat_day").hide();
        }
    }).trigger("cahnge");
    
    // 반복 종료 유무에 따라 반복종료일 show/hide
    $(":radio[name=repeatEnd]").change(function() {
        if ($(":radio[name=repeatEnd]:checked").val() == "false") {
            $("#repeatEndDate").hide();
        } else {
            $("#repeatEndDate").show();
            if ($("#repeatEndDate").val() == "") {
                $("#repeatEndDate").focus();                
            }
        }
    }).trigger("change");
    
    // 수정 폼에서 내용 textarea 입력 시 line 수 체크하여 제한
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
    
    // 수정 폼 전송하기 전 validation 체크
    $("#update-form").submit(function() {
        if ($("#title").val() == "" || $("#title").val() == null) {
            alert("등록할 일정의 내용을 입력해주세요.");
            return false;
        }
        // 내용 textarea 글자 수 제한
        else {
            var limitField = $("#title");
            var limitNum = 100;
            var text_byte = 0;
            var length = 0;
            var text = $("#title").val();
            
            for (var i=0; i<text.length; i++) {
                var one_char = text.charAt(i);
                
                text_byte += (one_char.charCodeAt(0) > 128) ? 2 : 1;
                
                if (text_byte <= limitNum) {
                    length = i + 1;
                } 
                else {
                    break;
                }
            }
            
            if (text_byte > limitNum) {
                var display_text = text.substr(0, length);
                limitField.val(display_text);
                var msg = "내용은 " + limitNum + "byte(한글 " + limitNum/2 + "자/영문,숫자 " + limitNum + "자) 이하로 입력해주세요.";
                alert(msg);
                return false;
            }
        }
        
        // 반복 빈도가 매주인 경우 하나 이상의 반복 요일 선택 필수
        if ($("#repeat_freq").val() == "WEEK") {
            var length = $(":checkbox[name=repeatDay]:checked").length;
            if (length < 1) {
                alert("매주 반복할 요일을 선택해주세요.");
                return false;
            }
        }
        
        // 반복 종료일 선택 시 종료일 날짜 지정 필수
        if ($(":radio[name=repeatEnd]:checked").val() == "true") {
            if ($("#repeatEndDate").val() == "" || $("#repeatEndDate").val() == null) {
                alert("반복을 종료할 날짜를 지정해주세요.");
                return false;
            }
        }
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
    
});
</script>
</body>
</html>