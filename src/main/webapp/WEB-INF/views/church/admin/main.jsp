<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<style type="text/css">
.progressbar-wrap {
   position: relative;
}

.progressbar-text {
   position: absolute;
   top: 0;
   left: 0;
   width: 100%;
   padding-top: 5px;
   text-align: center;
}
</style>
</head>
<body>
<h2>관리자 페이지</h2>
<div class="intro">
<div class="church_content">

<div class="myInfo">
<fieldset>
<legend>서비스 현황</legend>

<div class="form-controls">
<label class="form-label">서비스 신청일</label>
<div class="form-control">
<span class="form-control-text"><s:eval expression="ourChurch.createdDate" /></span>
</div>
</div>

<div class="form-controls">
<label class="form-label">서비스 시작일</label>
<div class="form-control">
<span class="form-control-text"><s:eval expression="ourChurch.modifiedDate" /></span>
</div>
</div>

<div class="form-controls">
<label class="form-label">교회 멤버</label>
<div class="form-control">
<span class="form-control-text">${memberCount} 명</span>
</div>
</div>

</fieldset>
</div>

<div class="myInfo">
<fieldset>
<legend>스토리지 사용량</legend>

<div class="form-controls">
<label class="form-label">총 사용량</label>
<div class="form-control">
<span class="form-control-text">${totalSize} MB</span>
<div class="progressbar-wrap">
<div id="progressbar1"></div>
<div id="pb-text1" class="progressbar-text"></div>
</div>
</div>
</div>

<div class="form-controls">
<label class="form-label">미디어 콘덴츠 사용량</label>
<div class="form-control">
<span class="form-control-text"><c:if test="${empty mediaSize}">0</c:if><c:if test="${not empty mediaSize}">${mediaSize}</c:if> MB</span>
<div class="progressbar-wrap">
<div id="progressbar2"></div>
<div id="pb-text2" class="progressbar-text"></div>
</div>
</div>
</div>
</fieldset>
</div>

</div>

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
<li><a href="<c:url value="/church/${ourChurch.id}/admin/editChurch"/>">교회정보 수정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/admin/editMain"/>">메인 이미지 수정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/admin/members"/>">교회멤버 관리</a></li>
</ul>

</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->


</div>

<%-- <script type="text/javascript">
$(function() {
	var total = '<c:out value="${totalSize}"/>' ;
	var media = '<c:out value="${mediaSize}"/>' ;
	var persentage1 = (total/(50 * 1024)) * 100;
	var persentage2 = (media/(50 * 1024)) * 100;
	
 	$('#progressbar1').progressbar({value: persentage1});
	$('#progressbar2').progressbar({value: persentage2});
	$('#pb-text1').html('<span>50 GB ('+ persentage1 + '%)</span>');
	$('#pb-text2').html('<span>50 GB ('+ persentage2 + '%)</span>'); 
});
</script> --%>

</body>
</html>