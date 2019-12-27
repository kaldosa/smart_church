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
<h3>새가족소개</h3>
<p class="home">Home &gt; 교회소식 &gt; <span id="reg-info">새가족소개</span></p>
</div>

<div id="popup" style="display: none;">
    <p>교회에 처음 나오시는 분이나 교회를 옮기시는 분 모두 교회에 등록을 하셔야 합니다.<br />
        교회등록을 신청하시면 교역자의 심방, 특수상담을 요청할 수 있고 교회, 새가족 안내책자, 교회주차 안내도,
        목사님 말씀, 소책자, 가정예배지, 교패 등을 받게 됩니다.<br /><br />
        교회등록을 하기 위해서는 ‘새가족모임’을 수료하셔야만 교회에서 진행하는 모든 프로그램에 참여할 수 있습
        니다. 교회등록 후 “새가족모임”을 필히 신청해주시고 <span class="em">새가족 모임 수료가 끝나면 교회 교인으로 등록이
        되며,</span> 담당교역자와 연결되어 심방을 받게 되고 신앙상담과 교회생활에 필요한 정보 및 각종 목회적인 배려를
        받을 수 있습니다.<br /><br />
        주일예배시 본당입구에 있는 등록대에 가면 필요한 모든 도움을 받을 수 있습니다.<br />
        좀 더 자세한 안내를 원하시면 본당으로 연락주세요. 언제든지 함께 합니다.</p>
    <div class="info_phone">
        <ul>
            <li class="em">${ourChurch.churchMeta.contact}</li>
            <li class="em">${ourChurch.churchMeta.address}</li>
        </ul>
    </div>
</div>

<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">

<div class="listWrap">

<c:if test="${empty list}">
<!-- <div class="empty-contents">
<h1>해당 콘덴츠가 없습니다.</h1>
</div> -->

<div class="row-fluid">
    <div class="nocontents-unit">
    <h1></h1>
    <p>해당 콘덴츠가 없습니다.</p>
    </div>
</div>
</c:if>

<div class="thumbnails-wrap">
<ul class="thumbnails">
<c:forEach var="item" items="${list}" varStatus="status">
<li class="spanW200">
<div class="thumbnail">
<a href="<c:url value="/church/${ourChurch.id}/family/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}"/>" ><img src="<c:url value="${item.attachment.path}"/>" width="150" height="150"/></a>
<div class="caption">
<h5><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.name, 7)" htmlEscape="true"/> [<s:eval expression="item.createdDate" />]</h5>
</div>
</div>
</li>
</c:forEach>
</ul><!-- // thumbnails -->

</div>
</div><!--// albumList -->
</sec:authorize>


<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/family/delete" />
<form:form id="deleteItems" action="${deleteUrl}">
<div class="listWrap">

<c:if test="${empty list}">
<div class="row-fluid">
    <div class="nocontents-unit">
    <h1></h1>
    <p>해당 콘덴츠가 없습니다.</p>
    </div>
</div>
</c:if>

<div class="thumbnails-wrap">
<ul class="thumbnails">
<c:forEach var="item" items="${list}" varStatus="status">
<li class="spanW200">
<div class="thumbnail">
<input type="checkbox" name="deleteItems" id="deleteItems-${item.id}" value="${item.id}" class="input_check"/>
<a href="<c:url value="/church/${ourChurch.id}/family/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}"/>" ><img src="<c:url value="${item.attachment.path}"/>" width="150" height="150"/></a>
<div class="caption">
<h5><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.name, 7)" htmlEscape="true"/> [<s:eval expression="item.createdDate" />]</h5>
</div>
</div>
</li>
</c:forEach>
</ul><!-- // thumbnails -->

</div>
</div><!--// albumList -->

<div class="action_btn_area">
<input type="image" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="삭제" class="del_btn"/>
<a href="<c:url value="/church/${ourChurch.id}/family/new" />" class="new_btn"><img src="<c:url value="/resources/images/church/common/btn_write3.gif" />" alt="글쓰기버튼" width="72" height="24" /></a>
</div>
</form:form>
</sec:authorize>


<div class="page_link">
<p>
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/church/${ourChurch.id}/family?page=1&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/church/${ourChurch.id}/family?page=${paginate.page - 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>

<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong>${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/church/${ourChurch.id}/family?page=${page}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/family?page=${paginate.page + 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/church/${ourChurch.id}/family?page=${paginate.totalPage}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</p>
</div>
<!--// page_link -->

<!--검색-->
<div id="news_search_box"><c:url var="searchUrl" value="/church/${ourChurch.id}/family"/>
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

<p class="pd20" />
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
<li><a href="<c:url value="/church/${ourChurch.id}/news" />">새소식</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/weekly" />">우리주보</a></li>
<%-- <li><a href="<c:url value="/church/${ourChurch.id}/schedule" />">교회일정</a></li> --%>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/family" />">새가족 소개</a></li>
</ul>
</div>
<!-- rnb -->
</div>
<!--// 오른쪽 프로필 및 서브메뉴 -->
</div>
<!-- intro -->

<script type="text/javascript">
	$("#popup").css("display","none");
	function popupOn(no){
	    for(i=1;i<=1;i++){
	        if(i==no)
	            $("#popup"+i).fadeIn(300);
	        else
	            $("#popup"+i).fadeOut(300);
	    }
	}
	$("#reg-info").mouseenter(function(){
	    $("#popup").fadeIn(300);
	});
	$("#popup").mouseleave(function(){
	    $("#popup").fadeOut(300);
	});

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
    
<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}">	
    $('#deleteItems').submit(function() {
        if (!$(':checkbox[name=deleteItems]:checked').length) {
            alert('삭제 할 목록을 선택하세요');
            return false;
        }
    });
</sec:authorize>
</script>

</body>
</html>