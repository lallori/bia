<%@page import="org.medici.docsources.common.util.ForumUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bia" uri="http://docsources.medici.org/jsp:jstl" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
		<c:param name="forumId" value="${category.forumId}"/>
	</c:url>


				<c:if test="${not empty category}">
					<c:if test="${category.option.canHaveSubCategory}">
						<c:forEach items="${subCategories}" var="currentCategory" varStatus="status">
							<div id="forumTable">
								<div class="list">
									<div class="rowFirst">
										<div class="one">${currentCategory.title}</div>
										<div class="two">FORUMS</div>
										<div class="three">POSTS</div>
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
											<span>Description of this forum</span>
										</div>
										<div class="two">${currentForum.topicsNumber}</div>
										<div class="three">${currentForum.postsNumber}</div>
										<c:if test="${not empty currentForum.lastPost}">
										<div class="four">by <a href="#" id="userName" class="link">${currentForum.lastPost.userInformation.account}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
										</c:if>
										<c:if test="${empty currentForum.lastPost}">
										<div class="four">empty forum</span></div>
										</c:if>
									</div>
	                           	</c:forEach>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</c:if>

				<c:if test="${not empty forum}">
						<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
							<c:param name="forumId" value="${forum.forumId}"/>
						</c:url>
						<c:url var="EditForumPostURL" value="/community/EditForumPost.do">
							<c:param name="postId" value="0"/>
							<c:param name="forumId" value="${forum.forumId}"/>
							<c:param name="topicId" value="0"/>
						</c:url>
						<h2>${forum.title}</h2>
						
						<div id="topicActions">
							<c:if test="${forum.option.canHaveTopics}">
							<a href="${EditForumPostURL}" class="buttonMedium" id="newTopic">New Topic</a>
							</c:if>
						    <div id="searchForumThisDiv">
						        <form id="SearchForumThis" action="/DocSources/src/SimpleSearch.do" method="post">
						            <input id="searchForumThisText" name="searchForumThisText" type="text" value="Search this forum...">
						            <input id="search" type="submit" title="Search" value="Search">
						        </form>
						    </div>
						</div>

					<c:if test="${forum.option.canHaveSubForum}">
						<div id="forumTable">
						    <div class="list">
						        <div class="rowFirst">
						            <div class="one">THREADS</div>
						            <div class="two">SUB-THREADS</div>
						            <div class="three">VIEWS</div>
						            <div class="four">LAST POST</div>
						        </div>

						<c:if test="${not empty subForumsPage.list}">
							<c:forEach items="${subForumsPage.list}" var="currentForum" varStatus="status">
								<c:url var="ShowForumURL" value="/community/ShowForum.do">
									<c:param name="forumId" value="${currentForum.forumId}" />
								</c:url>
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a href="${ShowForumURL}" class="forumHref">${currentForum.title}</a>
						                <span>${currentForum.description}</span>
						            </div>
						            <div class="two">${fn:length(currentForum.forumTopics)}</div>
						            <div class="three">0</div>
								<c:if test="${not empty currentForum.lastPost}">
						            <div class="four">by <a href="#" id="userName" class="link">${currentForum.lastPost.userInformation.account}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
						        </c:if>
								<c:if test="${empty currentForum.lastPost}">
						            <div class="four"></div>
						        </c:if>
						        </div>
						    </c:forEach>
						    </div>
					    </div>
						<c:set var="paginationData">
							<bia:paginationForum page="${subForumsPage}"/>
						</c:set>
							
						${paginationData}
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
						        </div>
						    </div>
						</c:if>    
						</div>
					</c:if>	

					<c:if test="${forum.option.canHaveTopics}">
						<div id="forumTable">
						    <div class="list">
						        <div class="rowFirst">
						            <div class="one">TOPIC</div>
						            <div class="two">REPLY</div>
						            <div class="three">VIEWS</div>
						            <div class="four">LAST POST</div>
						        </div>

						<c:if test="${not empty topicsPage.list}">
							<c:forEach items="${topicsPage.list}" var="currentTopic" varStatus="status">
								<c:url var="ShowTopicForumURL" value="/community/ShowTopicForum.do">
									<c:param name="topicId" value="${currentTopic.topicId}"/>
									<c:param name="forumId" value="${currentTopic.forum.forumId}"/>
								</c:url>
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a href="${ShowTopicForumURL}" class="forumHref">${currentTopic.subject}</a>
						                <span>subtitle</span>
						            </div>
						            <div class="two">${currentTopic.totalReplies}</div>
						            <div class="three">-</div>
								<c:if test="${not empty currentTopic.lastPost}">
						            <div class="four">by <a href="#" id="userName" class="link">${currentTopic.lastPost.userInformation.account}</a><span class="date">${currentTopic.lastPost.lastUpdate}</span></div>
						        </c:if>
								<c:if test="${empty currentTopic.lastPost}">
						            <div class="four"></div>
						        </c:if>
						        </div>
						    </c:forEach>
						</c:if>

							<c:if test="${empty topicsPage.list}">
								<div class="rowLast">						            
									<div class="one">
						            	<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry">
						                <a id="viewTopic">No topics available</a>
						                <span>${currentForum.description}</span>
						            </div>
						            <div class="two">0</div>
						            <div class="three">0</div>
						            <div class="four">empty forum</div>
						        </div>
						    </c:if>
						</div>
					</c:if>
				</c:if>
				<div id="forumPaginate">
				    <div id="jumpToDiv">
				    	Jump to:
				        <form id="jumpToForm" action="/DocSources/src/SimpleSearch.do" method="post">
				            <select id="selectForum" name="selectForum" selected""="" class="selectform_long">
				                <option value="" selected="selected">Select a Forum</option>
				            </select>
				            <input id="go" type="submit" title="go" value="Go" class="buttonMini">
				        </form>
				    </div>
					<span id="firstPaginateButton">First</span>
				    <span id="previousPaginateButton">Previous</span>
				    <span>
				    	<span class="paginateActive">1</span>
				        <span class="paginateButton">2</span>
				        <span class="paginateButton">3</span>
				        <span class="paginateButton">4</span>
				        <span class="paginateButton">5</span>
				    </span>
				    <span id="nextPaginateButton">Next</span>
				    <span id="lastPaginateButton">Last</span>
				</div>

				<c:if test="${category.forumId != 1}">
					<a href="/DocSources/forum/index.html" class="returnTo"> Return to <span>Board Index</span></a>
				</c:if>

					<script>
						$j(document).ready(function() {
							$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
			    				$j("#chronologyDiv").html(json.chronology);
								$j(".arrowForum").css('visibility','visible');
								$j(".forum").css('visibility','visible');
			    			}});

							$j('.forumHref').die();
							$j('.forumHref').live('click', function() {
								$j("#mainContent").load($j(this).attr("href"));
								return false;
							});

							$j('.boardIndex').die();
							$j('.boardIndex').live('click', function() {
								$j("#mainContent").load($j(this).attr("href"));
								return false;
							});
							
							$j('.forum').die();
							$j('.forum').live('click', function() {
								$j("#mainContent").load($j(this).attr("href"));
								return false;
							});

							$j('#newTopic').click(function (){
								$j("#mainContent").load($j(this).attr("href"));
								$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
								return false;
							});
						});
					</script>
					