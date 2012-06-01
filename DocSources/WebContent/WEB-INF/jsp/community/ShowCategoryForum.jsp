<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
					