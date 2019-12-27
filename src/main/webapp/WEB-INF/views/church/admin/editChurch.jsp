<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<style type="text/css">
div.contains {border: 1px solid #FBC2C4; border-radius: 5px 5px 5px 5px; color: #8A1F11; margin: 5px; padding: 10px 10px 10px 10px;}
div.contains ul {padding: 5px 15px;}
div.contains li {list-style-type: none; line-height: 16px;}
div.contains .error {font-size: .9em; color: #a33;}
form input.error {border-color: #ee0000;}
.form-control p {margin: 5px 0;}
</style>
</head>
<body>
<h2>교회 기본정보 수정</h2>
<div class="intro">
<div class="church_content">

<s:bind path="updateBean.*">
<div class="myInfo"><c:url var="updateUrl" value="/church/${ourChurch.id}/admin/editChurch"/>
<form:form id="update-form" modelAttribute="updateBean" action="${updateUrl}" method="put" enctype="multipart/form-data">
<fieldset>
<legend>교회정보수정</legend>

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="name" element="li" cssClass="error"/>
<form:errors path="pastor" element="li" cssClass="error"/>
<form:errors path="contact" element="li" cssClass="error"/>
<form:errors path="address" element="li" cssClass="error"/>
<form:errors path="traffic" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>

<div class="contains" style="display: none;"><ul></ul></div>

<div class="form-controls">
<label class="form-label" for="name">교회이름</label>
<div class="form-control">
<form:input id="name" path="name" maxlength="20" cssClass="input_text input_w150" />
</div>
</div>

<div class="form-controls">
<label class="form-label" for="pastor">담임목사</label>
<div class="form-control">
<form:input id="pastor" path="pastor" maxlength="20" cssClass="input_text input_w150" /> 목사
</div>
</div>

<div class="form-controls">
<label class="form-label" for="contact0">교회연락처</label>
<div class="form-control">
<input type="text" id="contact0" class="input_text input_w55" name="contact0" maxlength="4"> - <input type="text" id="contact1" class="input_text input_w55" name="contact1" maxlength="4"> - <input type="text" id="contact2" class="input_text input_w55" name="contact2" maxlength="4">
</div>
</div>

<div class="form-controls">
<label class="form-label" for="pastor">교회주소</label>
<div class="form-control">
<input type="text" id="addr1" name="addr1" class="input_text input_w350 uneditable-input" readonly="readonly"> <button id="find-zipcode" type="button" class="btn">주소검색</button>
<input type="text" id="addr2" name="addr2" placeholder="나머지주소" class="input_text input_w350" maxlength="20">
</div>
</div>

<div class="form-controls">
<label class="form-label" for="traffic">오시는 길</label>
<div class="form-control">
<textarea id="traffic" name="traffic" rows="3" style="width: 500px; resize: none;" maxlength="250" wrap="hard"></textarea>
</div>
</div>
                    
<div class="form-buttons">
<button type="submit" class="btn btn-inverse btn-large">수정</button>
<a href="<c:url value="/church/${ourChurch.id}/admin"/>" class="btn btn-large">취소</a>
</div>

<form:hidden id="contact" path="contact"/>
<form:hidden id="address" path="address"/>
</fieldset>
</form:form>
</div>
</s:bind>

</div>

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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/admin/editChurch"/>">교회정보 수정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/admin/editMain"/>">메인 이미지 수정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/admin/members"/>">교회멤버 관리</a></li>
</ul>

</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->

</div>

<script type="text/javascript">
    var phone = $('#contact').val().split('-');
    
    $('#contact0').val(phone[0]);
    $('#contact1').val(phone[1]);
    $('#contact2').val(phone[2]);
    
    $('#addr1').val($('#address').val());
    /* $('#addr2').hide(); */
    
    $('#update-form').validate({
        debug: true,
    	submitHandler: function(form) {
            $('#contact').val($('#contact0').val() + '-' + $('#contact1').val() + '-' + $('#contact2').val());
            $('#address').val($('#addr1').val() + ' ' + $('#addr2').val());

            form.submit();
        },
        errorContainer: ".contains",
        errorLabelContainer: ".contains ul",
        wrapper: "li",
        rules: {
        	'name' : {
                required: true,
                kor: true,
                rangelength: [1, 20]
            },
            'pastor' : {
                required: true,
                kor: true,
                rangelength: [1, 20]
            },
            'contact0' : {
                required: true,
                number: true,
                rangelength: [2, 4]
            },
            'contact1' : {
                required: true,
                number: true,
                rangelength: [3, 4]
            },
            'contact2' : {
                required: true,
                number: true,
                rangelength: [4, 4]
            },
            'addr1': {
                required: true
            },
            'traffic': {
                maxlength: 250
            }
        },
        messages: {
            'name': {
            	kor: '교회명은 한글만 입력 가능합니다.(공백 없이 입력해 주세요. 예: 우리교회)',
            	minlength: '교회명은 최소 한글자 이상 입력하셔야 합니다.'
            },
            'pastor': {
            	kor: '담임목사명은 한글만 입력 가능합니다. (공백 없이 입력해 주세요. 예: 홍길동)',
            	minlength: '담임목사명은 최소 두글자 이상 입력하셔야 합니다.'
            },
            'contact0': {
                required: "연락처를 입력하세요."
            },
            'contact1': {
                required: "연락처를 입력하세요."
            },
            'contact2': {
                required: "연락처를 입력하세요."
            },
            'addr1': {
                required: "교회주소를 입력하세요."
            },
            'traffic': {
                maxlength: '오시는 길 내용은 250이내로 입력하세요.'
            }
        }
    });

    $('#find-zipcode').click(function() {
        window.open('<c:url value="/popup/zipcode"/>', 'popup', 'width=420, height=380, toolbar=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes');
    });
</script>    
</body>
</html>