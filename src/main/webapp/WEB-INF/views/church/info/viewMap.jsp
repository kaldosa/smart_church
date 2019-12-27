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
<h3>오시는길</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>오시는길</span></p>
</div>

<div id="map_canvas"></div>

<div class="addr">
<ul>
<li>
    <span class="tit">+ 주소</span>
    ${ourChurch.churchMeta.address}
</li>
</ul>
<ul>
<li>
    <span class="tit">+ 전화번호</span>
    ${ourChurch.churchMeta.contact}
</li>
</ul>
<ul>
<li>
    <span class="tit">+ 대중교통</span>
    <p class="traffic"><%-- <s:eval expression='T(com.laonsys.springmvc.extensions.utils.StringUtils).replaceCRLF(church.traffic)'/> --%>
    <c:if test="${not empty ourChurch.churchMeta.traffic}"><s:eval expression='ourChurch.churchMeta.traffic.replaceAll("\r\n", "<br/>")'/></c:if>
    </p>
</li>
</ul>
</div><!--// addr -->

<p class="pd20" />
</div><!-- ser_content -->

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
<li><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>

<%-- <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div class="btn_admin"><a href="<c:url value="/church/${ourChurch.id}/map/edit" />"><img src="<c:url value="/resources/images/church/info/btn_admin.gif" />" alt="페이지관리" width="112" height="34" /></a></div>
</sec:authorize> --%>
</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->
<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false&language=ko&region=KR"></script>
<script type="text/javascript">
$(function() {
    var geocoder = new google.maps.Geocoder();
	var address = '${ourChurch.churchMeta.address}';
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var myOptions = {
                zoom: 18,
                maxZoom: 19,
                center: results[0].geometry.location,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                mapTypeControl: false
            };
            var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
            new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
});
</script>
</body>
</html>