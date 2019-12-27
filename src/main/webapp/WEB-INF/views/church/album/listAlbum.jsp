<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Insert title here</title>
</head>
<body>
<h2>교회앨범</h2>
<div class="intro">
<div class="church_content">

<div id="titleWrap">
<h3>교회앨범</h3>
<p class="home">Home &gt; 교회앨범 &gt; <span>교회앨범</span></p>
</div>

<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div class="listWrap">
<c:choose>
<c:when test="${not empty list}">
<div class="thumbnails-wrap">
<ul class="thumbnails">

<c:forEach var="item" items="${list}" varStatus="status">
<li class="spanW200">
<div class="thumbnail">
<a href="<c:url value="/church/${ourChurch.id}/album/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}"/>">
<c:choose>
<c:when test="${item.thumbnail != null}">
<img src="<c:url value="${item.thumbnail.path}"/>" alt="앨범" width="150" height="150"/>
</c:when>
<c:otherwise>
<img src="<c:url value="/resources/images/church/album/img_empty_album.png"/>" width="150" height="150"/>
</c:otherwise>
</c:choose>
</a>
<div class="caption break-word">
<h5 class="title"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 12)" htmlEscape="true"/> (${item.commentsCount})</h5>
</div>
</div>
</li>
</c:forEach>

</ul>
</div>
</c:when>

<c:otherwise>
<!-- <div class="empty-contents">
<h1>해당 콘덴츠가 없습니다.</h1>
</div> -->

<div class="row-fluid">
    <div class="nocontents-unit">
    <h1></h1>
    <p>해당 콘덴츠가 없습니다.</p>
    </div>
</div>
</c:otherwise>

</c:choose>
</div>
<sec:authorize access="isAuthenticated() and hasPermission(${ourChurch.id}, 'isChurchMember') or hasPermission(${ourChurch.id}, 'isOurChurchAdmin')">
<div class="action_btn_area">
<a href="<c:url value="/church/${ourChurch.id}/album/new" />" class="new_btn"><img src="<c:url value="/resources/images/church/common/btn_write3.gif" />" alt="글쓰기버튼" width="72" height="24" /></a>
</div>
</sec:authorize>
</sec:authorize>


<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/album/delete" />
<form:form id="deleteItems" action="${deleteUrl}">
<div class="listWrap">

<c:choose>
<c:when test="${not empty list}">
<div class="thumbnails-wrap">
<ul class="thumbnails">

<c:forEach var="item" items="${list}" varStatus="status">
<li class="spanW200">
<div class="thumbnail">
<input type="checkbox" name="deleteItems" id="deleteItems-${item.id}" value="${item.id}" class="input_check"/>
<a href="<c:url value="/church/${ourChurch.id}/album/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}"/>">
<c:choose>
<c:when test="${item.thumbnail != null}">
<img src="<c:url value="${item.thumbnail.path}"/>" alt="앨범" width="150" height="150"/>
</c:when>
<c:otherwise>
<img src="<c:url value="/resources/images/church/album/img_empty_album.png"/>" width="150" height="150"/>
</c:otherwise>
</c:choose>
</a>
<div class="caption break-word">
<h5 class="title"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 12)" htmlEscape="true"/> (${item.commentsCount})</h5>
</div>
</div>
</li>
</c:forEach>
</ul>
</div>
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


<div class="action_btn_area">
<input type="image" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="삭제" class="del_btn"/>
<a href="<c:url value="/church/${ourChurch.id}/album/new" />" class="new_btn"><img src="<c:url value="/resources/images/church/common/btn_write3.gif" />" alt="글쓰기버튼" width="72" height="24" /></a>
</div>
</form:form>
</sec:authorize>

<div class="page_link">
<p>
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/church/${ourChurch.id}/album?page=1&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/church/${ourChurch.id}/album?page=${paginate.page - 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>

<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong>${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/church/${ourChurch.id}/album?page=${page}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/album?page=${paginate.page + 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/album?page=${paginate.totalPage}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</p>
</div>
<!--// page_link -->

<!--검색-->
<div id="news_search_box"><c:url var="searchUrl" value="/church/${ourChurch.id}/album"/>
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

<p class="pd20" />
</div><!-- church_content -->

<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">
<sec:authorize ifAnyGranted="ROLE_USER">
<div class="profile-wrap">

<div class="profile">
<sec:authentication property="principal" var="user" />
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="댓글 썸네일" width="50" height="50" /></c:when>
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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/album" />">교회앨범</a></li>
</ul>
</div><!-- rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->
</div><!-- intro -->

<script type="text/javascript">
<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
    $('#deleteItems').submit(function() {
        if (!$(':checkbox[name=deleteItems]:checked').length) {
            alert('삭제 할 목록을 선택하세요');
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