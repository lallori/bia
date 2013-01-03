<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
		<c:param name="forumId" value="${category.forumId}"/>
	</c:url>
	
<!-- Main Forum Page -->
	<div id="urlActions">
		<a href="#" class="buttonMedium" id="button_refresh"><span><b>Refresh</b> page</span></a>
		<a href="#" class="buttonMedium" id="button_link" title="Use this to copy and paste url for citations"><span>Copy <b>link</b></span></a>
	</div>
	<c:if test="${not empty category}">
		<c:if test="${category.option.canHaveSubCategory}">
			<c:url var="ShowForumRefreshURL" value="/community/ShowForum.do">
				<c:param name="forumId" value="${category.forumId}"/>
			</c:url>
		
			<c:forEach items="${subCategories}" var="currentCategory" varStatus="status">
				<div id="forumTable">
					<div class="list">
						<div class="rowFirst">
							<div class="one">${currentCategory.title}</div>
							<!-- MD: Hide the second column if the category hasn't threads-->
							<c:if test="${currentCategory.dispositionOrder != 1}">
								<div class="two"></div>
							</c:if>
							<c:if test="${currentCategory.dispositionOrder == 1}">
								<div class="two">THREADS</div>
							</c:if>
							<div class="three">DISCUSSIONS</div>
							<div class="four">LAST POST</div>
						</div>

					<c:set var="forums" value="${forumsBySubCategories[currentCategory.forumId]}"/>

					<c:forEach items="${forums}" var="currentForum" varStatus="status">
						<c:url var="forumURL" value="/community/ShowForum.do">
							<c:param name="forumId" value="${currentForum.forumId}" />
						</c:url>
						<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">
						<div class="one">
							<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry" />
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
								<div class="two">${documentSubForumsWithTopics}</div>
							</c:if>
<%-- 							<div class="two"><span>${currentForum.topicsNumber}</span></div> --%>
							<div class="three">${currentForum.topicsNumber}</div>
							<c:if test="${not empty currentForum.lastPost}">
							<div class="four">Last post by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentForum.lastPost.user.account}" id="userName" class="link">${currentForum.lastPost.user.account}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
							</c:if>
							<c:if test="${empty currentForum.lastPost}">
							<div class="four">empty forum</div>
							</c:if>
						</div>
                         	</c:forEach>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</c:if>

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
			<h2>${forum.title}</h2>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
			<c:if test="${forum.option.canHaveTopics && forum.subType == 'DOCUMENT' && not empty documentExplorer}">
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
			
			<c:if test="${forum.option.canHaveTopics}">
				<security:authorize ifAnyGranted="ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS">
				<div id="topicActions">
					<a href="${EditForumPostURL}" class="buttonMedium" id="newTopic">New Topic</a>
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
      		<c:when test="${forum.option.groupBySubForum=='true'}">
			<div id="forumTable">
			    <div class="list">
			        <div class="rowFirst">
			            <div class="one">THREAD</div>
			            <div class="two">DISCUSSIONS</div>
			            <div class="three">VIEWS</div>
			            <div class="four">LAST POST</div>
			            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            	<div class="five">DEL</div>
			            </security:authorize>
			        </div>

			<c:if test="${not empty subForumsPage.list}">
				<c:forEach items="${subForumsPage.list}" var="currentForum" varStatus="status">
					<c:url var="ShowForumURL" value="/community/ShowForum.do">
						<c:param name="forumId" value="${currentForum.forumId}" />
					</c:url>
					<c:url var="DeleteForumURL" value="/de/community/DeleteForum.json">
						<c:param name="forumId" value="${currentForum.forumId}" />
					</c:url>
					<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
						<div class="one">
			            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
			                <a href="${ShowForumURL}" class="forumHref">${currentForum.title}</a>
			                <span>${currentForum.description}</span>
			            </div>
