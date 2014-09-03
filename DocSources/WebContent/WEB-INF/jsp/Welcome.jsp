<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a id="mapcourses" href="http://courses.medici.org/" target="_blank"></a>
	<div class="welcome_list">
		<h3>Welcome back <security:authentication property="principal.firstName"/>. <br /></h3>

	  <div id="accordion">
	  	<h1><fmt:message key="welcome.activityForums"/></h1>
    
   		<div id="topDiscussions">
   	
   			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS, ROLE_STUDENTS">
    			<h5><fmt:message key="welcome.most.teaching"/></h5>
    			<c:forEach var="extendedTopic" items="${teachingForumStatistics['MOST RECENT COURSE TOPICS']}">
    				<c:url var="showUserProfileURL" value="/community/ShowUserProfileForum.do">
		    			<c:param name="account" value="${extendedTopic.courseTopic.lastPost.user.account}"/>
		    			<c:param name="completeDOM" value="true"/>
		    		</c:url>
    				<c:choose>
    					<c:when test="${extendedTopic.mode == 'Q' || extendedTopic.mode == 'D'}">
    						<c:url var="courseTopicURL" value="/teaching/ShowTopicForum.do">
    							<c:param name="forumId" value="${extendedTopic.courseTopic.forum.forumId}" />
    							<c:param name="topicId" value="${extendedTopic.courseTopic.topicId}" />
    							<c:param name="completeDOM" value="true" />
    						</c:url>
    					</c:when>
    					<c:otherwise>
    						<c:url var="courseTopicURL" value="/teaching/ShowCourseTranscription.do">
								<c:param name="topicId" value="${extendedTopic.courseTopic.topicId}" />
								<c:param name="entryId" value="${extendedTopic.courseTopic.document.entryId}" />
								<c:param name="transcriptionMode" value="${extendedTopic.mode}" />
								<c:param name="completeDOM" value="true" />
							</c:url>
    					</c:otherwise>
    				</c:choose>
    				<div class="discussion">
		            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry" />
		            	<a href="${courseTopicURL}" class="forumHref" target="_blank" title="${extendedTopic.courseTopic.subject}"><bia:textFormatter inputText="${extendedTopic.courseTopic.subject}" size="60"/></a> 
		            	<br />
		            	<c:choose>
		            		<c:when test="${extendedTopic.courseTopic.totalReplies > 0}">
		            			<span style="margin-left:23px;">(${extendedTopic.courseTopic.totalReplies - 1} <fmt:message key="welcome.replies"/>)</span>
		            			<span>
				            		<fmt:message key="welcome.lastPost"/>
				            		<a href="${showUserProfileURL}" target="_blank" id="userName_topDiscussionLastPostId_${extendedTopic.courseTopic.lastPost.postId}" class="link">${extendedTopic.courseTopic.lastPost.user.account}</a>
				            		<span class="date"><fmt:formatDate pattern="MM/dd/yyyy" value="${extendedTopic.courseTopic.lastUpdate}" /></span>
				            	</span>
		            		</c:when>
		            		<c:otherwise>
		            			<span style="margin-left:23px;">(0 <fmt:message key="welcome.replies"/>)</span>
		            		</c:otherwise>
		            	</c:choose>
		        	</div>
    			</c:forEach>
    		</security:authorize>
    		
    		<br />
   			
   			<h5><fmt:message key="welcome.most"/></h5>
   			<c:forEach var="discussion" items="${forumStatistics['MOST RECENT DISCUSSIONS']}" varStatus="status">
	    		<c:url var="forumURL" value="/community/ShowTopicForum.do">
	    			<c:param name="forumId" value="${discussion.forum.forumId}"/>
	    			<c:param name="topicId" value="${discussion.topicId}"/>
	    			<c:param name="completeDOM" value="true"/>
	    		</c:url>
	    		<c:url var="showUserProfileURL" value="/community/ShowUserProfileForum.do">
	    			<c:param name="account" value="${discussion.lastPost.user.account}"/>
	    			<c:param name="completeDOM" value="true"/>
	    		</c:url>
	       		<div class="discussion">
	            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry" />
	            	<a href="${forumURL}" class="forumHref" target="_blank" title="${discussion.subject}"><bia:textFormatter inputText="${discussion.subject}" size="60"/></a>
	            	<br />
	            	<span style="margin-left:23px;">(${discussion.totalReplies - 1} <fmt:message key="welcome.replies"/>)</span>
	            	<span>
	            		<fmt:message key="welcome.lastPost"/>
	            		<a href="${showUserProfileURL}" target="_blank" id="userName_recentDiscussionLastPostId_${discussion.lastPost.postId}" class="link">${discussion.lastPost.user.account}</a>
	            		<span class="date"><fmt:formatDate pattern="MM/dd/yyyy" value="${discussion.lastUpdate}" /></span>
	            	</span>
	        	</div>
	    	</c:forEach>
			
			<br />
			
        	<h5><fmt:message key="welcome.top"/></h5>
    		<c:forEach var="discussion" items="${forumStatistics['TOP DISCUSSIONS']}">
	    		<c:url var="forumURL" value="/community/ShowTopicForum.do">
	    			<c:param name="forumId" value="${discussion.forum.forumId}"/>
	    			<c:param name="topicId" value="${discussion.topicId}"/>
	    			<c:param name="completeDOM" value="true"/>
	    		</c:url>
	    		<c:url var="showUserProfileURL" value="/community/ShowUserProfileForum.do">
	    			<c:param name="account" value="${discussion.lastPost.user.account}"/>
	    			<c:param name="completeDOM" value="true"/>
	    		</c:url>
	     		<div class="discussion">
	            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry" />
	            	<a href="${forumURL}" class="forumHref" target="_blank" title="${discussion.subject}"><bia:textFormatter inputText="${discussion.subject}" size="60"/></a> 
	            	<br />
	            	<span style="margin-left:23px;">(${discussion.totalReplies - 1} <fmt:message key="welcome.replies"/>)</span>
	            	<span>
	            		<fmt:message key="welcome.lastPost"/>
	            		<a href="${showUserProfileURL}" target="_blank" id="userName_topDiscussionLastPostId_${discussion.lastPost.postId}" class="link">${discussion.lastPost.user.account}</a>
	            		<span class="date"><fmt:formatDate pattern="MM/dd/yyyy" value="${discussion.lastUpdate}" /></span>
	            	</span>
	        	</div>
    		</c:forEach>
    		
    	</div>
        
    
    	<h1><fmt:message key="welcome.activityDatabase"/></h1>
    
    	<div id="lastLogOnDiv">
    		<h5><fmt:message key="welcome.title.lastLogon"/></h5>
        	<div>
        		<a href="${lastLogonUrls['DOCUMENT']}" class="databaseActivity"><fmt:message key="welcome.documents"/> </a> 
            	<span>${lastLogonDBStatistics['DOCUMENT']}</span>
        	</div>
        	<div>
        		<a href="${lastLogonUrls['VOLUME']}" class="databaseActivity"><fmt:message key="welcome.volumes"/></a>
        	    <span>${lastLogonDBStatistics['VOLUME']}</span>
        	</div>
        	<div>
        		<a href="${lastLogonUrls['PEOPLE']}" class="databaseActivity"><fmt:message key="welcome.people"/></a>
        	    <span>${lastLogonDBStatistics['PEOPLE']}</span>
        	</div>
        	<div>
        		<a href="${lastLogonUrls['PLACE']}" class="databaseActivity"><fmt:message key="welcome.places"/></a>
        	    <span>${lastLogonDBStatistics['PLACE']}</span>
        	</div>
		
			<br />
		      
    		<h5><fmt:message key="welcome.title.week"/></h5>
        	<div>
        		<a href="${currentWeekUrls['DOCUMENT']}" class="databaseActivity"><fmt:message key="welcome.documents"/></a>
        	    <span>${currentWeekDBStatistics['DOCUMENT']}</span>
        	</div>
        	<div>
        		<a href="${currentWeekUrls['VOLUME']}" class="databaseActivity"><fmt:message key="welcome.volumes"/></a>
        	    <span>${currentWeekDBStatistics['VOLUME']}</span>
        	</div>
        	<div>
        		<a href="${currentWeekUrls['PEOPLE']}" class="databaseActivity"><fmt:message key="welcome.people"/></a>
        	    <span>${currentWeekDBStatistics['PEOPLE']}</span>
        	</div>
        	<div>
        		<a href="${currentWeekUrls['PLACE']}" class="databaseActivity"><fmt:message key="welcome.places"/></a>
        	    <span>${currentWeekDBStatistics['PLACE']}</span>
        	</div>
			
			<br />
			    
    		<h5><fmt:message key="welcome.title.month"/></h5>
         	<div>
        		<a href="${currentMonthUrls['DOCUMENT']}" class="databaseActivity"><fmt:message key="welcome.documents"/></a>
            	<span>${currentMonthDBStatistics['DOCUMENT']}</span>
        	</div>
        	<div>
        		<a href="${currentMonthUrls['VOLUME']}" class="databaseActivity"><fmt:message key="welcome.volumes"/></a>
        	    <span>${currentMonthDBStatistics['VOLUME']}</span>
        	</div>
        	<div>
        		<a href="${currentMonthUrls['PEOPLE']}" class="databaseActivity"><fmt:message key="welcome.people"/></a>
        	    <span>${currentMonthDBStatistics['PEOPLE']}</span>
        	</div>
        	<div>
        		<a href="${currentMonthUrls['PLACE']}" class="databaseActivity"><fmt:message key="welcome.places"/></a>
        	    <span>${currentMonthDBStatistics['PLACE']}</span>
        	</div>
    	</div>
    </div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#accordion').accordion({
				active: 0, 
				autoHeight: false,
				collapsible: true
			});
			
			$j(".databaseActivity").click(function() {
				// this is search url form 
				var formSubmitURL = $j(this).attr("href");

				// If we found refine button of this search, user is in refine.
				if ($j('#tabs').find("#refine${command.searchUUID}").length==1) {
					// calculate tab position
					var index =$j("#tabs ul li").index($j("li:has(a[href='#" + $j("#tabs").find("#refine${command.searchUUID}").parent().attr("id") + "'])"));
					$j('#tabs ul li').eq(index).data('loaded', false).find('a').attr('href', formSubmitURL);
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