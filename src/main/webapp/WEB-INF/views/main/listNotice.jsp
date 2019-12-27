<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">
<div class="page-title"><img src="<c:url value="/resources/images/main/img_notice_title.gif"/>" /></div>
<sec:authorize ifAnyGranted="ROLE_ADMIN">
<div class="board-wrap">

<div class="board-nav no-boarder ph20">
<div class="board-extra">
<div class="board-btn-left">
<a href="<c:url value="/notices/new"/>"><img width="39" height="20" alt="글쓰기" src="<c:url value="/resources/images/main/btn_write.png" />" /></a>
<a id="delete-notice" href="#"><img alt="삭제" src="<c:url value="/resources/images/main/btn_del.png" />" width="39" height="20"/></a>
</div>

<c:url var="searchUrl" value="/notices"/>
<form id="search-form" action="${searchUrl}" method="get" class="form-inline">
<div class="board-search">
<select name="criteria" class="input-small" >
    <option value="subject" selected="selected">제목</option>
    <option value="contents">제목+내용</option>
</select>
<input id="keyword" type="text" name="keyword"  value="" maxlength="15" title="검색어입력" />
<!-- <input type="submit" class="btn" value="검색"/> -->
<input type="image" src="<c:url value="/resources/images/main/btn_search.png" />" alt="검색" />
</div>
</form>
</div>
</div>

<div class="board no-boarder">

<c:url var="deleteUrl" value="/notices/delete" />
<form id="deleteItems" action="${deleteUrl}" method="post">
<div class="board-list">
<table class="table">
<thead>
<tr>
<th class="span0">&nbsp;</th>
<th class="span1">번호</th>
<th>제목</th>
<th class="span1">등록일</th>
<th class="span1">조회</th>
</tr>
</thead>

<tbody>
<c:if test="${empty list}">
<tr>
<td colspan="5">등록된 게시물이 없습니다.</td>
</tr>
</c:if>

<c:forEach var="item" items="${list}" varStatus="status">
<tr>
<td><input type="checkbox" name="deleteItems" id="deleteItems-${item.id}" value="${item.id}" /></td>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td class="subject"><a href="<c:url value="/notices/${item.id}?page=${paginate.page}" />"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 40)" htmlEscape="true"/></a></td>
<td><s:eval expression="item.createdDate" /></td>
<td>${item.hits}</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</form>

<div class="board-extra">

<div class="paging">
<ul>

<li class="rewind">
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/notices?page=1" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
</li>

<li class="prev">
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page - 1}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="pageNumber">
<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong class="nowPage">${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${page}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>    
</li>

<li class="next">
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page + 1}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="foward">
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.totalPage}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</li>
</ul>
</div>

</div>

<div class="board-extra row-fluid">
<a href="<c:url value="/"/>" class="btn btn-mini">메인으로</a>
</div>

</div>

</div>
</sec:authorize>


<sec:authorize ifNotGranted="ROLE_ADMIN">
<div class="board-wrap">

<div class="board-nav no-boarder ph20">
<div class="board-extra">
<c:url var="searchUrl" value="/notices"/>
<form id="search-form" action="${searchUrl}" method="get" class="form-inline">
<div class="board-search">
<select name="criteria" class="input-small">
    <option value="subject" selected="selected">제목</option>
    <option value="contents">제목+내용</option>
</select>
<input id="keyword" type="text" name="keyword" value="" maxlength="15" title="검색어입력" />
<input type="image" src="<c:url value="/resources/images/main/btn_search.png" />" alt="검색" />
</div>
</form>
</div>
</div>

<div class="board no-boarder">
<div class="board-list">
<table class="table">
<thead>
<tr>
<th class="span1">번호</th>
<th>제목</th>
<th class="span1">등록일</th>
<th class="span1">조회</th>
</tr>
</thead>

<tbody>
<c:if test="${empty list}">
<tr>
<td colspan="4">등록된 게시물이 없습니다.</td>
</tr>
</c:if>

<c:forEach var="item" items="${list}" varStatus="status">
<tr>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td class="subject"><a href="<c:url value="/notices/${item.id}?page=${paginate.page}" />"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(item.subject, 40)" htmlEscape="true"/></a></td>
<td><s:eval expression="item.createdDate" /></td>
<td>${item.hits}</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>

<div class="board-extra">

<div class="paging">
<ul>

<li class="rewind">
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/notices?page=1" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
</li>

<li class="prev">
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page - 1}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="pageNumber">
<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong class="nowPage">${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${page}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>    
</li>

<li class="next">
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page + 1}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="foward">
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.totalPage}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</li>
</ul>
</div>

</div>

<div class="board-extra row-fluid ">
<a href="<c:url value="/"/>" class="btn btn-mini pull-right">메인으로</a>
</div>

</div>

</div>
</sec:authorize>

</div>

</div>

<script type="text/javascript">
/* <[CDATA[ */
$(function() {
	
<sec:authorize ifAnyGranted="ROLE_ADMIN">
	$('#delete-notice').click(function() {
		   $('#deleteItems').submit();	
	});
	
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
});
/* ]]> */
</script>

</body>
</html>