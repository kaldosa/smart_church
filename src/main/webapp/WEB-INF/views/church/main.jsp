<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<link href="<c:url value="/resources/js/jquery/easyslider/css/screen.css"/>" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
#slider {margin-top: 5px; width: 660px; height: 335px;}
#slider2 {margin-top: 5px; width: 660px; height: 335px;}
/* #slider ul li {width: 640px; height: 335px;} */
</style>
</head>
<body>

<!-- 메인 배너 -->    
<div class="banner">
    <div class="adBanner">
        <c:choose>
        <c:when test="${empty mainImages}">
        <div id="slider2">
        <img src="<c:url value="/resources/images/church/main/img_default_main.jpg" />" alt="" width="660" height="335"/>
        </div>
        </c:when>
        <c:when test="${fn:length(mainImages) eq 1}">
        <div id="slider2">
              <c:forEach var="image" items="${mainImages}" varStatus="stat">
                  <li><img src="<c:url value="${image.path}" />" alt="" width="660" height="335"/></li>
              </c:forEach>
        </div>
        </c:when>
        <c:otherwise>
          <div id="slider">
          <ul>
              <c:forEach var="image" items="${mainImages}" varStatus="stat">
                  <li><img src="<c:url value="${image.path}" />" alt="" width="660" height="335"/></li>
              </c:forEach>
          </ul>
         </div>
       </c:otherwise>
        </c:choose>
    </div>
    <div id="newsBn" class="mainBanner">
        <h2><img src="<c:url value="/resources/images/church/main/mainBanner_img1.jpg" />" alt="우리교회소식" width="220" height="70" /></h2>
        <h3><a href="<c:url value="/church/${churchId}/news" />">우리 교회소식</a></h3>
        <ul>
            <li><a href="<c:url value="/church/${churchId}/news" />">우리 교회가 궁금하신가요?<br />
                다양한 소식을<br />
                함께 나누세요.</a>
            </li>
        </ul>
        <div class="more"><a href="<c:url value="/church/${churchId}/news" />"><img src="<c:url value="/resources/images/church/main/icon_more.gif" />" alt="news_more" width="20" height="20"/></a></div>
    </div><!--// 배너1 -->
    <div id="prayBn" class="mainBanner">
        <h2><img src="<c:url value="/resources/images/church/main/mainBanner_img2.jpg" />" alt="우리중보기도" width="220" height="70" /></h2>
        <h3><a href="<c:url value="/church/${churchId}/pray" />">우리 중보기도</a></h3>
        <ul>
            <li><a href="<c:url value="/church/${churchId}/pray" />">마음을 나누고 함께 하는곳!<br />
                기도의 힘으로<br />
                이루리라!</a>
            </li>
        </ul>
        <div class="more"><a href="<c:url value="/church/${churchId}/pray" />"><img src="<c:url value="/resources/images/church/main/icon_more.gif" />" alt="pray_more" width="20" height="20"/></a></div>
    </div><!--// 배너2 -->
    <div id="albumBn" class="mainBanner">           
        <h2><img src="<c:url value="/resources/images/church/main/mainBanner_img3.jpg" />" alt="우리 앨범" width="220" height="70" /></h2>
        <h3><a href="<c:url value="/church/${churchId}/album" />">우리 앨범</a></h3>
        <ul>
            <li><a href="<c:url value="/church/${churchId}/album" />">추억과 기쁨이 있습니다.<br />
                함께한 날들이 더더욱 아름다운<br />
                우리교회 앨범입니다.</a>
            </li>
        </ul>
        <div class="more"><a href="<c:url value="/church/${churchId}/album" />"><img src="<c:url value="/resources/images/church/main/icon_more.gif" />" alt="album_more" width="20" height="20"/></a></div>
    </div><!--// 배너3 -->
</div><!-- Banner -->

<div id="rightBanner">
<sec:authorize ifNotGranted="ROLE_USER">    
    <%-- <c:url var="loginUrl" value="/auth/login/j_spring_security_check" /> --%>
    <s:eval var="loginUrl" expression="@envvars['web.login']" />
    <form name="loginForm" id="loginForm" method='post' action="${loginUrl}">
    <div class="navBox">
        <h3>로그인</h3>
        <fieldset>
            <legend>로그인</legend>
            <div class="row">
            <div class="span3">
            <input type="text" class="input_text input_w100" title="아이디" name="j_username"/>
            <input type="checkbox" name="_spring_security_remember_me" id="sid" /><label for="sid">ID 저장</label>
            </div>
            <div class="span3">
            <input type="password" class="input_text input_w100" title="비밀번호" name="j_password"/>
            <input type="image" src="<c:url value="/resources/images/church/main/btn_login.gif" />" class="btn_login" alt="로그인" />
            </div>
            </div>
            <input type="hidden" name="spring-security-redirect" value="/church/${churchId}" />
        </fieldset>
        <div class="forgot">
        <a href="<c:url value="/join?_step=init"/>"><strong>회원가입</strong></a><img src="<c:url value="/resources/images/church/main/icon_loginLine.gif" />" alt="line" width="19" height="10" />
        <a href="<c:url value="/findUser/findPw"/>">패스워드 찾기</a>
        </div>
    </div>
    </form>
</sec:authorize>
    
<sec:authorize ifAnyGranted="ROLE_USER"><sec:authentication property="principal" var="user" />
<div class="navBox">

<div class="profile">

<c:if test="${not empty user.photo}">
<img src="<c:url value="${user.photo.path}" />" alt="프로필이미지" width="50" height="50" />
</c:if>

<c:if test="${empty user.photo}">
<img src="<c:url value="/resources/images/church/common/right_profile_img.gif" />" alt="프로필이미지" width="50" height="50" />
</c:if>

