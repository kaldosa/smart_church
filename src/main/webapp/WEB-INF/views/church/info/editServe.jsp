<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
</head>
<body>
<h2>교회안내</h2>
<div class="intro">

<!-- 본문 -->
<div class="church_content">

<div id="titleWrap">
<h3>섬기는분들</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>섬기는분들</span></p>
</div>
    
<div id="writeWrap">

<div class="admin_form"><c:url var="createUrl" value="/church/${ourChurch.id}/serve" />
<form:form name="serverForm" action="${createUrl}" method="post" id="create-form" modelAttribute="server" enctype="multipart/form-data">        
<fieldset>
<legend>섬기는분들 폼</legend>
<div class="admin_form_input">
<label class="admin_form_label" for="server_position">직분</label><form:input id="server_position" path="position" cssClass="input_text input_w85" maxlength="10"/>
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="server_name">이름</label>
<form:input id="server_name" path="name" cssClass="input_text input_w85" maxlength="10"/>
</div>

<div class="admin_form_input">
<label class="admin_form_label" for="server_photo">사진</label>
<input type="text" id="fileName" class="file_input_textbox input_text input_w150" readonly="readonly" />
<div class="file_input_div">
<img class="file_input_button" src="<c:url value="/resources/images/commons/file_select.gif" />" alt="Browse" />
<input type="file" class="file_input_hidden" onchange="javascript: document.getElementById('fileName').value = this.value" id="server_photo" name="upload" />
</div>
</div>

<div class="admin_form_textarea">
<label class="admin_form_label label_intro" for="server_intro">소개</label>
<form:textarea id="server_intro" cssClass="input_textarea" path="intro" />
<input class="admin_form_btn textarea_btn" type="image" src="<c:url value="/resources/images/church/common/btn_save3.gif" />" alt="저장"/>
</div>

<form:hidden path="path" value="${ourChurch.path}" />
</fieldset>
</form:form>
</div>

<c:url var="updateUrl" value="/church/${ourChurch.id}/serve"/>
<form:form id="update-form" action="${updateUrl}" method="put">
<div class="result_list sortable">

<c:forEach var="server" items="${servers}" varStatus="status">
<table class="serve_table" summary="섬기는분들 테이블" id="table-${server.id}">
<tbody>
<tr>
    <th>직분</th>
    <td class="input_w100">${server.position}</td>
    <th>이름</th>
    <td class="input_w100">${server.name}</td>
    <th>사진</th>
    <td class="input_w60"><a class="server_photo" href="<c:url value="${server.photo.path}" />"><img src="<c:url value="/resources/images/church/common/icon_file3.gif"/>" alt="사진" /></a></td>
</tr>
<tr>
    <th>소개</th>
    <td colspan="4" class="none"><s:eval expression="server.intro.replaceAll('\r\n', '<br/>')"/></td>
    <td><img id="${server.id}" class="del-item" src="<c:url value="/resources//images/church/common/btn_delete.gif" />" alt="삭제" width="52" height="24"/></td>
</tr>
</tbody>
</table>
</c:forEach>

</div><!--// result_list -->

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <button type="submit" class="btn btn-inverse btn-large">완료</button>
    <a href="<c:url value="/church/${ourChurch.id}/serve"/>" class="btn  btn-large">취소</a>
</div><!--// 버튼 -->
</div>

</form:form>

</div><!--// writeWrap -->

<p class="pd10"></p>

</div><!--// 본문 -->


<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">

<sec:authorize ifAnyGranted="ROLE_USER">
<div class="profile-wrap">

<div class="profile">
<sec:authentication property="principal" var="user" />
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="프로필 사진" width="50" height="50" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/commons/img_default_profile.gif" />" alt="댓글 썸네일" width="50" height="50" /></c:otherwise>
</c:choose>

<div class="profile-info">
<p class="user-name"><span><strong>${user.name}</strong> 님</span></p>
<a href="<c:url value="/auth/confirmPw"/>">내정보</a>
</div>

<div class="logout-form">

<s:eval var="logoutUrl" expression="@envvars['web.logout']" />
<form name="logoutForm" method="post" action="${logoutUrl}">
<input type="hidden" name="spring-security-redirect" value="/church/${ourChurch.id}" />
<input type="image" src="<c:url value="/resources/images/church/common/btn_logout2.gif" />" alt="로그아웃" class="logout" />
</form>

