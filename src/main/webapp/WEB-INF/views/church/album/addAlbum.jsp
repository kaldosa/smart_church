<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="<c:url value="/resources/js/jquery/blueimp/image_gallery/css/bootstrap-image-gallery.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/js/jquery/blueimp/fileupload/css/jquery.fileupload-ui.css"/>">
<noscript><link rel="stylesheet" href="<c:url value="/resources/js/jquery/blueimp/fileupload/css/jquery.fileupload-ui-noscript.css"/>"></noscript>
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<style type="text/css">
.album-uploader {margin-top: 30px;}
.fileuploader-span, .alert-span {width: 660px;}
.files .progress {width: 100%;}
.btn-primary > span {color: #fff;}
</style>
</head>
<body>
<h2>교회앨범</h2>
<div class="intro">

<div class="church_content">

<div id="titleWrap">
<h3>교회앨범</h3>
<p class="home">Home &gt; 교회앨범 &gt; <span>교회앨범</span></p>
</div>

<div id="writeWrap">
<h4>앨범 사진을 올려주세요.</h4>
<div class="row"><c:url value="/church/${ourChurch.id}/album/${entityId}/photo" var="photoUrl"/>
<form id="fileupload" action="${photoUrl}" method="POST" enctype="multipart/form-data">
<input type="hidden" name="url" value="${ourChurch.path}"/>
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
    <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
        <c:if test="${not empty photos}">
        <c:forEach var="photo" items="${photos}">
        <tr class="template-download fade in">
            <td class="preview">
                <a href="${photo.path}" title="${photo.fileName}" rel="gallery" download="${photo.fileName}"><img src="${photo.path}" width="60" height="60"></a>
            </td>
            <td>
                <a href="${photo.path}" title="${photo.fileName}" rel="gallery" download="${photo.fileName}">${photo.fileName}</a>
                <br>
                <span><s:eval expression="T(com.laonsys.springmvc.extensions.utils.StringUtils).formatFileSize(photo.fileSize)" /></span>
            </td>
            <td colspan="2"></td>
            <td class="delete">
                <button class="btn" data-type="DELETE" data-url="<c:url value="/church/${ourChurch.id}/album/${entityId}/photo/${photo.id}"/>">
                    <i class="icon-trash"></i>
                    <span>삭제</span>
                </button>
                <input type="checkbox" name="delete" value="1">
            </td>
        </tr>
        </c:forEach>
        </c:if>
    </tbody>
    </table>
</div>
 </form>
 </div>

</div><!-- writeWrap -->

<div class="row">
<div class="span9" style="margin-top: 20px; text-align: center">
    <a href="<c:url value="/church/${ourChurch.id}/album"/>" class="btn btn-large">확인</a>
</div><!--// 버튼 -->
</div>

<p class="pd30"></p>
</div><!-- news_content -->

<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">
<sec:authorize ifAnyGranted="ROLE_USER">
<div class="profile-wrap">

<div class="profile">
<sec:authentication property="principal" var="user" />
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="프로필 사진" width="50" height="50" /></c:when>
<c:otherwise><img src="<c:url value="/resources/images/commons/img_default_profile.gif" />" alt="프로필 사진" width="50" height="50" /></c:otherwise>
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
<li class="bold"><a href="<c:url value="/church/${ourChurch.id}/album" />">교회앨범</a></li>
</ul>
</div>
<!-- rnb -->
</div>
<!--// 오른쪽 프로필 및 서브메뉴 -->
</div>

<div id="modal-gallery" class="modal modal-gallery hide fade" data-filter=":odd" tabindex="-1">
    <div class="modal-header">
        <a class="close" data-dismiss="modal">&times;</a>
        <h3 class="modal-title"></h3>
    </div>
    <div class="modal-body"><div class="modal-image"></div></div>
    <div class="modal-footer">
        <a class="btn modal-download" target="_blank">
            <i class="icon-download"></i>
            <span>Download</span>
        </a>
        <a class="btn btn-success modal-play modal-slideshow" data-slideshow="5000">
            <i class="icon-play icon-white"></i>
            <span>Slideshow</span>
        </a>
        <a class="btn btn-info modal-prev">
            <i class="icon-arrow-left icon-white"></i>
            <span>Previous</span>
        </a>
        <a class="btn btn-primary modal-next">
            <span>Next</span>
            <i class="icon-arrow-right icon-white"></i>
        </a>
    </div>
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
    <tr class="template-download fade">
        {% if (file.error) { %}
            <td></td>
            <td><span>{%=file.name.substring(0, 17) + '...'%}</span><br><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error break-word" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
        {% } else { %}
            <td class="preview">{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}" width="60" height="60"></a>
            {% } %}</td>
            <td>
                <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
                <br>
                <span>{%=o.formatFileSize(file.size)%}</span>
            </td>
            <td colspan="2"></td>
        {% } %}
        <td class="delete">
            <button class="btn" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                <i class="icon-trash"></i>
                <span>삭제</span>
            </button>
            <input type="checkbox" name="delete" value="1">
        </td>
    </tr>
{% } %}
</script>

<script src="<c:url value="/resources/js/jquery/blueimp/image_gallery/load-image.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/image_gallery/canvas-to-blob.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/image_gallery/bootstrap-image-gallery.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/tmpl.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.iframe-transport.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.fileupload.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.fileupload-fp.js"/>"></script>
<script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.fileupload-ui.js"/>"></script>
<!--[if gte IE 8]><script src="<c:url value="/resources/js/jquery/blueimp/fileupload/jquery.xdr-transport.js"/>"></script><![endif]-->
<script type="text/javascript">
$(function() {
    $('#fileupload').fileupload({
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        previewMaxWidth: 60,
        previewMaxHeight: 60,
        paramName: 'file'
    });
});
</script>
</body>
</html>