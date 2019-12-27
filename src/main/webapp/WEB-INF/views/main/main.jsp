<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Smart Church</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="contents-wrap">
<div class="row content-top">
<div class="span8">
<div class="content-title"><h3>공지사항</h3><a href="<c:url value="/notices"/>" class="more">더보기 &#43;</a></div>
<div class="list">
<ul class="list">
<c:forEach var="item" items="${listNotice}" varStatus="status">
<c:if test="${status.index < 7}">
<li><div class="row"><div class="span6">&bull; <a href="<c:url value="/notices/${item.id}?page=1"/>">
<s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 40)" htmlEscape="true"/></a></div><div class="span1"><s:eval expression="item.createdDate" /></div></div></li>
</c:if>
</c:forEach>
</ul>
</div>
</div>
<div class="span4">
<div class="apps">
<img src="<c:url value="/resources/images/main/img_bibleword_title.gif" />" />
<div class="bibleword-wrap">
<p class="bibleword"><s:eval expression="todayWords.words.replaceAll('\r\n', '<br/>')"/></p>
<p class="bibleabbr">${todayWords.verse}</p>
</div>
</div>

</div>
</div>

<div class="row">
<div class="span8">
<div class="content-title"><h3>최근가입현황</h3><a href="<c:url value="/listChurch?ipp=8"/>" class="more">더보기 &#43;</a></div>
<div class="recent">
<%-- <span class="recent_prev"><img src="<c:url value="/resources/images/main/btn_recent_prev.png" />" width="25" height="93"/></span> --%>
<div class="recent-list">
<c:if test="${empty listChurch}">
<div class="recent-empty">

</div>
</c:if>
<ul class="thumbnails">
<c:forEach var="item" items="${listChurch}" varStatus="status">
<li class="spanW95"><a href="<c:url value="/church/${item.id}" />">
<c:choose>
<c:when test="${not empty item.logo.path}"><img class="thumbnail" src="<c:url value="${item.logo.path}" />" width="83" height="83"/></c:when>
<c:otherwise><img class="thumbnail" src="<c:url value="/resources/images/church/common/img_default_logo.png" />"  width="83" height="83" /></c:otherwise>
</c:choose>
</a></li>
</c:forEach>
</ul>
</div>
<%-- <span class="recent_next"><img src="<c:url value="/resources/images/main/btn_recent_next.png" />" width="25" height="93"/></span> --%>
</div>
</div>
<div class="span4">
<div class="service-link">
<a href="<c:url value="/application-service"/>"><img src="<c:url value="/resources/images/main/img_service.png" />" /></a>
</div>
</div>
</div>

</div>
</div>
</body>
</html>