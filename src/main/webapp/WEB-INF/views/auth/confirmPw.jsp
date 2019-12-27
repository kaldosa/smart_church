<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<style type="text/css">
.btn {line-height: 18px; height: auto;}
label.error {margin-left: 5px; font-size: .9em; color: #a33;}
.myInfo {position: relative; margin: 30px auto; width: 680px; background-color:#EAEAEA; *border: 1px solid #ddd; padding: 3px 7px;}
.myInfo legend {border-bottom: 1px solid #D6D6D6; color:#333; display:block; font-size:21px; height:40px; line-height:40px; margin-bottom:20px; padding: 0; width:100%; visibility:visible;}
.form-controls {margin: 20px 0;}
.form-controls::before, .form-controls::after {content:""; display: table; line-height: 0;}
.form-controls::after {clear: both;}
.form-buttons:after {clear: both;}
.form-buttons:before, .form-buttons:after {content: ""; display: table; line-height: 0;}
.form-buttons:before, .form-buttons:after {content: ""; display: table; line-height: 0;}
.form-buttons {background-color: #EAEAEA; border-top: 1px solid #D6D6D6; margin-bottom: 20px; margin-top: 20px; padding: 19px 20px 20px; text-align: center;}
.form-buttons button {margin: 0 5px;}
.form-control {margin-left: 160px;}
.form-control-file {margin: 12px 0;}
.form-label {float: left; text-align:right; width:140px; font-size: 14px; font-weight: normal; line-height: 20px;}
.form-label-image {float: left; text-align:right; width:140px; font-size: 14px; font-weight: normal; line-height: 50px;}
.user-email {font-size: 14px; font-weight: normal; line-height: 20px; color: #666;}
</style>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="contents-wrap">

<div class="myInfo"><c:url var="confirmUrl" value="/auth/confirmPw"/>
<form:form id="confirm-form" modelAttribute="user" action="${confirmUrl}" method="post">
<fieldset>
<legend>회원정보관리</legend>

<div class="form-controls">
<label class="form-label">이메일</label>
<div class="form-control">
<span class="user-email">${user.email}</span>
</div>
</div>

<div class="form-controls">
<label class="form-label" for="password">비밀번호</label>
<div class="form-control">
<form:password id="password" path="password" maxlength="255" cssClass="input_text input_w150" />
</div>
</div>
<div class="form-buttons">
<button type="submit" class="btn btn-primary">확인</button>
<button type="button" id="cancel" class="btn">취소</button>
</div>
<form:hidden path="email" />
</fieldset>
</form:form>
</div>

</div>
</div>
<script type="text/javascript">
$(function() {
    
    $('#confirm-form').validate({
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'password': {
                required: true,
                passwordRex: true
            }
        },
        messages: {
            'password': {
                required: "비밀번호를 입력하세요."
            }
        }
    });

    $('#cancel').click(function() {
    	window.location.href = '<c:url value="/" />';
    });
});
</script>
</body>
</html>