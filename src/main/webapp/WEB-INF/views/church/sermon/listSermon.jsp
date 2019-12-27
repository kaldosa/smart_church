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
<h3>설교</h3>
<p class="home">Home &gt; 생명의말씀 &gt; <span>설교</span></p>
</div>

<c:choose>
<c:when test="${not empty list}">
<div class="boardbox">

<div class="header_wrap">
<ul>
<li class="title msg_title"><c:out value="${sermon.subject}" escapeXml="true"/></li>
<li class="date"><s:eval expression="sermon.sermonDate" /></li>
</ul>
</div>

<s:eval var="flv" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getMedia(sermon.attachments, 'video/x-flv')" />
<s:eval var="mp3" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getMedia(sermon.attachments, 'audio/mpeg')" />
<s:eval var="sermon_note" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getNote(sermon.attachments)" />

<div class="body_wrap msg_contents">
<div class="image_contents_wrap">
<div class="videoplayer" id="player"></div>
</div>

<div class="row-fluid">
<div class="span7">
<ul class="sermon_desc">
<li>설교자 : <c:out value="${sermon.preacher}" escapeXml="true"/></li>
<li>성경본문 : <c:out value="${sermon.bible}" escapeXml="true"/></li>
<%-- <li>보내기
<a href="#"><img src="<c:url value="/resources/images/church/talk/icon_twitter.gif" />" alt="트위터로 전송" width="14" height="14"/></a>
<a href="#"><img src="<c:url value="/resources/images/church/talk/icon_facebook.gif" />" alt="페이스북으로 전송" width="14" height="14"/></a>
<a href="#"><img src="<c:url value="/resources/images/church/talk/icon_me2day.gif" />" alt="미투데이로 전송" width="14" height="14"/></a></li> --%>
</ul>
</div>
<div class="span5">
<ul class="sermon_downlaod">
<li>
<c:if test="${not empty mp3}"><a href="${mp3.path}"><img src="<c:url value="/resources/images/church/talk/btn_down.gif" />" alt="설교음성 다운로드" width="157" height="32" /></a></c:if>
<c:if test="${not empty sermon_note}"><a href="${sermon_note.path}"><img src="<c:url value="/resources/images/church/talk/btn_note.gif" />" alt="설교노트 다운로드" width="157" height="32" /></a></c:if>
</li>
</ul>
</div>
</div>

</div>
</div>

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<div id="admin-action">
<a href="<c:url value="/church/${ourChurch.id}/sermon/${sermon.id}/edit" />" class="icons icons-edit">수정</a>
<a href="#" class="icons icons-del">삭제</a>
<c:url var="deleteSermonUrl" value="/church/${ourChurch.id}/sermon/${sermon.id}"/>
<form:form id="delete-sermon-form" action="${deleteSermonUrl}" method="delete">
</form:form>
</div>
</sec:authorize>

</c:when>


<c:otherwise>
<!-- <div class="boardbox">

<div class="body_wrap msg_contents">
<div class="image_contents_wrap">
<h3>해당 컨덴츠가 없습니다</h3>
<a href="#" class="videoplayer" id="player"></a>
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
<th>설교자</th>
<th>성경본문</th>
<th>설교날짜</th>
<th>설교노트</th>
</tr>
</thead>

<tbody>
<c:choose>
<c:when test="${not empty list}">
<c:forEach var="item" items="${list}" varStatus="status">
<s:eval var="item_mp3" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getMedia(item.attachments, 'audio/mpeg')" />
<s:eval var="item_note" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getNote(item.attachments)" />
<tr>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td class="subject"><a href="<c:url value="/church/${ourChurch.id}/sermon/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}" />">
<s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 15)" htmlEscape="true"/></a></td>
<td>${item.preacher}</td>
<td>${item.bible}</td>
<td><s:eval expression="item.sermonDate" /></td>
<td>
<c:if test="${not empty item_mp3}"><a href="${item_mp3.path}"><img src="<c:url value="/resources/images/church/common/icon_voice.gif" />" alt="설교음성 다운로드" width="16" height="15" /></a></c:if>
<c:if test="${not empty item_note}"><a href="${item_note.path}"><img src="<c:url value="/resources/images/church/common/icon_down.gif" />" alt="설교노트 다운로드" width="16" height="15" /></a></c:if>
</td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<tr>
<td colspan="6">게시물이 없습니다.</td>
</tr>
</c:otherwise>
</c:choose>
</tbody>
</table>
</div>
</sec:authorize>

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/sermon/delete" />
<form:form id="deleteItems" action="${deleteUrl}">

