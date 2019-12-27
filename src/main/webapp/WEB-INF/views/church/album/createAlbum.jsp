<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<style type="text/css">
.alert-span {width: 660px;}
</style>
</head>

<body>
<h2>교회앨범</h2>
<div class="intro">

<div class="church_content">

<div id="titleWrap">
<h3>교회앨범</h3>
<p class="home">Home &gt; 교회앨범 &gt; <span>교회앨범</span></p>
</div>


<div id="writeWrap">
<s:bind path="entity.*">
<c:url var="createUrl" value="/church/${ourChurch.id}/album" />
<form:form name="create-form" action="${createUrl}" method="post" id="create-form" modelAttribute="entity">
<fieldset><legend>교회앨범 등록 폼</legend>

<c:if test="${not empty status.errorMessages}">
<div class="row"><div class="alert-span">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="subject" element="li" cssClass="error"/>
<form:errors path="contents" element="li" cssClass="error"/>
</ul>
</div>
</div>
</div>
</div>
</c:if>

<div class="writeCon">
<dl>
<dt><label for="subject">앨범 제목</label></dt>
<dd><form:input path="subject" id="subject" maxlength="50" cssClass="input_text input_w500"/></dd>
</dl>

<dl>
<dt><label for="contents">내용</label></dt>
<dd>
<form:textarea path="contents" id="contents" cssClass="input_textarea" cols="120" rows="5" cssStyle="width: 520px; height: 100px;" />
<div class="ex">140자 이내로 작성해주세요.</div>
</dd>
</dl>
</div><!-- writeCon -->

<div id="album-btn" class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">등록</button>
    <a href="<c:url value="/church/${ourChurch.id}/album"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

<form:hidden path="path" value="${ourChurch.path}"/>
</fieldset>
</form:form>
</s:bind>
</div><!-- writeWrap -->


<p class="pd30"></p>
</div><!-- news_content -->

<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">
<sec:authorize ifAnyGranted="ROLE_USER">
<div class="profile-wrap">

<div class="profile">
<sec:authentication property="principal" var="user" />
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="프로필 사진" width="50" height="50" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/commons/img_default_profile.gif" />" alt="프로필 사진" width="50" height="50" /></c:otherwise>
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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/album" />">교회앨범</a></li>
</ul>
</div>
<!-- rnb -->
</div>
<!--// 오른쪽 프로필 및 서브메뉴 -->
</div>

<script type="text/javascript">
$(function() {
    $('#create-form').validate({
        showErrors: function(errorMap, errorList){
            if(this.numberOfInvalids()) {
            	alert(errorList[0].message);
            }
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
            'contents': {
                required: true,
                maxlength: 140,
                htmlTag: true
            }
        },
        messages: {
            'subject': {
                required: '<s:message code="NotEmpty.subject" />'
            },
            'contents': {
                required: '<s:message code="NotEmpty.contents" />'
            }
        }
    });
});
</script>
</body>
</html>