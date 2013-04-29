<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="postTable">
	<div id="topicIcons">
    	<a href="#" id="editPost" title="Edit post"></a>
        <a href="#" id="deletePost" title="Delete post"></a>
        <a href="#" id="reportPost" title="Report this post"></a>
        <a href="#" id="informationPost" title="Information"></a>
        <a href="#" id="quotePost" title="Reply with quote"></a>
    </div>
    
    <div id="post">
        <h2>${forumPost.subject}</h2>
        <p>by <a href="#" id="userName" class="link">${forumPost.user.account}</a> &#xbb <span class="date">${forumPost.lastUpdate}</span></p>
        <p>${forumPost.text}</p>
    </div>
    <div id="postProfile">
    	<ul>
        	<li>
        		<c:if test="${forumPost.user.portrait}">
        			<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
						<c:param name="account" value="${forumPost.user.account}" />
						<c:param name="time" value="${time}" />
					</c:url>
        			<img src="${ShowPortraitUserURL}" class="avatar"/>
        		</c:if>
        		<c:if test="${!forumPost.user.portrait}">
        			<img class="avatar" src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
        		</c:if>
        		<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${forumPost.user.account}" id="userName" class="link">${forumPost.user.account}</a>
        	</li>
            <li><security:authentication property="principal.significantRoleDescription"/></li>
            <li>Posts: <span>${forumPost.user.forumNumberOfPost}</span></li>
            <li>Joined: <span>${forumPost.user.forumJoinedDate}</span></li>
        </ul>
    </div>
    <div id="online" class="visible"></div>
    </div>
