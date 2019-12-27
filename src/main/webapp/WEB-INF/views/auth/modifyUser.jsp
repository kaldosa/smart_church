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
div.contains {border: 1px solid #FBC2C4; border-radius: 5px 5px 5px 5px; color: #8A1F11; margin: 5px; padding: 10px 10px 10px 10px;}
div.contains ul {padding: 5px 15px;}
div.contains li {list-style-type: none; line-height: 16px;}
div.contains .error {font-size: .9em; color: #a33;}
form input.error {border-color: #ee0000;}
</style>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="contents-wrap">
<div class="myInfo"><c:url var="updateUrl" value="/auth/modifyUser"/>
<form:form id="update-form" modelAttribute="user" action="${updateUrl}" method="put" enctype="multipart/form-data">
<fieldset>
<legend>회원 정보수정</legend>
<div class="contains" style="display: none;"><ul></ul></div>

<div class="form-controls">
<label class="form-label">이메일</label>
<div class="form-control">
<span class="user-email">${user.email}</span>
</div>
</div>

<div class="form-controls">
<div class="form-label-image">
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="프로필사진" width="50" height="50" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/church/common/right_profile_img.gif" />" alt="프로필사진" width="50" height="50" /></c:otherwise>
</c:choose>
</div>

<div class="form-control">
<input type="file" id="server_photo" name="upload" />
</div>
</div>

<div class="form-controls">
<label class="form-label" for="name">이름</label>
<div class="form-control">
<form:input id="name" path="name" maxlength="20" cssClass="input_text input_w150" />
</div>
</div>

<div class="form-controls">
<label class="form-label" for="password">새 비밀번호</label>
<div class="form-control">
<form:password id="password" path="password" maxlength="12" cssClass="input_text input_w150"/>
</div>
</div>

<div class="form-controls">
<label class="form-label" for="password-confirm">비밀번호 확인</label>
<div class="form-control">
<input type="password" id="password-confirm" name="confirm" maxlength="12" class="input_text input_w150" />
</div>
</div>

<div class="form-controls">
<label class="form-label" for="phone0">연락처</label>
<div class="form-control">
<input type="text" id="phone0" name="phone0" maxlength="4" class="input-small" />
<span class="bar">-</span>
<input type="text" id="phone1" name="phone1" maxlength="4" class="input-small" />
<span class="bar">-</span>
<input type="text" id="phone2" name="phone2" maxlength="4" class="input-small" />
</div>
</div>
<form:hidden id="mobile-phone" path="contact" />
<div class="form-buttons">
<button type="submit" class="btn btn-primary btn-large">확인</button>
<button type="button" id="cancel" class="btn btn-large">취소</button>
</div>
</fieldset>
</form:form>
</div>

</div>
</div>
<script type="text/javascript">
$(function() {
	var mobile = $('#mobile-phone');
	
    var phone = mobile.val().split('-');
    
    $('#phone0').val(phone[0]);
    $('#phone1').val(phone[1]);
    $('#phone2').val(phone[2]);	
	
    $('#update-form').validate({
        submitHandler: function(form) {
            mobile.val($('#phone0').val() + "-" + $('#phone1').val() + "-" + $('#phone2').val());
            form.submit();
        },
        errorContainer: ".contains",
        errorLabelContainer: ".contains ul",
        wrapper: "li",
        rules: {
         	'upload': {
                accept: "png|jpe?g|gif"
            },
            'name': {
            	required: true,
            	rangelength: [2, 20]
            },
            'password': {
                passwordRex: true
            },
            'confirm': {
            	equalTo: '#password'
            },
            'phone0': {
            	required: true,
            	number: true,
            	rangelength: [2, 4]
            },
            'phone1': {
            	required: true,
                number: true,
                rangelength: [3, 4]
            },
            'phone2': {
            	required: true,
                number: true,
                rangelength: [4, 4]
            }
        },
        messages: {
           'upload': {
                accept: "jp(e)g , png, gif 만 허용됩니다.",
            },
            'name': {
                required: '이름을 입력하세요.',
                rangelength: '이름을 [2, 20]자 이내로 입력하세요.'
            },
            'comfirm': {
                equalTo: "비밀번호가 일치하지 않습니다."
            },
            'phone0': {
                required: '연락처를 입력하세요.',
                number: '연락처는 숫자만 입력하세요.',
                rangelength: '연락처는 [2,4]자리 숫자만 입력하세요.'
            },
            'phone1': {
                required: '연락처를 입력하세요.',
                number: '연락처는 숫자만 입력하세요.',
                rangelength: '연락처는 4자리 숫자만 입력하세요.'
            },
            'phone1': {
                required: '연락처를 입력하세요.',
                number: '연락처는 숫자만 입력하세요.',
                rangelength: '연락처는 4자리 숫자만 입력하세요.'
            }
        }
    });

    $('#cancel').click(function() {
        window.location.replace('<c:url value="/" />');
    });
});
</script>
</body>
</html>