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
		<h2><fmt:message key="community.advancedSearchResultForumPost.searchCaps"/></h2>
		<p><fmt:message key="community.advancedSearchResultForumPost.found"/> <span>${searchResultPage.total}</span> <fmt:message key="community.advancedSearchResultForumPost.match"/> <span class="search">${yourSearch}</span>

<%-- <h2>${topic.subject }</h2> --%>

<!-- <div id="topicActions"> -->
<%-- 	<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><img src="<c:url value="/images/forum/img_reply.png"/>" alt="post a reply" width="17" height="15" /><span class="button_text">Post a <b>reply</b></span></a> --%>
<!--     <div id="searchThisForumFormDiv"> -->
<%--         <form id="SearchForm" action="/src/SimpleSearch.do" method="post"> --%>
<!--             <input id="text" name="text" type="text" value="Search this forum..."> -->
<!--             <input id="search" type="submit" title="Search" value="Search"/> -->
<%--         </form> --%>
<!--     </div> -->
<!--     <a href="#" id="printButton" class="buttonMedium"><img src="/images/forum/img_print.png" alt="Print discussion" width="17" height="15" /><span class="button_text">Print discussion</span></a> -->
<!-- </div> -->

<c:forEach items="${searchResultPage.list}" var="currentPost" varStatus="status">
	<c:url var="ReportForumPostURL" value="/community/ReportForumPost.do">
		<c:param name="postId" value="${currentPost.postId}"/>
		<c:param name="forumId" value="${currentPost.forum.forumId}"/>
		<c:param name="topicId" value="${currentPost.topic.topicId}"/>
	</c:url>

	<c:url var="ReplyWithQuoteForumPostURL" value="/community/ReportForumPost.do">
		<c:param name="postId" value="${currentPost.postId}"/>
		<c:param name="forumId" value="${currentPost.forum.forumId}"/>
		<c:param name="topicId" value="${currentPost.topic.topicId}"/>
	</c:url>
	
	<div id="postTable">
	<div id="topicIcons">
        <a href="${ReportForumPostURL}" class="reportPost" title="Report this post"></a>
        <a href="${ReplyWithQuoteForumPostURL}" id="quotePost" title="Reply with quote"></a>
    </div>
    <div id="post">
        <h2>${currentPost.subject}</h2>
        <p>by <a href="#" id="userName" class="link">${currentPost.user.account}</a> &#xbb <span class="date">${currentPost.lastUpdate}</span></p>
        <p class="textPost">${currentPost.text}</p>
    </div>
    <div id="postProfile">
    	<ul>
        	<li><a href="#" id="userName" class="link">${currentPost.user.account}</a></li>
            <li><fmt:message key="community.advancedSearchResultForumPost.commUser"/></li>
            <li><fmt:message key="community.advancedSearchResultForumPost.posts"/> <span>${currentPost.user.forumNumberOfPost}</span></li>
            <li><fmt:message key="community.advancedSearchResultForumPost.joined"/> <span>${currentPost.user.forumJoinedDate}</span></li>
        </ul>
    </div>
    <div id="online" class="visible"></div> <!--  Se l'utente è loggato in quel momento inserire la class "visible" a questo div -->
</div>
</c:forEach>

<div id="forumPaginate">
    <c:set var="paginationData">
		<bia:paginationForum page="${searchResultPage}"/>
	</c:set>
	
	${paginationData}
	
	<div id="jumpToDiv">
    	<fmt:message key="community.advancedSearchResultForumPost.jumpTo"/>
        <form id="jumpToForm" action="/src/SimpleSearch.do" method="post">
            <select id="selectForum" name="selectForum" selected""="" class="selectform_long">
                <option value="" selected="selected"><fmt:message key="community.advancedSearchResultForumPost.selectForum"/></option>
            </select>
            <input id="go" type="submit" title="go" value="Go" class="buttonMini button_mini">
        </form>
    </div>
    
    <input type="hidden" name="searchUUID" value="${command.searchUUID}">
    <input type="hidden" name="newSearch" value="${command.newSearch}">
 
</div>
					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; <fmt:message key="community.advancedSearchResultForumPost.returnTo"/> <span><fmt:message key="community.advancedSearchResultForumPost.boardIndex"/></span> <fmt:message key="community.advancedSearchResultForumPost.forum"/></a>


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
			
			$j('.paginateForumButton').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.paginateForumButton').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});

		});
	</script>