<%-- 			            <div class="two">${fn:length(currentForum.forumTopics)}</div> --%>
						<div class="two">${currentForum.topicsNumber}</div>
			            <div class="three"><c:if test="${currentForum.totalViews == null}">0</c:if><c:if test="${currentForum.totalViews != null}">${currentForum.totalViews}</c:if></div>
					<c:if test="${not empty currentForum.lastPost}">
			            <div class="four">Last post by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentForum.lastPost.user.account}" id="userName" class="link">${currentForum.lastPost.user.account}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
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
			    	Jump to:
			    	<select id="selectForum" name="selectForum" selected""="" class="selectform_long">
			        	<option value="" selected="selected">Select a Forum</option>
			        </select>
			        <input id="go" title="go" value="Go" class="buttonMini">
			    </div>
				<c:set var="paginationData">
				<bia:paginationForum page="${subForumsPage}"/>
				</c:set>
				
				${paginationData}   
			</div>
			
			</c:if>
			
			<c:if test="${empty subForumsPage.list}">
					<div class="rowLast">						            
						<div class="one">
			            	<img src="/DocSources/images/forum/img_forum.png" alt="entry">
			                <a id="viewTopic">No forums available</a>
			                <span>${currentForum.description}</span>
			            </div>
			            <div class="two">0</div>
			            <div class="three">0</div>
			            <div class="four">empty forum</div>
			            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            	<div class="five"></div>
			            </security:authorize>
			        </div>
			    </div>			    
			</c:if>    
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
			<c:otherwise>
				<!-- In this case we manage forum displaying subforums as topics-->
				<c:url var="ShowForumURL" value="/community/ShowForum.do">
					<c:param name="forumId" value="${forum.forumId}" />
				</c:url>
				<c:if test="${forum.title == 'Documents' }">
					<div id="searchDocument">
   						<p>In this forum you will find the community discussions related to the documents already transcribed in the BIA database. 
