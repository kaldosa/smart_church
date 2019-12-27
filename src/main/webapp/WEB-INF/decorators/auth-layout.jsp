<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<link rel="icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico"/>">
<title><decorator:title default="Smart Church" /></title>
<style type="text/css">
/*<![CDATA[*/

@import url("<c:url value='/resources/js/jquery/bootstrap/bootstrap.min.css' />");

@import url("<c:url value='/resources/css/layout.css'  />");

@import url("<c:url value='/resources/css/header.css'  />");

@import url("<c:url value='/resources/css/footer.css'  />");

@import url("<c:url value='/resources/js/jquery/ui/themes/redmond/jquery-ui-1.9.1.custom.css'  />");
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
    <div id="wapper">
        <page:applyDecorator name="header" page="/WEB-INF/decorators/header-nologin.jsp" />
        <hr class="separator" />
        <decorator:body />
        <hr class="separator" />
        <page:applyDecorator name="footer" page="/WEB-INF/decorators/footer.jsp" />
    </div>
</body>
</html>