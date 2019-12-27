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
<h3>1분메세지</h3>
<p class="home">Home &gt; 생명의말씀 &gt; <span>1분메세지</span></p>
</div>

<s:bind path="entity.*">

<div id="writeWrap">
<c:url var="createUrl" value="/church/${ourChurch.id}/message" />
<form:form name="create-form" action="${createUrl}" method="post" id="create-form" modelAttribute="entity">
<fieldset><legend>1분메세지 작성 폼</legend>


<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="subject" element="li" cssClass="error"/>
<form:errors path="subTitle" element="li" cssClass="error"/>
<form:errors path="contents" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>

<div class="writeCon">
<dl>
<dt>제목</dt>
<dd><form:input path="subject" id="subject" maxlength="50" cssClass="input_text input_w500"/></dd>
</dl>
<dl>
<dt><label for="sub-title">소제목</label></dt>
<dd><form:input path="subTitle" type="text" id="sub-title" maxlength="128" cssClass="input_text input_w500"/><div class="ex">128자 이내로 작성해주세요.</div></dd>
</dl>
<dl>
<dt><label for="contents">말씀</label></dt>
<dd>
<form:textarea path="contents" id="contents" cssClass="input_textarea" cols="120" rows="5" cssStyle="width: 520px; height: 100px;" />
<div class="ex">5000자 이내로 작성해주세요.</div>
</dd>
</dl>
</div><!-- writeCon -->

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">등록</button>
    <a href="<c:url value="/church/${ourChurch.id}/message"/>" class="btn btn-large">취소</a>
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
<li><a href="<c:url value="/church/${ourChurch.id}/sermon" />">설교</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/message" />">1분메세지</a></li>
</ul>
</div><!-- rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->
</div><!-- intro -->

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>

<script type="text/javascript">
$(function() {
	
    $('#create-form').validate({
        submitHandler: function(form) {
            form.submit();
        },
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
            'subTitle': {
                required: true,
                maxlength: 128,
                htmlTag: true
            },
            'contents': {
                required: true,
                maxlength: 5000,
                htmlTag: true
            }
        },
        messages: {
            'subject': {
                required: "제목를 입력해주세요."
            },
            'subTitle': {
                required: "소제목을 입력해주세요."
            },
            'contents': {
                required: "내용을 입력해주세요."
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