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
</head>
<body>
<sec:authentication property="principal" var="user" />
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="page-title"><img src="<c:url value="/resources/images/main/img_application_title.gif"/>" /></div>
        <div class="content_layout">
            <div class="container service-wrap">
                <div class="register-form-wrap">
<!--                     <div class="well">
                    
                    </div> -->
                        <div class="hero-unit">
                        <h2>서비스 신청 완료!</h2>
                        <p>우리교회 서비스 신청이 완료되었습니다. <br>
                        관리자 확인 후 ${user.email}로 승인메일을 보내드리겠습니다. <br>승인메일을 받으시면 서비스 이용이 가능합니다. 감사합니다.</p>
                        <p>
                        <a href="<c:url value="/"/>" class="btn btn-primary btn-large">메인페이지로</a>
                        </p>
                        </div>                   
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
