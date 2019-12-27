<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Insert title here</title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="board-wrap">

<h2>1 : 1 문의하기</h2>
<s:bind path="entity.*">

<c:url var="contactUsUrl" value="/contactUs" />
<form:form id="create-form" action="${contactUsUrl}" method="post" modelAttribute="entity">

<div class="board">
<div class="board-subject">
<label>제목</label><form:input path="subject" cssClass="input_text input_w650" maxlength="50" title="제목입력" />
</div>

<div class="board-content"><form:textarea path="contents" rows="12" cols="120" id="conts" cssClass="input_textarea" cssStyle="width: 716px;"/></div>

<div class="board-extra">
<div class="board-btn-right">
<button class="btn">문의하기</button>
<a href="<c:url value="/"/>" class="btn">취소</a>
</div>
</div>
</div>
</form:form>
</s:bind>

</div>

</div>

</div>

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>

<script type="text/javascript">
$(function() {
	
    var options = {
            dataType: 'json',
            success: function(data) {
              alert(data.msg);
            	
              if(data.result) {
            	   window.location.replace('<c:url value="/"/>');	
            	}
            }
        };
    
    $('#create-form').validate({
        submitHandler: function(form) {
            $(form).ajaxSubmit(options);
            return false;
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