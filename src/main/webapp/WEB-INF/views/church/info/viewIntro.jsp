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
<h2>교회안내</h2>
<div class="intro">
<div class="church_content">

<div id="titleWrap">
<h3>인사말</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>인사말</span></p>
</div>
    
    <c:choose>
    <c:when test="${not empty intro}">
        <div class="slogan">
            ${intro.slogan}
        </div>
        <div class="intro_image"><img src="<c:url value="${intro.introImage.path}" />" alt="소개이미지" width="680" height="240"/></div>
        <div class="txt"><s:eval expression="intro.intro.replaceAll('\r\n', '<br/>')"/></div>
    </c:when>
    <c:otherwise>
        <div class="row-fluid">
            <div class="nocontents-unit">
            <h1></h1>
            <p>해당 콘덴츠가 없습니다.</p>
            <p>
            <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
            <a class="btn btn-primary btn-large" href="<c:url value="/church/${ourChurch.id}/intro/new"/>">인사말 작성</a>
            </sec:authorize>
            </p>
            </div>
        </div>    
    </c:otherwise>
    </c:choose>

<p class="p20"></p>

</div><!--// intro_content -->

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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/intro"/>">인사말</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/history"/>">교회연혁</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/worship"/>">예배안내</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<c:if test="${not empty intro}">
<p class="btn_admin"><a href="<c:url value="/church/${ourChurch.id}/intro/edit" />"><img src="<c:url value="/resources/images/church/info/btn_admin.gif" />" alt="페이지관리" width="112" height="34" /></a></p>
</c:if>
</sec:authorize>

</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->
</body>
</html>