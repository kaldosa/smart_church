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
<h2>교회소식</h2>
<div class="intro">
<div class="church_content">

<div id="titleWrap">
<h3>우리주보</h3>
<p class="home">Home &gt; 교회소식 &gt; <span>우리주보</span></p>
</div>
    
<!-- 내용보기 --> 
<div class="boardbox">
<div class="header_wrap">
<ul>
<li class="title"><c:out value="${weekly.subject}" escapeXml="true"/></li>
<li class="date"><s:eval expression="weekly.createdDate"/></li>
</ul>
</div>
<div class="body_wrap">
<div class="text_contents_wrap">
<div class="text_contents"><s:eval expression="weekly.contents.replaceAll('\r\n', '<br/>')"/></div>
</div>
</div>
<c:if test="${not empty weekly.attach}">
<div class="attach_list_box">
<span class="attach_file"><a href="<c:url value="${weekly.attach.path}"/>">${weekly.attach.fileName}</a></span>
</div>
</c:if>
</div><!--// 내용보기 -->


<div class="action_btn_area">
<a href="<c:url value="/church/${ourChurch.id}/weekly?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}" />" class="list_btn"><img src="<c:url value="/resources/images/church/common/btn_list3.gif" />" alt="목록" width="72" height="24"/></a>
<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/weekly/${weeklyId}" />
<form:form action="${deleteUrl}" method="delete" id="delete-form">
<input type="image" name="btnSubmit" id="btnSubmit" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="등록" class="del_btn"/>
</form:form>
<a href="<c:url value="/church/${ourChurch.id}/weekly/${weeklyId}/edit" />" class="edit_btn"><img src="<c:url value="/resources/images/church/common/btn_edit.png" />" alt="목록" width="73" height="22"/></a>
</sec:authorize>
</div>

<p class="pd20"/>
</div><!--// news_content -->

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
<li><a href="<c:url value="/church/${ourChurch.id}/news" />">새소식</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/weekly" />">우리주보</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/schedule" />">교회일정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/family" />">새가족 소개</a></li>
</ul>
</div>
<!-- rnb -->
</div>
<!--// 오른쪽 프로필 및 서브메뉴 -->
</div>
<!-- intro -->
<script type="text/javascript">
$('#delete-form').submit(function() {
    if(!confirm("삭제하시겠습니까?")) {
        return false;
    }
});
</script>
</body>
</html>