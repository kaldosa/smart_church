<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
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

<s:bind path="entity.*">

<div id="writeWrap">

<div class="admin_form">
<c:url var="updateUrl" value="/church/${ourChurch.id}/orgadmin/${entity.id}" />
<form:form name="update-form" action="${updateUrl}" method="put" id="update-form" modelAttribute="entity" enctype="multipart/form-data">        
<fieldset><legend>교회기관 수정 폼</legend>

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="name" element="li" cssClass="error"/>
<form:errors path="intro" element="li" cssClass="error"/>
<form:errors path="upload" element="li" cssClass="error"/>
<form:errors path="manager" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>


<div class="admin_form_input">
<label class="admin_form_label" for="name">기관명</label><form:input id="name" path="name" cssClass="input_text input_w85" maxlength="20"/>
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="manager">담당자</label>
<form:input id="manager" path="manager" cssClass="input_text input_w85" maxlength="15"/>
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="org_photo">사진</label>
<input type="text" id="fileName" class="file_input_textbox input_text input_w150" readonly="readonly" />
<div class="file_input_div">
<img class="file_input_button" src="<c:url value="/resources/images/commons/file_select.gif" />" alt="Browse" />
<input type="file" class="file_input_hidden" onchange="javascript: document.getElementById('fileName').value = this.value" id="org_photo" name="upload" />
</div>
</div>

<div class="admin_form_textarea">
<label class="admin_form_label label_intro" for="intro">소개</label>
<form:textarea id="intro" cssClass="input_textarea" path="intro" rows="3" cols="120" wrap="hard" style="width: 530px; min-height: 45px; resize: none;" />
</div>

</fieldset>
</form:form>
</div>

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button id="update-btn" class="btn btn-inverse btn-large">수정</button>
    <a href="<c:url value="/church/${ourChurch.id}/orgadmin"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

<p class="pd30"></p>
</div>

</s:bind>

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
    <c:choose>
    <c:when test="${item.id == org.id}"><li class="bold"><a href="<c:url value="/church/${ourChurch.id}/org/${item.id}" />">${item.name}</a></li></c:when>
    <c:otherwise><li><a href="<c:url value="/church/${ourChurch.id}/org/${item.id}" />">${item.name}</a></li></c:otherwise>
    </c:choose>
</c:forEach>
</ul>
</div><!--// rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!--// intro -->

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>
<script type="text/javascript">
/* <[CDATA[ */
            
    $('#update-btn').click(function() {
        $('#update-form').submit(); 
    });
    
    $('#update-form').validate({
        errorLabelContainer: ".validate-container ul",
        wrapper: "li",
        errorPlacement: function(error, element) {
            error.appendTo(".validate-container ul");
        },
        invalidHandler: function() {
            $("#validation-dialog").dialog('open');
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'name': {
                required: true,
                maxlength: 20,
                htmlTag: true
            },
            'manager': {
                required: true,
                maxlength: 15,
                htmlTag: true
            },
            'intro': {
                required: true,
                maxlength: 200,
                htmlTag: true
            },
            'upload': {
                accept: 'png|jpe?g|gif'
            }
        },
        messages: {
            'name': {
                required: '<s:message code="NotEmpty.churchOrg.name" />'
            },
            'manager': {
                required: '<s:message code="NotEmpty.churchOrg.manager" />'
            },
            'intro': {
                required: '<s:message code="NotEmpty.churchOrg.intro" />'
            }
        }
    });
    
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
    $("#validation-dialog").dialog({
        autoOpen:false,
        modal:true,
        show: 'fade',
        hide: 'fade',
        resizable: false,
        close: function(event, ui) {
            $('.validate-container ul').children().remove();
        }
    });
/* ]]> */
</script>
</body>
</html>