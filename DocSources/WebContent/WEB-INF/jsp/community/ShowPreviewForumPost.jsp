<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="topicIcons">
    	<a href="#" id="editPost" title="Edit post"></a>
        <a href="#" id="deletePost" title="Delete post"></a>
        <a href="#" id="reportPost" title="Report this post"></a>
        <a href="#" id="informationPost" title="Information"></a>
        <a href="#" id="quotePost" title="Reply with quote"></a>
    </div>
    
    <div id="post">
        <h2>${forumPost.subject}</h2>
        <p>by <a href="#" id="userName" class="link">${forumPost.username}</a> » <span class="date">${forumPost.dateCreated}</span></p>
        <p>${forumPost.text}</p>
    </div>
    <div id="postProfile">
    	<ul>
        	<li><a href="#" id="userName" class="link">${user.account}</a></li>
            <li><security:authentication property="principal.significantRoleDescription"/></li>
            <li>Posts: <span>${user.forumNumberOfPost}</span></li>
            <li>Joined: <span>${user.forumJoinedDate}</span></li>
        </ul>
    </div>
