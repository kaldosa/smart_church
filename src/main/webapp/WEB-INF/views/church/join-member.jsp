<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery/bootstrap/bootstrap.form.css">
<style type="text/css">
.join-member-box {background-color: #EAEAEA; border: 1px solid #DDDDDD; border-radius: 4px 4px 4px 4px; margin: 2px 0; padding: 39px 19px 14px; position: relative;}
.join-member-box:after {background-color: #F5F5F5; border: 1px solid #DDDDDD; border-radius: 4px 0 4px 0; color: #9DA0A4; font-size: 12px; font-weight: bold; left: -1px; padding: 3px 7px; position: absolute; top: -1px;}
.join-member-box:after {content: "교회멤버가입";}
.form-actions {border: none; border-top: 1px solid #cccccc; background-color: #EAEAEA;}
.recaptcha-desc {margin: 5px 0; color: #898989;}
.help-inline{display:inline-block;*display:inline;*zoom:1;vertical-align:middle;padding-left:5px;}
.error {color: #a33;}
#recaptcha_div {margin: 10px 0;}

</style>
</head>
<body>

<div class="container">
<div class="join-member-box"><c:url var="postUrl" value="/church/${churchId}/join-member"/>
  <form id="join-church-member-form" method="post" action="${postUrl}" class="form-horizontal">
    <div class="control-group">
      <div class="controls"><c:if test="${not empty captcha}"><span class="help-inline error">보안문자가 올바르지 않습니다.</span></c:if></div>
    </div>
    <div class="control-group">
      <label class="control-label">교회이름</label>
      <div class="controls">
        <span class="input-small uneditable-input">${ourChurch.churchMeta.name}</span>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" >담임목사</label>
      <div class="controls">
        <span class="input-small uneditable-input">${ourChurch.churchMeta.pastor}</span>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">사용자</label>
      <div class="controls"><sec:authentication property="principal" var="user" />
        <span class="input-small uneditable-input">${user.name}</span>
      </div>
    </div>
     <div class="control-group">
     <label class="control-label">보안문자</label>
       <div class="controls">
         <span class="recaptcha-desc">프로그램을 이용한 자동 가입을 막기 위해 보안문자를 받고 있습니다.</span>
         <div id="recaptcha_div"></div>
         <span class="recaptcha-desc">스팸성 게시글/메일을 이용할 목적으로 가입할 경우, 이용 제한 조치 및 법적인 처벌을 받을 수 있습니다.</span>
       </div>
     </div>
    <div class="form-actions">
    <button type="submit" class="btn btn-primary">가입하기</button>
    <a href="<c:url value="/church/${churchId}"/>" class="btn">취소</a>
    </div>
  </form>
</div>
</div>
<s:eval var="recaptcha_key" expression="@envvars['recaptcha.public']" />
<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>
<script type="text/javascript">
	$(function() {
		var recaptcha = '${recaptcha_key}';
		Recaptcha.create(recaptcha,
				"recaptcha_div", {
					theme : "red",
					callback : Recaptcha.focus_response_field
				});
		$('#join-church-member-form').submit(function() {
			var challenge = Recaptcha.get_challenge();
			var response = Recaptcha.get_response();
			if(challenge === '' || response === '') {
				alert('보안문자를 입력하세요');
				return false;
			} else {
				inputChallenge = jQuery('<input>',{name:'challenge',value:challenge,type:'hidden'});
				inputResponse = jQuery('<input>',{name:'response',value:response,type:'hidden'});
				inputChallenge.appendTo(this);
				inputResponse.appendTo(this);
				return true;
			}
		});
	});
</script>

</body>
</html>