Use the textbox below to search this forum.</p>
    					<div id="topicActions">
        					<div id="searchThisForumFormDiv">
            					<form id="SearchForumThis" action="<c:url value="/community/AdvancedSearchForumPost.do"/>" method="post">
                					<input id="searchForumThisText" name="searchForumThisText" type="text" value="Search for a document...">
                					<input id="searchDocuments" type="submit" title="Search" value="Search" class="buttonSmall" disabled="disabled"/>
                					<input type="hidden" name="displayResults" value="Topics"/>
                					<input type="hidden" name="forumsId" value="${bia:getApplicationProperty('forum.identifier.document')}"/>
                					<input type="hidden" name="newSearch" value="true"/>
                					<input type="hidden" name="sortResults" value="POST_TIME"/>
                					<input type="hidden" name="order" value="desc"/>
                				</form>
                			</div>
                		</div>
            		</div>
            	</c:if>
				<c:choose>
					<c:when test="${forum.subType == 'DOCUMENT'}">
						<div id="documentTable">
							<div class="list">
								<div class="rowFirst">
									<div class="one">DISCUSSION</div>
				        			<div class="two">DOC. #ID</div>
				        			<div class="three">REPLIES</div>
				        			<div class="four">VIEWS</div>
				        			<div class="five">LAST POST</div>
				        			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            				<div class="six">DEL</div>
			            			</security:authorize>
								</div>						
					</c:when>
					<c:otherwise>
						<div id="forumTable">
				    		<div class="list">
				        		<div class="rowFirst">
				        	   		<div class="one">DISCUSSIONS</div>
				            		<div class="two">REPLIES</div>
				            		<div class="three">VIEWS</div>
				            		<div class="four">LAST POST</div>
				            		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			            				<div class="five">DEL</div>
			            			</security:authorize>
			            		</div>			            	
			       </c:otherwise>
			   	</c:choose>
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
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a href="${ShowTopicForumURL}" class="forumHref">${currentTopic.subject}</a><span>Created by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName" class="link">${currentTopic.user.account}</a></span>
						            </div>
						            <div class="two">${currentTopic.forum.description} <span>${currentTopic.forum.title}</span></div>
						            <div class="three">${currentTopic.totalReplies - 1}</div>
						            <div class="four">${currentTopic.totalViews}</div>
								<c:if test="${not empty currentTopic.lastPost}">
						            <div class="five">Last post by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName" class="link">${currentTopic.lastPost.user.account}</a><span class="date">${currentTopic.lastPost.lastUpdate}</span></div>
						        </c:if>
								<c:if test="${empty currentTopic.lastPost}">
						            <div class="five"></div>
						        </c:if>
						        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        		<div class="six"><a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a></div>
					        	</security:authorize>
						        </div>
							</c:when>
							<c:otherwise>
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a href="${ShowTopicForumURL}" class="forumHref">${currentTopic.subject}</a><span>Created by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName" class="link">${currentTopic.user.account}</a></span>
						            </div>
						            <div class="two">${currentTopic.totalReplies - 1}</div>
						            <div class="three"><c:if test="${currentTopic.totalViews != null}">${currentTopic.totalViews}</c:if></div>
								<c:if test="${not empty currentTopic.lastPost}">
						            <div class="four">Last post by <a href="#" id="userName" class="link">${currentTopic.lastPost.user.account}</a><span class="date">${currentTopic.lastPost.lastUpdate}</span></div>
						        </c:if>
								<c:if test="${empty currentTopic.lastPost}">
						            <div class="four"></div>
						        </c:if>
						        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        		<div class="five"><a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a></div>
					        	</security:authorize>
						        </div>
						    </c:otherwise>
						</c:choose>     
				    </c:forEach>
				</div>
			</div>
				    <div id="forumPaginate">
					    <c:set var="paginationData">
							<bia:paginationForum page="${subForumsTopicsPage}"/>
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
					
					<c:url var="ReturnToForumURL" value="/community/ShowForum.do">
						<c:param name="forumId" value="${forum.forumParent.forumId}" />
					</c:url>
				
					<c:if test="${not empty forum.forumParent}">
						<a href="${ReturnToForumURL}" class="returnTo">&larr; Return to <span>${forum.forumParent.title}</span></a>
					</c:if>
				</c:if>
	
	<!-- EACH TOPIC PAGE?-->
					<c:if test="${empty subForumsTopicsPage.list}">
						<c:choose>
							<c:when test="${forum.subType == 'DOCUMENT'}">
								<div class="rowLast">						            
									<div class="one">
				            			<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
				                		<a id="viewTopic">No discussions available</a>
				                		<span>${currentForum.description}</span>
				            		</div>
				            		<div class="two">empty forum</div>
				            		<div class="three">0</div>
				            		<div class="four">0</div>
				            		<div class="five">empty forum</div>
				            		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			        					<div class="six"></div>
			        				</security:authorize>
			        			</div>
			        		</c:when>
			        		<c:otherwise>
								<div class="rowLast">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a id="viewTopic">No discussions available</a>
						                <span>${currentForum.description}</span>
						            </div>
						            <div class="two">0</div>
						            <div class="three">0</div>
						            <div class="four">empty forum</div>
						            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        			<div class="five"></div>
					        		</security:authorize>
						        </div>
						    </c:otherwise>
						</c:choose>  
				    </c:if>
			</div>
			</div>
				</c:otherwise>
		</c:choose>
		</c:if>	

		<c:if test="${forum.option.canHaveTopics}">
		<!-- Topic For Category or Topic for general use forums (questions on bia, paleography, ecc.)-->
			<c:url var="ShowForumURL" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
			</c:url>
			<c:if test="${forum.title == 'Documents' }">
				<div id="searchDocument">
   					<p>Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.</p>
    				<div id="topicActions">
        				<div id="searchThisForumFormDiv">
            				<form id="SearchForumThis" action="<c:url value="/community/AdvancedSearchForumPost.do"/>" method="post">
               					<input id="searchForumThisText" name="searchForumThisText" type="text" value="Search for a document...">
               					<input id="searchDocuments" type="submit" title="Search" value="Search" class="buttonSmall" disabled="disabled"/>
               					<input type="hidden" name="displayResults" value="Topics"/>
                				<input type="hidden" name="forumsId" value="${bia:getApplicationProperty('forum.identifier.document')}"/>
                				<input type="hidden" name="newSearch" value="true"/>
                				<input type="hidden" name="sortResults" value="POST_TIME"/>
                				<input type="hidden" name="order" value="desc"/>
               				</form>
               			</div>
               		</div>
            	</div>
            </c:if>
<%--             <c:if test="${forum.hierarchyLevel == 3 &&  forum.title != 'Documents'}"> --%>
<!-- 					<div id="searchDocument"> -->
<%--    						<p>${forum.description}</p> --%>
<%--    						<p>${forum.forumHelpText}</p> --%>
<!--     				</div> -->
<%--             	</c:if> --%>
			<c:choose>
				<c:when test="${forum.subType == 'DOCUMENT'}">
					<c:if test="${forum.document != null}">
						<p></p>
						<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
							<c:param name="entryId" value="${forum.document.entryId}"/>
						</c:url>
						<a href="${ShowDocumentURL}" class="buttonMedium" id="showRecord">Show record</a>
					</c:if>
					<div id="documentTable">
						<div class="list">
							<div class="rowFirst">
								<div class="one">DISCUSSION</div>
				       			<div class="two">DOC. #ID</div>
				       			<div class="three">REPLIES</div>
				       			<div class="four">VIEWS</div>
				       			<div class="five">LAST POST</div>
				       			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			           				<div class="six">DEL</div>
			           			</security:authorize>
							</div>						
				</c:when>
				<c:otherwise>
