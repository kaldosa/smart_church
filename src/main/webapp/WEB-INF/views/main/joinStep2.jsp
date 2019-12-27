<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>회원가입</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

            <div class="container service-wrap">
                <div class="form-box verify-box">
                <div class="error-container well well-small" style="display: none;"></div><c:url var="step2" value="/join"/>
                    <form:form id="verify-code-form" class="form-inline" modelAttribute="joinFormBean" action="${step2}" method="post">
                    <p>이메일 확인 메일이 발송 되었습니다. 메일을 확인하여 아래의 인증코드를 입력해 주세요.</p>
                    <label class="control-label" for="code">인증코드</label><input id="code" name="code" placeholder="인증코드">
                    <button type="submit" class="btn btn-primary">확인</button>
                    <a href="<c:url value="/join?_step=cancel"/>" class="btn">취소</a>
                    <input type="hidden" name="_step" value="verify"/>
                    </form:form>
                </div>
            </div>
        </div>

<script type="text/javascript">
$(function () {
    $.validator.setDefaults({onkeyup:false,onclick:false,onfocusout:false});
    
    $('#verify-code-form').validate({
        errorLabelContainer: ".error-container",
        rules: {
        	'code': {
                required: true,
                alphanumeric: true,
                rangelength: [8, 8]
            }
        },
        messages: {
            'code': {
                required: '인증코드를 입력해 주세요.',
                alphanumeric: '올바른 인증코드 형식이 아닙니다.',
                rangelength: '올바른 인증코드 형식이 아닙니다.'
            }
        }
    });
});
</script>
</body>
</html>