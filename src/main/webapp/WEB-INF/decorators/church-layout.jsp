<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!ajaxRequest}">
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8"/>
<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico"/>">
<title>${ourChurch.churchMeta.name} 홈페이지</title>
<style type="text/css">
/*<![CDATA[*/
@import url("<c:url value='/resources/js/jquery/ui/themes/redmond/jquery-ui-1.9.1.custom.css'  />");

@import url("<c:url value='/resources/css/church-layout.css' />");

@import url("<c:url value='/resources/css/church-header.css' />");

@import url("<c:url value='/resources/css/church-footer.css' />");

@import url("<c:url value='/resources/js/jquery/bootstrap/bootstrap.css' />");
/*]]>*/
</style>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-1.8.2.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/form/jquery.form.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery.tools.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/validation/jquery.validate.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/validation/additional-methods.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/validation/messages_ko.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery.commons.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/ui/jquery-ui-1.9.1.custom.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery/bootstrap/bootstrap.min.js' />"></script>
<decorator:head />
</head>
<body>
<page:applyDecorator name="header" page="/WEB-INF/decorators/church-header.jsp" />
</c:if>
<div id="container">
    <div id="content">
        <decorator:body />
    </div>
</div>
<c:if test="${!ajaxRequest}">
<page:applyDecorator name="footer" page="/WEB-INF/decorators/church-footer.jsp" />
<script type="text/javascript">
/* <[CDATA[ */
$(function() {
    if ( $.browser.msie && parseInt($.browser.version, 10) < 9) {
        window.location.href = '<c:url value="/updateBrowser"/>';     
    }
});
/* ]]> */
</script>
</body>
</html>
</c:if>