<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.bia.common.pagination.Page" %>
<c:set var="pageCountToDiplay" value="5"/>
<c:set var="pageCountToDiplayHalf" value="3"/>

	<c:choose>
		<c:when test="${page.thisPage != 1 and not empty searchResultPage}">
			<c:url var="firstUrl" value="/community/AdvancedSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}" />
				<c:param name="resultPageNumber" value="1" />
				<c:param name="resultPageTotal" value="${page.totalPages}" />
				<c:param name="resultsForPage" value="${command.resultsForPage}" />
				<c:param name="sortResults" value="${command.sortResults}" />
				<c:param name="order" value="${command.order}" />
				<c:param name="newSearch" value="false" />
			</c:url>
			<c:url var="previousUrl" value="/community/AdvancedSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}" />
				<c:param name="resultPageNumber" value="${page.thisPage - 1}" />
				<c:param name="resultPageTotal" value="${page.totalPages}" />
				<c:param name="resultsForPage" value="${command.resultsForPage}" />
				<c:param name="sortResults" value="${command.sortResults}" />
				<c:param name="order" value="${command.order}" />
				<c:param name="newSearch" value="false" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty simpleSearchResultPage}">
			<c:url var="firstUrl" value="/community/SimpleSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}" />
				<c:param name="searchForumAllText" value="${command.searchForumAllText}" />
				<c:param name="resultPageNumber" value="1" />
				<c:param name="resultPageTotal" value="${page.totalPages}" />
				<c:param name="resultsForPage" value="${command.resultsForPage}" />
				<c:param name="sortResults" value="${command.sortResults}" />
				<c:param name="order" value="${command.order}" />
			</c:url>
			<c:url var="previousUrl" value="/community/SimpleSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}" />
				<c:param name="searchForumAllText" value="${command.searchForumAllText}" />
				<c:param name="resultPageNumber" value="${page.thisPage - 1}" />
				<c:param name="resultPageTotal" value="${page.totalPages}" />
				<c:param name="resultsForPage" value="${command.resultsForPage}" />
				<c:param name="sortResults" value="${command.sortResults}" />
				<c:param name="order" value="${command.order}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty subForumsPage}">
			<c:url var="firstUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="forumPageNumber" value="1" />
				<c:param name="forumPageTotal" value="${page.totalPages}" />
				<c:param name="forumsForPage" value="${command.forumsForPage}" />
			</c:url>
			<c:url var="previousUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="forumPageNumber" value="${page.thisPage - 1}" />
				<c:param name="forumPageTotal" value="${page.totalPages}" />
				<c:param name="forumsForPage" value="${command.forumsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty topicsPage}">
			<c:url var="firstUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="1" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
			<c:url var="previousUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="${page.thisPage - 1}" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty subForumsTopicsPage}">
			<c:url var="firstUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="1" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
			<c:url var="previousUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="${page.thisPage - 1}" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty postsPage and not empty forum and not empty topic}">
			<c:url var="firstUrl" value="/community/ShowTopicForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicId" value="${topic.topicId}" />
				<c:param name="postPageNumber" value="1" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
			<c:url var="previousUrl" value="/community/ShowTopicForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicId" value="${topic.topicId}" />
				<c:param name="postPageNumber" value="${page.thisPage - 1}" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty postsPage and (empty forum or empty topic)}">
			<c:url var="firstUrl" value="/community/ShowMyForumPost.do">
				<c:param name="postPageNumber" value="1" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
			<c:url var="previousUrl" value="/community/ShowMyForumPost.do">
				<c:param name="postPageNumber" value="${page.thisPage - 1}" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != 1 and not empty messageboxPage}">
			<c:url var="firstUrl" value="/community/ShowMyMessageBox.do">
				<c:param name="category" value="${category}" />
				<c:param name="messagePageNumber" value="1" />
				<c:param name="messagePageTotal" value="${page.totalPages}" />
				<c:param name="messageForPage" value="${command.resultsForPage}" />
			</c:url>
			<c:url var="previousUrl" value="/community/ShowMyMessageBox.do">
				<c:param name="category" value="${category}" />
				<c:param name="messagePageNumber" value="${page.thisPage - 1}" />
				<c:param name="messagePageTotal" value="${page.totalPages}" />
				<c:param name="messageForPage" value="${command.resultsForPage}" />
			</c:url>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${page.thisPage != page.totalPages and not empty searchResultPage}">
			<c:url var="nextUrl" value="/community/AdvancedSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}"/>
				<c:param name="resultPageNumber" value="${page.thisPage + 1}"/>
				<c:param name="resultPageTotal" value="${page.totalPages}"/>
				<c:param name="resultsForPage" value="${command.resultsForPage}"/>
				<c:param name="sortResults" value="${command.sortResults}"/>
				<c:param name="order" value="${command.order}"/>
				<c:param name="newSearch" value="false"/>
			</c:url>
			<c:url var="lastUrl" value="/community/AdvancedSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}"/>
				<c:param name="resultPageNumber" value="${page.totalPages}"/>
				<c:param name="resultPageTotal" value="${page.totalPages}"/>
				<c:param name="resultsForPage" value="${command.resultsForPage}"/>
				<c:param name="sortResults" value="${command.sortResults}"/>
				<c:param name="order" value="${command.order}"/>
				<c:param name="newSearch" value="false"/>
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty simpleSearchResultPage}">
			<c:url var="nextUrl" value="/community/SimpleSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}" />
				<c:param name="searchForumAllText" value="${command.searchForumAllText}" />
				<c:param name="resultPageNumber" value="${page.thisPage + 1}" />
				<c:param name="resultPageTotal" value="${page.totalPages}" />
				<c:param name="resultsForPage" value="${command.resultsForPage}" />
				<c:param name="sortResults" value="${command.sortResults}" />
				<c:param name="order" value="${command.order}" />
			</c:url>
			<c:url var="lastUrl" value="/community/SimpleSearchForumPost.do">
				<c:param name="searchUUID" value="${command.searchUUID}" />
				<c:param name="searchForumAllText" value="${command.searchForumAllText}" />
				<c:param name="resultPageNumber" value="${page.totalPages}" />
				<c:param name="resultPageTotal" value="${page.totalPages}" />
				<c:param name="resultsForPage" value="${command.resultsForPage}" />
				<c:param name="sortResults" value="${command.sortResults}" />
				<c:param name="order" value="${command.order}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty subForumsPage}">
			<c:url var="nextUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="forumPageNumber" value="${page.thisPage + 1}" />
				<c:param name="forumPageTotal" value="${page.totalPages}" />
				<c:param name="forumsForPage" value="${command.forumsForPage}" />
			</c:url>
			<c:url var="lastUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="forumPageNumber" value="${page.totalPages}" />
				<c:param name="forumPageTotal" value="${page.totalPages}" />
				<c:param name="forumsForPage" value="${command.forumsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty topicsPage}">
			<c:url var="nextUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="${page.thisPage + 1}" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
			<c:url var="lastUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="${page.totalPages}" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty subForumsTopicsPage}">
			<c:url var="nextUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="${page.thisPage + 1}" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
			<c:url var="lastUrl" value="/community/ShowForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicPageNumber" value="${page.totalPages}" />
				<c:param name="topicPageTotal" value="${page.totalPages}" />
				<c:param name="topicsForPage" value="${command.topicsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty postsPage and not empty forum and not empty topic}">
			<c:url var="nextUrl" value="/community/ShowTopicForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicId" value="${topic.topicId}" />
				<c:param name="postPageNumber" value="${page.thisPage + 1}" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
			<c:url var="lastUrl" value="/community/ShowTopicForum.do">
				<c:param name="forumId" value="${forum.forumId}" />
				<c:param name="topicId" value="${topic.topicId}" />
				<c:param name="postPageNumber" value="${page.totalPages}" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty postsPage and (empty forum or empty topic)}">
			<c:url var="nextUrl" value="/community/ShowMyForumPost.do">
				<c:param name="postPageNumber" value="${page.thisPage + 1}" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
			<c:url var="lastUrl" value="/community/ShowMyForumPost.do">
				<c:param name="postPageNumber" value="${page.totalPages}" />
				<c:param name="postPageTotal" value="${page.totalPages}" />
				<c:param name="postsForPage" value="${command.postsForPage}" />
			</c:url>
		</c:when>
		<c:when test="${page.thisPage != page.totalPages and not empty messageboxPage}">
			<c:url var="nextUrl" value="/community/ShowMyMessageBox.do">
				<c:param name="category" value="${category}" />
				<c:param name="messagePageNumber" value="${page.thisPage + 1}" />
				<c:param name="messagePageTotal" value="${page.totalPages}" />
				<c:param name="messageForPage" value="${command.resultsForPage}" />
			</c:url>
			<c:url var="lastUrl" value="/community/ShowMyMessageBox.do">
				<c:param name="category" value="${category}" />
				<c:param name="messagePageNumber" value="${page.totalPages}" />
				<c:param name="messagePageTotal" value="${page.totalPages}" />
				<c:param name="messageForPage" value="${command.resultsForPage}" />
			</c:url>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${not empty firstUrl and not empty previousUrl}">
			<a id="firstPaginateButton" href="${firstUrl}" class="paginateForumButton">First</a>
			<a id="previousPaginateButton" href="${previousUrl}" class="paginateForumButton">Previous</a>
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
		<c:when test="${not empty nextUrl and not empty lastUrl}">
			<a id="nextPaginateButton" href="${nextUrl}" class="paginateForumButton">Next</a>
			<a id="lastPaginateButton" href="${lastUrl}" class="paginateForumButton">Last</a>
		</c:when>
		<c:otherwise>
			<span id="nextPaginateButton">Next</span>
			<span id="lastPaginateButton">Last</span>
		</c:otherwise>
	</c:choose>

