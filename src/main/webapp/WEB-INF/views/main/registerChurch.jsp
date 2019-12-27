<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link media="screen" rel="stylesheet" href="<c:url value='/resources/js/jquery/bootstrap/bootstrap.form.css' />" />
<link media="screen" rel="stylesheet" href="<c:url value='/resources/css/application.css' />" />
<title></title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="page-title"><img src="<c:url value="/resources/images/main/img_application_title.gif"/>" /></div>
        <div class="content_layout">
            <div class="container service-wrap">
                <div id="register-church" class="register-box">
                <div class="error-container well well-small" style="display: none;"></div>
                    <c:url var="registerUrl" value="/application-service/register"/>
                    <form:form id="register-church-form" cssClass="form-horizontal" action="${registerUrl}" modelAttribute="churchMeta">
                    <div class="control-group">
                    <div class="controls">
                    <form:errors cssClass="help-inline error"/>
                    </div>
                    </div>
                    <div class="control-group">
                    <label class="control-label" for="churchName">교회이름</label>
                    <div class="controls">
                    <input type="text" id="churchName" placeholder="교회이름을 입력해주세요." name="name" maxlength="20"/> <form:errors path="name" cssClass="help-inline error"/>
                    </div>
                    </div>
                    <div class="control-group">
                    <label class="control-label" for="pastor">담임목사</label>
                    <div class="controls">
                    <input type="text" id="pastor" placeholder="담임목사명을 입력해주세요." name="pastor" maxlength="20" /> 목사<form:errors path="pastor" cssClass="help-inline error"/>
                    </div>
                    </div>
                    <div class="control-group">
                    <label class="control-label" for="contact0">교회연락처</label>
                    <div class="controls">
                    <input type="text" id="contact0" class="input-mini" name="contact0" maxlength="4"> - <input type="text" id="contact1" class="input-mini" name="contact1" maxlength="4"> - <input type="text" id="contact2" class="input-mini" name="contact2" maxlength="4"> <form:errors path="contact" cssClass="help-inline error"/> 
                    </div>
                    </div>
                    <div class="control-group">
                    <label class="control-label" for="find-zipcode">교회주소</label>
                    <div class="controls">
                    <p><button id="find-zipcode" type="button" class="btn">주소검색</button> <form:errors path="address" cssClass="help-inline error"/></p>
                    <p><input type="text" id="addr1" name="addr1" class="input-xxlarge uneditable-input" readonly="readonly"></p>
                    <input type="text" id="addr2" placeholder="나머지주소" class="input-large" maxlength="20">
                    </div>
                    </div>
                    <div class="control-group">
                    <label class="control-label" for="traffic">오시는 길</label>
                    <div class="controls">
                    <textarea id="traffic" name="traffic" rows="3" style="width: 530px; resize: none;" maxlength="250" wrap="hard"></textarea>
                    <form:errors path="traffic" cssClass="help-inline error"/>
                    </div>
                    </div>
                    <div class="control-group">
                    <div class="controls">
                    <form:hidden id="contact" path="contact"/>
                    <form:hidden id="address" path="address"/>
                    <button type="submit" class="btn btn-primary">등록</button> <a href="<c:url value="/application-service"/>" class="btn">취소</a>
                    </div>
                    </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function () {
	$.validator.setDefaults({onkeyup:false,onclick:false,onfocusout:false});
    
    $('#register-church-form').validate({
    	submitHandler: function(form) {
            $('#contact').val($('#contact0').val() + '-' + $('#contact1').val() + '-' + $('#contact2').val());
            var addr = $('#addr1').val() + ' ' + $('#addr2').val();
            $('#address').val(addr.trim());
            
            form.submit();
        },
    	errorLabelContainer: ".error-container",
     	rules: {
        	'name' : {
        		required: true,
        		kor: true,
        		rangelength: [1, 20]
        	},
        	'pastor' : {
        		required: true,
        		kor: true,
        		rangelength: [1, 20]
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
        	'addr1': {
        		required: true
        	},
        	'traffic': {
        		maxlength: 250
        	}
        },
        messages: {
        	'name': {
        		required: '교회이름을 입력하세요.'
        	},
        	'pastor': {
        		required: '담임목사 이름을 입력하세요.'
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
            },
        	'addr1': {
        		required: '교회주소를 입력하세요.'
        	},
        	'traffic': {
                maxlength: '오시는 길 내용은 250이내로 입력하세요.'
            }
        }
    });
    
    $('#find-zipcode').click(function() {
        window.open('<c:url value="/popup/zipcode"/>', 'popup', 'width=420, height=380, toolbar=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes');
    });

});
</script>
</body>
</html>
