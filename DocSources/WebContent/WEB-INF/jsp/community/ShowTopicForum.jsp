<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
		<c:param name="forumId" value="${topic.forum.forumId}"/>
	</c:url>
	
	<c:url var="ShowTopicRefreshURL" value="/community/ShowTopicForum.do">
		<c:param name="topicId" value="${topic.topicId}"/>
	</c:url>

<div id="urlActions">
	<a href="#" class="buttonMedium" id="button_refresh"><span><b>Refresh</b> page</span></a>
	<a href="#" class="buttonMedium" id="button_link" title="Use this to copy and paste url for citations"><span>Copy <b>link</b></span></a>
</div>

<h2>${topic.subject }</h2>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
	<c:if test="${topic.forum.document != null && not empty documentExplorer}">
		<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
			<c:param name="entryId" value="${documentExplorer.entryId}"/>
			<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
			<c:param name="flashVersion"   value="false" />
			<c:param name="showHelp" value="true" />
			<c:param name="showThumbnail" value="true" />
		</c:url>
		
		<c:url var="PageTurnerURL" value="/src/ShowManuscriptViewer.do"/>
	
		<input type="hidden" id="currentPage" value="${documentExplorer.image.imageOrder}"/>
		<input type="hidden" id="typeManuscript" value="DOCUMENT"/>
								
		<div id="prevNextButtons" class="thread">
	    	<c:if test="${documentExplorer.image.imageOrder == 1}">
	    		<div id="previousPage">
	        		<a href="#" style="visibility:hidden;"></a>
	    		</div>
	    	</c:if>
	    	<c:if test="${documentExplorer.image.imageOrder > 1}">
	    		<div id="previousPage">
	        		<a href="#"></a>
	    		</div>
	    	</c:if>
	    	<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total}">
	    		<div id="nextPage">
	        		<a href="#" style="visibility:hidden;"></a>
	    		</div>
	    	</c:if>
	    	<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total}">
	    		<div id="nextPage">
	        		<a href="#"></a>
	    		</div>
	    	</c:if>
		</div>
				
		<iframe class="iframeVolumeExplorer" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:100"></iframe>
	</c:if>
</security:authorize>

<div id="topicActions">
	<security:authorize ifAnyGranted="ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS">
		<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do">
			<c:param name="postId" value="0"/>
			<c:param name="forumId" value="${topic.forum.forumId}"/>
			<c:param name="topicId" value="${topic.topicId}"/>
		</c:url>
		<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><span class="button_reply">Post a <b>reply</b></span></a>
	</security:authorize>
    <div id="searchThisForumFormDiv">
        <form id="SearchForumThis" action="<c:url value="/community/SimpleSearchForumPost.do"/>" method="post">
            <input id="searchForumThisText" name="searchInForum" type="text" value="Search this forum...">
            <input id="search" type="submit" title="Search" value="Search"/>
        </form>
    </div>
    <a href="#" id="printButton" class="buttonMedium"><span class="button_print">Print thread</span></a>
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
		<%-- In this case we enter in "my posts page" --%>
    	<c:choose>
    		<c:when test="${topic.topicId == null}">
    			<h2>${currentPost.subject} <i>in</i> ${currentPost.topic.forum.subType} > ${currentPost.topic.forum.title} > ${currentPost.topic.subject}</h2>
    		</c:when>
    		<c:otherwise>
        		<h2>${currentPost.subject}</h2>
        	</c:otherwise>
        </c:choose>
        <p>by <a href="#" id="userName" class="link">${currentPost.user.account}</a> � <span class="date">${currentPost.lastUpdate}</span></p>
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
    <div id="online" class="visible"></div> <!--  Se l'utente � loggato in quel momento inserire la class "visible" a questo div -->
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

<div id="copyLink" title="Copy Link" style="display:none"> 
	<input id="linkToCopy" type="text" value="" size="50"/>
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
			
// 			$j('.boardIndex').die();
// 			Result links have a specific class style on which we attach click live. 
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
				$j("#prevUrl").val($j(".paginateActive").attr("href"));
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
				$j('#postReply').css("display", "none");
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
			
			//To change page in the ManuscriptViewer
			var $currentPage = $j("#currentPage").val();
			
			$j("#previousPage").die();
			$j("#previousPage").live('click', function(){
				$currentPage = parseInt($currentPage) - 1;
				var prevUrl;
				if($j("#typeManuscript").val() == 'DOCUMENT')
					prevUrl = "${PageTurnerURL}?entryId=${documentExplorer.entryId}&imageOrder=" + $currentPage + "&flashVersion=false&showHelp=true&showThumbnail=true";
				else
					prevUrl = "${PageTurnerURL}?summaryId=${volumeExplorer.summaryId}&imageOrder=" + $currentPage + "&flashVersion=false&showHelp=true&showThumbnail=true";
				$j(".iframeVolumeExplorer").attr("src", prevUrl);
				if($currentPage == 1){
					$j("#previousPage").children().css("visibility", "hidden");
				}else{
					$j("#previousPage").children().css("visibility", "visible");
					$j("#nextPage").children().css("visibility", "visible");
				}
				return false;
			});
			
			$j("#nextPage").die();
			$j("#nextPage").live('click', function(){
				$currentPage = parseInt($currentPage) + 1;
				var nextUrl;
				if($j("#typeManuscript").val() == 'DOCUMENT')
					nextUrl = "${PageTurnerURL}?entryId=${documentExplorer.entryId}&imageOrder=" + $currentPage + "&flashVersion=false&showHelp=true&showThumbnail=true";
				else
					nextUrl = "${PageTurnerURL}?summaryId=${volumeExplorer.summaryId}&imageOrder=" + $currentPage + "&flashVersion=false&showHelp=true&showThumbnail=true";
				$j(".iframeVolumeExplorer").attr("src", nextUrl);
				if($currentPage == '${documentExplorer.total}'){
					$j("#nextPage").children().css("visibility", "hidden");
				}else{
					$j("#previousPage").children().css("visibility", "visible");
					$j("#nextPage").children().css("visibility", "visible");
				}
				return false;
			});
			
			$j("#button_refresh").die();
			$j("#button_refresh").live('click', function(){
// 				$j("#main").load("${ShowTopicRefreshURL}");
				$j("#main").load($j(".paginateActive").attr('href'));
				return false;
			});
			
			$j("#button_link").die();
			$j("#button_link").live('click', function(){
				$j("#copyLink").css('display','inherit');
				$j("#copyLink").dialog({
					  autoOpen : false,
					  modal: true,
					  resizable: false,
					  scrollable: false,
					  width: 310,
					  height: 130, 
					  buttons: {
						  Ok: function() {
							  $j(this).dialog("close");
						  }
					  },
					  open: function(event, ui) { 
						  $j("#linkToCopy").val('${bia:getApplicationProperty("website.domain")}' + $j(".paginateActive").attr('href') + '&completeDOM=true');
						  $j("#linkToCopy").select();
						  return false;
					  }
				  });
				$j("#copyLink").dialog('open');
			});

		});
	</script>
