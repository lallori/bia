<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${topic.document.entryId}"/>
	</c:url>

	<h6>ROUND ROBIN TRANSCRIPTION</h6>
	
	<h2>${topic.subject}</h2>
	
	<p></p>
	<!-- <a href="${ShowDocumentURL}" class="buttonMedium button_medium" id="showRecord">Show record</a> -->
	
	<c:if test="${postsPage.list.size() eq 0}">
		<p>You have no posts.</p>
	</c:if>
		
	<c:if test="${postsPage.list.size() gt 0}">
	
		<div id="forumPaginate_upper">
		    <c:set var="paginationData">
				<bia:paginationCourseTopic page="${postsPage}" topicId="${topic.topicId}" buttonClass="intercepted" />
			</c:set>
			${paginationData}
		</div>
		
		<input:hidden id="clientEditing" />
		
		<c:forEach items="${postsPage.list}" var="currentPost" varStatus="status">
			<c:url var="ReportForumPostURL" value="/community/ReportForumPost.json">
				<c:param name="postId" value="${currentPost.postId}"/>
				<c:param name="forumId" value="${currentPost.forum.forumId}"/>
				<c:param name="topicId" value="${currentPost.topic.topicId}"/>
			</c:url>
		
			<c:url var="ReplyWithQuotePostURL" value="/teaching/ShowEditRoundRobinPost.do">
				<c:param name="topicId" value="${currentPost.topic.topicId}"/>
				<c:param name="postId" value="${currentPost.postId}"/>
				<c:param name="quote" value="true"/>
			</c:url>
			
			<c:url var="EditForumPostURL" value="/teaching/ShowEditRoundRobinPost.do">
				<c:param name="postId" value="${currentPost.postId}"/>
				<c:param name="topicId" value="${currentPost.topic.topicId}"/>
				<c:param name="quote" value="false"/>
			</c:url>
			
			<c:url var="DeleteForumPostURL" value="/teaching/DeleteRoundRobinPost.json">
				<c:param name="postId" value="${currentPost.postId}"/>
				<c:param name="topicId" value="${currentPost.topic.topicId}"/>
			</c:url>
			
			<div id="postTable_${currentPost.postId}" class="postTable">
				<div class="post">
					<div class="title">
						<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
							<c:param name="topicId" value="${currentPost.topic.topicId}"/>
							<c:param name="forumId" value="${currentPost.topic.forum.forumId}"/>
						</c:url>
						<c:choose>
							<c:when test="${topic.topicId == null}">
								<h2>${currentPost.subject} <i>in</i> <a href="${ShowTopicForumURL}" class="linkTopic">${currentPost.topic.forum.subType} > ${currentPost.topic.forum.title} > ${currentPost.topic.subject}</a></h2>
							</c:when>
							<c:otherwise>
								<h2>${currentPost.subject}</h2>
							</c:otherwise>
						</c:choose>
						<div class="topicIcons">
							<c:choose>
								<c:when test="${currentPost.user.account == account}">
									<a href="${EditForumPostURL}" class="editPost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Edit this post"></a>
									<a href="${DeleteForumPostURL}" class="deletePost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Delete post"></a>
								</c:when>
								<c:otherwise>
									<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
										<a href="${EditForumPostURL}" class="editPost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Edit this post"></a>
										<a href="${DeleteForumPostURL}" class="deletePost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Delete post"></a>
									</security:authorize>
								</c:otherwise>
							</c:choose>
							<!-- <a href="${ReportForumPostURL}" class="reportPost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Report this post"></a> -->
							<a id="quoteLink_${currentPost.postId}" href="${ReplyWithQuotePostURL}" class="quotePost" title="Quote this post"></a>
						</div>
					</div>
					<c:choose>
						<c:when test="${currentPost.updater == null || currentPost.user.account == currentPost.updater.account}">
							<p class="by">by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.user.account}" id="userName_postId_${currentPost.postId}" class="link">${currentPost.user.account}</a>&#xbb <span class="date">${currentPost.lastUpdate}</span></p>
						</c:when>
						<c:otherwise>
							<table class="by">
								<tr>
									<td><p>by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.user.account}" id="userName_postId_${currentPost.postId}" class="link">${currentPost.user.account}</a>&#xbb <span class="date">${currentPost.lastUpdate}</span></p></p></td>
									<td><span class="administratorEdit" title='<fmt:message key="community.forum.topic.editedByAdministrator" />' ></span></td>
									<td><a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.updater.account}" class="linkUpdater" title='<fmt:message key="community.forum.topic.editedByAdministrator" />' id="updaterName_postId_${currentPost.postId}">${currentPost.updater.account}</a></td>
								</tr>
							</table>
						</c:otherwise>
					</c:choose>
			        
					<div id="postText_${currentPost.postId}">${currentPost.text}</div>
				</div>
				<div class="postProfile">
					<ul>
						<li>
							<c:if test="${currentPost.user.portrait}">
								<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
									<c:param name="account" value="${currentPost.user.account}" />
									<c:param name="time" value="${time}" />
								</c:url>
								<img src="${ShowPortraitUserURL}" class="avatar"/>
							</c:if>
							<c:if test="${!currentPost.user.portrait}">
								<img class="avatar" src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
							</c:if>
							<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.user.account}" id="userName" class="link">${currentPost.user.account}</a>
						</li>
						<%-- <li>${maxAuthorities[currentPost.user.account].description}</li> --%>
						<li>Posts: <span>${currentPost.user.forumNumberOfPost}</span></li>
						<li>Joined: <span>${currentPost.user.forumJoinedDate}</span></li>
					</ul>
				</div>
