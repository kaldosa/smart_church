<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<style type="text/css">
.welcome-join {border-top: 1px solid #D6D6D6; border-bottom: 1px solid #D6D6D6; padding: 10px 0;}
.welcome-join h3 {margin-bottom: 10px;}
</style>

<title>Smart Church의 회원이 되신것을 진심으로 환영합니다!</title>
</head>
<body>

<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />"/></div>
<div class="contents-wrap">
<div class="login-wrap">

<div class="welcome-join">
<h3>Smart Church의 회원이 되신것을 진심으로 환영합니다!</h3>

<p>
안녕하세요. 고객님! <br/>
회원가입이 완료되었습니다.</p>

<p>
SmartChurch의 모든 고객서비스관리는 메일을 통해 이루어집니다. <br/>
고객님의 연락처가 변경된 경우에는 꼭 SmartChurch 홈페이지 내 [정보수정]을 이용해
최신 정보로 유지해 주시기 바랍니다. <br/>
<br/>
감사합니다.
</p>
</div>

<div class="login-footer clearfix">
<p>
<span class="forgot">메인페이지로 가기</span>
<a href="<c:url value="/"/>" class="btn">메인페이지로가기</a>
</p>
</div>

</div>
</div>
</div>
</body>
</html>