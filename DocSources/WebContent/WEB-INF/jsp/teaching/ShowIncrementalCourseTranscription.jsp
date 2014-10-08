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
	
	<c:url var="ShowCourseResourcesURL" value="/community/ShowForum.do">
		<c:param name="forumId" value="${topic.forum.forumId}" />
		<c:param name="completeDOM" value="true" />
	</c:url>
	
	<c:url var="RenameTopicURL" value="/de/community/RenameForumTopic.json" />
	
	<c:url var="OpenCloseCourseTopicURL" value="/teaching/OpenCloseCourseTopic.json">
		<c:param name="courseTopicId" value="${command.topicId}" />
	</c:url>
	
	<c:url var="baseUrl" value="/teaching/ShowCourseTranscription.do">
		<c:param name="transcriptionMode" value="I" />
	</c:url>
	
	<div id="csSection">
		<h6>
			COURSE TRANSCRIPTION
			<c:if test="${topic.locked}">&nbsp;<span style="color: #cc8585">[CLOSED]</span></c:if>
		</h6>
		<c:if test="${not topic.locked}">
			<c:if test="${!subscribed}">
				<c:url var="SubscribeForumTopicURL" value="/teaching/SubscribeForumTopic.json">
					<c:param name="topicId" value="${topic.topicId}"/>
				</c:url>
				<a id="subscribe" href="${SubscribeForumTopicURL}" class="buttonMedium button_medium">Subscribe</a>
			</c:if>
			<c:if test="${subscribed}">
				<c:url var="UnsubscribeForumTopicURL" value="/teaching/UnsubscribeForumTopic.json">
					<c:param name="topicId" value="${topic.topicId}"/>
				</c:url>
				<a id="unsubscribe" href="${UnsubscribeForumTopicURL}" class="buttonMedium button_medium">Unsubscribe</a>
			</c:if>
		</c:if>
	</div>
	
	<div id="topicTitleSection">
		<c:choose>
			<c:when test="${not topic.locked}">
				<a id="closeCourseTranscription" href="#" title="course transcription is opened: click to close it!">
					<img src="<c:url value="/images/forum/img_unlocked.png"/>" />
				</a>
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
			</c:when>
			<c:otherwise>
				<a id="openCourseTranscription" href="#" title="course transcription is closed: click to re-open it!">
					<img src="<c:url value="/images/forum/img_locked.png"/>" />
				</a>
			</c:otherwise>
		</c:choose>
		<h2 id="topicTitle_${topic.topicId}">${topic.subject}</h2>
		
		<!-- <a href="${ShowDocumentURL}" class="buttonMedium button_medium" id="showRecord">Show record</a> -->
		<a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium">Course Resources</a>
	</div>
	
	<hr id="upperSeparator"/>
	
	<c:if test="${postsPage.list.size() eq 0}">
		<span class="paginateActive" style="display: none;" href="${baseUrl}&topicId=${topic.topicId}&entryId=${topic.document.entryId}&completeDOM=false" />
		<p>There are no posts.</p>
	</c:if>
	
	<c:if test="${postsPage.list.size() gt 0}">
	
		<div id="forumPaginate_upper">
			<bia:paginator page="${postsPage}" url="${baseUrl}&topicId=${topic.topicId}&completeDOM=false"
   				thisPageAlias="postPageNumber" totalPagesAlias="postPageTotal" elementsForPageAlias="postsForPage"
   				buttonClass="paginateButton intercepted" activeButtonClass="paginateActive"/>
		</div>
		
		<input:hidden id="clientEditing" />
		
		<c:forEach items="${postsPage.list}" var="extendedPost" varStatus="status">
			<c:url var="EditIncrementalPostURL" value="/teaching/ShowIncrementalEditPost.do">
				<c:param name="postId" value="${extendedPost.post.postId}"/>
				<c:param name="topicId" value="${extendedPost.post.topic.topicId}"/>
				<c:param name="quote" value="false"/>
			</c:url>
			
			<c:url var="DeleteIncrementalPostURL" value="/teaching/DeleteIncrementalPost.json">
				<c:param name="postId" value="${extendedPost.post.postId}"/>
				<c:param name="topicId" value="${extendedPost.post.topic.topicId}"/>
			</c:url>
		
			<div id="postTable_${extendedPost.post.postId}" class="postTable ${bia:getValue(maxAuthorities,extendedPost.post.user.account) == 'STUDENTS' ? '' : 'postTableTeacher'}">
			
				<div class="postProfile">
					<div class="avatarContainer" title="${bia:getValue(maxAuthorities,extendedPost.post.user.account) == 'STUDENTS' ? 'STUDENT' : 'TEACHER'}">
						<c:choose>
							<c:when test="${extendedPost.post.user.portrait}">
								<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
									<c:param name="account" value="${extendedPost.post.user.account}" />
									<c:param name="time" value="${time}" />
								</c:url>
								<img class="avatar" src="${ShowPortraitUserURL}"/>
							</c:when>
							<c:otherwise>
								<img class="avatar" src="<c:url value='/images/1024/img_user.png'/>" alt="User Portrait"/>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="titleContainer">
						<h2>${extendedPost.post.subject}</h2>
						<p>by <a href="<c:url value='/community/ShowUserProfileForum.do'/>?account=${extendedPost.post.user.account}&completeDOM=true" target="_blank" id="userName_postId_${extendedPost.post.postId}" class="link">${extendedPost.post.user.account}</a>&#xbb <span class="date"><fmt:formatDate value="${extendedPost.post.lastUpdate}" pattern="yy-MM-dd HH:mm:ss" /></span></p></td>
						<c:if test="${extendedPost.post.updater != null && extendedPost.post.user.account != extendedPost.post.updater.account}">
							<p class="administratorEdit" title='<fmt:message key="community.forum.topic.editedByAdministrator" />'>
								also by
								<a href="<c:url value='/community/ShowUserProfileForum.do'/>?account=${extendedPost.post.updater.account}&completeDOM=true" target="_blank" class="link" title='<fmt:message key="community.forum.topic.editedByAdministrator" />' id="updaterName_postId_${extendedPost.post.postId}">${extendedPost.post.updater.account}</a>
							</p>
						</c:if>
					</div>
					<div class="volumeContainer">
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
						<div class="topicIcons">
							<c:if test="${not topic.locked}">
								<c:choose>
									<c:when test="${extendedPost.post.user.account == account}">
										<a href="${EditIncrementalPostURL}" class="editPost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Edit this post"></a>
										<a href="${DeleteIncrementalPostURL}" class="deletePost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Delete post"></a>
									</c:when>
									<c:otherwise>
										<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
											<a href="${EditIncrementalPostURL}" class="editPost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Edit this post"></a>
											<a href="${DeleteIncrementalPostURL}" class="deletePost notEditMode" style="${editingMode ? 'display: none;' : ''}" title="Delete post"></a>
										</security:authorize>
									</c:otherwise>
								</c:choose>
							</c:if>
						</div>
				</div>
				<div class="post">
					<div class="transcriptionContainer">
						<div class="transcritpionCaption">Transcription:</div>
						<c:choose>
							<c:when test="${not empty extendedPost.transcription}">
								<div id="transcription_${extendedPost.post.postId}" class="transcription">${extendedPost.transcription}</div>
							</c:when>
							<c:otherwise>
								<div id="postTranscription_${extendedPost.post.postId}" class="transcription">{ Empty transcription }</div>
							</c:otherwise>
						</c:choose>
					</div>
					<c:if test="${not empty extendedPost.post.text}">
						<div id="postText_${extendedPost.post.postId}" class="postText">${extendedPost.post.text}</div>
					</c:if>
				</div>
			</div>
		
		</c:forEach>
		
		<div id="forumPaginate_lower">
			<bia:paginator page="${postsPage}" url="${baseUrl}&topicId=${topic.topicId}&completeDOM=false"
   				thisPageAlias="postPageNumber" totalPagesAlias="postPageTotal" elementsForPageAlias="postsForPage"
   				buttonClass="paginateButton intercepted" activeButtonClass="paginateActive"/>
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
	
	<div id="changeTitleModal" title='<fmt:message key="community.forum.tooltip.changeTitle" />' style="display:none">
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
	
	<div id="openCourseTopicModal" title="Open Course Transcription" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			Do you want to re-open the course transcription?
		</p>
	</div>
	
	<div id="closeCourseTopicModal" title="Close Course Transcription" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			Do you want to close the course transcription?
		</p>
	</div>
	
	<script>
		$j(document).ready(function() {
			var _this = this;
			
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
						// from CourseTranscriptionDOM -> to disable CRUD post buttons
						setEditMode(true);
					}
				});
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
			
			$j(".intercepted").click(function() {
				if (typeof $j(this).attr('href') !== 'undefined') {
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
				}
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
			
			if ($j("#openCourseTranscription").length > 0) {
				$j("#openCourseTranscription").die();
				$j("#openCourseTranscription").live('click', function() {
					$j("#openCourseTopicModal").dialog({
						autoOpen : false,
						modal: true,
						resizable: false,
						scrollable: false,
						width: 310,
						height: 130, 
						buttons: {
							Yes: function() {
								$j.ajax({ 
									type:'POST', 
									url: '${OpenCloseCourseTopicURL}' + '&close=false',
									async: false,
									success: function(json) {
										$j("#openCourseTopicModal").dialog("close");
										if (json.operation = 'OK') {
											window.location.replace('${baseUrl}' + '&topicId=' + '${topic.topicId}' + '&entryId=' + '${topic.document.entryId}' + '&completeDOM=true');
										} else {
											alert('Operation error...please retry or contact the admin!');
										}
										return false;
									},
									error: function() {
										alert('Server error...please retry or contact the admin!');
									}
								});
								return false;
							},
							No: function() {
								$j(this).dialog("close");
							}
						}
					});
					$j("#openCourseTopicModal").dialog('open');
				});
			}
			
			if ($j("#closeCourseTranscription").length > 0) {
				$j("#closeCourseTranscription").die();
				$j("#closeCourseTranscription").live('click', function() {
					$j("#closeCourseTopicModal").dialog({
						autoOpen : false,
						modal: true,
						resizable: false,
						scrollable: false,
						width: 310,
						height: 130, 
						buttons: {
							Yes: function() {
								$j.ajax({ 
									type:'POST', 
									url: '${OpenCloseCourseTopicURL}' + '&close=true',
									async: false,
									success: function(json) {
										$j("#closeCourseTopicModal").dialog("close");
										if (json.operation = 'OK') {
											window.location.replace('${baseUrl}' + '&topicId=' + '${topic.topicId}' + '&entryId=' + '${topic.document.entryId}' + '&completeDOM=true');
										} else {
											alert('Operation error...please retry or contact the admin!');
										}
										return false;
									},
									error: function() {
										alert('Server error...please retry or contact the admin!');
									}
								});
								return false;
							},
							No: function() {
								$j(this).dialog("close");
							}
						}
					});
					$j("#closeCourseTopicModal").dialog('open');
				});
			}
		});
	</script>