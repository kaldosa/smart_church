<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Test Main Page</title>
</head>
<body>
<div id="contents">

    <c:url var="postUrl" value="/test"/>
    <form action="${postUrl}" method="post" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit">
    </form>
</div>
</body>
</html>