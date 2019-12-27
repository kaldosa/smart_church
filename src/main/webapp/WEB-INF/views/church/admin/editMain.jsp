<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="<c:url value="/resources/js/jquery/blueimp/fileupload/css/jquery.fileupload-ui.css"/>">
<noscript><link rel="stylesheet" href="<c:url value="/resources/js/jquery/blueimp/fileupload/css/jquery.fileupload-ui-noscript.css"/>"></noscript>
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<style type="text/css">
div.contains {border: 1px solid #FBC2C4; border-radius: 5px 5px 5px 5px; color: #8A1F11; margin: 5px; padding: 10px 10px 10px 10px;}
div.contains ul {padding: 5px 15px;}
div.contains li {list-style-type: none; line-height: 16px;}
div.contains .error {font-size: .9em; color: #a33;}
form input.error {border-color: #ee0000;}

.main-image { width:110px;height:110px; position:relative; }
.mask { display:none;  
  position: absolute;
  bottom: 0;
  right: 0;
  padding: 5px 5px;
  height: 15px !important; 
  width: 110px !important;
  background: #000;
  opacity: 0.7;
  zoom:1;
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=70)";
  filter: alpha(opacity=70);
  text-align: right;
  *overflow: hidden; }
.main-image:hover .mask { display:block; }

.fileuploader-span, .alert-span {width: 660px;}
.files .progress {width: 100%;}
.btn-primary > span {color: #fff;}
</style>
</head>
<body>
<h2>교회 로고 및 메인 이미지 수정</h2>
<div class="intro">
<div class="church_content">

<div class="myInfo">
<c:url value="/church/${ourChurch.id}/admin/editLogo" var="editLogoUrl"/>
<form:form id="logo-update-form" action="${editLogoUrl}" method="POST" modelAttribute="logoFormBean" enctype="multipart/form-data">
<fieldset>
<legend>교회 로고 변경</legend>
</fieldset>

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="file" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>

<div class="form-controls">
<label class="form-label" for="logo">교회로고 변경</label>
<div class="form-control">
<input type="file" id="logo" name="file"> <button type="submit" class="btn btn-primary">변경</button>
</div>
</div>

</form:form>
</div>

<div class="myInfo">
<c:url value="/church/${ourChurch.id}/admin/editMain" var="editUrl"/>
<form id="fileupload" action="${editUrl}" method="POST" enctype="multipart/form-data">
<fieldset>
<legend>교회 메인이미지 변경</legend>

<div id="limit-photo" class="alert alert-error alert-block">
<h4 class="alert-heading">Error!</h4>
<div class="error-container"><span>최대 5장까지만 허용됩니다.</span></div>
</div>

<div class="form-controls">
<ul class="thumbnails">
<c:if test="${not empty mainImages}">
<c:forEach var="photo" items="${mainImages}">
<li>
<div class="thumbnail main-image">
<img src="${photo.path}" width="110" height="110">
<div class="mask"><a href="<c:url value="/church/${ourChurch.id}/admin/editMain/${photo.id}"/>" class="main-delete"><i class="icon-trash icon-white"></i></a></div>
</div>
</li>
</c:forEach>
</c:if>
</ul>
</div>


<div class="form-controls">

<div class="album-uploader">
    <div class="fileupload-buttonbar" style="text-align: right;">
        <div class="fileuploader-span">
            <span class="btn btn-primary fileinput-button">
                <i class="icon-plus icon-white"></i>
                <span>사진 추가...</span>
                <input type="file" name="files" multiple>
            </span>
            <button type="submit" class="btn btn-primary start">
                <i class="icon-upload icon-white"></i>
                <span>전송</span>
            </button>
            <button type="reset" class="btn cancel">
                <i class="icon-ban-circle "></i>
                <span>취소</span>
            </button>
            <button type="button" class="btn delete">
                <i class="icon-trash "></i>
                <span>삭제</span>
            </button>
            <input type="checkbox" class="toggle">
        </div>
    </div>
     <div class="fileuploader-span fileupload-progress fade">
         <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
             <div class="bar" style="width:0%;"></div>
         </div>
         <div class="progress-extended">&nbsp;</div>
     </div>
    <div class="fileupload-loading"></div>
    <br>
    
    <table role="presentation" class="table table-striped">
    <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody>
    </table>
</div>
</div>

</fieldset>
<%-- </form:form> --%>
</form>
</div>

<div class="row">
<div class="span9" style="margin: 20px; text-align: center">
    <a href="<c:url value="/church/${ourChurch.id}/admin"/>" class="btn btn-large">확인</a>
</div><!--// 버튼 -->
</div>

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
<li><a href="<c:url value="/church/${ourChurch.id}/admin/editChurch"/>">교회정보 수정</a></li>
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/admin/editMain"/>">메인 이미지 수정</a></li>
<li><a href="<c:url value="/church/${ourChurch.id}/admin/members"/>">교회멤버 관리</a></li>
</ul>

</div><!-- rnb -->
</div><!--// 오른쪽 프로필 및 서브메뉴 -->


</div>

<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td class="preview"><span class="fade"></span></td>
        <td><span>{%=file.name%}</span><br><span>{%=o.formatFileSize(file.size)%}</span>
            <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
        </td>
        {% if (file.error) { %}
            <td class="error break-word"><span class="label label-important">Error</span> {%=file.error%}</td>
            <td></td>
        {% } else if (o.files.valid && !i) { %}
            <td></td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <i class="icon-upload icon-white"></i>
                    <span>전송</span>
                </button>
            {% } %}</td>
        {% } else { %}
            <td colspan="2"></td>
        {% } %}
        <td class="cancel">{% if (!i) { %}
            <button class="btn">
                <i class="icon-ban-circle "></i>
                <span>취소</span>
            </button>
        {% } %}</td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
   {% if (file.error) { %}
    <tr class="template-download fade">
            <td colspan="2"><span>{%=file.name%}</span><br><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error break-word" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
        <td class="delete">
            <button class="btn" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                <i class="icon-trash"></i>
                <span>삭제</span>
            </button>
            <input type="checkbox" name="delete" value="1">
        </td>
    </tr>
   {% } %}
{% } %}
</script>
<script src="<c:url value="/resources/js/jquery/blueimp/image_gallery/load-image.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/image_gallery/canvas-to-blob.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/tmpl.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.iframe-transport.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.fileupload.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.fileupload-fp.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.fileupload-ui.js"/>"></script>
<!--[if gte IE 8]><script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.xdr-transport.js"/>"></script><![endif]-->
<script type="text/javascript">
$(function() {
	$('#limit-photo').hide();
	
    $('#fileupload').fileupload({
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        previewMaxWidth: 60,
        previewMaxHeight: 60,
        paramName: 'file',
    }).bind('fileuploadsent', function(e, data) {
    	var files = data.originalFiles.length;
    	var remained = $('.thumbnails').children().length;
        var available = remained + files;
    	if(available > 5) {
            $('#limit-photo').show('fold', 300, function() {
                setTimeout(function() {
                	$('#limit-photo:visible').removeAttr("style").fadeOut();
                }, 1500);
            });    		
    		return false;
    	}
    }).bind('fileuploadcompleted', function (e, data) {
    	$.each(data.result, function(i, o) {
    		$('.thumbnails').append('<li><div class="thumbnail main-image"><img src="' + o.url + '" width="110" height="110">' +
    				'<div class="mask"><a href="' + o.delete_url + '" class="main-delete"><i class="icon-trash icon-white"></i></a></div></div></li>');
    	});
    });
    
    $('.main-delete').live('click', function (e) {
    	e.preventDefault();
    	$.ajax({
    		url: $(this).attr('href'),
    		type: 'DELETE',
    		context: $(this),
    		success: function(data) {
    		    if(data.result) {
                	$(this).parent().parent().parent().remove();
    		    } else {
    		    	alert('result false');
    		    }
    		}
    	});
    });
});
</script>
</body>
</html>