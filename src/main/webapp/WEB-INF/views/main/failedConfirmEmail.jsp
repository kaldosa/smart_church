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
</head>
<body>

<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" width="900px" height="227px"/></div>
<div class="contents-wrap">
<div class="login-wrap">
<div class="login-header" style="text-align: center;">
<span style="color:  #a33;">${status.description}</span>
</div>

<div class="login-body clearfix">
<c:url var="loginUrl" value="/auth/login/j_spring_security_check" />
<form id="login-form" action="${loginUrl}" method="post">
<fieldset>
<legend>비밀번호 찾기 폼</legend>
<p><label for="email">이메일</label><input name="j_username" type="text" id="email" title="이메일" class="input_text" /></p>
<p><label for="password">비밀번호</label><input name="j_password" type="password" id="password" title="비밀번호" class="input_text" /></p>
<input type="image" src="<c:url value="/resources/images/main/btn_login.png"/>" />
</fieldset>
</form>
</div>

<div class="login-footer clearfix">
<p>
<span class="forgot">비밀번호를 잊으셨나요?</span>
<a href="<c:url value="/findUser/findPw"/>" class="btn">비밀번호찾기<%-- <img width="92" height="21" alt="계정찾기" src="<c:url value="/resources/images/main/btn_accsearch.png"/>" /> --%></a>
</p>
<p>
<span class="forgot">아직 <strong>SMART CHURCH</strong> 회원이 아니신가요?</span>
<a href="<c:url value="/joinUser"/>" class="btn btn-primary">회원가입</a>
</p>
</div>

</div>
</div>
</div>

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>

<script type="text/javascript">
$(function() {
    
    $('#login-form').validate({     
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