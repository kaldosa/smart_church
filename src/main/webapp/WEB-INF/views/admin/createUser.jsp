<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
/* <[CDATA[ */
$(function() {
    $('#createUserForm').validate({
        errorLabelContainer: $("div.contains")
    });
});
/* ]]> */
</script>
</head>
<body>
    <div id="contents">
        <fieldset class="ui-widget ui-widget-content ui-corner-all">
            <div class="contains" style="display: none;"></div>
            <legend class="ui-widget ui-widget-header ui-corner-all">Customer Create</legend>
            <s:form id="createUserForm" action="createUser!create" theme="xhtml">
                <s:textfield name="user.email" label="USER-ID" title="Please enter your USER-ID (at least 3 characters)" cssClass="required ui-widget-content" />
                <s:password name="user.password" label="PASSWORD" title="Please enter your PASSORD (at least 3 characters)" cssClass="required ui-widget-content" />
                <s:checkboxlist name="authorities" list="authorities" label="AUTHORITY" value="" />
                <s:submit value="Submit" cssClass="submit" />
            </s:form>
        </fieldset>
    </div> 
</body>
</html>
