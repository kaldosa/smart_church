<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<c:if test="${!ajaxRequest}">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link media="screen" rel="stylesheet" href="<c:url value='/resources/js/jquery/colorbox/colorbox.css' />" />
<link rel="stylesheet" href="<c:url value='/resources/css/business/queryApplication.css' />" type="text/css" />
<title><s:message code="label.business.title" /></title>
</head>
<body>
</c:if>

<c:choose>
<c:when test="${not empty application}">
<table>
    <colgroup>
        <col class="name" />
        <col class="email" />
        <col class="phone" />
        <col class="date" />
        <col class="approv" />
    </colgroup>
    <tr>
        <th>신청자</th>
        <th>이메일</th>
        <th>연락처</th>
        <th>신청일</th>
        <th>처리상태</th>
    </tr>
    <tr>
        <td>${application.applicant}</td>
        <td>${application.applicantEmail}</td>
        <td><s:eval expression="application.applicantContacts" /></td>
        <td><s:eval expression="application.createdDate" /></td>
        <td>${application.approveStatus.value}</td>
    </tr>
    <tr>
        <td class="info" colspan="2">
            <p>
                <span>- 교회 : </span> ${application.churchName} 교회
            </p>
            <p>
                <span>- 주소 : </span> ${application.address1}
            </p>
            <p>
                <span>&nbsp;</span> ${application.address2}
            </p>
        </td>
        <td class="info" colspan="3">
            <p>
                <span>- URL : </span> www.smartchurch.co.kr/church/<strong class="url">${application.churchUrl}</strong>
            </p>
            <p>
                <span>- 전화 : </span> <s:eval expression="application.churchContacts" />
            </p>
        </td>
    </tr>
    <tr>
        <td class="comments" colspan="5"><textarea readonly="readonly">${application.applicantComments}</textarea></td>
    </tr>
</table>

<c:choose>
<c:when test="${application.approveStatus.key == 'APPROVAL'}">
<a href="<c:url value="/business/application/${application.id}"/>" class="btn_type setting"><span>설정</span></a>
</c:when>
<c:when test="${application.approveStatus.key == 'ACTIVE' }">
</c:when>
<c:otherwise>
    <fieldset><c:url var="cancelUrl" value="/business/application/query/${application.id}" />
    <form:form id="cancel_application" action="${cancelUrl}" method="delete">
        <button type="submit" class="btn_type revoke"><span>취소</span></button>
    </form:form>
    </fieldset>
</c:otherwise>
</c:choose>
</c:when>

<c:otherwise>
<h3>조회 결과가 없습니다.</h3>
</c:otherwise>
</c:choose>
<c:if test="${!ajaxRequest}">
</body>
</html>
</c:if>