<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>비밀번호찾기</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="container service-wrap">
<div class="form-box password-box">

<div class="login-header">
<p class="desc login-icon-search">
<span>비밀번호 찾기</span> <br/>
<c:if test="${empty statusCode}">
<em>가입 당시 입력하신 이메일과 이름을 입력하세요.</em>
</c:if>
<em class="error">${statusCode.description}</em>
</p>
</div>

<div class="error-container well well-small" style="display: none;"></div>

<div class="login-body clearfix">
<c:url var="findPwUrl" value="/findUser/findPw"/>
<form id="find-form" action="${findPwUrl}" method="post" class="form-horizontal">
<fieldset>

<div class="control-group">
<label class="control-label" for="email">이메일</label>
<div class="controls">
<input name="email" type="text" id="email" placeholder="이메일" maxlength="255" /> <!-- <form:errors path="name" cssClass="help-inline error"/> -->
</div>
</div>
<div class="control-group">
<label class="control-label" for="name">이름</label>
<div class="controls">
<input name="name" type="text" id="name" placeholder="이름" maxlength="20" /> <!-- <form:errors path="name" cssClass="help-inline error"/> -->
</div>
</div>

<div class="control-group">
<div class="controls">
<button type="submit" class="btn btn-primary">찾기</button>
</div>
</div>
</fieldset>
</form>
</div>

<div class="login-footer clearfix">
<p>
<span class="forgot">아직 <strong>SMART CHURCH</strong> 회원이 아니신가요?</span>
<a href="<c:url value="/join?_step=init"/>" class="btn btn-primary">회원가입<%-- <img width="92" height="21" alt="회원가입" src="<c:url value="/resources/images/main/btn_join2.png"/>" /> --%></a>
</p>
</div>

</div>

</div>
</div>

<script type="text/javascript">
$(function() {
    
    $('#find-form').validate({
        wrapper: "li",
        errorLabelContainer: ".error-container",
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'email': {
                required: true,
                email: true
            },
            'name': {
                required: true,
                kor: true
            }
        },
        messages: {
            'email': {
                required: "이메일을 입력하세요."
            },
            'name': {
                required: "이름을 입력하세요.",
                kor: '한글만 입력가능 합니다.'
            }
        }
    });
});
</script>
</body>
</html>