<div class="profile-info">
<p class="user-name"><span><strong>${user.name}</strong> 님</span></p>
<a href="<c:url value="/auth/confirmPw"/>">내정보</a>
</div>

<div class="logout-form">


<s:eval var="logoutUrl" expression="@envvars['web.logout']" />
<form name="logoutForm" method="post" action="${logoutUrl}">
<input type="hidden" name="spring-security-redirect" value="/church/${churchId}" />
<input type="image" src="<c:url value="/resources/images/church/common/btn_logout2.gif" />" alt="로그아웃" class="logout" />
</form>

</div>

</div>

<%-- <div class="profile2">
<p><strong>김동욱</strong> 님</p>
<p><img src="<c:url value="/resources/images/church/common/right_profile_img.gif" />" alt="프로필이미지" width="75" height="75" /></p>
<div class="logout-form">
<c:url var="logoutUrl" value="/church/login/j_spring_security_logout" />
<form name="logoutForm" method="post" action="${logoutUrl}">
<input type="hidden" name="spring-security-redirect" value="/church/${url}" />
<a href="#">내정보</a>
<input type="image" src="<c:url value="/resources/images/church/common/btn_logout2.gif" />" alt="로그아웃" class="logout" />
</form>
</div>
</div> --%>

</div>
</sec:authorize>
    
    <img src="<c:url value="/resources/images/church/main/rightLine.gif" />" alt="centerline" width="248" height="2" />
    
    <div class="navBox">
        <div class="lastvod"><a href="<c:url value="/church/${churchId}/sermon" />"><img src="<c:url value="/resources/images/church/main/icon_lastVod.gif" />" alt="지난방송보기" width="80" height="21" /></a></div>
        <h3>생명의 말씀</h3>
        <c:choose>
        <c:when test="${sermon != null}">
        <div class="vod-wrap"><s:eval var="poster" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getMedia(sermon.attachments, 'image/png')" />
            <a href="<c:url value="/church/${churchId}/sermon/${sermon.id}"/>"><img src="${poster.path}" alt="thumbnail" width="74" height="74" /></a>
            <div class="vod-desc">
                <p><img src="<c:url value="/resources/images/church/main/icon_arrow.gif" />" alt="arrow" width="2" height="6" /> <strong><a href="<c:url value="/church/${churchId}/sermon/${sermon.id}"/>"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(sermon.subject, 10)" htmlEscape="true"/></a></strong></p>
                <p><img src="<c:url value="/resources/images/church/main/icon_arrow.gif" />" alt="arrow" width="2" height="6" /> <font color="#888">${sermon.bible}</font></p>
                <p><img src="<c:url value="/resources/images/church/main/icon_arrow.gif" />" alt="arrow" width="2" height="6" /> <font color="#888">설교자 |</font> ${sermon.preacher}</p>
            </div>
        </div>
        </c:when>
        <c:otherwise>
        <div class="empty_content">
            <h4>등록된 영상이 없습니다.</h4>
        </div>
        </c:otherwise>
        </c:choose>
    </div><!-- talk -->
    
    <img src="<c:url value="/resources/images/church/main/rightLine.gif" />" alt="centerline" width="248" height="2" />
    
    <div class="navBox">
        <h3>교회기관</h3>

<c:choose>
<c:when test="${not empty listOrg}">

<div id="list-org">

<div class="carousel slide">
<div class="carousel-inner">

<c:forEach var="item" items="${listOrg}" varStatus="status">
<c:choose>
<c:when test="${status.index == 0}">
<div class="item active">
<a href="<c:url value="/church/${churchId}/org/${item.id}"/>">
<c:choose>
<c:when test="${not empty item.attachment}"><img src="<c:url value="${item.attachment.path}" />" alt="교회기관" width="200" height="150" /></c:when>
<c:otherwise>
<img src="<c:url value="/resources/images/church/common/img_default_group.png" />" alt="교회기관" width="200" height="150" />
</c:otherwise>
</c:choose>
</a>
<div class="carousel-caption">
<h4>${item.name}</h4>
</div>
</div>
</c:when>

<c:otherwise>
<div class="item">
<a href="<c:url value="/church/${churchId}/org/${item.id}"/>">
<c:choose>
<c:when test="${not empty item.attachment}"><img src="<c:url value="${item.attachment.path}" />" alt="교회기관" width="200" height="150" /></c:when>
<c:otherwise>
<img src="<c:url value="/resources/images/church/common/img_default_group.png" />" alt="교회기관" width="200" height="150" />
</c:otherwise>
</c:choose>
</a>
<div class="carousel-caption">
<h4>${item.name}</h4>
</div>
</div>
</c:otherwise>

</c:choose>
</c:forEach>
</div>
</div>

<span class="pre"><img src="<c:url value="/resources/images/church/main/btn_prev.gif" />" alt="이전" width="18" height="20" /></span>
<span class="next"><img src="<c:url value="/resources/images/church/main/btn_next.gif" />" alt="다음" width="18" height="20" /></span>

</div>

</c:when>

<c:otherwise>
<div class="empty_content">
<h4>등록된 기관이 없습니다.</h4>
</div>
</c:otherwise>
</c:choose>
</div>

</div><!-- rightBanner -->

<script type="text/javascript" src="<c:url value="/resources/js/jquery/easyslider/easySlider1.7.js" />"></script>
<script type="text/javascript">
$(function() {
    $('#slider').easySlider({auto: true, continuous: true, controlsShow: false});
    $('.carousel').carousel({interval: false});
    $('.pre').click(function() {
    	$('.carousel-inner').carousel('prev');
    });
    $('.next').click(function() {
        $('.carousel-inner').carousel('next');
    });    
});
</script>
</body>
</html>