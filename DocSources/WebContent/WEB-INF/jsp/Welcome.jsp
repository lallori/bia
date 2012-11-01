<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
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
            <a href="${forumURL}" class="forumHref" target="_blank"><bia:textFormatter inputText="${discussion.subject}" size="34"/></a> 
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
        	<a href="${lastLogonUrls['DOCUMENT']}" class="databaseActivity">Documents </a> 
            <span>${lastLogonDBStatistics['DOCUMENT']}</span>
        </div>
        <div>
        	<a href="${lastLogonUrls['VOLUME']}" class="databaseActivity">Volumes</a>
            <span>${lastLogonDBStatistics['VOLUME']}</span>
        </div>
        <div>
        	<a href="${lastLogonUrls['PEOPLE']}" class="databaseActivity">People</a>
            <span>${lastLogonDBStatistics['PEOPLE']}</span>
        </div>
        <div>
        	<a href="${lastLogonUrls['PLACE']}" class="databaseActivity">Places</a>
            <span>${lastLogonDBStatistics['PLACE']}</span>
        </div>
    </div>

    <div id="thisWeekDiv">
    	<h1>THIS WEEK</h1>
        <div>
        	<a href="${currentWeekUrls['DOCUMENT']}" class="databaseActivity">Documents</a>
            <span>${currentWeekDBStatistics['DOCUMENT']}</span>
        </div>
        <div>
        	<a href="${currentWeekUrls['VOLUME']}" class="databaseActivity">Volumes</a>
            <span>${currentWeekDBStatistics['VOLUME']}</span>
        </div>
        <div>
        	<a href="${currentWeekUrls['PEOPLE']}" class="databaseActivity">People</a>
            <span>${currentWeekDBStatistics['PEOPLE']}</span>
        </div>
        <div>
        	<a href="${currentWeekUrls['PLACE']}" class="databaseActivity">Places</a>
            <span>${currentWeekDBStatistics['PLACE']}</span>
        </div>
    </div>
    
    <div id="thisMonthDiv">
    	<h1>THIS MONTH</h1>
         <div>
        	<a href="${currentMonthUrls['DOCUMENT']}" class="databaseActivity">Documents</a>
            <span>${currentMonthDBStatistics['DOCUMENT']}</span>
        </div>
        <div>
        	<a href="${currentMonthUrls['VOLUME']}" class="databaseActivity">Volumes</a>
            <span>${currentMonthDBStatistics['VOLUME']}</span>
        </div>
        <div>
        	<a href="${currentMonthUrls['PEOPLE']}" class="databaseActivity">People</a>
            <span>${currentMonthDBStatistics['PEOPLE']}</span>
        </div>
        <div>
        	<a href="${currentMonthUrls['PLACE']}" class="databaseActivity">Places</a>
            <span>${currentMonthDBStatistics['PLACE']}</span>
        </div>
    </div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".databaseActivity").click(function() {
				// this is search url form 
				var formSubmitURL = $j(this).attr("href");

				// If we found refine button of this search, user is in refine.
				if ($j('#tabs').find("#refine${command.searchUUID}").length==1) {
					// calculate tab position
					var index =$j("#tabs ul li").index($j("li:has(a[href='#" + $j("#tabs").find("#refine${command.searchUUID}").parent().attr("id") + "'])"));
					$j("#tabs").tabs("url", index, formSubmitURL);
					$j("#tabs").tabs("select", index);
					$j("#tabs").tabs("load" , index);
					window.close()
				} else {
					//otherwise it's in a new search so we add a new tab.
					$j("#tabs").tabs("add", formSubmitURL,  $j(this).text() + " Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					window.close();
				}
				return false;
			});
		});
	</script>