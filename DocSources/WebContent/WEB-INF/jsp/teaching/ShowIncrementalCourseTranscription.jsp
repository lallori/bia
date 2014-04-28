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
	
	<c:url var="baseUrl" value="/teaching/ShowCourseTranscription.do">
		<c:param name="transcriptionMode" value="I" />
	</c:url>

	<h6>COURSE TRANSCRIPTION</h6>
	
	<div id="titleSection">
		<h2>${topic.subject}</h2>
		
		<!-- <a href="${ShowDocumentURL}" class="buttonMedium button_medium" id="showRecord">Show record</a> -->
		<a href="${ShowCourseResourcesURL}" id="goCourseResources" class="buttonMedium button_medium" style="float: right">Course Resources</a>
	</div>
	
	<hr />
	
	<c:if test="${postsPage.list.size() eq 0}">
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
			<c:set var="paginationData">
				<bia:paginationCourseTopic page="${postsPage}" topicId="${topic.topicId}" buttonClass="intercepted"  baseUrl="${baseUrl}" onlyInnerArgs="false"/>
			</c:set>
			${paginationData}
		</div>
		
	</c:if>
	
	<script>
		$j(document).ready(function() {
			var _this = this;
			
			$j("#clientEditing").val(${editingMode});
			
			/** Button and anchor links handler definitions **/
			
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
			
		});
	</script>