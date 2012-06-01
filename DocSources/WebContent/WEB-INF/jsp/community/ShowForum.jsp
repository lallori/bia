<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

						<h2>${forum.name}</h2>
						
						<div id="topicActions">
							<a href="/DocSources/forum/newPost.html" class="buttonMedium" id="newTopic">New Topic</a>
						    <div id="searchThisForumFormDiv">
						        <form id="SearchForm" action="/DocSources/src/SimpleSearch.do" method="post">
						            <input id="text" name="text" type="text" value="Search this forum...">
						            <input id="search" type="submit" title="Search" value="Search">
						        </form>
						    </div>
						</div>

						<div id="forumTable">
						    <div class="list">
						        <div class="rowFirst">
						            <div class="one">THREADS</div>
						            <div class="two">SUB-THREADS</div>
						            <div class="three">VIEWS</div>
						            <div class="four">LAST POST</div>
						        </div>

							<c:forEach items="${forums}" var="currentForum" varStatus="status">
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">						            
									<div class="one">
						            	<img src="/DocSources/images/forum/img_forum.png" alt="entry">
						                <a href="/DocSources/forum/viewSubForums.html" id="viewTopic">${currentForum.name}</a>
						                <span>${currentForum.description}</span>
						            </div>
						            <div class="two">5</div>
						            <div class="three">35</div>
						            <div class="four">by <a href="#" id="userName" class="link">aga11</a><span class="date">Wed Mar 17, 2012 5:42 pm</span></div>
						        </div>
						    </c:forEach>

							<c:if test="${empty forums}">
								<div class="rowLast">						            
									<div class="one">
						            	<img src="/DocSources/images/forum/img_forum.png" alt="entry">
						                <a id="viewTopic">No threads available</a>
						                <span>${currentForum.description}</span>
						            </div>
						            <div class="two">0</div>
						            <div class="three">0</div>
						            <div class="four">empty forum</div>
						        </div>
							</c:if>
						    </div>
						</div>	

						<div id="topicActions">
							<a href="#" class="buttonMedium" id="newTopic">New Topic</a>
						    <div id="jumpToDiv">
						    	Jump to:
						        <form id="jumpToForm" action="/DocSources/src/SimpleSearch.do" method="post">
						            <select id="selectForum" name="selectForum" selected""="" class="selectform_long">
						                <option value="" selected="selected">Select a Forum</option>
						            </select>
						            <input id="go" type="submit" title="go" value="Go" class="buttonMini">
						        </form>
						    </div>
						</div>

						<a href="/DocSources/forum/index.html" class="returnTo"> Return to <span>Board Index</span></a>
					</div>
					<c:forEach items="${categories}" var="currentCategory" varStatus="status">
						<div id="forumTable">
							<div class="list">
								<div class="rowFirst">
									<div class="one">${currentCategory.name}</div>
									<div class="two">TOPICS</div>
									<div class="three">POSTS</div>
									<div class="four">LAST POST</div>
								</div>

							<c:set var="forums" value="${forumsHashMap[currentCategory.id]}"/>

							<c:forEach items="${forums}" var="currentForum" varStatus="status">
								<c:url var="forumURL" value="/community/ShowForum.do">
									<c:param name="id" value="${currentForum.id}" />
								</c:url>
								<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">
								<div class="one">
									<img src="<c:url value="/images/forum/img_forum.png"/>" alt="entry" />
									<a href="${forumURL}" class="forum">${currentForum.name}</a>
										<span>Description of this forum</span>
									</div>
									<div class="two">${currentForum.topicsNumber}</div>
									<div class="three">${currentForum.postsNumber}</div>
									<c:if test="${not empty currentForum.lastPost}">
									<div class="four">by <a href="#" id="userName" class="link">${currentForum.lastPost.username}</a><span class="date">${currentForum.lastPost.lastUpdate}</span></div>
									</c:if>
									<c:if test="${empty currentForum.lastPost}">
									<div class="four">empty forum</span></div>
									</c:if>
								</div>
                           	</c:forEach>
							</div>
						</div>
					</c:forEach>

         

					<script>
						$j(document).ready(function() {
							$j(".forum").click(function(){
								$j("#mainContent").load($j(this).attr("href"));
								$j("#statisticsDiv").css('display','none');
								$j(".arrowForum").css('visibility','visible');
								$j(".forum").css('visibility','visible');
								return false;
							});
						});
					</script>
					