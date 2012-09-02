<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.bia.common.pagination.Page" %>
<c:set var="pageCountToDiplay" value="5"/>
<c:set var="pageCountToDiplayHalf" value="3"/>


	<c:choose>
	<c:when test="${page.thisPage != 1}">
		<c:if test="${not empty searchResultPage}">
			<c:url var="baseUrl" value="/community/AdvancedSearchForumPost.do"/>
			<c:set var="firstArgs">?searchUUID=${command.searchUUID}&resultPageNumber=1&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
			<c:set var="prevArgs">?searchUUID=${command.searchUUID}&resultPageNumber=${page.thisPage - 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
		</c:if>
		<c:if test="${not empty simpleSearchResultPage}">
			<c:url var="baseUrl" value="/community/SimpleSearchForumPost.do"/>
			<c:set var="firstArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=1&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
			<c:set var="prevArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=${page.thisPage - 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
		</c:if>
		<c:if test="${not empty subForumsPage}">
			<c:url var="baseUrl" value="/community/ShowForum.do"/>
			<c:set var="firstArgs">?forumId=${forum.forumId}&forumPageNumber=1&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
			<c:set var="prevArgs">?forumId=${forum.forumId}&forumPageNumber=${page.thisPage - 1}&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
		</c:if>
		<c:if test="${not empty topicsPage}">
			<c:url var="baseUrl" value="/community/ShowForum.do"/>
			<c:set var="firstArgs">?forumId=${forum.forumId}&topicPageNumber=1&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
			<c:set var="prevArgs">?forumId=${forum.forumId}&topicPageNumber=${page.thisPage - 1}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>	
		</c:if>
		<c:if test="${not empty postsPage}">
			<c:url var="baseUrl" value="/community/ShowTopicForum.do"/>
			<c:set var="firstArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber=1&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>
			<c:set var="prevArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber={page.thisPage - 1}&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>	
		</c:if>
		<span id="firstPaginateButton"><a href="${baseUrl}${firstArgs}" class="paginateForumButton">First</a></span>
		<span id="previousPaginateButton"><a href="${baseUrl}${prevArgs}" class="paginateForumButton">Previous</a></span>
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
			<c:url var="baseUrl" value="/community/AdvancedSearchForumPost.do"/>
			<c:set var="nextArgs">?searchUUID=${command.searchUUID}&resultPageNumber=${page.thisPage + 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
			<c:set var="lastArgs">?searchUUID=${command.searchUUID}&resultPageNumber=${page.totalPages}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}&newSearch=false</c:set>
		</c:if>
		<c:if test="${not empty simpleSearchResultPage}">
			<c:url var="baseUrl" value="/community/SimpleSearchForumPost.do"/>
			<c:set var="nextArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=${page.thisPage + 1}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
			<c:set var="lastArgs">?searchUUID=${command.searchUUID}&searchForumAllText=${command.searchForumAllText}&resultPageNumber=${page.totalPages}&resultPageTotal=${page.totalPages}&resultsForPage=${command.resultsForPage}&sortResults=${command.sortResults}&order=${command.order}</c:set>
		</c:if>
		<c:if test="${not empty subForumsPage}">
			<c:url var="baseUrl" value="/community/ShowForum.do"/>
			<c:set var="nextArgs">?forumId=${forum.forumId}&forumPageNumber=${page.thisPage + 1}&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
			<c:set var="lastArgs">?forumId=${forum.forumId}&forumPageNumber=${page.totalPages}&forumPageTotal=${page.totalPages}&forumsForPage=${command.forumsForPage}</c:set>
		</c:if>
		<c:if test="${not empty topicsPage}">
			<c:url var="baseUrl" value="/community/ShowForum.do"/>
			<c:set var="nextArgs">?forumId=${forum.forumId}&topicPageNumber=${page.thisPage + 1}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>
			<c:set var="lastArgs">?forumId=${forum.forumId}&topicPageNumber=${page.totalPages}&topicPageTotal=${page.totalPages}&topicsForPage=${command.topicsForPage}</c:set>	
		</c:if>
		<c:if test="${not empty postsPage}">
			<c:url var="baseUrl" value="/community/ShowTopicForum.do"/>
			<c:set var="nextArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber=${page.thisPage + 1}&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>
			<c:set var="lastArgs">?forumId=${forum.forumId}&topicId=${topic.topicId}&postPageNumber={page.totalPages}&postPageTotal=${page.totalPages}&postsForPage=${command.postsForPage}</c:set>	
		</c:if>
		<span id="nextPaginateButton"><a href="${baseUrl}${nextArgs}" class="paginateForumButton">Next</a></span>
		<span id="lastPaginateButton"><a href="${baseUrl}${lastArgs}" class="paginateForumButton">Last</a></span>
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

