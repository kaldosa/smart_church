<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<style type="text/css">
body {background-color:#4c78b9; width: 100%; margin-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px;}
#wrap {width:1034px; margin: 0px auto;}
</style>
<title>브라우저를 업데이트하세요.</title>
</head>
<body>
<div id="wrap">
<img src="<c:url value="/resources/images/img_update_browser.png"/>" width="1034" height="806" border="0" useMap="#browsers"/>
<map name="browsers" id="browsers">
  <area shape="rect"  coords="238,448,362,570" href="http://www.google.com/chrome">
  <area shape="rect" coords="390,447,516,570" href="http://www.mozilla.org/firefox">
  <area shape="rect" coords="538,442,665,573" href="http://www.apple.com/safari/">
  <area shape="rect" coords="687,443,809,571" href="http://www.opera.com">
  <area shape="rect" coords="898,616,1000,782" href="http://www.microsoft.com/internetexplorer">
</map>
</div>
</body>
</html>