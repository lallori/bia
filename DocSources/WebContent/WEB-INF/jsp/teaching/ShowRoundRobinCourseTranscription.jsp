<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${topic.document.entryId}"/>
	</c:url>
	
	<c:url var="baseUrl" value="/teaching/ShowCourseTranscription.do">
		<c:param name="transcriptionMode" value="R" />
	</c:url>
	
	<c:url var="RenameTopicURL" value="/de/community/RenameForumTopic.json" />

	<h6>ROUND ROBIN TRANSCRIPTION</h6>
	
	<div id="topicTitleSection">
		<c:choose>
			<c:when test="${topic.user.account == account}">
				<a id="changeTopicTitle" href="${RenameTopicURL}" title="change topic title"><img src="<c:url value="/images/forum/button_edit.png"/>"/></a>
			</c:when>
			<c:otherwise>
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
					<a id="changeTopicTitle" href="${RenameTopicURL}" title="change topic title"><img src="<c:url value="/images/forum/button_edit.png"/>"/></a>
				</security:authorize>
			</c:otherwise>
		</c:choose>
		<h2 id="topicTitle_${topic.topicId}">${topic.subject}</h2>
		
		<c:if test="${!subscribed}">
			<c:url var="SubscribeForumTopicURL" value="/teaching/SubscribeForumTopic.json">
				<c:param name="topicId" value="${topic.topicId}"/>
			</c:url>
			<a href="${SubscribeForumTopicURL}" id="subscribe" class="buttonMedium button_medium">Subscribe</a>
		</c:if>
		<c:if test="${subscribed}">
			<c:url var="UnsubscribeForumTopicURL" value="/teaching/UnsubscribeForumTopic.json">
				<c:param name="topicId" value="${topic.topicId}"/>
			</c:url>
			<a href="${UnsubscribeForumTopicURL}" id="unsubscribe" class="buttonMedium button_medium">Unsubscribe</a>
		</c:if>
	</div>
	
	<hr id="upperSeparator"/>
	
	<c:if test="${postsPage.list.size() eq 0}">
		<span class="paginateActive" style="display: none;" href="${baseUrl}&topicId=${topic.topicId}&entryId=${topic.document.entryId}&completeDOM=false" />
		<p>There are no posts.</p>
	</c:if>
	
	<c:if test="${postsPage.list.size() gt 0}">
	
		<div id="forumPaginate_upper">
		    <c:set var="paginationData">
				<bia:paginationCourseTopic page="${postsPage}" topicId="${topic.topicId}" buttonClass="intercepted" baseUrl="${baseUrl}" onlyInnerArgs="false"/>
			</c:set>
			${paginationData}
		</div>
		
		<input:hidden id="clientEditing" />
		
		<c:forEach items="${postsPage.list}" var="extendedPost" varStatus="status">
			<c:url var="ReportForumPostURL" value="/community/ReportForumPost.json">
				<c:param name="postId" value="${extendedPost.post.postId}"/>
				<c:param name="forumId" value="${extendedPost.post.forum.forumId}"/>
				<c:param name="topicId" value="${extendedPost.post.topic.topicId}"/>
			</c:url>
		
			<c:url var="ReplyWithQuotePostURL" value="/teaching/ShowEditRoundRobinPost.do">
				<c:param name="topicId" value="${extendedPost.post.topic.topicId}"/>
				<c:param name="postId" value="${extendedPost.post.postId}"/>
				<c:param name="quote" value="true"/>
			</c:url>
			
			<c:url var="EditForumPostURL" value="/teaching/ShowEditRoundRobinPost.do">
				<c:param name="postId" value="${extendedPost.post.postId}"/>
				<c:param name="topicId" value="${extendedPost.post.topic.topicId}"/>
				<c:param name="quote" value="false"/>
			</c:url>
			
			<c:url var="DeleteForumPostURL" value="/teaching/DeleteRoundRobinPost.json">
				<c:param name="postId" value="${extendedPost.post.postId}"/>
				<c:param name="topicId" value="${extendedPost.post.topic.topicId}"/>
			</c:url>
			
			<div id="postTable_${extendedPost.post.postId}" class="postTable">
				<div class="post">
					<div class="title">
					
						<h2>${extendedPost.post.subject}</h2>
						
						<div class="topicIcons">
							<c:choose>
								<c:when test="${extendedPost.post.user.account == account}">
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
							<a id="quoteLink_${extendedPost.post.postId}" href="${ReplyWithQuotePostURL}" class="quotePost" title="Quote this post"></a>
						</div>
					</div>
					<table class="by" style="width: 100%;">
						<tr>
							<td width="40%"><p>by <a href="<c:url value='/community/ShowUserProfileForum.do'/>?account=${extendedPost.post.user.account}&completeDOM=true" target="_blank" id="userName_postId_${extendedPost.post.postId}" class="link">${extendedPost.post.user.account}</a>&#xbb <span class="date"><fmt:formatDate value="${extendedPost.post.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p></td>
							<td width="20%">
								<c:choose>
									<c:when test="${extendedPost.post.updater == null || extendedPost.post.user.account == extendedPost.post.updater.account}">
									</c:when>
									<c:otherwise>
										<a title='<fmt:message key="community.forum.topic.editedByAdministrator" />' href="<c:url value='/community/ShowUserProfileForum.do'/>?account=${extendedPost.post.updater.account}&completeDOM=true" target="_blank" class="linkUpdater" id="updaterName_postId_${extendedPost.post.postId}">${extendedPost.post.updater.account}</a>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="40%">
								<div id="postLocation_${extendedPost.post.postId}" class="postLocation" style="margin-left: 10px;">
									<div class="folioDetailsContainer">
										<c:choose>
											<c:when test="${extendedPost.getVolumeFragment() != null}">
												Volume&nbsp;<span class="volumeFragment">${extendedPost.getVolumeFragment()}</span>
												<c:if test="${extendedPost.getInsertFragment() != null}">
													-&nbsp;Insert&nbsp;<span class="insertFragment">${extendedPost.getInsertFragment()}</span>
												</c:if>
												-&nbsp;Folio&nbsp;<span class="folioFragment">${extendedPost.getFolioFragment()}</span>
											</c:when>
											<c:otherwise>
												No folio details
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<hr/>
					<div id="postText_${extendedPost.post.postId}">${extendedPost.post.text}</div>
				</div>
				<div class="postProfile">
					<ul>
						<li>
							<c:if test="${extendedPost.post.user.portrait}">
								<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
									<c:param name="account" value="${extendedPost.post.user.account}" />
									<c:param name="time" value="${time}" />
								</c:url>
								<img src="${ShowPortraitUserURL}" class="avatar"/>
							</c:if>
							<c:if test="${!extendedPost.post.user.portrait}">
								<img class="avatar" src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
							</c:if>
							<a href="<c:url value='/community/ShowUserProfileForum.do'/>?account=${extendedPost.post.user.account}&completeDOM=true" target="_blank" id="userName" class="link">${extendedPost.post.user.account}</a>
						</li>
						<c:if test="${not empty maxAuthorities[extendedPost.post.user.account]}">
							<li>${maxAuthorities[extendedPost.post.user.account].description}</li>
						</c:if>
						<li>Posts: <span>${extendedPost.post.user.forumNumberOfPost}</span></li>
						<li>Joined: <span>${extendedPost.post.user.forumJoinedDate}</span></li>
					</ul>
				</div>
				<c:choose>
					<c:when test="${bia:contains(onlineUsers, extendedPost.post.user.account)}">
						<div class="online visible"></div>
					</c:when>
					<c:otherwise>
				    	<div class="online"></div>
					</c:otherwise>
				</c:choose>
			</div>
			
		</c:forEach>
		
		<div id="forumPaginate_lower">
			<c:set var="paginationData">
				<bia:paginationCourseTopic page="${postsPage}" topicId="${topic.topicId}" buttonClass="intercepted"  baseUrl="${baseUrl}" onlyInnerArgs="false"/>
			</c:set>
			${paginationData}
		</div>
		
	</c:if>
	
	<div id="subscribeModal" title="Subscribe topic" style="display: none;"> 
		<p>
			<fmt:message key="community.forum.topic.subscribed.yes"/>
		</p>
	</div>
	
	<div id="unsubscribeModal" title="Unsubscribe topic" style="display: none;"> 
		<p>
			<fmt:message key="community.forum.topic.subscribed.no"/>
		</p>
	</div>
	
	<div id="changeTitleModal" title='<fmt:message key="community.forum.tooltip.changeTitle" />' style="display: none;">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.messages.changeTitleMessage" />
			<form id="changeTitleModalForm" method="post">
				<input id="title" name="title" type="text" style="width: 98%" value="" />
				<input id="topicId" name="topicId" type="hidden" value="" />
			</form>
			<div id="changeTitleError" style="display: none; color: red;">Cannot leave empty title!!!</div>
		</p>
	</div>
	
	<script>
		$j(document).ready(function() {
			var _this = this;
			
			/*$j(".postLocation").each(function() {
				var postLocId = $j(this).attr('id');
				var id = postLocId.substring(postLocId.indexOf('_') + 1, postLocId.length);
				var comment = $j("#postText_" + id).contents().filter(function() {
					return this.nodeType === 8; 
				}).first();
				if (typeof $j(comment).get(0) !== 'undefined') {
					$j(this).html($j(comment).get(0).nodeValue);
				} else {
					$j(this).html('<div class="folioDetailsContainer">No folio details</div>');
					console.log('No folio details in post [' + id + ']');
				}
			});*/
			
			$j("#clientEditing").val(${editingMode});
			
			/** Button and anchor links handler definitions **/
			
			$j("#changeTopicTitle").click(function() {
				var topicId = ${topic.topicId};
				
				// set the topic identifier in the changeTitleModalForm
				$j("#changeTitleModalForm #topicId").val(topicId);
				// ...and set the beginning title
				$j("#changeTitleModalForm #title").val($j("#topicTitle_" + topicId).text());
				
				$j("#changeTitleModal").dialog('open');
				return false;
				
			});
			
			$j('.deletePost').click(function(){
				$j('#deletePostModal').data('deleteUrl', $j(this).attr('href')).dialog('open');
				return false;
			});
			
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
			
			$j(".intercepted").click(function() {
				$j("#postsContainer").load($j(this).attr('href')+'&editingMode='+$j("#clientEditing").val(), function(responseText, statusText, xhr) {
					var _this = $j(this);
					if (statusText !== 'error') {
						setTimeout(function() {
							console.log('Scrolling to top');
							_this.scrollTo(0, 0);
			    		},200);
					} else {
						// TODO: handle error
					}
				});
				return false;
			});
			
			/*$j(".link,.linkUpdater").click(function(){
				_this.title = '${fn2:getApplicationProperty("project.name")}';
				$j("body").load($j(this).attr("href") + "&completeDOM=true");
				return false;
			});*/
			
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
			
			$j("#subscribe").click(function() {
				$j.ajax({
					type: "POST",
					url: $j(this).attr('href'),
					async: false,
					success: function(json) {
						if (json.subscription) {
							$j("#subscribeModal").dialog({
								  autoOpen : false,
								  modal: true,
								  resizable: false,
								  width: 300,
								  height: 130, 
								  buttons: {
									  Ok: function() {
										  $j(this).dialog("close");
										  $j("#postsContainer").load($j(".paginateActive").attr('href'));
										  return false;
									  }
								  }
							  });
							$j("#subscribeModal").dialog('open');
						}
					}
				});
				return false;
			});
			
			$j("#unsubscribe").click(function() {
				$j.ajax({
					type: "POST",
					url: $j(this).attr('href'),
					async: false, 
					success: function(json) {
						if (json.subscription) {
							$j("#unsubscribeModal").dialog({
								  autoOpen : false,
								  modal: true,
								  resizable: false,
								  width: 300,
								  height: 130, 
								  buttons: {
									  Ok: function() {
										  $j(this).dialog("close");
										  $j("#postsContainer").load($j(".paginateActive").attr('href'));
										  return false;
									  }
								  }
							  });
							$j("#unsubscribeModal").dialog('open');
						}
					}
				});
				return false;
			});
			
			/** Dialogs definitions **/
			
			$j("#changeTitleModal").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 350,
				height: 170,
				buttons: {
					Ok: function() {
						var newTitle = $j("#changeTitleModalForm #title").val();
						if (newTitle === "") {
							$j("#changeTitleError").show();
							return;
						}
						$j.ajax({ 
							type: "POST", 
							url: "${RenameTopicURL}",
							data: $j("#changeTitleModalForm").serialize(),
							async: false, 
							success: function(json) {
				 				if (json.operation == 'OK') {
				 					$j("#topicTitle_${topic.topicId}").text(newTitle);
				 				} else {
				 					$j("#changeTitleError").hide();
				 					alert('The operation failed on server...cannot proceed!!!');
				 				}
				 				$j("#changeTitleModal").dialog('close');
							},
							error: function(data) {
								$j("#changeTitleError").hide();
								$j("#changeTitleModal").dialog('close');
								alert('Server error...operation aborted!!!');
							}
						});
						return false;	
					},
					Cancel: function() {
						$j("#changeTitleError").hide();
						$j("#changeTitleModal").dialog('close');
						return false;
					}
				},
				close: function(event, ui) {
					$j("#changeTitleError").hide();
					$j("#changeTitleModal").dialog('close');
					return false;
				}
			});
			
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
								if (typeof data.error !== 'undefined') {
									$j("#errorMsg").text('Post was not deleted: ' + data.error);
								} else {
									$j("#errorMsg").text('Post was not deleted due to a server error...please retry!');
								}
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
	
