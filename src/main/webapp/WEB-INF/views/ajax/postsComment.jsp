<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div id="comments-list">
    <sec:authentication property="principal" var="logonUser" />
    <s:iterator var="parent" value="commentsList" status="parent_status">
        <s:if test="level eq 0">
            <div class="comments">
                <p class="${id}">
                    <span style="width: 90px">작성자 이름 ${writerId}</span> <span style="margin-left: 10px; color: gray"><s:date name="writtenDate"
                            format="yyyy.MM.dd" /></span>
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <span class="edit"><img src="<s:url value='/images/test/ico_theple_gray.gif' />" alt="덧플" /></span>
                        <s:if test="writerId eq #attr.logonUser.id">
                            <span class="delete"><img src="<s:url value='/images/test/ico_del.gif' />" alt="삭제" /></span>
                        </s:if>
                        <s:else>
                            <span class="edit">댓글</span>
                        </s:else>
                    </sec:authorize>
                </p>
                <p class="comments-comments" style="padding-left: 90px;">${comments}</p>
                <div id="sub-comments-list${id}" style="margin-left: 90px;">
                    <s:iterator var="child" value="commentsList" status="child_status">
                        <s:if test="#parent.id eq #child.parentId and level gt 0">
                            <div class="sub-comments">
                                <p class="${child.id}">
                                    <span style="width: 90px">작성자 이름 ${writerId}</span> <span style="margin-left: 10px; color: gray"><s:date
                                            name="writtenDate" format="yyyy.MM.dd" /></span>
                                    <s:if test="writerId eq #attr.logonUser.id">
                                        <span class="delete"><img src="<s:url value='/images/test/ico_del.gif' />" alt="삭제" /></span>
                                    </s:if>
                                </p>
                                <p class="comments-comments" style="padding-left: 90px;">${comments}</p>
                            </div>
                        </s:if>
                    </s:iterator>
                </div>
                <div id="create-comments-reply${id}" style="display: none; padding-left: 90px; width: 678px">
                    <fieldset class="${id}">
                        <s:form id="create-comments-reply-form%{#parent_status.index}" class="comments-reply-form" cssClass="create-comments-reply-form"
                            action="createPostsCommentsReply!create" namespace="/test" method="post">
                            <s:hidden name="comments.postsId" value="%{postsId}" />
                            <s:hidden name="comments.parentId" value="%{id}" />
                            <s:hidden name="comments.level" value="%{level + 1}" />
                            <s:hidden name="comments.sortKey" value="%{sortKey}" />
                            <s:textarea name="comments.comments" value="" cssStyle="width: 520px;height: 30px" />
                            <s:submit value="확인" />
                        </s:form>
                    </fieldset>
                </div>
            </div>
        </s:if>
    </s:iterator>
</div>
