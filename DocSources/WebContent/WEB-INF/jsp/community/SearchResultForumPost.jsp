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
		<h2><fmt:message key="community.searchResultForumPost.sEarch"/></h2>
		<p><fmt:message key="community.searchResultForumPost.found"/> <span>${simpleSearchResultPage.total}</span> <fmt:message key="community.searchResultForumPost.match"/> <span class="search">${yourSearch}</span>

<c:forEach items="${simpleSearchResultPage.list}" var="currentPost" varStatus="status">
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
        <h2>${currentPost.subject} in: ${currentPost.topic.forum.subType} > ${currentPost.topic.forum.title} > ${currentPost.topic.subject}</h2>
        <p><fmt:message key="community.searchResultForumPost.by"/> <a href="#" id="userName" class="link">${currentPost.user.account}</a> &#xbb <span class="date">${currentPost.lastUpdate}</span></p>
        <p class="textPost">${currentPost.text}</p>
    </div>
    <div id="postProfile">
    	<ul>
        	<li><a href="#" id="userName" class="link">${currentPost.user.account}</a></li>
            <li><fmt:message key="community.searchResultForumPost.communityUser"/></li>
            <li><fmt:message key="community.searchResultForumPost.posts"/> <span>${currentPost.user.forumNumberOfPost}</span></li>
            <li><fmt:message key="community.searchResultForumPost.joined"/> <span>${currentPost.user.forumJoinedDate}</span></li>
        </ul>
    </div>
    <div id="online" class="visible"></div> <!--  Se l'utente � loggato in quel momento inserire la class "visible" a questo div -->
</div>
</c:forEach>

<div id="forumPaginate">
    <c:set var="paginationData">
		<bia:paginationForum page="${simpleSearchResultPage}"/>
	</c:set>
	
	${paginationData}
	
	<div id="jumpToDiv">
    	<fmt:message key="community.searchResultForumPost.jumpTo"/>
        <form id="jumpToForm" action="/src/SimpleSearch.do" method="post">
            <select id="selectForum" name="selectForum" selected""="" class="selectform_long">
                <option value="" selected="selected"><fmt:message key="community.searchResultForumPost.selectFor"/></option>
            </select>
            <input id="go" type="submit" title="go" value="Go" class="buttonMini button_mini">
        </form>
    </div>
 
</div>
					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; <fmt:message key="community.searchResultForumPost.returnTo"/> <span><fmt:message key="community.searchResultForumPost.boardIndex"/></span> <fmt:message key="community.searchResultForumPost.forum"/></a>


<!-- <div id="deletePostModal" title="Delete post" style="display:none">  -->
<!-- 	<p> -->
<!-- 		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span> -->
<!-- 		Are you sure you want to delete this post? -->
<!-- 	</p> -->
<!-- </div> -->


	<script type="text/javascript">
	$j.extend($j.expr[":"], {
		  "containsIgnoreCase": function(elem, i, match, array) {
		     return (elem.textContent || elem.innerText || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
		}});  	
	
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
			
			//MD: This code highlight the word inside the posts
			var test = [];
			test = $j('.search').text().split(" ");
			
			$j("#post > div > p").each(function(){
				var newText = $j(this).text().split(" ").join("</span> <span class='toRemove'>");
			 	newText = "<span class='toRemove'>" + newText + "</span>";
			  	for(var i = 0; i < test.length; i++){
			  		$j(this).html(newText).find('span').end().find(":containsIgnoreCase('" + test[i] + "')").wrap("<span class='highlighted' />");
			  		newText = $j(this).html();
			  	}
				$j(".toRemove").contents().unwrap();

			});				
// 			$j(".textPost:contains('${yourSearch}')").append($j('<span class="highlighted"></span>'));
			
			$j('.paginateForumButton').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.paginateForumButton').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
