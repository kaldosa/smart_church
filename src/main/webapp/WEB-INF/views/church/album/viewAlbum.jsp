<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/jquery/gallery/themes/classic/galleria.classic.css" />"/>
</head>
<body><sec:authentication property="principal" var="user" />
<h2>교회앨범</h2>
<div class="intro">
<div class="church_content">

<div id="titleWrap">
<h3>교회앨범</h3>
<p class="home">Home &gt; 교회앨범 &gt; <span>교회앨범</span></p>
</div>

<div class="boardbox">
<div class="header_wrap">
<ul>
<li class="title"><c:out value="${entity.subject}" escapeXml="true"/></li>
<li class="date"><s:eval expression="entity.createdDate" /></li>
<li class="author">${entity.author.name}</li>
</ul>
</div>
<div class="body_wrap">

<c:choose>
<c:when test="${not empty entity.attachments}">
<div id="gallery" class="image_contents_wrap">
<c:forEach var="photo" items="${entity.attachments}" varStatus="status">
    <img src="<c:url value="${photo.path}"/>" />
</c:forEach>
</div>
</c:when>
<c:otherwise>
<div class="image_contents_wrap">
    <img src="<c:url value="/resources/images/church/album/img_empty.png"/>" />
</div>
</c:otherwise>
</c:choose>

<div class="text_contents_wrap">
<div class="text_contents break-word"><c:out value="${entity.contents}" escapeXml="true"/></div>
</div>
</div>
</div><!--// 내용보기 -->

<div class="action_btn_area">
<a href="<c:url value="/church/${ourChurch.id}/album?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}" />" class="list_btn"><img src="<c:url value="/resources/images/church/common/btn_list3.gif" />" alt="목록" width="72" height="24"/></a>

<sec:authorize access="isAuthenticated() and (hasPermission(${ourChurch.id}, 'isChurchMember') or hasPermission(${ourChurch.id}, 'isOurChurchAdmin'))">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/album/${entity.id}" />
<form:form action="${deleteUrl}" method="delete" class="delete-album-form">
<input type="image" name="btnSubmit" id="btnSubmit" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="등록" class="del_btn"/>
</form:form>
<a href="<c:url value="/church/${ourChurch.id}/album/${entity.id}/edit" />" class="edit_btn"><img src="<c:url value="/resources/images/church/common/btn_edit.png" />" alt="목록" width="73" height="22"/></a>
</sec:authorize>
</div>

<div class="comment-wrap">

<!-- 댓글쓰기 -->
<div class="comment-box">
<c:url var="addCommentUrl" value="/church/${ourChurch.id}/album/${entity.id}"/>
<form:form action="${addCommentUrl}" method="post" modelAttribute="comment" id="comment-form">
<fieldset id="commentForm">
<legend>한줄댓글</legend>

<div class="comm_wrap"> 
<h5>한줄댓글</h5>
<form:textarea path="comments" cols="77" rows="4" cssClass="input_textarea" />
<input type="image" src="<c:url value="/resources/images/church/common/btn_confirm.gif" />" alt="확인" class="btn_ok" />
</div>

</fieldset>
</form:form>
</div><!--// 댓글쓰기 -->
        
<!-- 댓글 리스트 -->
<div class="comment-list">
<c:forEach var="comment" items="${entity.comments}" varStatus="status">
    <div class="item">
        <p class="user_photo">
        <c:choose>
        <c:when test="${not empty comment.user.photo}"><img src="<c:url value="${comment.user.photo.path}" />" alt="프로필 사진" width="64" height="64" /></c:when>
        <c:otherwise><img src="<c:url value="/resources/images/commons/img_default_profile.gif" />" alt="프로필 사진" width="64" height="64" /></c:otherwise>
        </c:choose>        
        </p>
        <dl class="comment">
            <dt class="nick"><span><strong>${comment.user.name}</strong> <s:eval expression="comment.createdDate" /></span></dt>
            <dd class="content break-word"><s:eval expression="comment.comments.replaceAll('\r\n', '<br/>')"/>
            <sec:authorize access="isAuthenticated() and hasPermission(${ourChurch.id}, 'isOurChurchAdmin') or (hasPermission(${ourChurch.id}, 'isChurchMember') and hasPermission(${comment.id}, 'isReplyUser'))">
            <span><a href="<c:url value="/church/${ourChurch.id}/album/${entity.id}/${comment.id}"/>" class="comment-del">삭제</a></span>
            </sec:authorize>
            </dd>
        </dl>
    </div>
