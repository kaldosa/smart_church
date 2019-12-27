<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">
    <sec:authorize ifAnyGranted="ROLE_USER">
    <div class="profile">
        <div class="name">
            <img src="<c:url value="/resources/images/church/common/icon_profile.gif" />" alt="프로필아이콘" width="31" height="23" /><strong>김동욱</strong> 님
        </div>
        <p><img src="<c:url value="/resources/images/church/common/right_profile_img.gif" />" alt="프로필이미지" width="64" height="64" /></p>
        <div class="logout">
            <ul>
                <li>
                    <a href="#">내정보</a></li>
                <li> <a><img src="<c:url value="/resources/images/church/common/btn_logout2.gif" />" alt="로그아웃" width="53" height="20" class="btn" /></a></li>
            </ul>
        </div>
    </div><!--// profile -->
    </sec:authorize>
    <div class="rnb">
        <ul>
            <li class="bold"><a href="intro.html">인사말</a></li>
            <li><a href="history.html">교회연혁</a></li>
            <li><a href="worship.html">예배안내</a></li>
            <li><a href="serve.html">섬기는분들</a></li>
            <li><a href="map.html">오시는길</a></li>
        </ul>
    </div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->