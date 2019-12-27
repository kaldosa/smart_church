<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<sec:authorize var="isLogin" ifAnyGranted="ROLE_USER"/>
<table class="table table-hover">
<thead>
<tr>
    <th>번호</th>
    <th>교회명</th>
    <th>담임목사</th>
    <th>전화번호</th>
    <th>주소</th>
    <th>상태</th>
</tr>
</thead>
<tbody>
<c:forEach var="church" items="${list}" varStatus="status">
<tr>
    <td>${paginate.totalItem - ((paginate.page - 1) * paginate.itemPerPage) - (status.index) }</td>
    <td>${church.name}</td>
    <td>${church.pastor} 목사</td>
    <td>${church.contact}</td>
    <td>${church.address}</td>
    <td>
    <c:choose>
    <c:when test="${not empty church.serviceStatus and church.serviceStatus != 'WAITING'}"><span class="label label-success">${church.serviceStatus.value}</span></c:when>
    <c:when test="${not empty church.serviceStatus and church.serviceStatus == 'WAITING'}"><span class="label label-warning">${church.serviceStatus.value}</span></c:when>
    <c:otherwise><a href="<c:url value="/application-service/signup?id=${church.id}"/>" style="text-decoration: none; cursor: pointer;" class="required-login"><span class="label label-info">서비스신청</span></a></c:otherwise>
    </c:choose>
    </td>
</tr>
</c:forEach>
</tbody>
</table>

<c:if test="${empty list}">
<div class="well well-small">
    <h3>검색된 교회가 없습니다.</h3>
    <p>검색된 교회가 없는 경우, 먼저 <strong>교회등록</strong>을 하신 후 서비스를 신청하시기 바랍니다.</p>
    <p style="text-align: right;">
    <a href="<c:url value="/application-service/register"/>" class="btn btn-primary required-login">교회등록</a>
    </p>
</div>
</c:if>

<div class="pagination pagination-centered">
<ul>
<li class="disabled"><a href="#">Prev</a></li>

<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<li><a href="<c:url value="/application-service/find?page=${page}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />" class="page">${page}</a></li>
</c:forEach>
<li><a href="#">Next</a></li>
</ul>
</div>

<script type="text/javascript">
$(function() {
	var isLogin = '${isLogin}' ;
	$('.required-login').click(function(e) {
		e.preventDefault();

		if(isLogin === 'false') {
			alert("로그인 먼저 하세요.");
		} else {
			window.location.replace($(this).attr('href'));
		}
	});
	
    $('.page').click(function(e) {
    	e.preventDefault();
    	
    	var url = encodeURI($(this).attr('href'));
    	
    	$.ajax({
    		cache: false,
    		url: url,
    		dataType: 'html',
    		type: 'GET',
    		success: function(html) {
                $('#result-list').children().remove();
                $('#result-list').append(html);
    		}
    	});
    });
});
</script>