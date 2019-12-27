<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/> 
<title>Insert title here</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="contents-wrap">
<div class="login-wrap">

<div class="login-body clearfix">
<form id="send-mail">
<input type="hidden" name="email" value="${email}" />
<input type="hidden" name="name" value="${name}"/>
<label>이메일</label><span>${email}</span> <input type="submit" class="btn" value="재발급받기"></input>
</form>
</div>

<div class="login-footer clearfix">
<%-- <p>
<span class="forgot">계정을 잊으셨나요?</span>
<a href="#" class="btn">계정찾기<img width="92" height="21" alt="계정찾기" src="<c:url value="/resources/images/main/btn_accsearch.png"/>" /></a>
</p>
<p>
<span class="forgot">아직 <strong>SMART CHURCH</strong> 회원이 아니신가요?</span>
<a href="<c:url value="/joinUser"/>" class="btn btn-primary">회원가입<img width="92" height="21" alt="회원가입" src="<c:url value="/resources/images/main/btn_join2.png"/>" /></a>
</p> --%>
<p>
<span class="forgot">메인으로돌아가기</span>
<a href="<c:url value="/"/>" class="btn">메인으로</a>
</p>
</div>

</div>
</div>
</div>

<script type="text/javascript">
$(function() {
var options = { 
	    url: '<c:url value="/findUser/sendMail" />',
	    dataType: 'json',
	    type: 'post',
	    success: function(data) { 
	        if(data.code == 0) {
	        	alert("임시 비밀번호를 메일로 발송하였습니다.");
	        	window.location.href = '<c:url value="/"/>';
	        } else {
	        	alert(data.description);
	        }
	    } 
	}; 

$('#send-mail').ajaxForm(options);
});
</script>
</body>
</html>