<%-- 				    <c:if test="${bia:contains(onlineUsers, currentPost.user.account)}">
				    	<div class="online visible"></div> <!--  Se l'utente è loggato in quel momento inserire la class "visible" a questo div -->
				    </c:if>
				    <c:if test="${! bia:contains(onlineUsers, currentPost.user.account)}">
				    	<div class="online"></div>
				    </c:if> --%>
			</div>
			
		</c:forEach>
		
		<div id="forumPaginate_lower">
			<c:set var="paginationData">
				<bia:paginationCourseTopic page="${postsPage}" topicId="${topic.topicId}" buttonClass="intercepted" />
			</c:set>
			${paginationData}
		</div>
		
	</c:if>
	
	<script>
		$j(document).ready(function() {
			
			$j("#clientEditing").val(${editingMode});
			
			/** Button and anchor links handler definitions **/
			
			$j(".editPost").click(function() {
				var addNewPostSel = $j("#addNewPost");
				$j("#editPostContainer").load($j(this).attr('href'), function(responseText, statusText, xhr) {
					if (statusText == 'error') {
						$j("#errorMsg").text('There was a server error during the page load: please refresh this page and retry!');
						$j("#errorModal").dialog('open');
					} else {
						$j(addNewPostSel).unbind();
						// from ShowRoundRobintranscriptionDOM -> to disable CRUD post buttons
						setEditMode(true);
					}
				});
				return false;
			});
			
			$j('.deletePost').click(function(){
				$j('#deletePostModal').data('deleteUrl', $j(this).attr('href')).dialog('open');
				return false;
			});
			
			$j(".quotePost").click(function() {
				var _this = $j(this);
				var addNewPostSel = $j("#addNewPost");
				var isEditingMode = $j("#clientEditing").val();
				if (typeof isEditingMode !== 'undefined' && isEditingMode === true) {
					var linkId = $j(this).attr('id');
					var id = linkId.substring(linkId.indexOf('_') + 1, linkId.length);
					var quotePost = new QuotePostGenerator().getQuotePost(id);
					tinyMCE.execInstanceCommand('htmlbox', "mceInsertContent", false, quotePost);
				} else {
					$j("#editPostContainer").load($j(this).attr('href'), function(responseText, statusText, xhr) {
						if (statusText == 'error') {
							$j("#errorMsg").text('There was a server error during the page load: please refresh this page and retry!');
							$j("#errorModal").dialog('open');
						} else {
							if (typeof addNewPostSel !== 'undefined' && addNewPostSel != null) {
								$j(addNewPostSel).unbind();
							}
							// from ShowRoundRobintranscriptionDOM -> to disable CRUD post buttons
							setEditMode(true);
						}
					});
				}
				return false;
			});
			
			$j(".intercepted").click(function() {
				$j("#postsContainer").load($j(this).attr('href')+'&editingMode='+$j("#clientEditing").val());
				return false;
			});
			
			/** Dialogs definitions **/
			
			$j("#deletePostModal").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 300,
				height: 130, 
				buttons: {
					Yes: function() {
						$j.ajax({
							type: "POST",
							url: $j(this).data('deleteUrl'),
							async: false,
							success: function(json) {
			 					var topicUrl = json.topicUrl;
			 					if (json.operation == 'OK') {
			 						$j("#postsContainer").load(topicUrl, function(responseText, statusText, xhr) {
			 							if (statusText == 'error') {
			 								$j("#errorMsg").text('Post was deleted but there was a server error during the page load: please refresh this page!');
			 								$j("#errorModal").dialog('open');
			 							}
			 						});
									$j("#deletePostModal").dialog('close');
									return false;
			 					}
			 					
			 					$j("#deletePostModal").dialog('close');
								$j("#errorMsg").text('Post was not deleted due to a server error...please retry!');
								$j("#errorModal").dialog('open');
							},
							error: function(data) {
								$j("#deletePostModal").dialog('close');
								$j("#errorMsg").text('Post was not deleted due to a server error...please retry!');
								$j("#errorModal").dialog('open');
							}
						});
						return false;
					},
					No: function() {
						$j("#deletePostModal").dialog('close');
					}
				}
			});
			
			$j("#errorModal").dialog({
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
		});
	</script>
	
