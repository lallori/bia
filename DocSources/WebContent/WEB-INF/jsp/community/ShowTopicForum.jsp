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
	
	<c:url var="RenameForumTopicURL" value="/de/community/RenameForumTopic.json" />
	
	<c:url var="BIAHomeURL" value="/Home.do" />
	
	<c:if test="${not empty topic.annotation}">
		<c:url var="ShowMakeTranscribedModalURL" value="/annotation/ShowMakeTranscribedModalWindow.do">
			<c:param name="annotationId" value="${topic.annotation.annotationId}" />
			<c:param name="transcribed" value="true" />
		</c:url>
		
		<c:url var="ShowMakeNotTranscribedModalURL" value="/annotation/ShowMakeTranscribedModalWindow.do">
			<c:param name="annotationId" value="${topic.annotation.annotationId}" />
			<c:param name="transcribed" value="false" />
		</c:url>
	</c:if>

	<c:if test="${not empty topic}">
		<!-- RR: This page is also used to show User's posts (in that case 'topic' is empty) -->
		
		<c:choose>
			<c:when test="${not isTeachingTopic}">
				<!-- RR: not in teaching module section -->
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					<c:set var="adminOK" value="true" />
				</security:authorize>
			</c:when>
			<c:otherwise>
				<!-- RR: teaching module section -->
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
					<c:set var="adminTeacherOK" value="true" />
				</security:authorize>
			</c:otherwise>
		</c:choose>
		
		<div id="otherTopicActions">
			<c:if test="${not topic.locked and not subscribed}">
				<c:url var="SubscribeForumTopicURL" value="/community/SubscribeForumTopic.json">
					<c:param name="forumTopicId" value="${topic.topicId}"/>
				</c:url>
				<a href="${SubscribeForumTopicURL}" class="buttonMedium subscribe" id="followTopic"><span><fmt:message key="community.showTopicForum.subscribe"/></span></a>
			</c:if>
			<c:if test="${not topic.locked and subscribed}">
				<c:url var="UnsubscribeForumTopicURL" value="/community/UnsubscribeForumTopic.json">
					<c:param name="forumTopicId" value="${topic.topicId}"/>
				</c:url>
				<a href="${UnsubscribeForumTopicURL}" class="buttonMedium unsubscribe" id="followTopic"><span><fmt:message key="community.showTopicForum.unsubscribe"/></span></a>
			</c:if>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS, ROLE_STUDENTS">
				<c:if test="${not empty courseTranscriptionURL}">
					<a href="${courseTranscriptionURL}" class="buttonLarge courseTranscription"><span><fmt:message key="community.showTopicForum.backTo"/> <b><fmt:message key="community.showTopicForum.transcription"/></b></span></a>
				</c:if>
			</security:authorize>
		</div>
		
		<div id="urlActions">
			<a href="#" class="buttonMedium" id="button_refresh"><span><b><fmt:message key="community.showTopicForum.refresh"/></b> <fmt:message key="community.showTopicForum.page"/></span></a>
			<a href="#" class="buttonMedium" id="button_link" title="<fmt:message key='community.forum.topic.tooltip.copyLink' />"><span><fmt:message key="community.showTopicForum.copy"/> <b><fmt:message key="community.showTopicForum.link"/></b></span></a>
		</div>
		
		<c:choose>
			<c:when test="${empty adminOK and empty adminTeacherOK}">
				<c:choose>
					<c:when test="${topic.locked}">
						<img title="<fmt:message key='community.forum.topic.tooltip.discussionClosed' />" src="<c:url value='/images/forum/img_locked.png' />" class="marginLR_0_10" />
					</c:when>
					<c:otherwise>
						<img title="<fmt:message key='community.forum.topic.tooltip.discussionOpened' />" src="<c:url value='/images/forum/img_unlocked.png' />" class="marginLR_0_10" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			
				<c:url var="OpenCloseTopicURL" value="/community/OpenCloseTopic.json">
					<c:param name="topicId" value="${topic.topicId}" />
				</c:url>
				
				<c:choose>
					<c:when test="${topic.locked}">
						<a id="openTopic" href="${OpenCloseTopicURL}&close=false" title="<fmt:message key='community.forum.topic.tooltip.locked' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/img_locked.png"/>"/></a>
					</c:when>
					<c:otherwise>
						<a id="closeTopic" href="${OpenCloseTopicURL}&close=true" title="<fmt:message key='community.forum.topic.tooltip.unlocked' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/img_unlocked.png"/>"/></a>
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty topic.annotation}">
					<c:url var="ShowHideAnnotationURL" value="/community/ShowHideAnnotation.json">
						<c:param name="annotationId" value="${topic.annotation.annotationId}" />
					</c:url>
					
					<c:choose>
						<c:when test="${topic.annotation.visible}">
							<a id="hideAnnotation" href="${ShowHideAnnotationURL}&show=false" title="<fmt:message key='community.forum.topic.tooltip.visible' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/img_showed.png"/>"/></a>
						</c:when>
						<c:otherwise>
							<a id="showAnnotation" href="${ShowHideAnnotationURL}&show=true" title="<fmt:message key='community.forum.topic.tooltip.unvisible' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/img_hidden.png"/>"/></a>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${empty topic.annotation.transcribed or not topic.annotation.transcribed}">
							<a id="makeTranscribed" href="${ShowMakeTranscribedModalURL}" title="<fmt:message key='community.forum.topic.tooltip.notTranscribed' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/img_notTranscribed.png"/>"/></a>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${topic.locked}">
									<img src="<c:url value="/images/forum/img_transcribed.png"/>" title="<fmt:message key='community.forum.topic.tooltip.transcribedLocked' />" class="marginLR_0_10"/>
									<c:if test="${topic.annotation.type eq 'TEACHING' and empty topic.annotation.exportedTo}">
										<c:url var="ExportAnnotationDiscussionURL" value="/community/exportAnnotationDiscussion.json">
											<c:param name="annotationId" value="${topic.annotation.annotationId}" />
										</c:url>
										<a id="exportAnnotationDiscussion" href="${ExportAnnotationDiscussionURL}" title="<fmt:message key='community.forum.topic.tooltip.exportToGeneralQuestions' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/button_exportTo.png"/>"/></a>
									</c:if>
								</c:when>
								<c:otherwise>
									<a id="makeNotTranscribed" href="${ShowMakeNotTranscribedModalURL}" title="<fmt:message key='community.forum.topic.tooltip.transcribed' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/img_transcribed.png"/>"/></a>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${not empty topic.annotation.exportedTo}">
						<img src="<c:url value="/images/forum/img_exported.png"/>" title="<fmt:message key='community.forum.topic.tooltip.exported' />" class="marginLR_0_10" />
					</c:if>
				</c:if>
				<c:if test="${not topic.locked}">
					<a id="changeTopicTitle" href="${RenameForumTopicURL}" title="<fmt:message key='community.forum.topic.tooltip.changeTitle' />" class="marginLR_0_10"><img src="<c:url value="/images/forum/button_edit.png"/>"/></a>
				</c:if>
			</c:otherwise>
		</c:choose>
		
		<h2 id="topicTitle_${topic.topicId}">${topic.subject}</h2>
		
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<c:choose>
				<c:when test="${(not empty topic.document or not empty topic.forum.document) && not empty documentExplorer}">
					<c:set var="document" value="${not empty topic.document ? topic.document : topic.forum.document}" />
					<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
						<c:param name="entryId" value="${documentExplorer.entryId}"/>
						<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
						<c:param name="flashVersion"   value="false" />
						<c:param name="showHelp" value="true" />
						<c:param name="showThumbnail" value="true" />
					</c:url>
					
					<c:url var="PageTurnerURL" value="/src/ShowManuscriptViewer.do"/>
					
					<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
						<c:param name="entryId" value="${document.entryId}"/>
					</c:url>
					
					<input type="hidden" id="currentPage" value="${documentExplorer.image.imageOrder}"/>
					<input type="hidden" id="typeManuscript" value="DOCUMENT"/>
					
					<p>${topic.forum.description}</p>
					<a href="${ShowDocumentURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.showTopicForum.showRecord"/></a>
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
							
					<iframe class="iframeVolumeExplorer" onload="iFrameHasLoaded();" 
						scrolling="no" marginheight="0" marginwidth="0" 
						src="${manuscriptViewerURL}" style="z-index:100"></iframe>
				</c:when>
				<c:when test="${(not empty topic.document or not empty topic.forum.document) && empty documentExplorer}">
					<p></p>
					<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
						<c:param name="entryId" value="${topic.forum.document.entryId}"/>
					</c:url>
					<a href="${ShowDocumentURL}" class="buttonMedium button_medium" id="showRecord"><fmt:message key="community.showTopicForum.showRecord"/></a>		
				</c:when>
				<c:when test="${not empty topic.annotation}">
					<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
						<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
						<c:param name="volNum" value="${volumeExplorer.volNum}"/>
						<c:param name="volLetExt" value="${volumeExplorer.volLetExt}"/>
						<c:param name="annotationId" value="${topic.annotation.annotationId}" />
						<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
						<c:param name="flashVersion"   value="false" />
						<c:param name="showHelp" value="true" />
						<c:param name="showThumbnail" value="true" />
					</c:url>

					<iframe class="iframeVolumeExplorer" onload="iFrameHasLoaded();"
						scrolling="no" marginheight="0" marginwidth="0"
						src="${manuscriptViewerURL}" style="z-index: 100"></iframe>
				</c:when>
				<c:when test="${not empty topic.forum.place}">
					<p></p>
					<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
						<c:param name="placeAllId" value="${topic.forum.place.placeAllId}"/>
					</c:url>
					<a href="${ShowPlaceURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.showTopicForum.showRecord"/></a>
				</c:when>
				<c:when test="${not empty topic.forum.person}">
					<p></p>
					<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
						<c:param name="personId" value="${topic.forum.person.personId}"/>
					</c:url>
					<a href="${ShowPersonURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.showTopicForum.showRecord"/></a>
				</c:when>
				<c:when test="${not empty topic.forum.volume}">
					<p></p>
					<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
						<c:param name="summaryId" value="${topic.forum.volume.summaryId}"/>
					</c:url>
					<a href="${ShowVolumeURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.showTopicForum.showRecord"/></a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</security:authorize>
	
		<div id="topicActions">
		
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS, ROLE_STUDENTS">
				<c:if test="${not empty courseTranscriptionURL}">
					<div id="postReply_upperButtons">
						<a href="${courseTranscriptionURL}" class="buttonLarge courseTranscription"><span><fmt:message key="community.showTopicForum.backTo"/> <b><fmt:message key="community.showTopicForum.transcription"/></b></span></a>
					</div>
				</c:if>
			</security:authorize>
		
			<c:if test="${not topic.locked}">
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS">
					<c:choose>
						<c:when test="${not empty topic.annotation}">
							<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do">
								<c:param name="postId" value="0"/>
								<c:param name="forumId" value="${topic.forum.forumId}"/>
								<c:param name="topicId" value="${topic.topicId}"/>
								<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
								<c:param name="volNum" value="${volumeExplorer.volNum}"/>
								<c:param name="volLetExt" value="${volumeExplorer.volLetExt}"/>
								<c:param name="annotationId" value="${topic.annotation.annotationId}" />
								<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
							</c:url>
						</c:when>
						<c:otherwise>
							<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do">
								<c:param name="postId" value="0"/>
								<c:param name="forumId" value="${topic.forum.forumId}"/>
								<c:param name="topicId" value="${topic.topicId}"/>
							</c:url>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${not isTeachingTopic or isCoursePerson}">
						<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><span class="button_reply"><fmt:message key="community.showTopicForum.postA"/> <b><fmt:message key="community.showTopicForum.reply"/></b></span></a>
					</c:if>
				</security:authorize>
			</c:if>
			
		    <div id="searchThisForumFormDiv">
		    	<form id="SearchForumThis" action="<c:url value="/community/SimpleSearchForumPost.do"/>" method="post">
		            <input id="searchForumThisText" class="button_small" name="searchInForum" type="text" value="Search this forum...">
		            <input id="search" type="submit" title="Search" value="Search"/>
		        </form>
		    </div>
		<!--     <a href="#" id="printButton" class="buttonMedium button_medium"><span class="button_print">Print discussion</span></a> -->
		</div>
	
	</c:if>
	
	<c:if test="${empty isEmpty}">
	
		<div id="forumPaginate_upper">
		    <c:set var="paginationData">
				<c:choose>
		    		<c:when test="${isTeachingTopic}">
		    			<c:url var="baseUrl" value="/teaching/ShowTopicForum.do">
		    				<c:param name="topicId" value="${topic.topicId}" />
		    				<c:param name="completeDOM" value="false" />
		    			</c:url>
		    			<bia:paginator page="${postsPage}" url="${baseUrl}"
		    				thisPageAlias="postPageNumber" totalPagesAlias="postPageTotal" elementsForPageAlias="postsForPage"
		    				pageViewId="main" buttonClass="paginateButton" activeButtonClass="paginateActive"/>
		    		</c:when>
		    		<c:otherwise>
						<bia:paginationForum page="${postsPage}"/>
		    		</c:otherwise>
		    	</c:choose>
			</c:set>
			
			${paginationData}
		 
		</div>
		
		<c:forEach items="${postsPage.list}" var="currentPost" varStatus="status">
			<c:url var="ReportForumPostURL" value="/community/ReportForumPost.json">
				<c:param name="postId" value="${currentPost.postId}"/>
				<c:param name="forumId" value="${currentPost.forum.forumId}"/>
				<c:param name="topicId" value="${currentPost.topic.topicId}"/>
			</c:url>
			
			<c:url var="DeleteForumPostURL" value="/community/DeletePost.json">
				<c:param name="postId" value="${currentPost.postId}"/>
			</c:url>
			
			<c:choose>
				<c:when test="${not empty topic.annotation}">
					<c:url var="ReplyWithQuoteForumPostURL" value="/community/ReplyForumPost.do">
						<c:param name="postId" value="0"/>
						<c:param name="parentPostId" value="${currentPost.postId}"/>
						<c:param name="forumId" value="${currentPost.forum.forumId}"/>
						<c:param name="topicId" value="${currentPost.topic.topicId}"/>
						<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
						<c:param name="volNum" value="${volumeExplorer.volNum}"/>
						<c:param name="volLetExt" value="${volumeExplorer.volLetExt}"/>
						<c:param name="annotationId" value="${topic.annotation.annotationId}" />
						<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
					</c:url>
					
					<c:url var="EditForumPostURL" value="/community/EditForumPost.do">
						<c:param name="postId" value="${currentPost.postId}"/>
						<c:param name="forumId" value="${currentPost.forum.forumId}"/>
						<c:param name="topicId" value="${currentPost.topic.topicId}"/>
						<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
						<c:param name="volNum" value="${volumeExplorer.volNum}"/>
						<c:param name="volLetExt" value="${volumeExplorer.volLetExt}"/>
						<c:param name="annotationId" value="${topic.annotation.annotationId}" />
						<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
					</c:url>
				</c:when>
				<c:otherwise>
					<c:url var="ReplyWithQuoteForumPostURL" value="/community/ReplyForumPost.do">
						<c:param name="postId" value="0"/>
						<c:param name="parentPostId" value="${currentPost.postId}"/>
						<c:param name="forumId" value="${currentPost.forum.forumId}"/>
						<c:param name="topicId" value="${currentPost.topic.topicId}"/>
					</c:url>
					
					<c:url var="EditForumPostURL" value="/community/EditForumPost.do">
						<c:param name="postId" value="${currentPost.postId}"/>
						<c:param name="forumId" value="${currentPost.forum.forumId}"/>
						<c:param name="topicId" value="${currentPost.topic.topicId}"/>
					</c:url>
				</c:otherwise>
			</c:choose>
		
			<div id="postTable">
				<c:if test="${not empty topic}">
					<div id="topicIcons">
						<c:if test="${not topic.locked and (not isTeachingTopic or isCoursePerson)}">
							<c:choose>
								<c:when test="${currentPost.user.account == account}">
									<a href="${EditForumPostURL}" class="editPost" title="Edit this post"></a>
									<a href="${DeleteForumPostURL}" class="deletePost" title="Delete post"></a>
								</c:when>
								<c:otherwise>
									<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
										<a href="${EditForumPostURL}" class="editPost" title="Edit this post"></a>
										<a href="${DeleteForumPostURL}" class="deletePost" title="Delete post"></a>
									</security:authorize>
								</c:otherwise>
							</c:choose>
							<a href="${ReportForumPostURL}" class="reportPost" title="Report this post"></a>
							<a href="${ReplyWithQuoteForumPostURL}" class="quotePost" title="Reply with quote"></a>
						</c:if>
					</div>
				</c:if>
			    <div id="post">
					<%-- In this case we enter in "my posts page" --%>
					<c:choose>
						<c:when test="${currentPost.topic.forum.subType == 'COURSE' and not empty currentPost.topic.document}">
							<c:url var="ShowTopicForumURL" value="/teaching/ShowCourseTranscription.do">
								<c:param name="topicId" value="${currentPost.topic.topicId}"/>
								<c:param name="entryId" value="${currentPost.topic.document.entryId}"/>
								<c:param name="completeDOM" value="true"/>
							</c:url>
						</c:when>
						<c:when test="${currentPost.topic.forum.subType == 'COURSE'}">
							<c:url var="ShowTopicForumURL" value="/teaching/ShowTopicForum.do">
								<c:param name="topicId" value="${currentPost.topic.topicId}"/>
								<c:param name="forumId" value="${currentPost.topic.forum.forumId}"/>
								<c:param name="completeDOM" value="true"/>
							</c:url>
						</c:when>
						<c:otherwise>
							<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
								<c:param name="topicId" value="${currentPost.topic.topicId}"/>
								<c:param name="forumId" value="${currentPost.topic.forum.forumId}"/>
								<c:param name="completeDOM" value="true"/>
							</c:url>
						</c:otherwise>
					</c:choose>
			    	<c:choose>
			    		<c:when test="${topic.topicId == null}">
			    			<h2>${currentPost.subject} <i>in</i> <a href="${ShowTopicForumURL}" class="linkTopic">${currentPost.topic.forum.subType} > ${currentPost.topic.forum.title} > ${currentPost.topic.subject}</a></h2>
			    		</c:when>
			    		<c:otherwise>
			        		<h2>${currentPost.subject}</h2>
			        	</c:otherwise>
			        </c:choose>
			        <c:choose>
			        	<c:when test="${empty currentPost.updater || currentPost.user.account == currentPost.updater.account}">
			        		<p class="by"><fmt:message key="community.showTopicForum.by"/> <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.user.account}" id="userName_postId_${currentPost.postId}" class="link">${currentPost.user.account}</a>&#xbb <span class="date">${currentPost.lastUpdate}</span></p>
			        	</c:when>
			        	<c:otherwise>
			        		<table class="by">
			        			<tr>
			        				<td><p><fmt:message key="community.showTopicForum.by"/> <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.user.account}" id="userName_postId_${currentPost.postId}" class="link">${currentPost.user.account}</a>&#xbb <span class="date">${currentPost.lastUpdate}</span></p></p></td>
			        				<td><span class="administratorEdit" title='<fmt:message key="community.forum.topic.editedByAdministrator" />' ></span></td>
			        				<td><a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.updater.account}" class="linkUpdater" title='<fmt:message key="community.forum.topic.editedByAdministrator" />' id="updaterName_postId_${currentPost.postId}">${currentPost.updater.account}</a></td>
			        			</tr>
					        </table>
			        	</c:otherwise>
			        </c:choose>
			        
			        <p>${currentPost.text}</p>
			    </div>
			    <div id="postProfile">
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
			        		<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentPost.user.account}" id="userName_postId_${currentPost.postId}" class="link">${currentPost.user.account}</a>
			        	</li>
			            <li>${maxAuthorities[currentPost.user.account].description}</li>
			            <li><fmt:message key="community.showTopicForum.posts"/> <span>${currentPost.user.forumNumberOfPost}</span></li>
			            <li><fmt:message key="community.showTopicForum.joined"/> <span>${currentPost.user.forumJoinedDate}</span></li>
			        </ul>
			    </div>
			    <c:choose>
				    <c:when test="${not empty onlineUsers and bia:contains(onlineUsers, currentPost.user.account)}">
				    	<div id="online" class="visible"></div> <!--  Se l'utente � loggato in quel momento inserire la class "visible" a questo div -->
				    </c:when>
				    <c:otherwise>
				    	<div id="online"></div>
				    </c:otherwise>
			    </c:choose>
			</div>
		</c:forEach>
		
		
		<!-- <div id="topicActions"> -->
		<%-- 	<security:authorize ifAnyGranted="ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS"> --%>
		<%-- 	<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do"> --%>
		<%-- 			<c:param name="postId" value="0"/> --%>
		<%-- 			<c:param name="forumId" value="${topic.forum.forumId}"/> --%>
		<%-- 			<c:param name="topicId" value="${topic.topicId}"/> --%>
		<%-- 	</c:url> --%>
		<%-- 	<a href="${ReplyForumPostURL}" class="buttonMedium button_medium" class="postReply"><span class="button_reply">Post a <b>reply</b></span></a> --%>
		<%-- 	</security:authorize> --%>
		<!-- </div> -->
		<div id="topicActions2">
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS, ROLE_STUDENTS">
				<c:if test="${not empty courseTranscriptionURL}">
					<a href="${courseTranscriptionURL}" class="buttonLarge courseTranscription"><span><fmt:message key="community.showTopicForum.backTo"/> <b><fmt:message key="community.showTopicForum.transcription"/></b></span></a>
				</c:if>
			</security:authorize>
		</div>
		<br />
		<div id="forumPaginate_lower">
		    <c:set var="paginationData">
		    	<c:choose>
		    		<c:when test="${isTeachingTopic}">
		    			<c:url var="baseUrl" value="/teaching/ShowTopicForum.do">
		    				<c:param name="topicId" value="${topic.topicId}" />
		    				<c:param name="completeDOM" value="false" />
		    			</c:url>
		    			<bia:paginator page="${postsPage}" url="${baseUrl}"
		    				thisPageAlias="postPageNumber" totalPagesAlias="postPageTotal" elementsForPageAlias="postsForPage"
		    				pageViewId="main" buttonClass="paginateButton" activeButtonClass="paginateActive"/>
		    		</c:when>
		    		<c:otherwise>
						<bia:paginationForum page="${postsPage}"/>
		    		</c:otherwise>
		    	</c:choose>
			</c:set>
			
			<c:if test="${not empty topic}">
				<div id="jumpToDiv">
			    	<fmt:message key="community.showTopicForum.jumpTo"/>
			        <select id="selectForum" name="selectForum" class="selectform_long">
			        	<option value="" selected="selected"><fmt:message key="community.showTopicForum.selectAForum"/></option>
			        </select>
			        <input id="go" type="submit" title="go" value="Go" class="buttonMini button_mini">
			    </div>
		    </c:if>
			
			${paginationData}
		 
		</div>
	
	</c:if>
	<c:if test="${not empty isEmpty and (empty topic or empty topic.topicId)}">
		<p><fmt:message key="community.showTopicForum.youHaveNoPosts"/></p>
	</c:if>
					


	<c:url var="ShowForumOfTopicURL" value="/community/ShowForum.do">
		<c:param name="forumId" value="${topic.forum.forumId}"></c:param>
	</c:url>
	<a href="${ShowForumOfTopicURL}" class="returnTo">&larr; <fmt:message key="community.showTopicForum.returnTo"/> <span>${topic.forum.title}</span> <fmt:message key="community.showTopicForum.forum"/></a>
	
	
	<div id="deletePostModal" title="Delete post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.showTopicForum.theQuestionDelete"/>
		</p>
		
		<input type="hidden" value="" id="deleteUrl"/>
	</div>
	
	<div id="reportPostModal" title="Report post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.showTopicForum.theQuestionReport"/>
		</p>
		
		<input type="hidden" value="" id="reportUrl"/>
	</div>
	
	<div id="subscribeModal" title="Subscribe topic" style="display:none"> 
		<p>
			<fmt:message key="community.forum.topic.subscribed.yes"/>
		</p>
	</div>
	
	<div id="unsubscribeModal" title="Unsubscribe topic" style="display:none"> 
		<p>
			<fmt:message key="community.forum.topic.subscribed.no"/>
		</p>
	</div>
	
	<div id="notDeletePost" title="Delete post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.showTopicForum.notDeleted"/>
		</p>
	</div>
	
	<div id="notReportPost" title="Report post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.showTopicForum.notReported"/>
		</p>
	</div>
	
	<div id="copyLink" title="Copy Link" style="display:none"> 
		<input id="linkToCopy" type="text" value="" size="50"/>
	</div>
	
	<div id="changeTitleModal" title='<fmt:message key="community.forum.tooltip.changeTitle" />' style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.messages.changeTitleMessage" />
			<form id="changeTitleModalForm" method="post">
				<input id="title" name="title" type="text" style="width: 98%" value="" />
				<input id="topicId" name="topicId" type="hidden" value="" />
			</form>
			<div id="changeTitleError" style="display: none; color: red;"><fmt:message key="community.showTopicForum.cannotLeave"/></div>
		</p>
	</div>
	
	<div id="hideAnnotationModal" title="<fmt:message key='community.forum.topic.annotation.makeHidden' />" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.topic.annotation.hideQuestion" />
		</p>
	</div>
	
	<div id="showAnnotationModal" title="<fmt:message key='community.forum.topic.annotation.makeVisible' />" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.topic.annotation.showQuestion" />
		</p>
	</div>

	<div id="openTopicModal" title="<fmt:message key='community.forum.topic.openTopic' />" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.topic.openTopicQuestion" />
		</p>
	</div>
	
	<div id="closeTopicModal" title="<fmt:message key='community.forum.topic.closeTopic' />" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.topic.closeTopicQuestion" />
		</p>
	</div>
	
	<div id="exportAnnotationDiscussionModal" title="<fmt:message key='community.forum.topic.annotation.exportDiscussion' />" style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.topic.annotation.exportDiscussionQuestion" />
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
			
			var delay = (function(){
				  var timer = 0;
				  return function(callback, ms){
				    clearTimeout (timer);
				    timer = setTimeout(callback, ms);
				  };
				})();
			
			$j("#showRecord").die();
			$j("#showRecord").live('click', function(e){
				e.preventDefault();
				if(window.opener != null){
					window.opener.$j("#body_left").load($j(this).attr('href'));
					window.opener.alert('<fmt:message key="home.showRecordAlertMessage"/>');
				}else{
					//TODO: If the main window is closed
// 					window.opener = window.open("${BIAHomeURL}", "_blank");
					window.alert("Please open BIA application window and reload the forum");
				}
				return false;
			});
			