</c:forEach>
</div><!--// 댓글 리스트 -->

<div class="action_btn_area">
<a href="<c:url value="/church/${ourChurch.id}/album?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}" />" class="list_btn"><img src="<c:url value="/resources/images/church/common/btn_list3.gif" />" alt="목록" width="72" height="24"/></a>

<sec:authorize access="isAuthenticated() and (hasPermission(${ourChurch.id}, 'isChurchMember') or hasPermission(${ourChurch.id}, 'isOurChurchAdmin'))">
<c:url var="deleteUrl" value="/church/${ourChurch.id}/album/${entity.id}" />
<form:form action="${deleteUrl}" method="delete" class="delete-album-form">
<input type="image" name="btnSubmit" id="btnSubmit" src="<c:url value="/resources/images/church/common/btn_delete.png" />" alt="등록" class="del_btn"/>
</form:form>
<a href="<c:url value="/church/${ourChurch.id}/album/${entity.id}/edit" />" class="edit_btn"><img src="<c:url value="/resources/images/church/common/btn_edit.png" />" alt="목록" width="73" height="22"/></a>
</sec:authorize>
</div>
        
</div><!--// commentWrap -->
</div><!--// album_content -->
          
<!-- 오른쪽 프로필 및 서브메뉴 -->
<div class="con_right">
<sec:authorize ifAnyGranted="ROLE_USER">
<div class="profile-wrap">

<div class="profile">
<c:choose>
<c:when test="${not empty user.photo}"><img src="<c:url value="${user.photo.path}" />" alt="댓글 썸네일" width="50" height="50" /></c:when>
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
</div><!-- rnb -->

</div><!--// 오른쪽 프로필 및 서브메뉴 -->
</div><!-- intro -->

<s:eval var="loginUrl" expression="@envvars['web.login']" />
<form id="login-form" name="login-form" action="${loginUrl}" method="post" style="display:none">
<fieldset>
<input type="text" id="username" name="j_username"/>
<input type="password" id="password" name="j_password"/>
<input type="hidden" name="spring-security-redirect" value="/church/${ourChurch.id}/album/${entity.id}?criteria=${queryParam.criteria}&keyword=${queryParam.keyword}&page=${queryParam.paginate.page}" />
</fieldset>
</form>

<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/gallery/galleria-1.2.8.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/gallery/themes/classic/galleria.classic.js" />" ></script>
<script type="text/javascript">
/* <[CDATA[ */
$(function() {
	<c:if test="${not empty entity.attachments}">    
    Galleria.loadTheme('<c:url value="/resources/js/jquery/gallery/themes/classic/galleria.classic.js" />');
    
    $('#gallery').galleria({
        width: 640,
        height: 480,
        extend: function(options) {
            this.bind('image', function(e) {
                $(e.imageTarget).css('cursor','pointer').click(this.proxy(function() {
                   this.openLightbox();
                }));
            });
        }
    });
    </c:if>
    $('.btn_ok').click(function() {

    	var result = false;
    	
        $.ajax({
            cache:false,
            async:false,
            type: "GET",
            dataType: "json",
            url: '<c:url value="/ajax/checkAuth"/>',
            success: function(data) {
                if(!data.result) {
                	alert("로그인 먼저 하세요.");
                	$('textarea').blur();
                    window.open('<c:url value="/popup/login"/>', 'popup', 'width=420, height=380, toolbar=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no');
                	result = false;
                } else {
                	result = true;
                }                
            }, 
            error: function(xhr, status, error) {
                alert(error);
                result = false;
            }
        });
        
        return result;
    });

    $('#comment-form').validate({
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
            'comments': {
                required: true,
                maxlength: 140,
                htmlTag: true
            }
        },
        messages: {
            'comments': {
                required: "내용를 입력해주세요.",
                maxlength: "140자 이하로 작성해 주세요."
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
    
    $('.delete-album-form').submit(function() {
        if(!confirm("삭제하시겠습니까?")) {
            return false;
        }
    });

    $('.comment-del').click(function() {
        if(!confirm("삭제하시겠습니까?")) {
            return false;
        }
    });    
});
/* ]]> */   
</script>
</body>
</html>