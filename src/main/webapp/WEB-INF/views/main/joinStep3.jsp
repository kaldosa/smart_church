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
                <div class="form-box join-box">
                <div class="error-container well well-small" style="display: none;"></div><c:url var="step3" value="/join"/>
                    <form:form id="join-form" class="form-horizontal" modelAttribute="joinFormBean" action="${step3}">

                    <div class="control-group">
                    <label class="control-label" for="email">이메일</label>
                    <div class="controls">
                    <span class="input-xlarge uneditable-input">${joinFormBean.email}</span> <form:errors path="email" cssClass="help-inline error"/>
                    <form:hidden path="email" />
                    </div>
                    </div>
                    
                    <div class="control-group">
                    <label class="control-label" for="name">이름</label>
                    <div class="controls">
                    <form:input id="name" path="name" placeholder="이름" maxlength="20" /> <form:errors path="name" cssClass="help-inline error"/>
                    </div>
                    </div>
                    
                    <div class="control-group">
                    <label class="control-label" for="password">비밀번호</label>
                    <div class="controls">
                    <form:password id="password" path="password" maxlength="12" placeholder="비밀번호" /> <form:errors path="password" cssClass="help-inline error"/>
                    </div>
                    </div>
                    
                    <div class="control-group">
                    <label class="control-label" for="confirm">비밀번호 확인</label>
                    <div class="controls">
                    <input type="password" id="confirm" name="confirm" maxlength="12" placeholder="비밀번호 확인" />
                    </div>
                    </div>
                    
                    <div class="control-group">
                    <label class="control-label" for="contact0">연락처</label>
                    <div class="controls">
                    <input type="text" id="contact0" class="input-mini" maxlength="4" name="contact0"> - <input type="text" id="contact1" class="input-mini" name="contact1" maxlength="4"> - <input type="text" id="contact2" class="input-mini" name="contact2" maxlength="4"> <form:errors path="contact" cssClass="help-inline error"/> 
                    </div>
                    </div>
                    
                    <form:hidden id="contact" path="contact"/>
                    
                    <div class="control-group">
                    <div class="controls">
                    <button type="submit" class="btn btn-primary">가입</button>
                    <a href="<c:url value="/join?_step=cancel"/>" class="btn">취소</a>
                    <input type="hidden" name="_step" value="join"/>
                    </div>
                    </div>
                    </form:form>
                </div>
            </div>
        </div>

<script type="text/javascript">
$(function () {
    $.validator.setDefaults({onkeyup:false,onclick:false,onfocusout:false});
    
    $('#join-form').validate({
        submitHandler: function(form) {
            $('#contact').val($('#contact0').val() + '-' + $('#contact1').val() + '-' + $('#contact2').val());
            
            form.submit();
        },
        errorLabelContainer: ".error-container",
        rules: {
            'name' : {
                required: true,
                rangelength: [2, 20]
            },
            'contact0' : {
                required: true,
                number: true,
                rangelength: [2, 4]
            },
            'contact1' : {
                required: true,
                number: true,
                rangelength: [3, 4]
            },
            'contact2' : {
                required: true,
                number: true,
                rangelength: [4, 4]
            },
            'password': {
                required: true,
                passwordRex: true
            },
            'confirm': {
                equalTo: '#password'
            }
        },
        messages: {
            'name': {
                required: '이름을 입력하세요.',
                rangelength: '이름을 [2, 20]자 이내로 입력하세요.'
            },
            'password': {
                required: '비밀번호를 입력하세요.'
            },
            'confirm': {
            	equalTo: '비밀번호가 일치하지 않습니다.'
            },
            'contact0': {
                required: '연락처를 입력하세요.',
               	number: '연락처는 숫자만 입력하세요.',
               	rangelength: '연락처는 [2,4]자리 숫자만 입력하세요.'
            },
            'contact1': {
                required: '연락처를 입력하세요.',
                number: '연락처는 숫자만 입력하세요.',
                rangelength: '연락처는 [3,4]자리 숫자만 입력하세요.'
            },
            'contact2': {
                required: '연락처를 입력하세요.',
                number: '연락처는 숫자만 입력하세요.',
                rangelength: '연락처는 4자리 숫자만 입력하세요.'
            }
        }
    });
});
</script>
</body>
</html>