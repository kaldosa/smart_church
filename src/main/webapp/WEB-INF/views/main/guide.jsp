<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html">
<html lang="ko">
<head>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="page-title"><img src="<c:url value="/resources/images/main/img_guide_title.gif"/>" /></div>
<div class="page-content">
<img src="<c:url value="/resources/images/main/img_guide.gif"/>" useMap="#email"/>
<map name="email" id="email">
  <area shape="rect"  coords="255,520,550,560" href="mailto:smartchurch@laonsys.com">
</map>
</div>
<div class="application-link"><a href="<c:url value="/application-service"/>"><img src="<c:url value="/resources/images/main/btn_application.gif"/>" /></a></div>

</div>

</div>

</body>
</html>