<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/jquery/cleditor/jquery.cleditor.css" />" />
<title>Insert title here</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="board-wrap">
<s:bind path="entity.*">

<c:url var="updateUrl" value="/notices/${entity.id}" />
<form:form id="update-form" action="${updateUrl}" method="put" modelAttribute="entity">

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="subject" element="li" cssClass="error"/>
<form:errors path="content" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>

<div class="board">
<div class="board-subject">
<label>제목</label><form:input path="subject" cssClass="input_text input_w650" maxlength="50" title="제목입력" />
</div>

<div class="board-content"><form:textarea path="content" rows="20" cols="80" id="conts" cssClass="input_textarea" /></div>

<div class="board-extra">
<div class="board-btn-right">
<input type="image" alt="수정" src="<c:url value="/resources/images/main/btn_save.png" />" />
<a href="<c:url value="/notices"/>"><img width="39" height="20" alt="취소" src="<c:url value="/resources/images/main/btn_cancelsave.png" />" /></a>
</div>
</div>
</div>
</form:form>
</s:bind>
</div>

</div>

</div>

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/cleditor/jquery.cleditor.min.js" />"></script>
<script type="text/javascript">
$(function() {
    var editor = $("#conts").cleditor({
        width : 724,
        height : 370
    });
    
    $('#update-form').validate({
        submitHandler: function(form) {
           var contents = editor[0].$area[0].value;

            if (contents == "" || contents == "<br>") {
                contents = editor[0].$area[0].value = '';
                $(".validate-container ul").append('<li class="error">내용을 입력하세요.</li>');
                $(".validate-container ul").show();
                $("#validation-dialog").dialog('open');
                return false;
            } 
            
            form.submit();
        },
        errorLabelContainer: ".validate-container ul",
        wrapper: "li",
        errorPlacement: function(error, element) {
            error.appendTo(".validate-container ul");
        },
        invalidHandler: function() {
            $("#validation-dialog").dialog('open');
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'subject': {
                required: true,
                maxlength: 50,
                htmlTag: true
            }
        },
        messages: {
            'subject': {
                required: "제목를 입력해주세요."
            }
        }
    });
    
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
    $("#validation-dialog").dialog({
        autoOpen:false,
        modal:true,
        show: 'fade',
        hide: 'fade',
        resizable: false,
        close: function(event, ui) {
            $('.validate-container ul').children().remove();
        }
    });
});
</script>
</body>
</html>