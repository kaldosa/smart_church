<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/jquery/bootstrap/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/jquery/bootstrap/bootstrap.form.css" />" />
<title>Insert title here</title>
<style type="text/css">
a {text-decoration: none; cursor: pointer;}
.form-actions {text-align: right;}
.form-actions span {font-size: 12px;}
div.error-container {padding: 5px 15px;}
div.error-container .error {font-size: .7em; color: #B94A48;}
</style>
</head>
<body>
<spring:eval var="loginUrl" expression="@envvars['web.login']" />
<form id="login-form" name="login-form" action="${loginUrl}" method="post" >
<fieldset>
<legend>로그인</legend>
<div class="error-container" style="display: none;"><ul></ul></div>
<div class="control-group">
<input type="text" id="u" class="input-medium" placeholder="Email" name="j_username"/>
<input type="password" id="p" class="input-medium" placeholder="Password" name="j_password"/>
<button type="submit" class="btn" onclick="closeSelf();">로그인</button>
</div>
</fieldset>
</form>

<div class="form-actions">
<div class="controls">
<p><span class="control-label">비밀번호를 잊으셨나요?</span> <a id="findPw" href="<c:url value="/findUser/findPw"/>" class="btn link">비밀번호찾기</a></p>
<p><span class="control-label">아직 <strong>SMART CHURCH</strong> 회원이 아니신가요?</span> <a id="join" href="<c:url value="/joinUser"/>" class="btn btn-primary link">회원가입</a></p>
</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.7.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/form/jquery.form.js"/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/validation/jquery.validate.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/validation/additional-methods.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/validation/messages_ko.js' />"></script>
<script type="text/javascript">

    $('.link').click(function() {
    	window.opener.parent.location.href = $(this).attr('href');
    	self.close();
    });
    
    $('#login-form').validate({
    	submitHandler: function(form) {
            $(opener.document).find('#username').val($('#u').val());
            $(opener.document).find('#password').val($('#p').val());
            $(opener.document).find('#login-form').submit();
            self.close();
            return false;
    	},
        errorPlacement: function(error, element) {
            error.appendTo(".error-container");
            element.parent().addClass("error");
        },
        success: function(element) {
            var id = element.attr('id');
            $("label.error[for='" + id + "']").remove();
        },
        showErrors: function(errorMap, errorList) {
            $('.error-container').show();
            this.defaultShowErrors();
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

</script>
</body>
</html>