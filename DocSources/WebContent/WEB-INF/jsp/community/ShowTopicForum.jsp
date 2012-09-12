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
	
	<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
		<c:param name="forumId" value="${topic.forum.forumId}"/>
	</c:url>

<h2>${topic.subject }</h2>

<div id="topicActions">
	<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><img src="<c:url value="/images/forum/img_reply.png"/>" alt="post a reply" width="17" height="15" /><span class="button_text">Post a <b>reply</b></span></a>
    <div id="searchThisForumFormDiv">
        <form id="SearchForumThis" action="<c:url value="/community/SimpleSearchForumPost.do"/>" method="post">
            <input id="searchForumThisText" name="searchInForum" type="text" value="Search this forum...">
            <input id="search" type="submit" title="Search" value="Search"/>
        </form>
    </div>
    <a href="#" id="printButton" class="buttonMedium"><img src="/DocSources/images/forum/img_print.png" alt="Print thread" width="17" height="15" /><span class="button_text">Print thread</span></a>
</div>

<c:forEach items="${postsPage.list}" var="currentPost" varStatus="status">
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
	
	<c:url var="EditForumPostURL" value="/community/EditForumPost.do">
		<c:param name="postId" value="${currentPost.postId}"/>
		<c:param name="forumId" value="${currentPost.forum.forumId}"/>
		<c:param name="topicId" value="${currentPost.topic.topicId}"/>
	</c:url>
	
	<c:url var="DeleteForumPostURL" value="/community/DeletePost.json">
		<c:param name="postId" value="${currentPost.postId}"/>
	</c:url>
	
	<div id="postTable">
	<div id="topicIcons">
		<c:choose>
		<c:when test="${currentPost.user.account == account}">
			<a href="${EditForumPostURL}" class="editPost" title="Edit this post"></a>
			<a href="${DeleteForumPostURL}" class="deletePost" title="Delete post"></a>
		</c:when>
		<c:otherwise>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				<a href="${DeleteForumPostURL}" class="deletePost" title="Delete post"></a>
			</security:authorize>
		</c:otherwise>
		</c:choose>
        <a href="${ReportForumPostURL}" class="reportPost" title="Report this post"></a>
        <a href="${ReplyWithQuoteForumPostURL}" class="quotePost" title="Reply with quote"></a>
    </div>
    <div id="post">
        <h2>${currentPost.subject}</h2>
        <p>by <a href="#" id="userName" class="link">${currentPost.user.account}</a> » <span class="date">${currentPost.lastUpdate}</span></p>
        <p>${currentPost.text}</p>
    </div>
    <div id="postProfile">
    	<ul>
        	<li><a href="#" id="userName" class="link">${currentPost.user.account}</a></li>
            <li>Community User</li>
            <li>Posts: <span>${currentPost.user.forumNumberOfPost}</span></li>
            <li>Joined: <span>${currentPost.user.forumJoinedDate}</span></li>
        </ul>
    </div>
    <div id="online" class="visible"></div> <!--  Se l'utente è loggato in quel momento inserire la class "visible" a questo div -->
</div>
</c:forEach>

<div id="forumPaginate">
    <c:set var="paginationData">
		<bia:paginationForum page="${postsPage}"/>
	</c:set>
	
	<div id="jumpToDiv">
    	Jump to:
        <select id="selectForum" name="selectForum" selected""="" class="selectform_long">
        	<option value="" selected="selected">Select a Forum</option>
        </select>
        <input id="go" title="go" value="Go" class="buttonMini">
    </div>
	
	${paginationData}
 
</div>
					


<c:url var="ShowForumOfTopicURL" value="/community/ShowForum.do">
	<c:param name="forumId" value="${topic.forum.forumId}"></c:param>
</c:url>
<a href="${ShowForumOfTopicURL}" class="returnTo">&larr; Return to <span>${topic.forum.title}</span> Forum</a>


<div id="deletePostModal" title="Delete post" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Are you sure you want to delete this post?
	</p>
	
	<input type="hidden" value="" id="deleteUrl"/>
</div>

<div id="notDeletePost" title="Delete post" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Not deleted
	</p>
</div>


	<script type="text/javascript">
		$j(document).ready(function() {
			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
   				$j("#chronologyDiv").html(json.chronology);
				$j("#selectForum").append(json.selectChronology);
				$j("#selectForum").append("<option value='${ShowForumOfTopicURL}'>${topic.forum.title}</option>");
				$j(".arrowForum").css('display','');
				$j(".forum").css('display','');
   			}});

			$j('.pageHref').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.pageHref').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.boardIndex').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.boardIndex').live('click', function() {
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
			
			$j('.editPost').die();
			$j('.editPost').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			})
			
			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j("#go").click(function(){
				if($j("#selectForum option:selected").val() == '')
					return false;
				else{
					$j("#main").load($j("#selectForum option:selected").val());
					return false;
				}
			});
			
			$j('.paginateForumButton').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.paginateForumButton').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			if("${topic.topicId}" == ''){
				$j('#postReply').css("visibility", "hidden");
			}
			
			$j('#searchForumThisText').click(function(){
				$j(this).val('');
				return false;
			});
			
			$j('#SearchForumThis').submit(function (){
				$j("#main").load($j(this).attr("action") + '?searchForumAllText=' + $j("#searchForumThisText").val() + "&topicId=${topic.topicId}&sortResults=POST_TIME&order=asc");
				return false;
			});
			
			$j('.deletePost').click(function(){
				$j('#deletePostModal').dialog('open');
				$j('#deleteUrl').val($j(this).attr('href') + '&topicId=${topic.topicId}');
				return false;
			});
			
			$j( "#deletePostModal" ).dialog({
				  autoOpen : false,
				  modal: true,
				  resizable: false,
				  width: 300,
				  height: 130, 
				  buttons: {
					  Yes: function() {
						  $j.ajax({ type:"POST", url:$j("#deleteUrl").val(), async:false, success:function(json) {
				 			    var topicUrl = json.topicUrl;
				 				if (json.operation == 'OK') {
				 					 $j("#main").load(topicUrl);
									 $j( "#deletePostModal" ).dialog('close');
									 return false;
				 				} else {
				 					 $j( "#deletePostModal" ).dialog('close');
									$j("#notDeletePost").dialog({
										  autoOpen : false,
										  modal: true,
										  resizable: false,
										  width: 300,
										  height: 130, 
										  buttons: {
											  Ok: function() {
												  $j(this).dialog("close");
											  }
										  }
									  });
									$j("#notDeletePost").dialog('open');
				 				}
							}});
							return false;
					  },
					  No: function() {
						  $j( "#deletePostModal" ).dialog('close');
					  }
				  }
			  });

		});
	</script>