<!-- 				MD: Inserted the button to view the record on bia -->
					<c:if test="${forum.place != null}">
						<p></p>
						<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
							<c:param name="placeAllId" value="${forum.place.placeAllId}"/>
						</c:url>
						<a href="${ShowPlaceURL}" class="buttonMedium" id="showRecord">Show record</a>
					</c:if>
					<c:if test="${forum.person != null}">
						<p></p>
						<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
							<c:param name="personId" value="${forum.person.personId}"/>
						</c:url>
						<a href="${ShowPersonURL}" class="buttonMedium" id="showRecord">Show record</a>
					</c:if>
					<c:if test="${forum.volume != null}">
						<p></p>
						<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
							<c:param name="summaryId" value="${forum.volume.summaryId}"/>
						</c:url>
						<a href="${ShowVolumeURL}" class="buttonMedium" id="showRecord">Show record</a>
					</c:if>
					<div id="forumTable">
			    		<div class="list">
			        		<div class="rowFirst">
			        	   		<div class="one">DISCUSSION</div>
			            		<div class="two">REPLIES</div>
			            		<div class="three">VIEWS</div>
			            		<div class="four">LAST POST</div>
			            		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
			           				<div class="five">DEL</div>
			           			</security:authorize>
			           		</div>			            	
				</c:otherwise>
			</c:choose>

			<c:if test="${not empty topicsPage.list}">
				<c:forEach items="${topicsPage.list}" var="currentTopic" varStatus="status">
					<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
						<c:param name="topicId" value="${currentTopic.topicId}"/>
						<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
					</c:url>
					<c:url var="DeleteTopicForumURL" value="/de/community/DeleteForumTopic.json">
						<c:param name="topicId" value="${currentTopic.topicId}" />
					</c:url>
					<c:choose>
							<c:when test="${forum.subType == 'DOCUMENT'}">
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a href="${ShowTopicForumURL}" class="forumHref">${currentTopic.subject}</a><span>Created by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName" class="link">${currentTopic.user.account}</a></span>
						            </div>
						            <div class="two">${currentTopic.forum.description} <span>${currentTopic.forum.title}</span></div>
						            <div class="three">${currentTopic.totalReplies - 1}</div>
						            <div class="four"><c:if test="${currentTopic.totalViews != null}">${currentTopic.totalViews}</c:if></div>
								<c:if test="${not empty currentTopic.lastPost}">
						            <div class="five">Last post by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.lastPost.user.account}" id="userName" class="link">${currentTopic.lastPost.user.account}</a><span class="date">${currentTopic.lastPost.lastUpdate}</span></div>
						        </c:if>
								<c:if test="${empty currentTopic.lastPost}">
						            <div class="five"></div>
						        </c:if>
						        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        		<div class="six"><a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a></div>
					        	</security:authorize>
						        </div>
							</c:when>
							<c:otherwise>
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a href="${ShowTopicForumURL}" class="forumHref">${currentTopic.subject}</a><span>Created by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentTopic.user.account}" id="userName" class="link">${currentTopic.user.account}</a></span>
						            </div>
						            <div class="two">${currentTopic.totalReplies - 1}</div>
						            <div class="three"><c:if test="${currentTopic.totalViews != null}">${currentTopic.totalViews}</c:if></div>
								<c:if test="${not empty currentTopic.lastPost}">
						            <div class="four">Last post by <a href="#" id="userName" class="link">${currentTopic.lastPost.user.account}</a><span class="date">${currentTopic.lastPost.lastUpdate}</span></div>
						        </c:if>
								<c:if test="${empty currentTopic.lastPost}">
						            <div class="four"></div>
						        </c:if>
						        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        		<div class="five"><a href="${DeleteTopicForumURL}" class="button_delete"><img src="<c:url value="/images/forum/button_delete.png"/>"/></a></div>
					        	</security:authorize>
						        </div>
						    </c:otherwise>
						</c:choose>     
			    </c:forEach>
			</div>
		</div>
			    <div id="forumPaginate">
				    <c:set var="paginationData">
						<bia:paginationForum page="${topicsPage}"/>
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
				
				<c:url var="ReturnToForumURL" value="/community/ShowForum.do">
					<c:param name="forumId" value="${forum.forumParent.forumId}" />
				</c:url>
			
				<c:if test="${not empty forum.forumParent}">
					<a href="${ReturnToForumURL}" class="returnTo">&larr; Return to <span>${forum.forumParent.title}</span></a>
				</c:if>
			</c:if>

