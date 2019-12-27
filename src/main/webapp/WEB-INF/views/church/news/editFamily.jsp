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
<h2>교회소식</h2>
<div class="intro">
<!-- 본문 -->
<div class="church_content">

<div id="titleWrap">
<h3>새가족소개</h3>
<p class="home">Home &gt; 교회소식 &gt; <span>새가족소개</span></p>
</div>

<s:bind path="entity.*">

<div id="writeWrap">
<c:url var="updateUrl" value="/church/${ourChurch.id}/family/${entity.id}" />
<form:form name="update-form" action="${updateUrl}" method="put" id="update-form" modelAttribute="entity" enctype="multipart/form-data">
<fieldset><legend>새가족 소개 수정 폼</legend>

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="name" element="li" cssClass="error"/>
<form:errors path="intro" element="li" cssClass="error"/>
<form:errors path="upload" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>

<div class="writeCon">
<dl>
<dt><label for="name">이름</label></dt>
<dd><form:input path="name" id="name" maxlength="10" cssClass="input_text input_w80"/></dd>
</dl>
<dl>
<dt><label for="family_photo">이미지</label></dt>
<dd>
<input type="text" id="fileName" class="file_input_textbox input_text input_w450" readonly="readonly" />
<div class="file_input_div">
<img class="file_input_button" src="<c:url value="/resources/images/commons/file_select.gif" />" alt="Browse" />
<input type="file" class="file_input_hidden" onchange="javascript: document.getElementById('fileName').value = this.value" id="family_photo" name="upload" />
</div>
</dd>
</dl>
<dl class="end">
<dt><label for="intro">소개글</label></dt>
<dd>
<form:textarea path="intro" id="intro" cssClass="input_textarea" cols="120" rows="5" cssStyle="width: 520px; height: 100px;" />
<div class="ex">150자 이내로 작성해주세요.</div>
<%-- <c:if test="${not empty churchNews.attach}"> --%>
<div class="attach_list_box">
<span class="attach_file">${entity.attachment.fileName}</span>
</div>
<form:hidden path="attachment.id" />
<%-- </c:if> --%>
</dd>
</dl>
</div><!-- writeCon -->

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">수정</button>
    <a href="<c:url value="/church/${ourChurch.id}/family"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

</fieldset>
</form:form>
</div><!-- writeWrap -->

</s:bind>

<p class="pd30"></p>
</div><!-- news_content -->

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
<li><a href="<c:url value="/church/${ourChurch.id}/news" />">새소식</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/weekly" />">우리주보</a></li>
<%-- <li><a href="<c:url value="/church/${ourChurch.id}/schedule" />">교회일정</a></li> --%>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/family" />">새가족 소개</a></li>
</ul>
</div>
<!-- rnb -->
</div>
<!--// 오른쪽 프로필 및 서브메뉴 -->
</div><!-- intro -->

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>

<script type="text/javascript">
$(function() {
    
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
                maxlength: 10,
                htmlTag: true
            },
            'intro': {
                required: true,
                maxlength: 150,
                htmlTag: true
            },
            'upload': {
                accept: 'png|jpe?g|gif'
            }
        },
        messages: {
            'name': {
                required: "제목를 입력해주세요."
            },
            'intro': {
                required: "소개를 입력해주세요."
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
});
</script>
</body>
</html>