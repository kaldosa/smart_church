<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><s:message code="label.business.title" /></title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="container service-wrap">
<div class="form-box login-box">
<div class="login-header">
<c:if test="${not empty error}">
<p class="desc login-icon-error">
<span>입력하신 아이디 혹은 비밀번호가 일치하지 않습니다.</span> <br/>
<em>비밀번호는 대/소문자를 구분하여 입력해주시기 바랍니다.</em>
</p>
</c:if>
</div>

<div class="error-container well well-small" style="display: none;"></div>

<div class="login-body clearfix">
<s:eval var="loginUrl" expression="@envvars['web.login']" />
<form id="login-form" action="${loginUrl}" method="post" class="form-horizontal">
<fieldset>

<div class="control-group">
<label class="control-label" for="email">이메일</label>
<div class="controls">
<input name="j_username" type="text" id="email" placeholder="이메일" maxlength="255" /> <!-- <form:errors path="name" cssClass="help-inline error"/> -->
</div>
</div>

<div class="control-group">
<label class="control-label" for="password">비밀번호</label>
<div class="controls">
<input name="j_password" type="password" id="password" placeholder="비밀번호" maxlength="14" /> <!-- <form:errors path="name" cssClass="help-inline error"/> -->
</div>
</div>

<input type="hidden" name="spring-security-redirect" value="${redirectUrl}" />

<div class="control-group">
<div class="controls">
<button type="submit" class="btn btn-primary">로그인</button>
</div>
</div>
</fieldset>
</form>
</div>

<div class="login-footer clearfix">
<p>
<span class="forgot">비밀번호를 잊으셨나요?</span>
<a href="<c:url value="/findUser/findPw"/>" class="btn">비밀번호찾기</a>
</p>
<p>
<span class="forgot">아직 <strong>SMART CHURCH</strong> 회원이 아니신가요?</span>
<a href="<c:url value="/join?_step=init"/>" class="btn btn-primary">회원가입</a>
</p>
</div>

</div>
</div>
</div>

<script type="text/javascript">
$(function() {
    
    $('#login-form').validate({ 	
    	errorLabelContainer: ".error-container",
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'j_username': {
                required: true,
                email: true
            },
            'j_password': {
            	required: true,
            	passwordRex: true
            }
        },
        messages: {
            'j_username': {
                required: "이메일을 입력하세요."
            },
            'j_password': {
                required: "비밀번호를 입력하세요."
            }
        }
    });
});
</script>
</body>
</html>