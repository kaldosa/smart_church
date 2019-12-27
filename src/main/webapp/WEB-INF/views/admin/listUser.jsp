<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="board-wrap">

<div class="board-nav no-boarder ph20">
<div class="board-extra">
<div class="board-btn-left">
<a href="<c:url value="/admin"/>" class="btn">관리자 페이지로</a> <button id="user-delete" type="button" class="btn">삭제</button>
</div>

<div class="board-search"><c:url var="searchUrl" value="/admin/users"/>
<form id="search-form" action="${searchUrl}" method="get">
<select name="criteria" class="input_select">
    <option value="name" selected="selected">이름</option>
    <option value="email">이메일</option>
</select>
<input id="keyword" type="text" name="keyword" class="input_text input_w150" value="" maxlength="15" title="검색어입력" />
<input type="image" src="<c:url value="/resources/images/main/btn_search.png" />" alt="검색" />
</form>
</div>
</div>
</div>

<div class="board no-boarder">

<c:url var="deleteUrl" value="/admin/users/delete" />
<form id="deleteItems" action="${deleteUrl}" method="post">
<div class="board-list">
<table class="table">
<thead>
<tr>
<th class="span0">&nbsp;</th>
<th class="span1">번호</th>
<th>email</th>
<th>이름</th>
<th>권한</th>
<th class="span1">가입일</th>
<th class="span1">상태</th>
</tr>
</thead>

<tbody>
<c:if test="${empty userList}">
<tr>
<td colspan="7">검색 결과가 없습니다.</td>
</tr>
</c:if>

<c:forEach var="item" items="${userList}" varStatus="status">
<tr>
<td><input type="checkbox" name="deleteItems" id="deleteItems-${item.id}" value="${item.id}" /></td>
<td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
<td><a href="<c:url value="/admin/users/${item.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page}" />">${item.email}</a></td>
<td>${item.name}</td>
<td>${item.roleName}</td>
<td><s:eval expression="item.joinedDate" /></td>
<td>${item.enabled}</td>
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
<c:when test="${paginate.page > 1}"><a href="<c:url value="/admin/users?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=1" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
</li>

<li class="prev">
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/admin/users?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page - 1}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="pageNumber">
<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong class="nowPage">${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/admin/users?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${page}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>    
</li>

<li class="next">
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/admin/users?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.page + 1}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="foward">
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/admin/users?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${paginate.totalPage}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></c:otherwise>
</c:choose>
</li>
</ul>
</div>

</div>
</div>

</div>

</div>

</div>

<sec:authorize ifAnyGranted="ROLE_ADMIN">
<script type="text/javascript">
/* <[CDATA[ */

	$('#user-delete').click(function() {
		   $('#deleteItems').submit();	
	});
	
    $('#deleteItems').submit(function() {
        if (!$(':checkbox[name=deleteItems]:checked').length) {
            alert('삭제 할 목록을 선택하세요');
            return false;
        }
    });
    
        
    $('#search-form').validate({
       errorPlacement: function(error,element){
            alert(error.html());
       },
        rules: {
            'keyword': {
                required: true,
                maxlength: 50
            }
        },
        messages: {
            'keyword': {
                required: '검색어를 입력해주세요.'
            }
        }
    });

/* ]]> */
</script>
</sec:authorize>

</body>
</html>
