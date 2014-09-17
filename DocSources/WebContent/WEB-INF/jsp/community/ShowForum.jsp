<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<!--
	    #### March 12th, 2014 - RR ###
		This page does not include security controls for 'category' and 'forum' types because page 
		could become too big-sized.
		We prefer to filter accesses and visibility in the controller (ShowForumController) so in 
		this page it should be considered:
		  1 - the showed categories and forums are the only allowed for the connected user
		  2 - if connected user, even anonymous one, has few privileges the page is not showed and 
		      the user is redirected to 'access denied page'.
	 -->
	
	<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
		<c:param name="forumId" value="${category.forumId}"/>
	</c:url>
	
	<!-- Main Forum Page -->
	<div id="urlActions">
		<a href="#" class="buttonMedium button_medium" id="button_refresh"><fmt:message key="community.forum.link.refreshPage" /></a>
		<a href="#" class="buttonMedium button_medium" id="button_link" title='<fmt:message key="community.forum.tooltip.copyLink" />'><fmt:message key="community.forum.link.copyLink" /></a>
	</div>
	
	<!-- Category TYPE -->
	<c:if test="${not empty category}">
		<c:if test="${category.option.canHaveSubCategory}">
			<c:url var="ShowForumRefreshURL" value="/community/ShowForum.do">
				<c:param name="forumId" value="${category.forumId}"/>
			</c:url>
		
			<c:forEach items="${subCategories}" var="currentCategory" varStatus="status">
				<!-- RR: Category's identifier appended to div's identifier to avoid duplicate identifiers in DOM -->
				<div id="forumTable_${currentCategory.forumId}">
					<div class="list">
						<div class="rowFirst">
							<div class="one">${currentCategory.title}</div>
							<!-- MD: Hide the second column if the category hasn't threads-->
							<c:choose>
								<c:when test="${currentCategory.dispositionOrder != 1}">
									<div class="two"></div>
								</c:when>
								<c:otherwise>
									<div class="two"><fmt:message key="community.forum.title.threads" /></div>
								</c:otherwise>
							</c:choose>
							<div class="three"><fmt:message key="community.forum.title.discussions" /></div>
							<div class="four"><fmt:message key="community.forum.title.lastPost" /></div>
						</div>

						<c:set var="forums" value="${forumsBySubCategories[currentCategory.forumId]}"/>

						<c:forEach items="${forums}" var="currentForum" varStatus="status">
							<c:url var="forumURL" value="/community/ShowForum.do">
								<c:param name="forumId" value="${currentForum.forumId}" />
							</c:url>
							<!-- <div class="${not status.last ? 'row' : 'rowLast'}">  -->
							<div class="row">
								<div class="one">
									<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />' />
									<a href="${forumURL}" class="forumHref">${currentForum.title}</a>
										<span>${currentForum.description}</span>
								</div>
								<!-- MD: Hide the second column if the category hasn't threads -->
								<c:if test="${currentCategory.dispositionOrder != 1}">
									<div class="two"></div>
								</c:if>
								<c:if test="${currentCategory.dispositionOrder == 1 && currentForum.forumId != bia:getApplicationProperty('forum.identifier.document')}">
									<div class="two">${currentForum.subForumsNumber}</div>
								</c:if>
								<c:if test="${currentCategory.dispositionOrder == 1 && currentForum.forumId == bia:getApplicationProperty('forum.identifier.document')}">
									<div class="two"></div>
								</c:if>
	<%-- 							<div class="two"><span>${currentForum.topicsNumber}</span></div> --%>
								<div class="three">${currentForum.topicsNumber}</div>
								<c:if test="${not empty currentForum.lastPost}">
									<!-- RR: Post's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
									<div class="four"><fmt:message key="community.forum.text.lastPostBy" /> <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentForum.lastPost.user.account}" id="userName_lastPostId_${currentForum.lastPost.postId}" class="link">${currentForum.lastPost.user.account}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
								</c:if>
								<c:if test="${empty currentForum.lastPost}">
									<div class="four"><fmt:message key="community.forum.text.emptyForum" /></div>
								</c:if>
							</div>
                         </c:forEach>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</c:if>
	
	<!-- Forum or forum container -->
	<c:if test="${not empty forum}">
		<!-- Sub Forum Category (for example Documents) Page -->
		<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
			<c:param name="forumId" value="${forum.forumId}"/>
		</c:url>
		
		<c:url var="ShowForumRefreshURL" value="/community/ShowForum.do">
			<c:param name="forumId" value="${forum.forumId}"/>
		</c:url>
		
		<c:url var="EditForumPostURL" value="/community/EditForumPost.do">
			<c:param name="postId" value="0"/>
			<c:param name="forumId" value="${forum.forumId}"/>
			<c:param name="topicId" value="0"/>
		</c:url>
		
		<c:url var="RenameForumURL" value="/de/community/RenameForum.json" />
		
		<c:if test="${forum.hierarchyLevel > 3}">
			<c:choose>
				<c:when test="${forum.subType == 'COURSE'}">
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
						<a id="changeForumTitle" href="${RenameForumURL}" title="change forum title" style="margin-left: 0px; margin-right: 10px;"><img src="<c:url value="/images/forum/button_edit.png"/>"/></a>
					</security:authorize>
				</c:when>
				<c:otherwise>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
						<a id="changeForumTitle" href="${RenameForumURL}" title="change forum title" style="margin-left: 0px; margin-right: 10px;"><img src="<c:url value="/images/forum/button_edit.png"/>"/></a>
					</security:authorize>
				</c:otherwise>
			</c:choose>
		</c:if>
		<h2 id="forumTitle_${forum.forumId}">${forum.title}</h2>
		
		<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<c:if test="${forum.option.canHaveTopics && forum.subType == 'DOCUMENT' && not empty documentExplorer}">
				<c:url var="LoginURL" value="/LoginUser.do" />
				<c:url var="RegisterURL" value="/user/RegisterUser.do" />
				<div>
					<fmt:message key="community.forum.text.notLoggedIn" />&nbsp;
					<fmt:message key="community.forum.text.logIn" />&nbsp;<a href="${LoginURL}" target="_self" class="link"><fmt:message key="community.forum.link.here" /></a>
					<br />
					<fmt:message key="community.forum.text.notRegistered" />&nbsp;
					<fmt:message key="community.forum.text.subscribe" />&nbsp;<a href="${RegisterURL}" target="_self" class="link"><fmt:message key="community.forum.link.here" /></a>&nbsp;<fmt:message key="community.forum.text.forFree" />
				</div>
			</c:if>
		</security:authorize>
		
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<c:if test="${forum.option.canHaveTopics && forum.subType == 'DOCUMENT' && not empty documentExplorer}">
				<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
					<c:param name="entryId" value="${documentExplorer.entryId}"/>
					<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
					<c:param name="flashVersion" value="false" />
					<c:param name="showHelp" value="true" />
					<c:param name="showThumbnail" value="true" />
				</c:url>
				
				<c:url var="PageTurnerURL" value="/src/ShowManuscriptViewer.do"/>

				<input type="hidden" id="currentPage" value="${documentExplorer.image.imageOrder}"/>
				<input type="hidden" id="typeManuscript" value="DOCUMENT"/>
								
				<div id="prevNextButtons">
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
			<c:if test="${forum.option.canHaveTopics && forum.subType == 'VOLUME' && not empty volumeExplorer}">
				<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
					<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
					<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
					<c:param name="flashVersion"   value="false" />
					<c:param name="showHelp" value="true" />
					<c:param name="showThumbnail" value="true" />
				</c:url>
				
				<c:url var="PageTurnerURL" value="/src/ShowManuscriptViewer.do"/>

				<input type="hidden" id="currentPage" value="${volumeExplorer.image.imageOrder}"/>
				<input type="hidden" id="typeManuscript" value="VOLUME"/>
								
				<div id="prevNextButtons">
    				<c:if test="${volumeExplorer.image.imageOrder == 1}">
    					<div id="previousPage">
        					<a href="#" style="visibility:hidden;"></a>
    					</div>
    				</c:if>
    				<c:if test="${volumeExplorer.image.imageOrder > 1}">
    					<div id="previousPage">
        					<a href="#"></a>
    					</div>
    				</c:if>
    				<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total}">
    					<div id="nextPage">
        					<a href="#" style="visibility:hidden;"></a>
    					</div>
    				</c:if>
    				<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total}">
    					<div id="nextPage">
        					<a href="#"></a>
    					</div>
    				</c:if>
				</div>
			
				<iframe class="iframeVolumeExplorer" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:100"></iframe>
			</c:if>
		</security:authorize>
	
		<c:if test="${forum.option.canHaveTopics && forum.subType != 'COURSE'}">
			<security:authorize ifAnyGranted="ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS">
				<div id="topicActions">
					<a href="${EditForumPostURL}" class="buttonMedium button_medium" id="newTopic"><fmt:message key="community.forum.link.newTopic" /></a>
				</div>
			</security:authorize>
		</c:if>
		<c:if test="${forum.hierarchyLevel == 3 &&  forum.title != 'Documents'}">
			<div id="searchDocument">
				<p>${forum.description}</p>
				<p>${forum.forumHelpText}</p>
			</div>
		</c:if>
	
		<c:if test="${forum.option.canHaveSubForum}">
			<c:choose>
				<c:when test="${forum.option.groupBySubForum=='true' && not empty subForumsPage.list}">
					<div id="forumTable">
						<div class="list">
							<div class="rowFirst">
					            <div class="one"><fmt:message key="community.forum.title.thread" /></div>
					            <div class="two"><fmt:message key="community.forum.title.discussions" /></div>
					            <div class="three"><fmt:message key="community.forum.title.views" /></div>
					            <div class="four"><fmt:message key="community.forum.title.lastPost" /></div>
					            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					            	<div class="five"><fmt:message key="community.forum.title.delete" /></div>
					            </security:authorize>
					        </div>
					        
					        <c:forEach items="${subForumsPage.list}" var="currentForum" varStatus="status">
								<c:url var="ShowForumURL" value="/community/ShowForum.do">
									<c:param name="forumId" value="${currentForum.forumId}" />
								</c:url>
								<c:url var="DeleteForumURL" value="/de/community/DeleteForum.json">
									<c:param name="forumId" value="${currentForum.forumId}" />
								</c:url>
								<!-- <div class="${not status.last ? 'row' : 'rowLast'}">  -->						            
								<div class="row">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
										<a href="${ShowForumURL}" class="forumHref">${currentForum.title}</a>
						                <span>${currentForum.description}</span>
						            </div>
			<%-- 			            <div class="two">${fn:length(currentForum.forumTopics)}</div> --%>
									<div class="two">${currentForum.topicsNumber}</div>
						            <div class="three">${currentForum.totalViews == null ? '0' : currentForum.totalViews}</div>
									<c:if test="${not empty currentForum.lastPost}">
										<!-- RR: Post's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
							            <div class="four"><fmt:message key="community.forum.text.lastPostBy" />&nbsp;<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentForum.lastPost.user.account}" id="userName_lastPostId_${currentForum.lastPost.postId}" class="link">${currentForum.lastPost.user.account}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
							        </c:if>
									<c:if test="${empty currentForum.lastPost}">
							            <div class="four"></div>
							        </c:if>
							        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
							        	<div class="five"><a href="${DeleteForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a></div>
							        </security:authorize>
						        </div>
						    </c:forEach>
						</div>
					</div>
					<div id="forumPaginate">
					    <div id="jumpToDiv">
					    	<fmt:message key="community.forum.text.jumpTo" />
					    	<select id="selectForum" name="selectForum" class="selectform_long">
					        	<option value="" selected="selected"><fmt:message key="community.forum.options.selectAForum" /></option>
					        </select>
					        <input id="go" type="submit" title='<fmt:message key="community.forum.tooltip.go" />' value='<fmt:message key="community.forum.inputValue.go" />' class="buttonMini">
					    </div>
						<c:set var="paginationData">
							<bia:paginationForum page="${subForumsPage}"/>
						</c:set>
						
						${paginationData}   
					</div>
					
					<c:url var="ReturnToForumURL" value="/community/ShowForum.do">
						<c:param name="forumId" value="${forum.forumParent.forumId}" />
					</c:url>
					
					<c:if test="${not empty forum.forumParent && forum.forumParent.type != 'CATEGORY'}">
						<a href="${ReturnToForumURL}" class="returnTo">&larr; Return to <span>${forum.forumParent.title}</span></a>
					</c:if>
					
					<c:if test="${not empty forum.forumParent && forum.forumParent.type == 'CATEGORY'}">
						<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; Return to <span>Board Index</span></a>
					</c:if>
				</c:when>
				<c:when test="${forum.option.groupBySubForum=='true' && empty subForumsPage.list}">
					<div id="forumTable">
						<div class="list">
							<div class="rowFirst">
								<div class="one"><fmt:message key="community.forum.title.thread" /></div>
								<div class="two"><fmt:message key="community.forum.title.discussions" /></div>
								<div class="three"><fmt:message key="community.forum.title.views" /></div>
								<div class="four"><fmt:message key="community.forum.title.lastPost" /></div>
								<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
									<div class="five"><fmt:message key="community.forum.title.delete" /></div>
								</security:authorize>
							</div>
							<div class="rowLast">						            
								<div class="one">
									<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
									<a id="viewTopic"><fmt:message key="community.forum.text.noForum" /></a>
									<span>${currentForum.description}</span>
								</div>
								<div class="two">0</div>
								<div class="three">0</div>
								<div class="four"><fmt:message key="community.forum.text.emptyForum" /></div>
								<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
									<div class="five"></div>
								</security:authorize>
							</div>
						</div>
					</div>
					
					<c:url var="ReturnToForumURL" value="/community/ShowForum.do">
						<c:param name="forumId" value="${forum.forumParent.forumId}" />
					</c:url>
					
					<c:if test="${not empty forum.forumParent && forum.forumParent.type != 'CATEGORY'}">
						<a href="${ReturnToForumURL}" class="returnTo"><fmt:message key="community.forum.link.returnTo" /><span>${forum.forumParent.title}</span></a>
					</c:if>
					
					<c:if test="${not empty forum.forumParent && forum.forumParent.type == 'CATEGORY'}">
						<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo"><fmt:message key="community.forum.link.returnToBoardIndex" /></a>
					</c:if>
				</c:when>
				<c:otherwise>
					<!-- In this case we manage forum displaying subforums as topics-->
					<c:url var="ShowForumURL" value="/community/ShowForum.do">
						<c:param name="forumId" value="${forum.forumId}" />
					</c:url>
					<c:if test="${forum.title == 'Documents'}">
						<div id="searchDocument">
	   						<p><fmt:message key="community.forum.messages.documents" /></p>
	    					<div id="topicActions">
	        					<div id="searchThisForumFormDiv">
	            					<form id="SearchForumThis" action="<c:url value="/community/AdvancedSearchForumPost.do"/>" method="post">
	                					<input id="searchForumThisText" name="searchForumThisText" type="text" value='<fmt:message key="community.forum.inputValue.searchDocument" />'>
	                					<input id="searchDocuments" type="submit" title='<fmt:message key="community.forum.tooltip.search" />' value='<fmt:message key="community.forum.inputValue.search" />' class="buttonSmall button_small" disabled="disabled"/>
	                					<input type="hidden" name="displayResults" value="Topics"/>
	                					<input type="hidden" name="forumsId" value="${bia:getApplicationProperty('forum.identifier.document')}"/>
	                					<input type="hidden" name="newSearch" value="true"/>
	                					<input type="hidden" name="sortResults" value="POST_TIME"/>
	                					<input type="hidden" name="order" value="desc"/>
	                				</form>
	                			</div>
	                			<div style="clear:both"></div>
	                		</div>
	            		</div>
	            	</c:if>
            		<div id="${forum.subType == 'DOCUMENT' ? 'documentTable' : 'forumTable'}">
            			<div class="list">
            				<div class="rowFirst">
            					<c:choose>
	            					<c:when test="${forum.subType == 'DOCUMENT'}">
	            						<div class="one"><fmt:message key="community.forum.title.discussion" /></div>
					        			<div class="two"><fmt:message key="community.forum.title.docId" /></div>
					        			<div class="three"><fmt:message key="community.forum.title.replies" /></div>
					        			<div class="four"><fmt:message key="community.forum.title.views" /></div>
					        			<div class="five"><fmt:message key="community.forum.title.lastPost" /></div>
					        			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				            				<div class="six"><fmt:message key="community.forum.title.delete" /></div>
				            			</security:authorize>
	            					</c:when>
	            					<c:otherwise>
	            						<div class="one"><fmt:message key="community.forum.title.discussions" /></div>
					            		<div class="two"><fmt:message key="community.forum.title.replies" /></div>
					            		<div class="three"><fmt:message key="community.forum.title.views" /></div>
					            		<div class="four"><fmt:message key="community.forum.title.lastPost" /></div>
					            		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				            				<div class="five"><fmt:message key="community.forum.title.delete" /></div>
				            			</security:authorize>
	            					</c:otherwise>
            					</c:choose>
            				</div>
           					<c:if test="${not empty subForumsTopicsPage.list}">
								<c:forEach items="${subForumsTopicsPage.list}" var="currentTopic" varStatus="status">
									<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
										<c:param name="topicId" value="${currentTopic.topicId}"/>
										<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
									</c:url>
									<c:url var="DeleteTopicForumURL" value="/de/community/DeleteForumTopic.json">
										<c:param name="topicId" value="${currentTopic.topicId}" />
									</c:url>
									<c:choose>
										<c:when test="${forum.subType == 'DOCUMENT'}">
											<!-- <div class="${not status.last ? 'row' : 'rowLast'}">  -->						            
											<div class="row">						            
												<div class="one">
										            <img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
													<a href="${ShowTopicForumURL}" class="forumHref">
														${currentTopic.subject}
														<c:if test="${currentTopic.locked}">&nbsp;<span class="topicClosed">[CLOSED]</span></c:if>
													</a>
									                <span>
									                	<fmt:message key="community.forum.text.createdBy" />&nbsp;
										            	<!-- RR: Topic's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
									                	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName_topicId_${currentTopic.topicId}" class="link">${currentTopic.user.account}</a>
									                </span>
									            </div>
									            <div class="two">${currentTopic.forum.description} <span>${currentTopic.forum.title}</span></div>
									            <div class="three">${currentTopic.totalReplies - 1}</div>
									            <div class="four">${currentTopic.totalViews}</div>
												<c:if test="${not empty currentTopic.lastPost}">
										            <div class="five">
										            	<fmt:message key="community.forum.text.lastPostBy" />&nbsp;
														<!-- RR: Topic's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
										            	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName_${currentTopic.lastPost.postId}" class="link">${currentTopic.lastPost.user.account}</a>
										            	<span class="date">${currentTopic.lastPost.lastUpdate}</span>
										            </div>
										        </c:if>
												<c:if test="${empty currentTopic.lastPost}">
										            <div class="five"></div>
										        </c:if>
										        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
									        		<div class="six">
									        			<c:if test="${not currentTopic.locked}">
									        				<a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a>
									        			</c:if>
									        		</div>
									        	</security:authorize>
									        </div>
										</c:when>
										<c:otherwise>
											<!-- <div class="${not status.last ? 'row' : 'rowLast'}"> -->						            
											<div class="row">						            
												<div class="one">
								            		<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
									                <a href="${ShowTopicForumURL}" class="forumHref">
									                	${currentTopic.subject}
									                	<c:if test="${currentTopic.locked}">&nbsp;<span class="topicClosed">[CLOSED]</span></c:if>
									                </a>
									                <span>
									                	<fmt:message key="community.forum.text.createdBy" />&nbsp;
										            	<!-- RR: Topic's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
									                	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName_topicId_${currentTopic.topicId}" class="link">${currentTopic.user.account}</a>
									                </span>
									            </div>
									            <div class="two">${currentTopic.totalReplies - 1}</div>
									            <div class="three">${currentTopic.totalViews != null ? currentTopic.totalViews : ''}</div>
												<c:if test="${not empty currentTopic.lastPost}">
										            <div class="four">
										            	<fmt:message key="community.forum.text.lastPostBy" />&nbsp;
														<!-- RR: Post's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
										            	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName_lastPostId_${currentTopic.lastPost.postId}" class="link">${currentTopic.lastPost.user.account}</a>
										            	<span class="date">${currentTopic.lastPost.lastUpdate}</span>
										            </div>
										        </c:if>
												<c:if test="${empty currentTopic.lastPost}">
										            <div class="four"></div>
										        </c:if>
										        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
									        		<div class="five">
									        			<c:if test="${not currentTopic.locked}">
									        				<a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a>
									        			</c:if>
									        		</div>
									        	</security:authorize>
									        </div>
									    </c:otherwise>
									</c:choose>     
							    </c:forEach>
							</c:if>
            			</div>
            		</div>
            		<c:if test="${not empty subForumsTopicsPage.list}">
            			<div id="forumPaginate">
						    <c:set var="paginationData">
								<bia:paginationForum page="${subForumsTopicsPage}"/>
							</c:set>
							
							<div id="jumpToDiv">
						    	<fmt:message key="community.forum.text.jumpTo" />
						    	<select id="selectForum" name="selectForum" class="selectform_long">
						        	<option value="" selected="selected"><fmt:message key="community.forum.options.selectAForum" /></option>
						        </select>
						        <input id="go" type="submit" title='<fmt:message key="community.forum.tooltip.go" />' value='<fmt:message key="community.forum.inputValue.go" />' class="buttonMini">
						    </div>
							
							${paginationData}
							  
						</div>
						
						<c:url var="ReturnToForumURL" value="/community/ShowForum.do">
							<c:param name="forumId" value="${forum.forumParent.forumId}" />
						</c:url>
					
						<c:if test="${not empty forum.forumParent && forum.forumParent.type != 'CATEGORY'}">
							<a href="${ReturnToForumURL}" class="returnTo"><fmt:message key="community.forum.link.returnTo" /><span>${forum.forumParent.title}</span></a>
						</c:if>
						<c:if test="${not empty forum.forumParent && forum.forumParent.type == 'CATEGORY'}">
							<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo"><fmt:message key="community.forum.link.returnToBoardIndex" /></a>
						</c:if>
            		</c:if>
            		<c:if test="${empty subForumsTopicsPage.list}">
						<c:choose>
							<c:when test="${forum.subType == 'DOCUMENT'}">
								<div class="rowLast">						            
									<div class="one">
				            			<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
				                		<a id="viewTopic"><fmt:message key="community.forum.text.noDiscussion" /></a>
				                		<span>${currentForum.description}</span>
				            		</div>
				            		<div class="two"><fmt:message key="community.forum.text.emptyForum" /></div>
				            		<div class="three">0</div>
				            		<div class="four">0</div>
				            		<div class="five"><fmt:message key="community.forum.text.emptyForum" /></div>
				            		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			        					<div class="six"></div>
			        				</security:authorize>
			        			</div>
			        		</c:when>
			        		<c:otherwise>
								<div class="rowLast">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
						                <a id="viewTopic"><fmt:message key="community.forum.text.noDiscussion" /></a>
						                <span>${currentForum.description}</span>
						            </div>
						            <div class="two">0</div>
						            <div class="three">0</div>
						            <div class="four"><fmt:message key="community.forum.text.emptyForum" /></div>
						            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        			<div class="five"></div>
					        		</security:authorize>
						        </div>
						    </c:otherwise>
						</c:choose>  
				    </c:if>
				</c:otherwise>
			</c:choose>
		</c:if>
	
		<c:if test="${forum.option.canHaveTopics}">
			<!-- Topic For Category or Topic for general use forums (questions on bia, paleography, ecc.)-->
			<c:url var="ShowForumURL" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
			</c:url>
			<c:if test="${forum.title == 'Documents'}">
				<div id="searchDocument">
   					<p><fmt:message key="community.forum.messages.documents" /></p>
    				<div id="topicActions">
        				<div id="searchThisForumFormDiv">
            				<form id="SearchForumThis" action="<c:url value="/community/AdvancedSearchForumPost.do"/>" method="post">
               					<input id="searchForumThisText" name="searchForumThisText" type="text" value="Search for a document...">
               					<input id="searchDocuments" type="submit" title='<fmt:message key="community.forum.tooltip.search" />' value='<fmt:message key="community.forum.inputValue.search" />' class="buttonSmall button_small" disabled="disabled"/>
               					<input type="hidden" name="displayResults" value="Topics"/>
                				<input type="hidden" name="forumsId" value="${bia:getApplicationProperty('forum.identifier.document')}"/>
                				<input type="hidden" name="newSearch" value="true"/>
                				<input type="hidden" name="sortResults" value="POST_TIME"/>
                				<input type="hidden" name="order" value="desc"/>
               				</form>
               			</div>
               			<div style="clear:both"></div>
               		</div>
            	</div>
            </c:if>
            
            <c:if test="${forum.subType == 'DOCUMENT' && forum.document != null}">
            	<p></p>
				<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
					<c:param name="entryId" value="${forum.document.entryId}"/>
				</c:url>
				<a href="${ShowDocumentURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.forum.link.showRecord" /></a>
            </c:if>
            <c:if test="${forum.subType != 'DOCUMENT'}">
            	<!-- MD: Inserted the button to view the record on bia -->
				<c:if test="${forum.place != null}">
					<p></p>
					<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
						<c:param name="placeAllId" value="${forum.place.placeAllId}"/>
					</c:url>
					<a href="${ShowPlaceURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.forum.link.showRecord" /></a>
				</c:if>
				<c:if test="${forum.person != null}">
					<p></p>
					<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
						<c:param name="personId" value="${forum.person.personId}"/>
					</c:url>
					<a href="${ShowPersonURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.forum.link.showRecord" /></a>
				</c:if>
				<c:if test="${forum.volume != null}">
					<p></p>
					<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
						<c:param name="summaryId" value="${forum.volume.summaryId}"/>
					</c:url>
					<a href="${ShowVolumeURL}" class="buttonMedium" id="showRecord"><fmt:message key="community.forum.link.showRecord" /></a>
				</c:if>
            </c:if>
            
            <div id="${forum.subType == 'DOCUMENT' ? 'documentTable' : 'forumTable'}">
				<div class="list">
           			<div class="rowFirst">
           				<c:choose>
            				<c:when test="${forum.subType == 'DOCUMENT'}">
            					<div class="one"><fmt:message key="community.forum.title.discussion" /></div>
				        		<div class="two"><fmt:message key="community.forum.title.docId" /></div>
				        		<div class="three"><fmt:message key="community.forum.title.replies" /></div>
				        		<div class="four"><fmt:message key="community.forum.title.views" /></div>
				        		<div class="five"><fmt:message key="community.forum.title.lastPost" /></div>
				        		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            			<div class="six"><fmt:message key="community.forum.title.delete" /></div>
			            		</security:authorize>
            				</c:when>
            				<c:when test="${forum.subType == 'COURSE'}">
            					<div class="one">COURSE TOPICS / DISCUSSIONS</div>
				            	<div class="two">POSTS</div>
				            	<div class="three"><fmt:message key="community.forum.title.views" /></div>
				            	<div class="four"><fmt:message key="community.forum.title.lastPost" /></div>
				            	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            			<div class="five"><fmt:message key="community.forum.title.delete" /></div>
			            		</security:authorize>
            				</c:when>
            				<c:otherwise>
            					<div class="one"><fmt:message key="community.forum.title.discussions" /></div>
				            	<div class="two"><fmt:message key="community.forum.title.replies" /></div>
				            	<div class="three"><fmt:message key="community.forum.title.views" /></div>
				            	<div class="four"><fmt:message key="community.forum.title.lastPost" /></div>
				            	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            			<div class="five"><fmt:message key="community.forum.title.delete" /></div>
			            		</security:authorize>
            				</c:otherwise>
           				</c:choose>
           			</div>
           			
           			<c:if test="${not empty topicsPage.list}">
           				<c:forEach items="${topicsPage.list}" var="currentTopic" varStatus="status">
							<c:choose>
								<c:when test="${forum.subType == 'DOCUMENT'}">
									<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
										<c:param name="topicId" value="${currentTopic.topicId}"/>
										<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
									</c:url>
									<c:url var="DeleteTopicForumURL" value="/de/community/DeleteForumTopic.json">
										<c:param name="topicId" value="${currentTopic.topicId}" />
									</c:url>
									<!-- <div class="${not status.last ? 'row' : 'rowLast'}"> -->
									<div class="row">
										<div class="one">
						            		<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
											<a href="${ShowTopicForumURL}" class="forumHref">
												${currentTopic.subject}
												<c:if test="${currentTopic.locked}">&nbsp;<span class="topicClosed">[CLOSED]</span></c:if>
											</a>
											<span>
											<fmt:message key="community.forum.text.createdBy" />&nbsp;
								            	<!-- RR: Topic's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
							                	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName_topicId_${currentTopic.topicId}" class="link">${currentTopic.user.account}</a>
							                </span>
							            </div>
							            <div class="two">${currentTopic.forum.description} <span>${currentTopic.forum.title}</span></div>
							            <div class="three">${currentTopic.totalReplies - 1}</div>
							            <div class="four">${currentTopic.totalViews != null ? currentTopic.totalViews : ''}</div>
										<c:if test="${not empty currentTopic.lastPost}">
								            <div class="five">
								            	<fmt:message key="community.forum.text.lastPostBy" />&nbsp;
												<!-- RR: Post's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
								            	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName_lastPostId_${currentTopic.lastPost.postId}" class="link">${currentTopic.lastPost.user.account}</a>
								            	<span class="date">${currentTopic.lastPost.lastUpdate}</span>
								            </div>
								        </c:if>
										<c:if test="${empty currentTopic.lastPost}">
								            <div class="five"></div>
								        </c:if>
								        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
							        		<div class="six">
							        			<c:if test="${not currentTopic.locked}">
							        				<a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a>
							        			</c:if>
							        		</div>
							        	</security:authorize>
							        </div>
								</c:when>
								<c:when test="${forum.subType == 'COURSE'}">
									<c:url var="DeleteCourseTopicURL" value="/teaching/DeleteCourseFragmentTopic.json">
										<c:param name="topicId" value="${currentTopic.topicId}" />
									</c:url>
									<c:set var="topicType" value="${bia:getValue(topicsMap, currentTopic.topicId)}" />
									<c:choose>
										<c:when test="${not empty topicsMap and topicType != 'Q' and topicType != 'D'}">
											<c:url var="ShowCourseFragmentURL" value="/teaching/ShowCourseTranscription.do">
												<c:param name="topicId" value="${currentTopic.topicId}" />
												<c:param name="entryId" value="${currentTopic.document.entryId}" />
												<c:param name="transcriptionMode" value="${topicType}" />
												<c:param name="completeDOM" value="true" />
											</c:url>
										</c:when>
										<c:when test="${not empty topicsMap and topicType == 'Q'}">
											<%-- QUESTION WITH ANNOTATION --%>
											<c:url var="ShowCourseFragmentURL" value="/teaching/ShowTopicForum.do">
												<c:param name="topicId" value="${currentTopic.topicId}"/>
												<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
												<c:param name="completeDOM" value="true" />
											</c:url>
										</c:when>
										<c:otherwise>
											<%-- GENERAL DISCUSSION --%>
											<c:url var="ShowCourseFragmentURL" value="/teaching/ShowTopicForum.do">
												<c:param name="topicId" value="${currentTopic.topicId}"/>
												<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
												<c:param name="completeDOM" value="true" />
											</c:url>
										</c:otherwise>
									</c:choose>
									<!-- <div class="${not status.last ? 'row' : 'rowLast'}"> -->
									<div class="row">
										<div class="one">
								            <img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
											<a href="${ShowCourseFragmentURL}" class="courseFragmentHref">
												${currentTopic.subject}
												<c:if test="${currentTopic.locked}">&nbsp;<span class="topicClosed">[CLOSED]</span></c:if>
											</a>
							            	<span>
							            		Topic Type:&nbsp;
							            		 <c:choose>
							            		 	<c:when test="${topicType == 'Q'}">
							            		 		Comment
							            		 		<c:if test="${not currentTopic.annotation.visible}">
								            		 		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
								            		 			&nbsp;&lt;<i>hidden</i>&gt;
								            		 		</security:authorize>
							            		 		</c:if>
							            		 	</c:when>
							            		 	<c:when test="${topicType == 'D'}">
							            		 		Discussion
							            		 	</c:when>
							            		 	<c:otherwise>
							            		 		<b>Collaborative Transcription</b>
							            		 	</c:otherwise>
							            		 </c:choose>
							            	</span>
							            	<span>
							            		<fmt:message key="community.forum.text.createdBy" />&nbsp;
												<!-- RR: Topic's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
							            		<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName_topicId_${currentTopic.topicId}" class="link">${currentTopic.user.account}</a>
							            	</span>
							            </div>
							            <div class="two">
							            	${currentTopic.totalReplies}
							            </div>
							            <div class="three">
							            	${currentTopic.totalViews != null ? currentTopic.totalViews : ''}
							            </div>
										<c:if test="${not empty currentTopic.lastPost}">
											<!-- RR: Post's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
								            <div class="four">
								            	<fmt:message key="community.forum.text.lastPostBy" />&nbsp;
								            	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName_lastPostId_${currentTopic.lastPost.postId}" class="link">${currentTopic.lastPost.user.account}</a>
								            	<span class="date">${currentTopic.lastPost.lastUpdate}</span>
								            </div>
								        </c:if>
										<c:if test="${empty currentTopic.lastPost}">
								            <div class="four"></div>
								        </c:if>
								        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
							        		<div class="five">
							        			<c:if test="${not cuurentTopic.locked and (topicType == 'Q' or topicType == 'D')}">
							        				<!-- Cannot delete course transcription fragment: delete course fragment resources forum instead (if needed) -->
								        			<a href="${DeleteCourseTopicURL}" class="button_delete">
								        				<img src="<c:url value="/images/forum/button_delete.png"/>"/>
								        			</a>
							        			</c:if>
							        		</div>
							        	</security:authorize>
							        </div>
							    </c:when>
								<c:otherwise>
									<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
										<c:param name="topicId" value="${currentTopic.topicId}"/>
										<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
									</c:url>
									<c:url var="DeleteTopicForumURL" value="/de/community/DeleteForumTopic.json">
										<c:param name="topicId" value="${currentTopic.topicId}" />
									</c:url>
									<!-- <div class="${not status.last ? 'row' : 'rowLast'}"> -->
									<div class="row">
										<div class="one">
							            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
							                <a href="${ShowTopicForumURL}" class="forumHref">
							                	${currentTopic.subject}
							                	<c:if test="${currentTopic.locked}">&nbsp;<span class="topicClosed">[CLOSED]</span></c:if>
								                <c:if test="${not empty currentTopic.annotation and not currentTopic.annotation.visible}">
						            		 		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
						            		 			&nbsp;&lt;<i>hidden</i>&gt;
						            		 		</security:authorize>
					            		 		</c:if>
				            		 		</a>
							                <span>
							                	<fmt:message key="community.forum.text.createdBy" />&nbsp;
								            	<!-- RR: Topic's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
							                	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName_topicId_${currentTopic.topicId}" class="link">${currentTopic.user.account}</a>
							                </span>
							            </div>
							            <div class="two">${currentTopic.totalReplies - 1}</div>
							            <div class="three">${currentTopic.totalViews != null ? currentTopic.totalViews : ''}</div>
										<c:if test="${not empty currentTopic.lastPost}">
											<!-- RR: Post's identifier appended to anchor's identifier to avoid duplicate identifiers in DOM -->
								            <div class="four">
								            	<fmt:message key="community.forum.text.lastPostBy" />&nbsp;
								            	<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName_lastPostId_${currentTopic.lastPost.postId}" class="link">${currentTopic.lastPost.user.account}</a>
								            	<span class="date">${currentTopic.lastPost.lastUpdate}</span>
								            </div>
								        </c:if>
										<c:if test="${empty currentTopic.lastPost}">
								            <div class="four"></div>
								        </c:if>
								        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
							        		<div class="five">
							        			<c:if test="${not currentTopic.locked}">
							        				<a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a>
							        			</c:if>
							        		</div>
							        	</security:authorize>
							        </div>
							    </c:otherwise>
							</c:choose>     
					    </c:forEach>
           			</c:if>
           		</div>
            </div>
            <c:if test="${not empty topicsPage.list}">
            	<div id="forumPaginate">
				    <c:set var="paginationData">
						<bia:paginationForum page="${topicsPage}"/>
					</c:set>
					
					<div id="jumpToDiv">
				    	<fmt:message key="community.forum.text.jumpTo" />
				    	<select id="selectForum" name="selectForum" class="selectform_long">
				        	<option value="" selected="selected">Select a Forum</option>
				        </select>
				        <input id="go" type="submit" title='<fmt:message key="community.forum.tooltip.go" />' value='<fmt:message key="community.forum.inputValue.go" />' class="buttonMini">
				    </div>
					
					${paginationData}
					
				</div>
				
				<c:url var="ReturnToForumURL" value="/community/ShowForum.do">
					<c:param name="forumId" value="${forum.forumParent.forumId}" />
				</c:url>
			
				<c:if test="${not empty forum.forumParent && forum.forumParent.type != 'CATEGORY'}">
					<a href="${ReturnToForumURL}" class="returnTo"><fmt:message key="community.forum.link.returnTo" /><span>${forum.forumParent.title}</span></a>
				</c:if>
				<c:if test="${not empty forum.forumParent && forum.forumParent.type == 'CATEGORY'}">
					<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo"><fmt:message key="community.forum.link.returnToBoardIndex" /></a>
				</c:if>
            </c:if>
            <c:if test="${empty topicsPage.list}">
            	<c:choose>
					<c:when test="${forum.subType == 'DOCUMENT'}">
						<div class="rowLast">
							<div class="one">
				            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
				                <a id="viewTopic"><fmt:message key="community.forum.text.noDiscussion" /></a>
				                <span>${currentForum.description}</span>
					        </div>
					        <div class="two"><fmt:message key="community.forum.text.emptyForum" /></span></div>
					        <div class="three">0</div>
					        <div class="four">0</div>
							<div class="five"><fmt:message key="community.forum.text.emptyForum" /></div>	
					        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				        		<div class="six"></div>
				        	</security:authorize>
						</div>
					</c:when>
					<c:otherwise>					
						<div class="rowLast">						            
							<div class="one">
				            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt='<fmt:message key="community.forum.tooltip.entry" />'>
				                <a id="viewTopic"><fmt:message key="community.forum.text.noDiscussion" /></a>
				                <span>${currentForum.description}</span>
				            </div>
				            <div class="two">0</div>
				            <div class="three">0</div>
				            <div class="four"><fmt:message key="community.forum.text.emptyForum" /></div>
				            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
				        		<div class="five"></div>
				        	</security:authorize>
				        </div>
				    </c:otherwise>
				</c:choose>
            </c:if>
		</c:if>
	</c:if>
	
	<div id="copyLink" title='<fmt:message key="community.forum.tooltip.copy" />' style="display:none"> 
		<input id="linkToCopy" type="text" value="" size="50"/>
	</div>
	
	<script>
		$j(document).ready(function() {
			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
   				$j("#chronologyDiv").html(json.chronology);
				$j("#selectForum").append(json.selectChronology);
				$j("#chronologyDiv .arrowForum").css('display','');
				$j("#chronologyDiv .forum").css('display','');
   			}});
			
			$j('#searchForumThisText').die();
			$j('#searchForumThisText').live('click',function(){
				$j(this).val('');
				$j("#searchDocuments").removeAttr('disabled');
				return false;
			});
			
			$j('#SearchForumThis').submit(function(){
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
				$j("#main").load(formSubmitURL);
 				return false;
			});

			$j('.forumHref').die();
			$j('.forumHref').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			/*$j('.courseFragment').die();
			$j('.courseFragment').live('click', function() {
				window.open($j(this).attr('href'), '<fmt:message key="docbase.showChoiceCoursesOrDocumentsForumModal.title.courseTranscription"/>', 'width=' + screen.width + ', height=' + screen.height + ', scrollbars=no');
				return false;
			});*/
			
			$j('.row').die();
			$j('.row').live('click', function(){
				var rowUrl = $j(this).children().find("a").attr('href');
				if (rowUrl != '') {
					if ($j(this).children().find("a").hasClass('courseFragmentHref')) {
						window.location = rowUrl;
					} else {
						$j("#main").load($j(this).children().find("a").attr('href'));
					}
				}
			});

			$j('.pageHref').die();
			$j('.pageHref').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});

				//$j('.boardIndex').die();
				//$j('.boardIndex').live('click', function() {
				//	$j("#main").load($j(this).attr("href"));
				//	return false;
				//});
			
			$j('.returnTo').die();
			$j('.returnTo').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.forum').die();
			$j('.forum').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j(".link").die();
			$j(".link").live('click', function(){
				$j("#main").load($j(this).attr("href"));
				if($j(".paginateActive").length > 0)
					$j("#prevUrl").val($j(".paginateActive").attr('href'));
				else
					$j("#prevUrl").val("${ShowForumURL}");
				return false;
			});
			
			$j("#members").die();
			$j("#members").live('click', function(){
				$j("#main").load($j(this).attr('href'));
				if($j(".paginateActive").length > 0)
					$j("#prevUrl").val($j(".paginateActive").attr('href'));
				else
					$j("#prevUrl").val("${ShowForumURL}");
				return false;
			});

			$j('#newTopic').click(function (){
				$j("#main").load($j(this).attr("href"));
				if($j(".paginateActive").length > 0)
					$j("#prevUrl").val($j(".paginateActive").attr('href'));
				else
					$j("#prevUrl").val("${ShowForumURL}");
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
				$j("#main").load("${ShowForumRefreshURL}");
				return false;
			});
			
			$j("#button_link").die();
			$j("#button_link").live('click', function(){
				$j("#copyLink").css('display','inherit');
				$j("#copyLink").dialog({
					  autoOpen : false,
					  modal: true,
					  resizable: false,
					  width: 310,
					  height: 130, 
					  buttons: {
						  Close: function() {
							  safeCloseAndDestroyModal("#copyLink");
							  return false;
						  }
					  },
					  open: function(event, ui) { 
						  $j("#linkToCopy").val('http://${bia:getApplicationProperty("website.domain")}${ShowForumRefreshURL}' + '&completeDOM=true');
						  $j("#linkToCopy").select();
						  return false;
					  },
					  close: function(event, ui){
						  safeCloseAndDestroyModal("#copyLink");
						  return false;
					  }						  
				  });
				$j("#copyLink").dialog('open');
				return false;
			});
			
			$j('.button_delete').click(function(){
				var deleteUrl = $j(this).attr('href');
				$j("#deleteModal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 300,
					height: 130, 
					buttons: {
						Yes: function() {
							$j.ajax({ 
								type: "POST", 
								url: deleteUrl, 
								async: false, 
								success: function(json) {
					 				if (json.operation == 'OK') {
					 					$j("#main").load('${ShowForumRefreshURL}');
					 					safeCloseAndDestroyModal("#deleteModal");
					 				} else {
					 					$j("#deleteModal").dialog('close');
					 					alert('The operation failed on server...cannot proceed!!!');
					 				}
								},
								error: function(data) {
									$j("#deleteModal").dialog('close');
									alert('Server error...operation aborted!!!');
								}
							});
							return false;
						},
						No: function() {
							safeCloseAndDestroyModal("#deleteModal");
							return false;
						}
					},
					close: function(event, ui){
						safeDestroyModal("#deleteModal");
						return false;
					}
				});
				$j('#deleteModal').dialog('open');
				return false;
			});
			
			$j("#changeForumTitle").click(function() {
				var forumId = 0;
				<c:if test="${not empty forum}">
					forumId = ${forum.forumId};
				</c:if>
				
				// set forum or topic identifier in the changeTitleModalForm
				$j("#changeTitleModalForm #forumId").val(${forum.forumId});
				// ...and set the beginning title and description
				$j("#changeTitleModalForm #title").val('${forum.title}');
				$j("#changeTitleModalForm #description").val('${forum.description}');
				
				$j("#changeTitleModal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 350,
					height: 220,
					buttons: {
						Ok: function() {
							var newTitle = $j("#changeTitleModalForm #title").val();
							if (newTitle === "") {
								$j("#changeTitleError").show();
								return;
							}
							$j.ajax({ 
								type: "POST", 
								url: "${RenameForumURL}",
								data: $j("#changeTitleModalForm").serialize(),
								async: false, 
								success: function(json) {
					 				if (json.operation == 'OK') {
					 					$j("#main").load('${ShowForumRefreshURL}');
					 					safeCloseAndDestroyModal("#changeTitleModal");
					 				} else {
					 					$j("#changeTitleError").hide();
					 					$j("#changeTitleModal").dialog('close');
					 					alert('The operation failed on server...cannot proceed!!!');
					 				}
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
							safeCloseAndDestroyModal("#changeTitleModal");
							return false;
						}
					},
					close: function(event, ui) {
						$j("#changeTitleError").hide();
						safeCloseAndDestroyModal("#changeTitleModal");
						return false;
					}
				});
				$j("#changeTitleModal").dialog('open');
				return false;
				
			});
			
			$j("#showRecord").die();
			$j("#showRecord").live('click', function(e){
				e.preventDefault();
				if(window.opener != null){
					window.opener.$j("#body_left").load($j(this).attr('href'));
					window.opener.alert('<fmt:message key="home.showRecordAlertMessage"/>');
				}else{
					//TODO: If the main window is closed
					//window.opener = window.open("${BIAHomeURL}", "_blank");
					window.alert('<fmt:message key="community.forum.messages.doOpenBIA" />');
				}
				return false;
			});
			
			<c:if test="${command.forumId != null && command.forumId != 1}">
				$j("#footer").css('display','none');
			</c:if>
			
			function safeCloseAndDestroyModal(sel) {
				$j(sel).dialog('close');
				safeDestroyModal(sel);
			}
			
			function safeDestroyModal(sel) {
				try {
					$j(sel).dialog("destroy");
				} catch (err) {
					console.log("Cannot destroy modal [" + sel + "]");
				}
				//MD: move the div back after closing
				$j(sel).appendTo("#main").css("display", "none");
			};
		});
	</script>
		
	<div id="deleteModal" title='<fmt:message key="community.forum.tooltip.delete" />' style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.messages.sureOnDelete" />
		</p>
	</div>
	
	<div id="changeTitleModal" title='<fmt:message key="community.forum.tooltip.changeTitle" />' style="display:none">
		<p>
			<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
			<fmt:message key="community.forum.messages.changeTitleMessage" />
			<form id="changeTitleModalForm" method="post">
				<label for="title">Title</label>
				<input id="title" name="title" type="text" style="width: 98%" value="" />
				<label for="description">Description</label>
				<input id="description" name="description" type="text" style="width: 98%" value="" />
				<input id="forumId" name="forumId" type="hidden" value="" />
			</form>
			<div id="changeTitleError" style="display: none; color: red;">Cannot leave empty title!!!</div>
		</p>
	</div>