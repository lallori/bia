<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a id="mapcourses" href="http://courses.medici.org/" target="_blank"></a>
	<div class="welcome_list">
		<h3>Welcome back <security:authentication property="principal.firstName"/>. <br /></h3>

	  <h5>ACTIVITY IN FORUMS</h5>
    
    <div id="topDiscussions">
        <h1>TOP DISCUSSIONS</h1>
    <c:forEach var="discussion" items="${forumStatistics['TOP DISCUSSIONS']}">
    	<c:url var="forumURL" value="/community/ShowTopicForum.do">
    		<c:param name="forumId" value="${discussion.forum.forumId}"/>
    		<c:param name="topicId" value="${discussion.topicId}"/>
    		<c:param name="completeDOM" value="true"/>
    	</c:url>
     	<div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="${forumURL}" class="forumHref" target="_blank">${discussion.subject}</a> 
            <span>(${discussion.totalReplies} replies)</span>
            <span>by <a href="#" id="userName" class="link">${discussion.user.account}</a><span class="date"><fmt:formatDate pattern="MM/dd/yyyy" value="${discussion.lastUpdate}" /></span></span>
        </div>
    </c:forEach>
    </div>
    
    <div id="mostRecentDiscussions">
        <h1>MOST RECENT DISCUSSIONS</h1>
	<c:forEach var="discussion" items="${forumStatistics['MOST RECENT DISCUSSIONS']}" varStatus="status">
    	<c:url var="forumURL" value="/community/ShowTopicForum.do">
    		<c:param name="forumId" value="${discussion.forum.forumId}"/>
    		<c:param name="topicId" value="${discussion.topicId}"/>
    		<c:param name="completeDOM" value="true"/>
    	</c:url>
       	<div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="${forumURL}" class="forumHref" target="_blank">${discussion.subject}</a>
            <span>(${discussion.totalReplies} replies)</span>
            <span>by <a href="#" id="userName" class="link">${discussion.user.account}</a><span class="date"><fmt:formatDate pattern="MM/dd/yyyy" value="${discussion.lastUpdate}" /></span></span>
        </div>
	    </c:forEach>
    </div>
        
    
    <h5>ACTIVITY IN THE DATABASE</h5>
    
    <div id="lastLogOnDiv">
    	<h1>FROM YOUR LAST LOG ON</h1>
        <div>
        	<a href="#" class="databaseActivity">Document </a> 
            <span>${lastLogonDBStatistics['DOCUMENT']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Volumes</a>
            <span>${lastLogonDBStatistics['VOLUME']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">People</a>
            <span>${lastLogonDBStatistics['PEOPLE']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Places</a>
            <span>${lastLogonDBStatistics['PLACE']}</span>
        </div>
    </div>

    <div id="thisWeekDiv">
    	<h1>THIS WEEK</h1>
        <div>
        	<a href="#" class="databaseActivity">Document</a>
            <span>${currentWeekDBStatistics['DOCUMENT']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Volumes</a>
            <span>${currentWeekDBStatistics['VOLUME']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">People</a>
            <span>${currentWeekDBStatistics['PEOPLE']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Places</a>
            <span>${currentWeekDBStatistics['PLACE']}</span>
        </div>
    </div>
    
    <div id="thisMonthDiv">
    	<h1>THIS MONTH</h1>
         <div>
        	<a href="#" class="databaseActivity">Document</a>
            <span>${currentMonthDBStatistics['DOCUMENT']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Volumes</a>
            <span>${currentMonthDBStatistics['VOLUME']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">People</a>
            <span>${currentMonthDBStatistics['PEOPLE']}</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Places</a>
            <span>${currentMonthDBStatistics['PLACE']}</span>
        </div>
    </div>