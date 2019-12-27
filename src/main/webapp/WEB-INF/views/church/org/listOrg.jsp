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
<h2>교회기관</h2>
<div class="intro">

<div class="church_content">

<div id="titleWrap">
<h3>교회기관</h3>
<p class="home">Home &gt; 교회기관 &gt; <span>교회기관</span></p>
</div>


<div id="writeWrap">

<div id="admin-action"><a href="<c:url value="/church/${ourChurch.id}/orgadmin/new" />" class="icons icons-add">기관추가</a></div>

<!-- <h3 class="org-empty">등록된 기관이 없습니다.</h3> -->

<c:url var="updateUrl" value="/church/${ourChurch.id}/orgadmin"/>
<form:form id="update-form" action="${updateUrl}" method="put">
<div class="result_list sortable" style="height: 600px;">
<c:forEach var="org" items="${listOrg}" varStatus="status">
<table class="serve_table" summary="교회기관 테이블" id="table-${org.id}">
<tbody>
<tr>
    <th>기관명</th>
    <td class="input_w150"><c:out value="${org.name}" escapeXml="true" /></td>
    <th>담당자</th>
    <td class="input_w150"><c:out value="${org.manager}" escapeXml="true"/></td>
    <th>사진</th>
    <td>
    <c:if test="${not empty org.attachment}"><a class="org_image" href="<c:url value="${org.attachment.path}" />"><img src="<c:url value="/resources/images/church/common/icon_file3.gif"/>" alt="사진" /></a></c:if>
    </td>
</tr>
<tr>
    <th>소개글</th>
    <td colspan="4" class="none break-word"><s:eval expression="org.intro.replaceAll('\r\n', '<br/>')"/><%-- <c:out value="${org.intro}" escapeXml="true"/> --%>
    </td>
    <td>
    <a href="<c:url value="/church/${ourChurch.id}/orgadmin/${org.id}/edit" />"><img src="<c:url value="/resources/images/church/common/btn_edit2.png" />" alt="수정" width="52" height="24"/></a>
    <a href="#" class="del-item" id="del-${org.id}"><img src="<c:url value="/resources/images/church/common/btn_delete.gif" />" alt="수정" width="52" height="24"/></a>
    </td>
</tr>
</tbody>
</table>
</c:forEach>
</div>

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">완료</button>
    <a href="<c:url value="/church/${ourChurch.id}/org"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

</form:form>

</div>

<p class="pd10"></p>

</div><!--// admin_content -->

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
<c:forEach var="item" items="${listOrg}" varStatus="status">
<li><a href="<c:url value="/church/${ourChurch.id}/org/${item.id}" />">${item.name}</a></li>
</c:forEach>
</ul>
</div><!--// rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!--// intro -->

<script type="text/javascript" src="<c:url value="/resources/js/jquery/cal/jquery.qtip-1.0.0-rc3.min.js"/>"></script>
<script type="text/javascript">

$(function() {
    
    $( ".sortable" ).sortable({
        revert: true,
        cursor: "move",
        scroll: true
    });
    
    $('table, th, tr, td').disableSelection();
    
    $('.org_image').live('mouseover', function(event) {

        var thumbnail = $('<img />').attr({
            src: $(this).attr('href'),
            alt: 'Loading thumbnail...',
            width: 120,
            height: 80
        });
        
        $(this).qtip({
            content: thumbnail,
            show: {
                event: event.type,
                ready: true
             },            
            position: {
                corner: {
                    tooltip: 'bottomLeft',
                    target: 'topMiddle'
                }
            },
            style: {
                tip: true,
                name: 'light'
            }
        });     
    });
    
    $('#update-form').submit(function() {
        var result = $( ".sortable" ).sortable('toArray');
        if(result.length > 0) {
            $.each(result, function(i, val) {
                var element = $('<input type="hidden" name="order" />');
                element.val(val.split("-")[1]);
                element.appendTo($('#update-form'));
            });
        } else {
            return false;
        }
    });
    
    $('.del-item').click(function() {
        var id = $(this).attr("id").split("-")[1];
        
        if(confirm("교회기관을 삭제하시면 관련 게시물이 모두 삭제됩니다.\r\n그래도 삭제하시겠습니까?")) {
        	 $.ajax({
                 url: '<c:url value="/church/${ourChurch.id}/orgadmin/' + id + '" />',
                 type: 'DELETE',
                 dataType: 'json',
                 success: function(data) {
                     if(data.result == true) {
                         window.location.replace('<c:url value="/church/${ourChurch.id}/orgadmin" />');
                     } else {
                         alert(data);
                     }
                 }
             });
        } 
        return false;
    });
});
</script>
</body>
</html>