<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do">
		<c:param name="postId" value="0"/>
		<c:param name="forumId" value="${topic.forum.forumId}"/>
		<c:param name="topicId" value="${topic.topicId}"/>
	</c:url>
	
	<div id="searchResults">
		<h2>SEARCH</h2>
		<p>Found <span>${searchResultPage.total}</span> match: <span class="search">${yourSearch}</span>

<%-- <h2>${topic.subject }</h2> --%>

<!-- <div id="topicActions"> -->
<%-- 	<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><img src="<c:url value="/images/forum/img_reply.png"/>" alt="post a reply" width="17" height="15" /><span class="button_text">Post a <b>reply</b></span></a> --%>
<!--     <div id="searchThisForumFormDiv"> -->
<%--         <form id="SearchForm" action="/DocSources/src/SimpleSearch.do" method="post"> --%>
<!--             <input id="text" name="text" type="text" value="Search this forum..."> -->
<!--             <input id="search" type="submit" title="Search" value="Search"/> -->
<%--         </form> --%>
<!--     </div> -->
<!--     <a href="#" id="printButton" class="buttonMedium"><img src="/DocSources/images/forum/img_print.png" alt="Print discussion" width="17" height="15" /><span class="button_text">Print discussion</span></a> -->
<!-- </div> -->

		<div id="forumTable">
			<div class="list">
				<div class="rowFirst">
					<div class="one">TOPIC</div>
			        <div class="two">REPLY</div>
			        <div class="three">VIEWS</div>
			        <div class="four">LAST POST</div>
			        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			        	<div class="five">DEL</div>
			        </security:authorize>
				</div>
				<c:forEach items="${searchResultPage.list}" var="currentTopic" varStatus="status">
					<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
						<c:param name="topicId" value="${currentTopic.topicId}"/>
						<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
					</c:url>
					<c:url var="DeleteTopicForumURL" value="/community/DeleteForumTopic.json">
						<c:param name="topicId" value="${currentTopic.topicId}" />
					</c:url>
					<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
						<div class="one">
					       	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
					        <a href="${ShowTopicForumURL}" class="forumHref">${currentTopic.subject}</a>
					        <span>subtitle</span>
					    </div>
					    <div class="two">${currentTopic.totalReplies}</div>
					    <div class="three">-</div>
						<c:if test="${not empty currentTopic.lastPost}">
							<div class="four">by <a href="#" id="userName" class="link">${currentTopic.lastPost.user.account}</a><span class="date">${currentTopic.lastPost.lastUpdate}</span></div>
						</c:if>
						<c:if test="${empty currentTopic.lastPost}">
						    <div class="four"></div>
						</c:if>
						<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					    	<div class="five"><a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a></div>
					    </security:authorize>
					</div>
				</c:forEach>
			</div>
		</div>


<div id="forumPaginate">
    <c:set var="paginationData">
		<bia:paginationForum page="${searchResultPage}"/>
	</c:set>
	
	${paginationData}
	
	<div id="jumpToDiv">
		<input id="goBackTo" class="button_medium" value="Go Back" type="submit" />
<!--     	Jump to: -->
<%--         <form id="jumpToForm" action="/DocSources/src/SimpleSearch.do" method="post"> --%>
<!--             <select id="selectForum" name="selectForum" selected""="" class="selectform_long"> -->
<!--                 <option value="" selected="selected">Select a Forum</option> -->
<!--             </select> -->
<!--             <input id="go" type="submit" title="go" value="Go" class="buttonMini"> -->
<%--         </form> --%>
    </div>
    
    <input type="hidden" name="searchUUID" value="${command.searchUUID}">
    <input type="hidden" name="newSearch" value="${command.newSearch}">
 
</div>
					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; Return to <span>Board Index</span> Forum</a>


<!-- <div id="deletePostModal" title="Delete post" style="display:none">  -->
<!-- 	<p> -->
<!-- 		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span> -->
<!-- 		Are you sure you want to delete this post? -->
<!-- 	</p> -->
<!-- </div> -->


	<script type="text/javascript">
		$j(document).ready(function() {
			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
   				$j("#chronologyDiv").html(json.chronology);
				$j(".arrowForum").css('visibility','visible');
				$j(".forum").css('visibility','visible');
   			}});

			$j('.pageHref').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.pageHref').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
// 			$j('.boardIndex').die();
			// Result links have a specific class style on which we attach click live. 
// 			$j('.boardIndex').live('click', function() {
// 				$j("#main").load($j(this).attr("href"));
// 				return false;
// 			});

			$j('.quotePost').die();
			$j('.quotePost').click(function (){
				$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
				return false;
			});

			$j('#postReply').click(function (){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j("#goBackTo").click(function(){
				$j("#main").load($j("#prevUrl").val());
				return false;
			});
			
			$j('.paginateForumButton').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.paginateForumButton').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});

		});
	</script>
