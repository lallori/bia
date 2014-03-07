<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.bia.common.pagination.Page" %>
<c:set var="pageCountToDiplay" value="5"/>
<c:set var="pageCountToDiplayHalf" value="3"/>


	<c:choose>
		<c:when test="${page.thisPage != 1}">
			<c:if test="${not empty searchResultPage}">
				<c:url var="preBaseUrl" value="/community/AdvancedSearchForumPost.do"/>
				<c:set var="preFirstArgs">?searchUUID=${command.searchUUID}&resultPageNumber=1&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
				<c:set var="prePrevArgs">?searchUUID=${command.searchUUID}&resultPageNumber=${page.thisPage - 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
			</c:if>
			<c:if test="${not empty simpleSearchResultPage}">
				<c:url var="prevBaseUrl" value="/community/SimpleSearchForumPost.do"/>
				<c:set var="preFirstArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=1&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
				<c:set var="prePrevArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=${page.thisPage - 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
			</c:if>
			<c:if test="${not empty subForumsPage}">
				<c:url var="prevBaseUrl" value="/community/ShowForum.do"/>
				<c:set var="preFirstArgs">?forumId=${forum.forumId}&forumPageNumber=1&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
				<c:set var="prePrevArgs">?forumId=${forum.forumId}&forumPageNumber=${page.thisPage - 1}&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
			</c:if>
			<c:if test="${not empty topicsPage}">
				<c:url var="prevBaseUrl" value="/community/ShowForum.do"/>
				<c:set var="preFirstArgs">?forumId=${forum.forumId}&topicPageNumber=1&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
				<c:set var="prePrevArgs">?forumId=${forum.forumId}&topicPageNumber=${page.thisPage - 1}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>	
			</c:if>
			<c:if test="${not empty subForumsTopicsPage}">
				<c:url var="prevBaseUrl" value="/community/ShowForum.do"/>
				<c:set var="preFirstArgs">?forumId=${forum.forumId}&topicPageNumber=1&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
				<c:set var="prePrevArgs">?forumId=${forum.forumId}&topicPageNumber=${page.thisPage - 1}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>	
			</c:if>
			<c:if test="${not empty postsPage}">
				<c:url var="prevBaseUrl" value="/community/ShowTopicForum.do"/>
				<c:set var="preFirstArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber=1&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>
				<c:set var="prePrevArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber=${page.thisPage - 1}&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>	
			</c:if>
			<c:if test="${not empty messageboxPage}">
				<c:url var="prevBaseUrl" value="/community/ShowMyMessageBox.do"/>
				<c:set var="preFirstArgs">?category=${category}&messagePageNumber=1&messagePageTotal=${page.totalPages}&messageForPage=${command.resultsForPage}</c:set>
				<c:set var="prePrevArgs">?category=${category}&messagePageNumber=${page.thisPage - 1}&messagePageTotal=${page.totalPages}&messageForPage=${command.resultsForPage}</c:set>
			</c:if>
			<a id="firstPaginateButton" href="${preBaseUrl}${preFirstArgs}" class="paginateForumButton">First</a>
			<a id="previousPaginateButton" href="${preBaseUrl}${prePrevArgs}" class="paginateForumButton">Previous</a>
		</c:when>
		<c:when test="${page.thisPage == 1}">
				<span id="firstPaginateButton">First</span>
				<span id="previousPaginateButton">Previous</span>
		</c:when>
		<c:otherwise>
			<span id="firstPaginateButton">First</span>
			<span id="previousPaginateButton">Previous</span>
		</c:otherwise>
	</c:choose>

	<span>
	<c:choose>
		  	<c:when test="${page.totalPages <= pageCountToDiplay}">
				<c:forEach begin="1" end="${page.totalPages}" var="currentPage">
					<%@ include file="pageLinkForum.tagf" %>
				</c:forEach>
			</c:when>
		  	<c:when test="${page.thisPage <= pageCountToDiplayHalf}">
				<c:forEach begin="1" end="${pageCountToDiplay}" var="currentPage">
					<%@ include file="pageLinkForum.tagf" %>
				</c:forEach>
			</c:when>	
		  	<c:when test="${page.thisPage >= (page.totalPages-pageCountToDiplayHalf)}">
				<c:forEach begin="${page.totalPages-pageCountToDiplay+1}" end="${page.totalPages}" var="currentPage">
					<%@ include file="pageLinkForum.tagf" %>
				</c:forEach>
			</c:when>	
			<c:otherwise>
				<c:forEach begin="${page.thisPage-(pageCountToDiplay/2)+1}" end="${(page.thisPage-(pageCountToDiplay/2)+1)+pageCountToDiplay-1}" var="currentPage">
					<%@ include file="pageLinkForum.tagf" %>
				</c:forEach>
		 	</c:otherwise>
		</c:choose>
	</span>

	<c:choose>
		<c:when test="${page.thisPage != page.totalPages}">
			<c:if test="${not empty searchResultPage}">
				<c:url var="postBaseUrl" value="/community/AdvancedSearchForumPost.do"/>
				<c:set var="postNextArgs">?searchUUID=${command.searchUUID}&resultPageNumber=${page.thisPage + 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
				<c:set var="postLastArgs">?searchUUID=${command.searchUUID}&resultPageNumber=${page.totalPages}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
			</c:if>
			<c:if test="${not empty simpleSearchResultPage}">
				<c:url var="postBaseUrl" value="/community/SimpleSearchForumPost.do"/>
				<c:set var="postNextArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=${page.thisPage + 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
				<c:set var="postLastArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=${page.totalPages}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
			</c:if>
			<c:if test="${not empty subForumsPage}">
				<c:url var="postBaseUrl" value="/community/ShowForum.do"/>
				<c:set var="postNextArgs">?forumId=${forum.forumId}&forumPageNumber=${page.thisPage + 1}&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
				<c:set var="postLastArgs">?forumId=${forum.forumId}&forumPageNumber=${page.totalPages}&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
			</c:if>
			<c:if test="${not empty topicsPage}">
				<c:url var="postBaseUrl" value="/community/ShowForum.do"/>
				<c:set var="postNextArgs">?forumId=${forum.forumId}&topicPageNumber=${page.thisPage + 1}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
				<c:set var="postLastArgs">?forumId=${forum.forumId}&topicPageNumber=${page.totalPages}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>	
			</c:if>
			<c:if test="${not empty subForumsTopicsPage}">
				<c:url var="postBaseUrl" value="/community/ShowForum.do"/>
				<c:set var="postNextArgs">?forumId=${forum.forumId}&topicPageNumber=${page.thisPage + 1}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
				<c:set var="postLastArgs">?forumId=${forum.forumId}&topicPageNumber=${page.totalPages}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
			</c:if>
			<c:if test="${not empty postsPage}">
				<c:url var="postBaseUrl" value="/community/ShowTopicForum.do"/>
				<c:set var="postNextArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber=${page.thisPage + 1}&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>
				<c:set var="postLastArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber=${page.totalPages}&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>	
			</c:if>
			<c:if test="${not empty messageboxPage}">
				<c:url var="postBaseUrl" value="/community/ShowMyMessageBox.do"/>
				<c:set var="postNextArgs">?category=${category}&resultPageNumber=${page.thisPage + 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}</c:set>
				<c:set var="postLastArgs">?category=${category}&resultPageNumber=${page.totalPages}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}</c:set>
			</c:if>
			<a id="nextPaginateButton" href="${postBaseUrl}${postNextArgs}" class="paginateForumButton">Next</a>
			<a id="lastPaginateButton" href="${postBaseUrl}${postLastArgs}" class="paginateForumButton">Last</a>
		</c:when>
		<c:when test="${page.thisPage == page.totalPages}">
				<span id="nextPaginateButton">Next</span>
				<span id="lastPaginateButton">Last</span>
		</c:when>
		<c:otherwise>
			<span id="nextPaginateButton">Next</span>
			<span id="lastPaginateButton">Last</span>
		</c:otherwise>
	</c:choose>

