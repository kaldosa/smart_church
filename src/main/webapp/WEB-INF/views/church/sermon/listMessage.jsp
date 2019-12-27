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

<c:choose><c:when test="${not empty list}">

<div class="boardbox">

<div class="header_wrap">
<ul>
<li class="title msg_title"><c:out value="${message.subject}" escapeXml="true"/></li>
<li class="date"><s:eval expression="message.createdDate" /></li>
</ul>
</div>

<div class="body_wrap msg_contents">
<p class="sub_title break-word"><c:out value="${message.subTitle}" escapeXml="true"/></p>
<div class="text_contents_wrap"><div class="text_contents"><s:eval expression="message.contents.replaceAll('\r\n', '<br/>')"/></div></div>
</div>

</div>

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div id="admin-action">
<a href="<c:url value="/church/${ourChurch.id}/message/${message.id}/edit" />" class="icons icons-edit">수정</a>
<a href="#" class="icons icons-del">삭제</a>
<c:url var="deleteMessageUrl" value="/church/${ourChurch.id}/message/${message.id}"/>
<form:form id="delete-message-form" action="${deleteMessageUrl}" method="delete">
</form:form>
</div>
</sec:authorize>

</c:when>

<c:otherwise>
<!-- <div class="boardbox">

<div class="body_wrap msg_contents">
<div class="image_contents_wrap">
<h3>해당 컨덴츠가 없습니다</h3>
</div>

</div>
</div> -->
</c:otherwise>
</c:choose>

<div class="listWrap">

<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div class="listTit">
<table class="table">
<thead>
<tr>
<th class="span1">번호</th>
<th>제목</th>
<th class="span1">작성일</th>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${not empty list}">
<c:forEach var="item" items="${list}" varStatus="status">
<tr>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td class="subject"><a href="<c:url value="/church/${ourChurch.id}/message/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}" />">
<s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 30)" htmlEscape="true"/></a></td>
<td><s:eval expression="item.createdDate" /></td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<tr>
<td colspan="3">게시물이 없습니다.</td>
</tr>
</c:otherwise>
</c:choose>
</tbody>
</table>
</div>
</sec:authorize>


<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/message/delete" />
<form:form id="deleteItems" action="${deleteUrl}">
<fieldset><legend>메세지 삭제 폼</legend>

<div class="listTit">
<table class="table">
<thead>
<tr>
<th class="span0">&nbsp;</th>
<th class="span1">번호</th>
<th>제목</th>
<th class="span1">작성일</th>
</tr>
</thead>
<tbody>
<c:choose>
<c:when test="${not empty list}">
<c:forEach var="item" items="${list}" varStatus="status">
<tr>
<td><input type="checkbox" name="deleteItems" id="deleteItems-${item.id}" value="${item.id}" /></td>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td class="subject"><a href="<c:url value="/church/${ourChurch.id}/message/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}" />">
<s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 30)" htmlEscape="true"/></a></td>
<td><s:eval expression="item.createdDate" /></td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<tr>
<td colspan="4">게시물이 없습니다.</td>
</tr>
</c:otherwise>
</c:choose>
</tbody>
</table>
</div>

<div class="action_btn_area">
<input type="image" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="삭제" class="del_btn"/>
<a href="<c:url value="/church/${ourChurch.id}/message/new" />" class="new_btn"><img src="<c:url value="/resources/images/church/common/btn_write3.gif" />" alt="글쓰기버튼" width="72" height="24" /></a>
</div>

</fieldset>
</form:form>
</sec:authorize>

<div class="page_link">
<p>
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/church/${ourChurch.id}/message?page=1&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/church/${ourChurch.id}/message?page=${paginate.page - 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>

<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong>${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/church/${ourChurch.id}/message?page=${page}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/message?page=${paginate.page + 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/message?page=${paginate.totalPage}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</p>
</div>
<!--// page_link -->

<!--검색-->
<div id="news_search_box"><c:url var="searchUrl" value="/church/${ourChurch.id}/message"/>
<form id="search-form" name="search" action="${searchUrl}" method="get">
<span class="selectbox">
<select name="criteria" class="input_select">
    <option value="subject" selected="selected">제목</option>
    <option value="contents">제목+내용</option>
</select>
</span>
<span class="inputbox"><input id="keyword" type="text" name="keyword"/></span>
<span class="search_btn">
<input type="image" src="<c:url value="/resources/images/church/common/btn_search.gif" />" alt="검색" />
</span>
</form>
</div>
<!--// 검색-->

</div>

</div><!--// church_content -->

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

<script type="text/javascript">

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
$(".icons-del").click(function() {
    $('#delete-message-form').submit();
});

$('#deleteItems').submit(function() {
    if (!$(':checkbox[name=deleteItems]:checked').length) {
        alert('삭제 할 목록을 선택하세요');
        return false;
    }
});

$('#delete-message-form').submit(function() {
    if(!confirm("삭제하시겠습니까?")) {
        return false;
    }
});
</sec:authorize>


$('#search-form').validate({
    errorPlacement: function(error,element){
        alert(error.html());
   },
    rules: {
        'keyword': {
            required: true,
            maxlength: 50,
            htmlTag: true
        }
    },
    messages: {
        'keyword': {
            required: '검색어를 입력해주세요.'
        }
    }
});
</script>
</body>
</html>