<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>관리자 페이지</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="contents-wrap">
<div class="row content-top">
<div class="span12">
<div class="content-title"><h3>관리자</h3></div>
<div class="list">
<ul class="list">
<li><a href="<c:url value="/admin/users" />">회원 목록</a></li>
<li><a href="<c:url value="/admin/qnas" />">질문과답변 게시판</a></li>
<li><a href="<c:url value="/admin/services" />">우리교회 서비스 관리</a></li>
</ul>
</div>

<h3>스토리지 폴더 생성</h3>
<form action="${pageContext.request.contextPath}/admin/make-folder" method="post">
  <input type="text" name="path" /> <input type="submit" />
</form>

<h3>스토리지 폴더 삭제</h3>
<form action="${pageContext.request.contextPath}/admin/del-folder" method="post">
  <input type="text" name="path" /> <input type="submit" />
</form>

<h3>encoding</h3>
<form id="pw-encoding" action="${pageContext.request.contextPath}/admin/pw-encoding" method="post">
  <input type="text" name="email" /> <input type="text" name="password"/> <input type="submit" />
</form>

</div>
</div>
</div>
</div>

<script type="text/javascript">
$(function() {
    
    var options = {
            dataType: 'json',
            success: function(data) {
              alert(data.result);
            }
        };
    
    $('#pw-encoding').submit(function() {
        this.ajaxSubmit(options);
        return false;
    });
});
</script>

</body>
</html>