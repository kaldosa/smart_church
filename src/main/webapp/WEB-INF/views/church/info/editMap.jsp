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
<h3>오시는길</h3>
<p class="home">Home &gt; 교회안내 &gt; <span>오시는길</span></p>
</div>

<s:bind path="church.*">
<c:url var="updateUrl" value="/church/${ourChurch.id}/map" />
<form:form name="mapForm" action="${updateUrl}" method="put" id="update-form" modelAttribute="church">
<fieldset><legend>오시는길 폼</legend>

<div id="writeWrap">


<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="address1" element="li" cssClass="error"/>
<form:errors path="phoneVO.phone0" element="li" cssClass="error"/>
<form:errors path="phoneVO.phone1" element="li" cssClass="error"/>
<form:errors path="phoneVO.phone2" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>

<table class="mapBox_admin" summary="주소정보 입력 테이블"><tbody>

<tr>
<th scope="row"><label for="address">주소</label></th>
<td>
<p>
<input type="text" id="zipCd1" name="zipcode0" class="input_text input_w35" type="text" maxlength="3" readonly="readonly"/>
<span class="bar">-</span>
<input type="text" id="zipCd2" name="zipcode1" class="input_text input_w35" type="text" maxlength="3" readonly="readonly"/>
<span style="padding-left:8px;"><a id="findZipcode" href="#"><img src="<c:url value="/resources/images/church/common/btn_zip.gif" />" alt="우편번호 찾기" width="71" height="20" class="button_zip"/></a></span>
</p>
<p><form:input path="address1" id="addr1" cssClass="input_text input_w500" readonly="true" /></p>
<p><form:input path="address2" id="addr2" cssClass="input_text input_w500" /></p>
</td>
</tr>

<tr class="phoneNumber">
<th scope="row"><label for="phone1">전화번호</label></th>
<td>
<input type="text" id="church-phone0" name="phone0" maxlength="4" class="input_text input_w60" />
<span class="bar">-</span>
<input type="text" id="church-phone1" name="phone1" maxlength="4" class="input_text input_w60" />
<span class="bar">-</span>
<input type="text" id="church-phone2" name="phone2" maxlength="4" class="input_text input_w60" />
</td>
</tr>

<tr class="phoneNumber">
<th scope="row"><label for="fax0">팩스</label></th>
<td>
<input type="text" id="church-fax0" name="fax0" maxlength="4" class="input_text input_w60" />
<span class="bar">-</span>
<input type="text" id="church-fax1" name="fax1" maxlength="4" class="input_text input_w60" />
<span class="bar">-</span>
<input type="text" id="church-fax2" name="fax2" maxlength="4" class="input_text input_w60" />
</td>
</tr>

<tr>
<th scope="row"><label for="traffic"></label>대중교통</th>
<td><form:textarea path="traffic" id="traffic" cssClass="input_textarea input_w500" /></td>
</tr>

</tbody></table>

<!-- 버튼 -->
<div class="list_btn_cen2">
    <input type="image" name="btnSubmit" id="btnSubmit" src="<c:url value="/resources/images/church/common/btn_save4.gif" />" alt="등록"  style="border-width:0px;visibility:visible;" />
    <a href="<c:url value="/church/${ourChurch.id}/map"/>"><img src="<c:url value="/resources/images/church/common/btn_cancel5.gif" />" alt="취소" width="87" height="38" class="space" /></a>
</div><!--// 버튼 -->

</div>

<form:hidden id="zipcode" path="zipcode" />
<form:hidden id="churchContacts" path="contact" />
<form:hidden id="churchFax" path="fax" />
</fieldset>
</form:form>
</s:bind>

<p class="pd30"></p>

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
<li><a href="<c:url value="/church/${ourChurch.id}/serve"/>">섬기는분들</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/map"/>">오시는길</a></li>
</ul>
</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div><!-- intro -->

<div id="validation-dialog"><div class="contains"><ul></ul></div></div>
<script type="text/javascript">
$(function() {

    var phone = $('#churchContacts').val().split('-');
    
    $('#church-phone0').val(phone[0]);
    $('#church-phone1').val(phone[1]);
    $('#church-phone2').val(phone[2]);
    
    if($('#churchFax').val() != '') {
        var fax = $('#churchFax').val().split('-');
        
        $('#church-fax0').val(fax[0]);
        $('#church-fax1').val(fax[1]);
        $('#church-fax2').val(fax[2]);
    }
    
    var zipcode = $('#zipcode').val().split('-');
    
    $('#zipCd1').val(zipcode[0]);
    $('#zipCd2').val(zipcode[1]);
    
    $('#update-form').validate({
        submitHandler: function(form) {
            $('#churchContacts').val($('#church-phone0').val() + "-" + $('#church-phone1').val() + "-" + $('#church-phone2').val());
            $('#zipcode').val($('#zipCd1').val() + "-" + $('#zipCd2').val());
            
            if($('#church-fax0').val() != "" && $('#church-fax1').val() != "" && $('#church-fax2').val() != "") {
                $('#churchFax').val($('#church-fax0').val() + "-" + $('#church-fax1').val() + "-" + $('#church-fax2').val());
            }
            form.submit();
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
        	'phone0': {
                required: true,
                number: true,
                maxlength: 4
            },
            'phone1': {
                required: true,
                number: true,
                maxlength: 4
            },
            'phone2': {
                required: true,
                number: true,
                maxlength: 4
            },            
            'fax0': {
                number: true,
                maxlength: 4
            },
            'fax1': {
                number: true,
                maxlength: 4
            },
            'fax2': {
                number: true,
                maxlength: 4
            }, 
            'address1': {
                required: true
            },
            'traffic': {
            	htmlTag: true
            }
        },
        messages: {
            'phone0': {
                required: "연락처를 입력하세요."
            },
            'phone1': {
                required: "연락처를 입력하세요."
            },
            'phone2': {
                required: "연락처를 입력하세요."
            },
            'address1': {
                required: "교회 주소를 입력하세요."     
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

    $("#findZipcode").click(function() {
    	window.open('<c:url value="/popup/zipcode"/>', 'popup', 'width=420, height=380, toolbar=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes');
    });
});
</script>
</body>
</html>