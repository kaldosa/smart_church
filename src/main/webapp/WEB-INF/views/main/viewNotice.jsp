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
<div class="board-wrap">
<div class="board">
<div class="board-subject">
<h4 class="break-word"><c:out value="${entity.subject}" escapeXml="true"/></h4>
<span>|&nbsp; 조회수 ${entity.hits} &nbsp;|&nbsp; <s:eval expression="entity.createdDate" /></span>
</div>

<div class="board-content">
${entity.content}
</div>

<sec:authorize ifAnyGranted="ROLE_ADMIN">
<c:url var="deleteUrl" value="/notices/${entity.id}" />
<form:form action="${deleteUrl}" method="delete" id="delete-notice-form">
<div class="board-extra">
<div class="board-btn-right">
<a href="<c:url value="/notices/${entity.id}/edit"/>"><img width="39" height="20" alt="수정" src="<c:url value="/resources/images/main/btn_edit.png" />" /></a>
<input type="image" alt="삭제" src="<c:url value="/resources/images/main/btn_del.png" />" />
</div>
</div>
</form:form>
</sec:authorize>

</div>

<div class="board-nav">
<ul>
<li class="first">
<span>&lt; 이전글</span><a href="<c:url value="/notices/${prev.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}"/>">
<c:if test="${not empty prev}"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(prev.subject, 15)" htmlEscape="true"/></c:if></a>
</li>
<li><a href="<c:url value="/notices?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}"/>"><img width="39" height="20" alt="목록" src="<c:url value="/resources/images/main/btn_list.png" />" /></a></li>
<li class="last">
<a href="<c:url value="/notices/${next.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}"/>">
<c:if test="${not empty next}"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(next.subject, 15)" htmlEscape="true"/></c:if></a><span>다음글 &gt;</span>
</li>
</ul>
</div>

</div>

</div>

</div>

<script type="text/javascript">
$('#delete-notice-form').submit(function() {
    if(!confirm("삭제하시겠습니까?")) {
        return false;
    }
});
</script>
</body>
</html>