</div>

</div>

</div>
</sec:authorize>

<div class="rnb">
<ul>
<li><a href="<c:url value="/church/${ourChurch.id}/intro"/>">인사말</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/history"/>">교회연혁</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/worship"/>">예배안내</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>
</div><!-- rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->
<div id="validation-dialog"><div class="contains"><ul></ul></div></div>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/cal/jquery.qtip-1.0.0-rc3.min.js"/>"></script>
<script type="text/javascript">

$(function() {
	
    $( ".sortable" ).sortable({
        revert: true,
        cursor: "move",
        scroll: true
    });
    
    $('table, th, tr, td').disableSelection();
    
    var options = {
        dataType: 'json',
        success: function(data) {
            $('#create-form').resetForm();
            if(data != null) {
                $( ".result_list" ).prepend('<table class="serve_table" summary="섬기는분들 테이블" id="table-' + data.id + '">'
                    + '<tbody><tr>'
                    + '<th>직분</th>'
                    + '<td class="input_w100">' + data.position + '</td>'
                    + '<th>이름</th>'
                    + '<td class="input_w100"> ' + data.name + '</td>'
                    + '<th>사진</th>'
                    + '<td class="input_w60"><a class="server_photo" href="' + data.photo.path + '"><img src="<c:url value="/resources/images/church/common/icon_file3.gif"/>" alt="사진" /></a></td>'
                    + '<tr><th>소개</th><td colspan="4" class="none">' + data.intro + '</td>'
                    + '<td><img id="' + data.id + '" class="del-item" src="<c:url value="/resources/images/church/common/btn_delete.gif" />" alt="삭제" width="52" height="24"/></td></tr>'
                    + '</tbody></table>');
            } else {
                $('.contains ul').append('<li><label class="error">입력 정보가 유효하지 않습니다.</label></li>');
                $('.contains ul').show();
                $("#validation-dialog").dialog('open');
            }
            return false;
        }
    };
        
    $('#create-form').validate({
        submitHandler: function(form) {
            $(form).ajaxSubmit(options);
            return false;
        },
        errorLabelContainer: ".contains ul",
        wrapper: "li",
        errorPlacement: function(error, element) {
            error.appendTo("#validation-dialog ul");
        },
        invalidHandler: function() {
            $("#validation-dialog").dialog('open');
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'position': {
                required: true,
                maxlength: 10,
                htmlTag: true
            },
            'name': {
                required: true,
                maxlength: 10,
                htmlTag: true
            },
            'intro': {
                required: true,
                maxlength: 255,
                htmlTag: true
            },
            'upload': {
            	required: true,
            	accept: 'jpg|jpeg|gif|png'
            }
        },
        messages: {
            'position': {
                required: '직분을 입력하세요.'
            },
            'name': {
                required: '이름을 선택하세요.'
            },
            'intro': {
                required: '소개를 입력하세요.'
            },
            'upload': {
                required: '사진을 추가하세요.'
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
    });
    
    $('.del-item').live('click', function() {
    	if(!confirm("삭제하시겠습니까?")) {
    		return false;
    	}
    	
        var id = $(this).attr("id");
        $.ajax({
            url: '<c:url value="/church/${ourChurch.id}/serve/' + id + '" />',
            type: 'DELETE'
        });
        
        $('#table-' + id).remove();
        return false;
    });
    
    $('#update-form').submit(function() {
        var result = $( ".sortable" ).sortable('toArray');
        if(result.length > 0) {
            $.each(result, function(i, val) {
                var element = $('<input type="hidden" name="order" />');
                element.val(val.split("-")[1]);
                element.appendTo($('#update-form'));
            });
        } else {
            return false;
        }
    });
    
    $('.server_photo').live('mouseover', function(event) {

        var thumbnail = $('<img />').attr({
            src: $(this).attr('href'),
            alt: 'Loading thumbnail...',
            width: 120,
            height: 80
        });
    	
        $(this).qtip({
            content: thumbnail,
            show: {
                event: event.type,
                ready: true
             },            
            position: {
                corner: {
                    tooltip: 'bottomLeft',
                    target: 'topMiddle'
                }
            },
            style: {
                tip: true,
                name: 'light'
            }
        });    	
    });
    	        
});
</script>
</body>
</html>