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
<h3>섬기는분들</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>섬기는분들</span></p>
</div>
    
<div class="listWrap">

<c:choose>
<c:when test="${not empty servers}">
<ul class="thumbnails">
<c:forEach var="item" items="${servers}" varStatus="status">
<li class="span4">

<div class="row">
<div class="thumbnail spanW80">
<c:choose>
<c:when test="${not empty item.photo}"><img src="<c:url value="${item.photo.path}"/>" alt="thumb" width="80" height="90" class="left_type"/></c:when>
<c:otherwise><img src="<c:url value="/resources/images/church/common/img_default_profile.png"/>" alt="사진" width="80" height="90" class="left_type"/></c:otherwise>
</c:choose>
</div>

<div class="span2 break-word">
<h3 class="title">${item.name} ${item.position}</h3>
<p><s:eval expression="item.intro.replaceAll('\r\n', '<br/>')"/></p>
</div>

</div>
</li>
</c:forEach>

</ul>
</c:when>

<c:otherwise>
<div class="row-fluid">
    <div class="nocontents-unit">
    <h1></h1>
    <p>해당 콘덴츠가 없습니다.</p>
    </div>
</div>
</c:otherwise>
</c:choose>
</div>

</div><!-- church_content -->


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
<li><a href="<c:url value="/church/${ourChurch.id}/worship"/>">예배안내</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>
</div><!-- rnb -->

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div class="btn_admin"><a href="<c:url value="/church/${ourChurch.id}/serve/edit" />"><img src="<c:url value="/resources/images/church/info/btn_admin.gif" />" alt="페이지관리" width="112" height="34" /></a></div>
</sec:authorize>

</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->
</body>
</html>