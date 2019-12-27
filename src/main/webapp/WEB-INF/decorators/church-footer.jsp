<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="footer">
    <div id="footerWrap" class="footerWrap">
        <h2>
<c:choose>
<c:when test="${not empty ourChurch.logo.path}"><img src="<c:url value="${ourChurch.logo.path}" />" alt="${ourChurch.churchMeta.name}" width="81" height="35" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/church/common/img_default_logo.png" />" alt="${ourChurch.churchMeta.name}" width="81" height="35" /></c:otherwise>
</c:choose>
        </h2>
        <div class="companyInfo">
            <ul>
                <li class="first">${ourChurch.churchMeta.address}</li>
                <li>전화 : ${ourChurch.churchMeta.contact}</li>
            </ul>
            <p class="copyright">All Contents Copyright 2012 ${ourChurch.churchMeta.name}. </p>
        </div><!-- companyInfo -->
        <div class="main-link"><a href="<c:url value="/"/>"><img src="<c:url value="/resources/images/church/common/img_sc_link.png"/>" alt="메인페이지로"> <span>www.smartchurch.co.kr</span></a></div>
    </div> 
</div><!-- footerWrap -->