<div class="listTit">
<table class="table">
<thead>
<tr>
<th class="span0">&nbsp;</th>
<th class="span1">번호</th>
<th>제목</th>
<th>설교자</th>
<th>성경본문</th>
<th>설교날짜</th>
<th>설교노트</th>
</tr>
</thead>

<tbody>
<c:choose>
<c:when test="${not empty list}">
<c:forEach var="item" items="${list}" varStatus="status">
<s:eval var="item_mp3" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getMedia(item.attachments, 'audio/mpeg')" />
<s:eval var="item_note" expression="T(com.laonsys.smartchurch.domain.church.Sermon).getNote(item.attachments)" />
<tr>
<td><input type="checkbox" name="deleteItems" id="deleteItems-${item.id}" value="${item.id}" /></td>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td class="subject"><a href="<c:url value="/church/${ourChurch.id}/sermon/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}" />">
<s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 15)" htmlEscape="true"/></a></td>
<td>${item.preacher}</td>
<td>${item.bible}</td>
<td><s:eval expression="item.sermonDate" /></td>
<td>
<c:if test="${not empty item_mp3}"><a href="${item_mp3.path}"><img src="<c:url value="/resources/images/church/common/icon_voice.gif" />" alt="설교음성 다운로드" width="16" height="15" /></a></c:if>
<c:if test="${not empty item_note}"><a href="${item_note.path}"><img src="<c:url value="/resources/images/church/common/icon_down.gif" />" alt="설교노트 다운로드" width="16" height="15" /></a></c:if>
</td>
</tr>
</c:forEach>
</c:when>
<c:otherwise>
<tr>
<td colspan="7">게시물이 없습니다.</td>
</tr>
</c:otherwise>
</c:choose>
</tbody>
</table>
</div>

<div class="action_btn_area">
<input type="image" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="삭제" class="del_btn"/>
<a href="<c:url value="/church/${ourChurch.id}/sermon/new" />" class="new_btn"><img src="<c:url value="/resources/images/church/common/btn_write3.gif" />" alt="글쓰기버튼" width="72" height="24" /></a>
</div>
</form:form>
</sec:authorize>

<div class="page_link">
<p>
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/church/${ourChurch.id}/sermon?page=1&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/church/${ourChurch.id}/sermon?page=${paginate.page - 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>

<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong>${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/church/${ourChurch.id}/sermon?page=${page}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/sermon?page=${paginate.page + 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/sermon?page=${paginate.totalPage}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</p>
</div>
<!--// page_link -->

<!--검색-->
<div id="news_search_box"><c:url var="searchUrl" value="/church/${ourChurch.id}/sermon"/>
<form id="search-form" name="search" action="${searchUrl}" method="get">
<span class="selectbox">
<select name="criteria" class="input_select">
    <option value="subject" selected="selected">제목</option>
    <option value="preacher">설교자</option>
</select>
</span>
<span class="inputbox"><input id="keyword" type="text" name="keyword"/></span>
<span class="search_btn">
<input type="image" src="<c:url value="/resources/images/church/common/btn_search.gif" />" alt="검색" />
</span>
</form>
</div>
<!--// 검색-->

</div><!-- listWrap -->
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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/sermon" />">설교</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/message" />">1분메세지</a></li>
</ul>
</div><!-- rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->
</div><!-- intro -->

<s:eval var="streamer" expression="@envvars['storage.host.vod']" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jwplayer/jwplayer.js" />"></script>
<script type="text/javascript">
var file = '${flv.realFileName}';
var streamer = '${streamer}';
if(file != '') {
jwplayer("player").setup({
	width: 570,
	height: 300,
	flashplayer: "<c:url value='/resources/js/jquery/jwplayer/player.swf' />",
    streamer: streamer,/*"rtmp://streaming.smartchurch.co.kr/td1526vol00/_definst_/",*/
    provider: "rtmp",
    file: file
});
} else {
	$('#player').append('<h3>해당 컨덴츠가 없습니다</h3>');
}

</script>

<script type="text/javascript">
<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">

$(".icons-del").click(function() {
    $('#delete-sermon-form').submit();
});


$('#deleteItems').submit(function() {
    if (!$(':checkbox[name=deleteItems]:checked').length) {
        alert('삭제 할 목록을 선택하세요');
        return false;
    }
});

$('#delete-sermon-form').submit(function() {
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