// 			$j('.boardIndex').die();
// 			Result links have a specific class style on which we attach click live. 
// 			$j('.boardIndex').live('click', function() {
// 				$j("#main").load($j(this).attr("href"));
// 				return false;
// 			});

// 			$j('.quotePost').die();
// 			$j('.quotePost').click(function (){
// 				$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
// 				return false;
// 			});

			$j('.forum').die();
			$j('.forum').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});

			$j('#postReply').click(function (){
				$j("#main").load($j(this).attr("href"));
				$j("#prevUrl").val($j(".paginateActive").attr("href"));
				return false;
			});
			
			$j('.quotePost').click(function (){
				$j("#main").load($j(this).attr("href"));
				$j("#prevUrl").val($j(".paginateActive").attr("href"));
				return false;
			});
			
			$j(".link").click(function(){
				$j("#main").load($j(this).attr("href"));
				$j("#prevUrl").val($j(".paginateActive").attr('href'));
				return false;
			});
			
			$j("#members").click(function(){
				$j("#main").load($j(this).attr('href'));
				$j("#prevUrl").val($j(".paginateActive").attr('href'));
				return false;
			});
			
			$j('.editPost').die();
			$j('.editPost').click(function(){
				$j("#main").load($j(this).attr("href"));
				$j("#prevUrl").val($j(".paginateActive").attr("href"));
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
			
			$j('.reportPost').click(function(){
				$j('#reportPostModal').dialog('open');
				$j('#reportUrl').val($j(this).attr('href'));
				return false;
			});
			
			$j('.deletePost').click(function(){
				$j('#deletePostModal').dialog('open');
				$j('#deleteUrl').val($j(this).attr('href') + '&topicId=${topic.topicId}');
				return false;
			});
			
			$j(".subscribe").click(function() {
				$j.ajax({ type:"POST", url:$j(this).attr('href'), async:false, success:function(json) {
					if(json.subscription){
						$j("#subscribeModal").dialog({
							  autoOpen : false,
							  modal: true,
							  resizable: false,
							  width: 300,
							  height: 130, 
							  buttons: {
								  Ok: function() {
									  $j(this).dialog("close");
									  $j("#main").load($j(".paginateActive").attr('href'));
									  return false;
								  }
							  }
						  });
						$j("#subscribeModal").dialog('open');
					}
				}});
				return false;
			});
			
			$j(".unsubscribe").click(function(){
				$j.ajax({ type:"POST", url:$j(this).attr('href'), async:false, success:function(json) {
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
									  $j("#main").load($j(".paginateActive").attr('href'));
									  return false;
								  }
							  }
						  });
						$j("#unsubscribeModal").dialog('open');
						
					}
				}});
				return false;
			});
			
			<c:if test="${not empty topic}">
			
				$j("#changeTopicTitle").click(function() {
					var topicId = ${topic.topicId};
					
					// set the topic identifier in the changeTitleModalForm
					$j("#changeTitleModalForm #topicId").val(topicId);
					// ...and set the beginning title
					$j("#changeTitleModalForm #title").val($j("#topicTitle_" + topicId).text());
					
					$j("#changeTitleModal").dialog('open');
					return false;
					
				});
				
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
								url: "${RenameForumTopicURL}",
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
						}
					},
					close: function(event, ui) {
						$j("#changeTitleError").hide();
						$j("#changeTitleModal").dialog('close');
						return false;
					}
				});
				
			</c:if>
			
			$j( "#reportPostModal" ).dialog({
				  autoOpen : false,
				  modal: true,
				  resizable: false,
				  width: 300,
				  height: 130, 
				  buttons: {
					  Yes: function() {
						  $j.ajax({ type:"POST", url:$j("#reportUrl").val(), async:false, success:function(json) {
				 			    if (json.operation == 'OK') {
				 					  $j( "#reportPostModal" ).dialog('close');
									 return false;
				 				} else {
				 					$j( "#reportPostModal" ).dialog('close');
									$j("#notReportPost").dialog({
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
									$j("#notReportPost").dialog('open');
				 				}
							}});
							return false;
					  },
					  No: function() {
						  $j( "#reportPostModal" ).dialog('close');
					  }
				  }
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
						  $j("#linkToCopy").val('http://${bia:getApplicationProperty("website.domain")}' + $j(".paginateActive").attr('href') + '&completeDOM=true');
						  $j("#linkToCopy").select();
						  return false;
					  }
				  });
				$j("#copyLink").dialog('open');
			});
			
			if ($j("#makeTranscribed").length > 0) {
				$j("#makeTranscribed").die();
				$j("#makeTranscribed").live('click', function() {
					Modalbox.show($j(this).attr("href"), {title: "Transcribe this highlighted section", width: 350, height: 160});
					return false;
				});
			}
			
			if ($j("#makeNotTranscribed").length > 0) {
				$j("#makeNotTranscribed").die();
				$j("#makeNotTranscribed").live('click', function() {
					Modalbox.show($j(this).attr("href"), {title: "Untranscribe this highlighted section", width: 350, height: 160});
					return false;
				});
			}
			
			if ($j("#showAnnotation").length > 0) {
				
				initDialog('showAnnotationModal', 310, 130, 'showAnnotation');
				
				$j("#showAnnotation").die();
				$j("#showAnnotation").live('click', function() {
					$j("#showAnnotationModal").dialog('open');
					return false;
				});
			}
			
			if ($j("#hideAnnotation").length > 0) {
				
				initDialog('hideAnnotationModal', 310, 130, 'hideAnnotation');
				
				$j("#hideAnnotation").die();
				$j("#hideAnnotation").live('click', function() {
					$j("#hideAnnotationModal").dialog('open');
					return false;
				});
			}
			
			if ($j("#openTopic").length > 0) {
				
				initDialog('openTopicModal', 310, 130, 'openTopic');
				
				$j("#openTopic").die();
				$j("#openTopic").live('click', function() {
					$j("#openTopicModal").dialog('open');
					return false;
				});
			}
			
			if ($j("#closeTopic").length > 0) {
				
				initDialog('closeTopicModal', 310, 130, 'closeTopic');
				
				$j("#closeTopic").die();
				$j("#closeTopic").click(function() {
					$j("#closeTopicModal").dialog('open');
					return false;
				});
			}
			
			if ($j("#exportAnnotationDiscussion").length > 0) {
				
				initDialog('exportAnnotationDiscussionModal', 310, 160, 'exportAnnotationDiscussion');
				
				$j("#exportAnnotationDiscussion").die();
				$j("#exportAnnotationDiscussion").live('click', function() {
					$j("#exportAnnotationDiscussionModal").dialog('open');
					return false;
				});
			}
			
			/**
			 * This function initializes the dialog window linked to the provided button.
			 *
			 * NOTE: It is used for the following buttons
			 *    1 - showAnnotation
			 *    2 - hideAnnotation
			 *    3 - openTopic
			 *    4 - closeTopic
			 *    5 - exportAnnotationDiscussion
			 *
			 * @param $dialogId the dialog DOM element identifier
			 * @param $width the dialog width
			 * @param $height the dialog height
			 * @param $buttonId the button DOM element identifier
			 **/
			function initDialog($dialogId, $width, $height, $buttonId) {
				//console.log('Initialize dialog ' + $dialogId + '\nwith callbackUrl ' + ($j("#" + $buttonId).attr('href')));
				$j("#" + $dialogId).dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					scrollable: false,
					width: $width,
					height: $height, 
					buttons: {
						Yes: function() {
							$j.ajax({ 
								type: 'POST', 
								url: $j("#" + $buttonId).attr('href'),
								cache: false,
								async: false,
								success: function(json) {
									if (json.operation == 'OK') {
										$j("#" + $dialogId).dialog("close");
										$j("#main").load($j(".paginateActive").attr('href'));
									} else {
										alert('Operation error...please retry or contact the admin!');
										$j("#" + $dialogId).dialog("close");
									}
									return false;
								},
								error: function() {
									alert('Server error...please retry or contact the admin!');
								}
							});
							$j(this).dialog('destroy');
							return false;
						},
						No: function() {
							$j(this).dialog("close");
						}
					}
				});
			};
			
			<c:if test="${not empty courseTranscriptionURL}">
				// RR: This function scrolls the browser to the 'PostReply' button after the
				// manuscript viewer has loaded (only for teaching module).
				var scrollToPostsSection = function() {
					setTimeout(function() {
						$j('body').scrollTo('#postReply');
		    		},200);
				};
			</c:if>
			
			window.iFrameHasLoaded = function() {
				if (typeof scrollToPostsSection === 'function') {
					scrollToPostsSection();
				}
			}
			
			//MD: Fix a problem with tinyMCE alert when change page.
			window.onbeforeunload = function() {};
			
		});
	</script>
