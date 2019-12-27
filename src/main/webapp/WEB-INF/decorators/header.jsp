<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="header">
<div class="container">
<div id="logo"><a href="<c:url value="/" />"><img src="<c:url value="/resources/images/main/logo.png"/>" alt="logo"/></a></div>
<nav>
<div class="header-nav-container">
<ul class="header-nav">
<li><a href="<c:url value="/introduction"/>">서비스소개</a></li>
<li><a href="<c:url value="/guide"/>">이용안내</a></li>
<li><a href="<c:url value="/application-service"/>">서비스신청</a></li>
<li><a href="<c:url value="/faq"/>">FAQ</a></li>
</ul>
</div>
</nav>
<div id="nav-login">
<sec:authorize ifNotGranted="ROLE_USER">
    <span class="welcome">여러분, 환영합니다!</span>
    <a class="signin" href="#"><span>로그인</span></a><spring:eval var="loginUrl" expression="@envvars['web.login']" />
    <fieldset id="signin_menu"><%-- <c:url var="loginUrl" value="/auth/login/j_spring_security_check" /> --%>
        <form id="singin" action="${loginUrl}" method="post" >
            <p>
                <label for="email">이메일</label>
                <input type="text" id="email" name="j_username" title="email" class="input_text"/>
            </p>
            <p>
                <label for="password">비밀 번호</label>
                <input type="password" id="password" name="j_password" title="password" class="input_text"/>
            </p>
            <p class="remember">
                <input type="submit" id="signin_submit" value="로그인" />
                <input type="checkbox" id="remember" name="_spring_security_remember_me" />
                <label for="remember">Remember me</label>
            </p>
            <p class="forgot">
                <a href="<c:url value="/join?_step=init" />">회원가입</a>
                <a href="<c:url value="/findUser/findPw" />">비밀번호 찾기</a>
            </p>
        </form>
    </fieldset>
</sec:authorize>
<sec:authorize ifAnyGranted="ROLE_USER, ROLE_ADMIN">
<sec:authentication property="principal" var="user" /><spring:eval var="logoutUrl" expression="@envvars['web.logout']" />
    <span class="welcome">반갑습니다!</span>
    <a class="myinfo" href="#"><span id="small_signup" >${user.name} 님</span></a>
    <div id="myinfo_menu">
        <ul>
            <li><a href="${logoutUrl}"><span>로그아웃</span></a></li>
            <li><a href="<c:url value="/auth/confirmPw"/>">내정보</a></li>
            <sec:authorize ifNotGranted="ROLE_ADMIN"><li><a href="<c:url value="/contactUs"/>">1:1 문의하기</a></li></sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_ADMIN"><li><a href="<c:url value="/admin"/>">관리자페이지</a></li></sec:authorize>
        </ul>
    </div>
</sec:authorize>
</div>
</div>
</div>
<script type="text/javascript">
        $(document).ready(function() {
            $(".signin").click(function(e) {
                e.preventDefault();
                $("fieldset#signin_menu").toggle();
                $(".signin").toggleClass("menu-open");
            });

            $(".myinfo").click(function(e) {
                e.preventDefault();
                $("#myinfo_menu").toggle();
                $(".myinfo").toggleClass("menu-open");
            });
            
            $("fieldset#signin_menu, #myinfo_menu").mouseup(function() {
                return false;
            });
            
            $(document).mouseup(function(e) {
                if($(e.target).parent("a.signin").length==0) {
                    $(".signin").removeClass("menu-open");
                    $("fieldset#signin_menu").hide();
                }
                
                if($(e.target).parent("a.myinfo").length==0) {
                    $(".myinfo").removeClass("menu-open");
                    $("#myinfo_menu").hide();
                }
            });            

        });
</script>