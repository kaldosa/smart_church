<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<link rel="styleSheet" type="text/css" href="<c:url value="/resources/js/jquery/ui/themes/smoothness/jquery.ui.timepicker.css" />"/>
<style type="text/css">
.input_w500 {width: 500px;}
</style>
</head>
<body>
<h2>교회안내</h2>
<div class="intro">
<!-- 본문 -->
<div class="church_content">

<div id="titleWrap">
<h3>예배안내</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>예배안내</span></p>
</div>
    
<div id="writeWrap">

<div class="admin_form"><c:url var="createUrl" value="/church/${ourChurch.id}/worship" />
<form:form name="hisForm" action="${createUrl}" method="post" modelAttribute="worship" id="create-form">        
<fieldset>
<legend>예배안내 폼</legend>
<div class="admin_form_input">
<label class="admin_form_label" for="worship_title">예배명</label><form:input id="worship_title" path="title" cssClass="input_text input_w100" maxlength="20"/>
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="worship_day_of_week">예배일</label>
<form:select path="dayOfWeek" id="worship_day_of_week" cssClass="input_select input_w200" >
<option value="">예배일을 선택하세요</option>
<form:options itemLabel="value" itemValue="key"/>
</form:select>
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="timepicker">시간</label>
<form:input id="timepicker" path="time" cssClass="input_select input_w100" />
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="place">장소</label>
<form:input id="place" path="place" cssClass="input_text input_w500" maxlength="20"/><input class="admin_form_btn" type="image" src="<c:url value="/resources/images/church/common/btn_save3.gif" />" alt="저장"/>
</div>

</fieldset>
</form:form>
</div>

<c:url var="updateUrl" value="/church/${ourChurch.id}/worship"/>
<form:form id="update-form" action="${updateUrl}" method="put">
<div class="result_list">
<ul class="sortable">

<c:forEach var="worship" items="${worships}" varStatus="status">
<li id="${worship.id}">
<span class="item worship_title"><c:out escapeXml="true" value="${worship.title}"/></span>
<span class="item worship_date">${worship.dayOfWeek.value} ${worship.time}</span>
<span class="item worship_place"><c:out escapeXml="true" value="${worship.place}"/></span>
<span class="item delete_btn"><img class="del-item" src="<c:url value="/resources/images/church/common/btn_delete.gif" />" alt="삭제" width="52" height="24"/></span>
</li>
</c:forEach>

</ul>
</div>

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">완료</button>
    <a href="<c:url value="/church/${ourChurch.id}/worship"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

</form:form>

<p class="pd20"></p>
</div>
</div><!--// 본문 -->


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

<s:eval var="logoutUrl" expression="@envvars['web.logout']" />
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
<li><a href="<c:url value="/church/${ourChurch.id}/intro"/>">인사말</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/history"/>">교회연혁</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/worship"/>">예배안내</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>
</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->

<div id="validation-dialog"><div class="contains"><ul></ul></div></div>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/ui/jquery.ui.timepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/i18n/jquery.ui.timepicker-ko.js"/>"></script>
<script type="text/javascript">

$(function() {

	$('#timepicker').timepicker({
	    showPeriod: true,
	    showPeriodPosition: true
	});
	
    $( ".sortable" ).sortable({
        revert: true,
        cursor: "move",
        scroll: true
    });
    
    $('ul, li').disableSelection();

    var options = {
        dataType: 'json',
        success: function(data) {
            $('#create-form').resetForm();
            
            if(data == null) {
                $('.contains ul').append('<li><label class="error">입력 정보가 유효하지 않습니다.</label></li>');
                $('.contains ul').show();
                $("#validation-dialog").dialog('open');
            } else {
                $( ".sortable" ).prepend('<li '
                        + 'id="' 
                        + data.id
                        + '">'
                        + '<span class="item worship_title">' + data.title + '</span>'
                        + '<span class="item worship_date">' + data.dayOfWeek + ' ' + data.time + '</span>'
                        + '<span class="item worship_place">' + data.place + '</span>'
                        + '<span class="item delete_btn"><img class="del-item" src="<c:url value="/resources/images/church/common/btn_delete.gif" />" alt="삭제" width="52" height="24"/></span>'
                        + '</li>');
            }
            return false;
        }
    };
        
    $('#create-form').validate({
        submitHandler: function(form) {
            $(form).ajaxSubmit(options);
            return false;
        },
        errorLabelContainer: ".contains ul",
        wrapper: "li",
        errorPlacement: function(error, element) {
            error.appendTo("#validation-dialog ul");
        },
        invalidHandler: function() {
            $("#validation-dialog").dialog('open');
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'title': {
                required: true,
                maxlength: 20,
                htmlTag: true
            },
            'dayOfWeek': {
            	required: true
            },
            'time': {
                required: true,
                timeFormat: true
            },
            'place': {
            	required: true,
            	maxlength: 20,
            	htmlTag: true
            }
        },
        messages: {
            'title': {
                required: '예배명을 입력하세요.'
            },
            'dayOfWeek': {
            	required: '예배일을 선택하세요.'
            },
            'time': {
                required: '시간을 선택하세요.'
            },
            'place': {
                required: '장소를 입력하세요.'
            }
        }
    });

    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
    $("#validation-dialog").dialog({
        autoOpen:false,
        modal:true,
        show: 'fade',
        hide: 'fade',
        resizable: false,
    });
    
    $('.del-item').live('click', function() {
        if(!confirm("삭제하시겠습니까?")) {
            return false;
        }
        
        var id = $(this).parent().parent().attr("id");
        $.ajax({
            url: '<c:url value="/church/${ourChurch.id}/worship/' + id + '" />',
            type: 'DELETE'
        });
        $(this).parent().parent().remove();
        return false;
    });
    
    $('#update-form').submit(function() {
        var result = $( ".sortable" ).sortable('toArray');
        if(result.length > 0) {
            $.each(result, function(i, val) {
                var element = $('<input type="hidden" name="order" />');
                element.val(val);
                element.appendTo($('#update-form'));
            });
        } else {
            return false;
        }
    });
});
</script>
</body>
</html>