<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="logonUser" />
<div class="sub-comment">
    <p class="${postsComments.position}">
        <span style="width: 90px">작성자 이름 ${postsComments.writerId}</span> <span style="margin-left: 10px; color: gray"><s:date
                name="postsComments.writtenDate" format="yyyy.MM.dd" /></span>
        <s:if test="postsComments.writerId eq #attr.logonUser.id">
            <span class="edit">수정</span>
            <span class="delete">삭제</span>
        </s:if>
    </p>
    <p class="post-comment" style="padding-left: 90px;">${postsComments.comment}</p>
</div>