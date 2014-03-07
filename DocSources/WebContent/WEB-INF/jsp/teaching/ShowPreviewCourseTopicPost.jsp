<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="postTablePreviewMsg" class="postTable">
	    <div class="post">
	        <h2>${post.subject}</h2>
	        <p>by <a href="#" id="userName" class="link">${post.user.account}</a> &#xbb <span class="date">${post.lastUpdate}</span></p>
	        <p>${post.text}</p>
	    </div>
	    <div class="postProfile">
	    	<ul>
	        	<li>
	        		<c:if test="${post.user.portrait}">
	        			<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
							<c:param name="account" value="${post.user.account}" />
							<c:param name="time" value="${time}" />
						</c:url>
	        			<img src="${ShowPortraitUserURL}" class="avatar"/>
	        		</c:if>
	        		<c:if test="${!post.user.portrait}">
	        			<img class="avatar" src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
	        		</c:if>
	        		<a href="#" id="userName" class="link">${post.user.account}</a>
	        	</li>
	            <%-- <li><security:authentication property="principal.significantRoleDescription"/></li> --%>
	            <li>Posts: <span>${post.user.forumNumberOfPost}</span></li>
	            <li>Joined: <span>${post.user.forumJoinedDate}</span></li>
	        </ul>
	    </div>
	    <div class="online visible"></div>
    </div>
    
    <a id="closePreview" href="#" class="buttonMedium button_medium">Close Preview</a>

    <script>
    	$j(document).ready(function() {
    		$j("#closePreview").click(function() {
    			$j("#postTablePreview").hide();
    			$j("#postTablePreview").html("");
    			$j(this).unbind();
    			return false;
    		});
    		
    		//delayed scrollTo preview
    		setTimeout(function() {
    			$j("#editPostContainer").scrollTo("#postTablePreviewMsg");
    		},200);
    		
    	});
    </script>