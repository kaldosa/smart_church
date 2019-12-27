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

.welcome-join {border-top: 1px solid #D6D6D6; border-bottom: 1px solid #D6D6D6; padding: 10px 0;}
.welcome-join h3 {margin-bottom: 10px;}
</style>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />"/></div>
<div class="contents-wrap">
<div class="login-wrap">

<div class="welcome-join">
<h3>Smart Church 회원가입이 완료 되었습니다.</h3>

<p>
가입하신 메일(${email})로 계정 활성화 메일이 발송되었습니다.
</p>
<br/>
<p>
회원가입 후 계정 활성 메일을 수신하지 못 하였다면, <br/>
아래의 "메일 재 발송페이지"을 통해 계정을 활성 하시기 바랍니다.
<br/><br/>
감사합니다.
</p>

</div>

<div class="login-footer clearfix">
<p>
<span class="forgot">메인페이지로 가기</span>
<a href="<c:url value="/"/>" class="btn">메인페이지로가기<%-- <img width="92" height="21" alt="계정찾기" src="<c:url value="/resources/images/main/btn_accsearch.png"/>" /> --%></a>
</p>
<p>
<span class="forgot">재 발송페이지로가기</span>
<a href="<c:url value="/joinUser/notEnable"/>" class="btn">재 발송페이지로가기<%-- <img width="92" height="21" alt="계정찾기" src="<c:url value="/resources/images/main/btn_accsearch.png"/>" /> --%></a>
</p>
</div>

</div>
</div>
</div>

</body>
</html>