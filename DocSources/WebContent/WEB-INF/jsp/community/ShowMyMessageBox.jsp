update <%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
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
	
	<div id="messagesTable">
		<div class="list">
        	<c:if test="${command.category == 'inbox'}">
	        	<div class="rowFirst">
	           		<div class="one">FROM</div>
	            	<div class="two">MESSAGE</div>
	            	<div class="three">DATE</div>
	        	</div>
        	</c:if>
        	<c:if test="${command.category == 'outbox'}">
        		<div class="rowFirst">
        			<div class="one">TO</div>
        			<div class="two">MESSAGE</div>
        			<div class="three">DATE</div>
        		</div>
        	</c:if>

<%-- <h2>${topic.subject }</h2> --%>

<!-- <div id="topicActions"> -->
<%-- 	<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><img src="<c:url value="/images/forum/img_reply.png"/>" alt="post a reply" width="17" height="15" /><span class="button_text">Post a <b>reply</b></span></a> --%>
<!--     <div id="searchThisForumFormDiv"> -->
<%--         <form id="SearchForm" action="/DocSources/src/SimpleSearch.do" method="post"> --%>
<!--             <input id="text" name="text" type="text" value="Search this forum..."> -->
<!--             <input id="search" type="submit" title="Search" value="Search"/> -->
<%--         </form> --%>
<!--     </div> -->
<!--     <a href="#" id="printButton" class="buttonMedium"><img src="/DocSources/images/forum/img_print.png" alt="Print thread" width="17" height="15" /><span class="button_text">Print thread</span></a> -->
<!-- </div> -->

			<c:forEach items="${messageboxPage.list}" var="currentMessage" varStatus="status">
			<div class="row">
				<div class="one"><input type="checkbox" name="css" value="css"/><a href="<c:url value="/community/ShowMessage.do?messageId=${currentMessage.messageId}"/>">${currentMessage.sender}</a></div>
				<div class="two"><span class="subject">${currentMessage.messageSubject}</span> - <span class="message">${currentMessage.messageBody}</span></div>
				<div class="three">${currentMessage.messageSendedDate}</div>
			</div>
			</c:forEach>
		</div>
	</div>

<div id="forumPaginate">
    <c:set var="paginationData">
		<bia:paginationForum page="${messageboxPage}"/>
	</c:set>
	
	${paginationData}
 
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
// 			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
//    				$j("#chronologyDiv").html(json.chronology);
// 				$j(".arrowForum").css('visibility','visible');
// 				$j(".forum").css('visibility','visible');
//    			}});

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

			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
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
