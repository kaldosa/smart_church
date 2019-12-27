<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery/bootstrap/bootstrap.form.css">
<style type="text/css">
.denied {margin: 20px 0;}
.denied-msg {font-size: 14px; color: #666; line-height: 20px;}
.denied-link {margin: 55px 0 10px; text-align: center;}
</style>
<title>${ourChurch.churchMeta.name}</title>
</head>
<body>
<div class="error_wrap" >
<div class="error_box">
<h1>Access denied!</h1>
<div class="denied">
<p class="denied-msg">해당 콘덴츠에 대한 접근 권한이 없습니다. <br/>
${ourChurch.churchMeta.name} 멤버가 아니시면 멤버 가입 먼저 하셔야합니다.</p>
<div class="denied-link"><button id="join-member" class="btn btn-primary">멤버가입</button> <button id="cancel" class="btn">취소</button>
</div>
</div>
</div>
</div>

<script type="text/javascript">
$('#join-member').click(function() {
	window.location.replace('<c:url value="/church/${churchId}/join-member"/>');
});

$('#cancel').click(function() {
    window.location.replace('<c:url value="/church/${churchId}"/>');
});
</script>

</body>
</html>