<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link media="screen" rel="stylesheet" href="<c:url value='/resources/js/jquery/bootstrap/bootstrap.min.css' />" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery/bootstrap/bootstrap.min.js" />"></script>
<style type="text/css">
body {margin: 0 auto; background-color: #4C78B9; background-size: 100% 100%;}
.service-wrap {width: 940px;}
.manage-box:after {content: "서비스관리"}
</style>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="container service-wrap">
<div class="form-box manage-box">
    <div id="register-church">
        <c:url var="updateUrl" value="/admin/services/${entity.id}" />
        <form:form id="service-enable-Form"  class="form-horizontal" action="${updateUrl}" modelAttribute="entity" method="put"> 
        <div class="control-group">
        <div class="controls">
        <form:errors />
        </div>
        </div>
        <div class="control-group">
        <label class="control-label" for="churchName">교회이름</label>
        <div class="controls">
        <span class="input-small uneditable-input">${entity.churchMeta.name}</span>
        </div>
        </div>
        <div class="control-group">
        <label class="control-label" for="pastor">담임목사</label>
        <div class="controls">
        <span class="input-small uneditable-input">${entity.churchMeta.pastor}</span>
        </div>
        </div>
        <div class="control-group">
        <label class="control-label" for="address">교회연락처</label>
        <div class="controls">
        <span class="input-small uneditable-input">${entity.churchMeta.contact}</span> 
        </div>
        </div>
        <div class="control-group">
        <label class="control-label" for="address">교회주소</label>
        <div class="controls">
        <span class="input-xxlarge uneditable-input">${entity.churchMeta.address}</span> 
        </div>
        </div>
        <div class="control-group">
        <label class="control-label" for="address">신청인 ID</label>
        <div class="controls">
        <span class="input-small uneditable-input"><s:eval expression="entity.applicant.email.split('@')[0]"/></span> @ <span class="input-small uneditable-input"><s:eval expression="entity.applicant.email.split('@')[1]"/></span>
        </div>
        </div>
        <div class="control-group">
        <label class="control-label">신청 날짜</label>
        <div class="controls">
        <span class="input-small uneditable-input"><s:eval expression="entity.createdDate"/></span>
        </div>
        </div>
        <c:if test="${entity.status != 'WAITING'}">
        <div class="control-group">
        <label class="control-label">승인 날짜</label>
        <div class="controls">
        <span class="input-small uneditable-input"><s:eval expression="entity.modifiedDate"/></span>
        </div>
        </div>
        <div class="control-group">
        <label class="control-label" for="enabled">공개/비공개</label>
        <div class="controls">
            <label class="checkbox inline"><form:checkbox id="enabled" path="enabled"/> 공개</label>
        </div>
        </div>
        </c:if>
        <div class="control-group">
        <label class="control-label" for="status">Status</label>
        <div class="controls">
            <form:select path="status">
                <c:choose>
                    <c:when test="${entity.status == 'WAITING'}">
                        <form:option value="WAITING" label="서비스승인"/>
                    </c:when>
                    <c:otherwise>
                        <form:option value="SERVICE" label="서비스" />
                        <form:option value="STOP" label="서비스중지" />
                    </c:otherwise>
                </c:choose>
            </form:select>
        </div>
        </div>
        </form:form>

        <c:choose>
            <c:when test="${entity.status == 'WAITING'}">
            <c:url var="deleteUrl" value="/admin/services/${entity.id}/cancel" />
            <form:form id="service-delete-Form"  class="form-horizontal" action="${deleteUrl}" modelAttribute="entity" method="delete"> 
            </form:form>
            </c:when>
            <c:otherwise>
            <c:url var="deleteUrl" value="/admin/services/${entity.id}" />
            <form:form id="service-delete-Form"  class="form-horizontal" action="${deleteUrl}" modelAttribute="entity" method="delete"> 
            </form:form>
            </c:otherwise>
        </c:choose>
        
        <div class="row-fluid">
            <div class="pull-right">
            <button id="enable-btn" type="button" class="btn btn-primary">변경</button> <button id="delete-btn" type="button" class="btn btn-danger">삭제</button>
            </div>
        </div>
        
        <ul class="pager">
        <li class="previous">
            <a href="<c:url value="/admin/services?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}"/>">뒤로가기</a>
        </li>
        </ul>
    </div>
</div>
</div>
</div>

<script type="text/javascript">

    $('#enable-btn').click(function(e) {
        e.preventDefault();
        $('#service-enable-Form').submit();
    });

    $('.btn-danger').click(function(e) {
    	e.preventDefault();
    	if(confirm('정말로 삭제하시겠습니까?')) {
    		$('#service-delete-Form').submit();
        }
    });
</script>
</body>
</html>