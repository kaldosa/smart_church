<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
</head>
<body>
<h2>생명의말씀</h2>
<div class="intro">
<div class="church_content">

<div id="titleWrap">
<h3>설교</h3>
<p class="home">Home &gt; 생명의말씀 &gt; <span>설교</span></p>
</div>

<s:bind path="entity.*">

<div id="writeWrap">
<c:url var="updateUrl" value="/church/${ourChurch.id}/sermon/${entity.id}" />
<form:form name="update-form" action="${updateUrl}" method="put" id="update-form" modelAttribute="entity" enctype="multipart/form-data">
<fieldset><legend>설교 수정 폼</legend>


<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="subject" element="li" cssClass="error"/>
<form:errors path="preacher" element="li" cssClass="error"/>
<form:errors path="bible" element="li" cssClass="error"/>
<form:errors path="sermonDate" element="li" cssClass="error"/>
<form:errors path="note" element="li" cssClass="error"/>
<form:errors path="mp3" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>


<div class="writeCon">
<dl>
<dt>제목</dt>
<dd><form:input path="subject" id="subject" maxlength="50" cssClass="input_text input_w450" htmlEscape="true"/></dd>
</dl>
<dl>
<dt><label for="preacher">설교자</label></dt>
<dd><form:input path="preacher" type="text" id="preacher" maxlength="20" cssClass="input_text input_w150" htmlEscape="true"/></dd>
</dl>
<dl>
<dt><label for="bible">성경본문</label></dt>
<dd><form:input path="bible" type="text" id="bible" maxlength="50" cssClass="input_text input_w150" htmlEscape="true"/></dd>
</dl>

<dl>
<dt><label for="date">설교날짜</label></dt>
<dd><form:input path="sermonDate" type="text" id="date" maxlength="10" cssClass="input_text input_w150"/></dd>
</dl>

<dl>
<dt><label for="sermon_mp3">설교음성</label></dt>
<dd>
<input type="text" id="fileName-mp3" class="file_input_textbox input_text input_w450" readonly="readonly" />
<div class="file_input_div">
<img class="file_input_button" src="<c:url value="/resources/images/commons/file_select.gif" />" alt="Browse" />
<input type="file" class="file_input_hidden" onchange="javascript: document.getElementById('fileName-mp3').value = this.value" id="sermon_mp3" name="mp3" />
</div>
</dd>
</dl>

<dl>
<dt class="end"><label for="sermon_note">설교노트</label></dt>
<dd>
<input type="text" id="fileName-note" class="file_input_textbox input_text input_w450" readonly="readonly" />
<div class="file_input_div">
<img class="file_input_button" src="<c:url value="/resources/images/commons/file_select.gif" />" alt="Browse" />
<input type="file" class="file_input_hidden" onchange="javascript: document.getElementById('fileName-note').value = this.value" id="sermon_note" name="note" />
</div>
</dd>
</dl>

</div><!-- writeCon -->

<div id="sermon-btn" class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">수정</button>
    <a href="<c:url value="/church/${ourChurch.id}/sermon"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

</fieldset>
</form:form>
</div><!-- writeWrap -->

</s:bind>

<p class="pd20"></p>
</div><!-- news_content -->
    
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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/sermon" />">설교</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/message" />">1분메세지</a></li>
</ul>
</div><!-- rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->
</div><!-- intro -->

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>

<script type="text/javascript">
$(function() {
    
	$( "#date" ).datepicker({dateFormat: 'yy.mm.dd'});
	
    $('#update-form').validate({
        errorLabelContainer: ".validate-container ul",
        wrapper: "li",
        errorPlacement: function(error, element) {
            error.appendTo(".validate-container ul");
        },
        invalidHandler: function() {
            $("#validation-dialog").dialog('open');
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'subject': {
                required: true,
                maxlength: 50,
                htmlTag: true
            },
            'preacher': {
                required: true,
                maxlength: 10,
                htmlTag: true
            },
            'sermonDate': {
                required: true,
                dateFormat: true
            },
            'note': {
                accept: "txt|doc|pdf"
            },
            'mp3': {
                accept: "mp3"
            }
        },
        messages: {
            'subject': {
                required: '<s:message code="NotEmpty.subject" />'
            },
            'preacher': {
                required: '설교자를 입력해주세요.'
            },
            'sermonDate': {
                required: '<s:message code="NotNull.sermonDate" />'
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
        close: function(event, ui) {
            $('.validate-container ul').children().remove();
        }
    });
});
</script>
</body>
</html>