<!-- EACH TOPIC PAGE?-->
				<c:if test="${empty topicsPage.list}">
					<c:choose>
						<c:when test="${forum.subType == 'DOCUMENT'}">
							<div class="rowLast">
								<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a id="viewTopic">No discussions available</a>
						                <span>${currentForum.description}</span>
						        </div>
						        <div class="two">empty forum</span></div>
						        <div class="three">0</div>
						        <div class="four">0</div>
								<div class="five">empty forum</div>	
						        <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        		<div class="six"></div>
					        	</security:authorize>
							</div>
						</c:when>
						<c:otherwise>					
							<div class="rowLast">						            
								<div class="one">
					            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
					                <a id="viewTopic">No discussions available</a>
					                <span>${currentForum.description}</span>
					            </div>
					            <div class="two">0</div>
					            <div class="three">0</div>
					            <div class="four">empty forum</div>
					            <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					        		<div class="five"></div>
					        	</security:authorize>
					        </div>
					    </c:otherwise>
					</c:choose>
			    </c:if>
		</div>
		</div>
 		</c:if> 
		
	</c:if>
	
	<div id="copyLink" title="Copy Link" style="display:none"> 
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
				
				$j('.row').die();
				$j('.row').live('click', function(){
					if($j(this).children().find("a").attr('href') != '')
						$j("#main").load($j(this).children().find("a").attr('href'));
				});

				$j('.pageHref').die();
				$j('.pageHref').live('click', function() {
					$j("#main").load($j(this).attr("href"));
					return false;
				});

// 				$j('.boardIndex').die();
// 				$j('.boardIndex').live('click', function() {
// 					$j("#main").load($j(this).attr("href"));
// 					return false;
// 				});
				
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
								  $j(this).dialog("close");
								  $j(this).dialog("destroy");
								  //MD: This instruction is for move the div back after closing
								  $j(this).appendTo("#main").css("display", "none");
								  return false;
							  }
						  },
						  open: function(event, ui) { 
							  $j("#linkToCopy").val('http://${bia:getApplicationProperty("website.domain")}${ShowForumRefreshURL}' + '&completeDOM=true');
							  $j("#linkToCopy").select();
							  return false;
						  },
						  close: function(event, ui){
							  $j(this).dialog("destroy");
							  //MD: This instruction is for move the div back after closing
							  $j(this).appendTo("#main").css("display", "none");
							  return false;
						  }						  
					  });
					$j("#copyLink").dialog('open');
					return false;
				});
				
				$j('.button_delete').click(function(){
					var deleteUrl = $j(this).attr('href');
					$j( "#deleteModal" ).dialog({
						  autoOpen : false,
						  modal: true,
						  resizable: false,
						  width: 300,
						  height: 130, 
						  buttons: {
							  Yes: function() {
								  $j.ajax({ type:"POST", url:deleteUrl, async:false, success:function(json) {
						 			    if (json.operation == 'OK') {
						 					 $j("#main").load('${ShowForumRefreshURL}');
											 $j( "#deleteModal" ).dialog('close');
											 $j("#deleteModal").dialog("destroy");
											 $j(this).appendTo("#main").css("display", "none");
											 return false;
						 				} else {
						 					//MD: In this case the delete operation returns a problem
						 				}
									}});
									return false;
							  },
							  No: function() {
								  $j( "#deleteModal" ).dialog('close');
								  $j("#deleteModal").dialog("destroy");
								  $j(this).appendTo("#main").css("display", "none");
								  return false;
							  }
						  },
						  close: function(event, ui){
							  $j("#deleteModal").dialog("destroy");
							  //MD: This instruction is for move the div back after closing
							  $j("#deleteModal").appendTo("#main").css("display", "none");
							  return false;
						  }
					  });
					$j('#deleteModal').dialog('open');
					return false;
				});
				
				$j("#showRecord").die();
				$j("#showRecord").live('click', function(e){
					e.preventDefault();
					if(window.opener != null){
						window.opener.$j("#body_left").load($j(this).attr('href'));
						window.opener.alert("Record showed");
					}else{
						//TODO: If the main window is closed
//	 					window.opener = window.open("${BIAHomeURL}", "_blank");
					}
					return false;
				});
				
				<c:if test="${command.forumId != null && command.forumId != 1}">
					$j("#footer").css('display','none');
				</c:if>
			});
		</script>
		
		<div id="deleteModal" title="Delete" style="display:none"> 
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
				Are you sure you want to delete?
			</p>
		</div>
