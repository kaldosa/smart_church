<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap bg-white">
<div class="page-title"><img src="<c:url value="/resources/images/main/img_customer_title.gif"/>" /></div>

<div class="board-wrap">

<div class="board no-boarder">

<div class="board-extra">
<div class="board-btn-right"><c:url var="searchUrl" value="/listChurch"/>
<form id="search-form" action="${searchUrl}" method="get" class="form-inline">
<img width="14" height="13" alt="" src="<c:url value="/resources/images/main/icon_list_church.png" />"/>
<input type="hidden" name="criteria" value="name" />
<input id="keyword" type="text" name="keyword" class="input_text input_w150" value="" maxlength="15" title="검색어입력" />
<input type="image" src="<c:url value="/resources/images/main/btn_search.png" />" alt="검색" />
</form>
</div>
</div>

<ul class="thumbnails">

<c:forEach var="item" items="${list}">
<li class="spanW340">
<div class="row">
<div class="thumbnail spanW80">
<a href="<c:url value="/church/${item.id}"/>">
<c:choose>
<c:when test="${not empty item.logo.path}"><img width="71" height="71" alt="${item.churchMeta.name}" src="<c:url value="${item.logo.path}"/>" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/church/common/img_default_logo.png" />"  width="71" height="71" alt="${item.churchMeta.name}"/></c:otherwise>
</c:choose>


</a>
</div>
<div class="span3">
<p><img width="14" height="13" alt="" src="<c:url value="/resources/images/main/icon_list_church.png" />"/> <a href="<c:url value="/church/${item.id}"/>">${item.churchMeta.name}</a></p>
<p class="desc">${item.churchMeta.address} <br/>Tel. ${item.churchMeta.contact}<br/>담임목사. ${item.churchMeta.pastor}</p>
</div>
</div>
</li>
</c:forEach>
</ul>
<!-- </div> -->

<div class="board-extra">

<div class="paging">
<ul>

<li class="rewind">
<c:choose>
<c:when test="${paginate.page > 1}"><a href="<c:url value="/listChurch?page=1&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></a></c:when>
<c:otherwise><img width="12" height="13" class="front" alt="맨앞" src="<c:url value="/resources/images/church/common/btn_prev2.gif"/>" /></c:otherwise>
</c:choose>
</li>

<li class="prev">
<c:choose>
<c:when test="${paginate.page - 1 > 0}"><a href="<c:url value="/listChurch?page=${paginate.page - 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" alt="이전" src="<c:url value="/resources/images/church/common/btn_prev1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="pageNumber">
<c:forEach var="page" begin="${paginate.startPage}" end="${paginate.endPage}" step="1">
<c:choose>
<c:when test="${page == paginate.page}"><strong class="nowPage">${paginate.page}</strong></c:when>
<c:otherwise><a href="<c:url value="/listChurch?page=${page}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />">${page}</a></c:otherwise>
</c:choose>
</c:forEach>    
</li>

<li class="next">
<c:choose>
<c:when test="${paginate.page + 1 <= paginate.totalPage}"><a href="<c:url value="/listChurch?page=${paginate.page + 1}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></a></c:when>
<c:otherwise><img width="12" height="13" class="next" alt="다음" src="<c:url value="/resources/images/church/common/btn_next1.gif"/>"/></c:otherwise>
</c:choose>
</li>

<li class="foward">
<c:choose>
<c:when test="${paginate.page < paginate.totalPage}"><a href="<c:url value="/listChurch?page=${paginate.totalPage}&criteria=${queryParam.criteria}&keyword=${queryParam.keyword}" />"><img width="12" height="13" alt="맨뒤" src="<c:url value="/resources/images/church/common/btn_next2.gif"/>"/></a></c:when>
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

</div>

</div>

<script type="text/javascript">
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