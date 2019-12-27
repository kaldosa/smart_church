<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<div class="header">
<%--     <c:choose>
        <c:when test="${not empty url}"> --%>
        <div id="headerWrap">
            <h1><a href="<c:url value='/church/${ourChurch.id}' />">
            <c:choose>
            <c:when test="${not empty ourChurch.logo.path}"><img src="<c:url value="${ourChurch.logo.path}" />" alt="${ourChurch.churchMeta.name}" width="140" height="56" /></c:when>
            <c:otherwise><img src="<c:url value="/resources/images/church/common/img_default_logo.png" />" alt="${ourChurch.churchMeta.name}" width="140" height="56" /></c:otherwise>
            </c:choose>
            </a>
            </h1>
            
            <ul class="gnb">
                <li><a href="<c:url value='/church/${ourChurch.id}/intro' />" onmouseover="menuOn(1);">교회안내</a></li>
                <li><a href="<c:url value="/church/${ourChurch.id}/news" />" onmouseover="menuOn(2);">교회소식</a></li>
                <li><a href="<c:url value="/church/${ourChurch.id}/sermon" />" onmouseover="menuOn(3);">생명의말씀</a></li>
                <li><a href="<c:url value="/church/${ourChurch.id}/pray" />" onmouseover="menuOn(0);">중보기도</a></li>
                <li><a href="<c:url value="/church/${ourChurch.id}/album" />" onmouseover="menuOn(0);">교회앨범</a></li>
                <li><a href="<c:url value="/church/${ourChurch.id}/org" />" onmouseover="menuOn(4);">교회기관</a></li>
                <sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_${ourChurch.path}"><li style="width:43px;"></li></sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${ourChurch.path}"><li style="width:43px;"><a href="<c:url value="/church/${ourChurch.id}/admin"/>"><img src="<c:url value="/resources/images/church/common/btn_admin.gif" />" alt="관리자" width="43" height="18"/></a></li></sec:authorize>
            </ul><!--// gnb -->     
        </div>
<%--         </c:when>
        <c:otherwise>
        <div id="headerWrap">
            <h1><a href=""><img src="<c:url value="/resources/images/church/common/logo.gif" />" alt="logo" width="140" height="56" /></a></h1>
            <ul class="gnb">
                <li><a href="" onmouseover="menuOn(1);">교회안내</a></li>
                <li><a href="" onmouseover="menuOn(2);">교회소식</a></li>
                <li><a href="" onmouseover="menuOn(3);">생명의말씀</a></li>
                <li><a href="" onmouseover="menuOn(0);">중보기도</a></li>
                <li><a href="" onmouseover="menuOn(0);">교회앨범</a></li>
                <li><a href="" onmouseover="menuOn(4);">교회기관</a></li>
                <sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_${url}"><li style="width:43px;"></li></sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_${url}"><li style="width:43px;"><a href="<c:url value="/church/${url}/admin"/>"><img src="<c:url value="/resources/images/church/common/btn_admin.gif" />" alt="관리자" width="43" height="18"/></a></li></sec:authorize>
            </ul><!--// gnb -->     
        </div> --%>
<%--         </c:otherwise>
    </c:choose> --%>


</div><!--// header -->

<%-- <c:if test="${not empty url}"> --%>
<div id="menuContainer">
    <div id="subMenu1">
        <a href="<c:url value='/church/${ourChurch.id}/intro' />">인사말</a> | <a href="<c:url value='/church/${ourChurch.id}/history' />">교회연혁</a> | <a href="<c:url value="/church/${ourChurch.id}/worship" />">예배안내</a> | <a href="<c:url value="/church/${ourChurch.id}/serve" />">섬기는 분들</a> | <a href="<c:url value="/church/${ourChurch.id}/map" />">오시는 길</a>
    </div>
    <div id="subMenu2">
        <a href="<c:url value="/church/${ourChurch.id}/news" />">새소식</a>
         | 
        <a href="<c:url value="/church/${ourChurch.id}/weekly" />">우리주보</a>
         | 
        <a href="<c:url value="/church/${ourChurch.id}/family" />">새가족소개</a>
    </div>
    <div id="subMenu3">
        <a href="<c:url value="/church/${ourChurch.id}/sermon" />">설교</a> | <a href="<c:url value="/church/${ourChurch.id}/message" />">1분메세지</a>
    </div>
    <c:if test="${not empty listOrg}">
    <div id="subMenu4">
        <c:forEach var="org" items="${listOrg}" varStatus="status" begin="0" end="7">
        <a href="<c:url value="/church/${ourChurch.id}/org/${org.id}"/>"><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).substing(org.name, 7)" htmlEscape="true"/></a><c:if test="${not status.last}"> |</c:if>
        </c:forEach>
        <c:if test="${fn:length(listOrg) > 7}">| ...more</c:if>
    </div>
    </c:if>
</div>
<%-- </c:if>
 --%>
<script>
$("#subMenu1").css("display","none");
$("#subMenu2").css("display","none");
$("#subMenu3").css("display","none");
$("#subMenu4").css("display","none");
function menuOn(no){
    for(var i = 1;i <= 4; i++){
        if(i==no)
            $("#subMenu"+i).fadeIn(300);
        else
            $("#subMenu"+i).fadeOut(300);
    }
};
</script>