<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html">
<html lang="ko">
<head>
<style type="text/css">
.collapse{position:relative;height:0;overflow:hidden;-webkit-transition:height 0.35s ease;-moz-transition:height 0.35s ease;-o-transition:height 0.35s ease;transition:height 0.35s ease;}.collapse.in{height:auto;}
.accordion{margin-bottom:20px;}
.accordion-group{margin-bottom:2px;}
.accordion-heading{border-bottom:0;}
.accordion-heading .accordion-toggle{display:block;padding:8px 15px;}
.accordion-toggle{cursor:pointer;}
.accordion-inner{padding:9px 15px;}
.accordion-heading a {outline: 0;}
.faq-link {position: relative; padding: 0 73px; text-align: right;}
</style>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="page-title"><img src="<c:url value="/resources/images/main/img_faq_title.gif"/>" /></div>
<div class="page-content">

<div id="faq" class="accordion">

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer1" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_1_q.gif"/>" /></a>
</div>    
<div id="answer1" class="accordion-body collapse">
<div class="accordion-inner">
<img src="<c:url value="/resources/images/main/img_faq_1_a.gif"/>" />
<div class="faq-link"><a href="<c:url value="/findUser/findPw"/>"><img src="<c:url value="/resources/images/main/btn_faq_1.gif"/>" /></a></div>
</div>
</div>
</div>

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer2" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_2_q.gif"/>" /></a>
</div>    
<div id="answer2" class="accordion-body collapse">
<div class="accordion-inner">
<img src="<c:url value="/resources/images/main/img_faq_2_a.gif"/>" />
<div class="faq-link">
<a href="<c:url value="/join?_step=init"/>"><img src="<c:url value="/resources/images/main/btn_faq_2-1.gif"/>" /></a><a href="<c:url value="/application-service"/>"><img src="<c:url value="/resources/images/main/btn_faq_2-2.gif"/>" /></a>
</div>
</div>
</div>
</div>

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer3" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_3_q.gif"/>" /></a>
</div>
<div id="answer3" class="accordion-body collapse">
<div class="accordion-inner"><img src="<c:url value="/resources/images/main/img_faq_3_a.gif"/>" />
<div class="faq-link">
<a href="<c:url value="/application-service/register"/>"><img src="<c:url value="/resources/images/main/btn_faq_3.gif"/>" /></a>
</div>
</div>
</div>
</div>

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer4" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_4_q.gif"/>" /></a>
</div>    
<div id="answer4" class="accordion-body collapse">
<div class="accordion-inner"><img src="<c:url value="/resources/images/main/img_faq_4_a.gif"/>" />
<div class="faq-link">
<a href="<c:url value="/listChurch"/>"><img src="<c:url value="/resources/images/main/btn_faq_4.gif"/>" /></a>
</div>
</div>
</div>
</div>

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer5" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_5_q.gif"/>" /></a>
</div>    
<div id="answer5" class="accordion-body collapse">
<div class="accordion-inner"><img src="<c:url value="/resources/images/main/img_faq_5_a.gif"/>" />
<div class="faq-link">
<a href="<c:url value="/resources/SmartChurchAdminManual.pdf"/>"><img src="<c:url value="/resources/images/main/btn_faq_5.gif"/>" /></a>
</div>
</div>
</div>
</div>

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer6" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_6_q.gif"/>" /></a>
</div>    
<div id="answer6" class="accordion-body collapse">
<div class="accordion-inner"><img src="<c:url value="/resources/images/main/img_faq_6_a.gif"/>" />
<div class="faq-link">
<a href="http://laonsys.wordpress.com/2013/02/28/우리교회-웹에-설교-동영상-올리기/"><img src="<c:url value="/resources/images/main/btn_faq_6.gif"/>" /></a>
</div>
</div>
</div>
</div>

<div class="accordion-group">
<div class="accordion-heading">
<a class="accordion-toggle" href="#answer7" data-toggle="collapse" data-parent="#faq"><img src="<c:url value="/resources/images/main/img_faq_7_q.gif"/>" /></a>
</div>    
<div id="answer7" class="accordion-body collapse">
<div class="accordion-inner"><img src="<c:url value="/resources/images/main/img_faq_7_a.gif"/>" />
<div class="faq-link">
<a href="https://play.google.com/store?hl=ko&tab=w8"><img src="<c:url value="/resources/images/main/btn_faq_7-1.gif"/>" /></a><a href="http://appstore.com"><img src="<c:url value="/resources/images/main/btn_faq_7-2.gif"/>" /></a>
</div>
</div>
</div>
</div>

</div> 

</div>

</div>

</div>
<%--  <script>
$(function() {
	$(".collapse").collapse();
});
</script> --%>
</body>
</html>