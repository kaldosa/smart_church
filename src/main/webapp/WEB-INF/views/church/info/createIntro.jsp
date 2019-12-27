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
<h2>교회안내</h2>
<div class="intro">

<!-- 본문 -->
<div class="church_content">

<div id="titleWrap">
<h3>인사말</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>인사말</span></p>
</div>
    

<s:bind path="churchIntro.*">

<div id="writeWrap">

<c:url var="createUrl" value="/church/${ourChurch.id}/intro" />
<form:form id="create-form" name="introForm" action="${createUrl}" modelAttribute="churchIntro"  method="post" enctype="multipart/form-data">
<fieldset><legend>인사말 폼</legend>

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="slogan" element="li" cssClass="error"/>
<form:errors path="intro" element="li" cssClass="error"/>
<form:errors path="file" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>


<div class="writeCon">
<dl>
<dt><label for="intro_image">이미지</label></dt>
<dd>
<input type="text" id="fileName" class="file_input_textbox input_text input_w450" readonly="readonly" />
<div class="file_input_div">
<img class="file_input_button" src="<c:url value="/resources/images/commons/file_select.gif" />" alt="Browse" />
<input type="file" class="file_input_hidden" onchange="javascript: document.getElementById('fileName').value = this.value" id="intro_image" name="file" />
</div>
<div class="ex">교회를 대표하는 이미지를 등록해주세요. <br />권장 사이즈: 680x240 px</div>
</dd>
</dl>

<dl>
<dt class="tit"><label for="slogan">표어</label></dt>
<dd>
<form:textarea path="slogan" id="slogan" cssClass="input_textarea" cssStyle="width: 520px; height: 80px;"></form:textarea>
<div class="ex">50자 이내로 작성해주세요.</div>
</dd>
</dl>

<dl class="end">
<dt class="tit"><label for="intro_conts">내용</label></dt>
<dd>
<form:textarea path="intro" id="intro_conts" rows="15" cols="120" cssClass="input_textarea" cssStyle="width: 520px; min-height: 300px;" />
</dd>
</dl>
</div>
<form:hidden path="path" value="${ourChurch.path}"/>

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">등록</button>
    <a href="<c:url value="/church/${ourChurch.id}/intro"/>" class="btn btn-large">취소</a>
</div><!--// 버튼 -->
</div>

</fieldset>
</form:form><!--// 게시판 글 작성 -->
</div>
</s:bind>
<p class="pd30"></p>

</div><!--// 본문 -->

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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/intro"/>">인사말</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/history"/>">교회연혁</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/worship"/>">예배안내</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>
</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->

<div id="validation-dialog">
    <div class="validate-container"><ul></ul></div>
</div>

<script type="text/javascript">

    $('#create-form').validate({
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
            'slogan': {
                required: true,
                maxlength: 50,
                htmlTag: true
            },
            'intro': {
                required: true,
                maxlength: 3000,
                htmlTag: true
            },
            'file': {
            	accept: "jpg|jpeg|png|gif"
            }
        },
        messages: {
            'slogan': {
                required: "표어를 입력해 주세요."
            },
            'intro': {
            	required: "인사말을 입력해 주세요."
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
</script>
</body>
</html>