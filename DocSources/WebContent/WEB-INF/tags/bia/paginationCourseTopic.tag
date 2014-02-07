<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.bia.common.pagination.Page" %>
<%@ attribute name="topicId" required="true" type="java.lang.Integer" %>
<%@ attribute name="buttonClass" required="true" type="java.lang.String" %>
<c:set var="pageCountToDiplay" value="5"/>
<c:set var="pageCountToDiplayHalf" value="3"/>

	<c:choose>
		<c:when test="${page.thisPage != 1}">
			<c:url var="baseUrl" value="/teaching/ShowDocumentRoundRobinTranscription.do"/>
			<c:set var="firstArgs">?topicId=${topicId}&postPageNumber=1&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>
			<c:set var="prevArgs">?topicId=${topicId}&postPageNumber=${page.thisPage - 1}&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>
			<span class="firstPaginateButton"><a href="${baseUrl}${firstArgs}" class="paginateForumButton ${buttonClass}">First</a></span>
			<span class="previousPaginateButton"><a href="${baseUrl}${prevArgs}" class="paginateForumButton ${buttonClass}">Previous</a></span>
		</c:when>
		<c:otherwise>
			<span class="firstPaginateButton">First</span>
			<span class="previousPaginateButton">Previous</span>
		</c:otherwise>
	</c:choose>
	
	<span>
		<c:choose>
		  	<c:when test="${page.totalPages <= pageCountToDiplay}">
				<c:forEach begin="1" end="${page.totalPages}" var="currentPage">
					<%@ include file="pageLinkCourseTopic.tagf" %>
				</c:forEach>
			</c:when>
		  	<c:when test="${page.thisPage <= pageCountToDiplayHalf}">
				<c:forEach begin="1" end="${pageCountToDiplay}" var="currentPage">
					<%@ include file="pageLinkCourseTopic.tagf" %>
				</c:forEach>
			</c:when>	
		  	<c:when test="${page.thisPage >= (page.totalPages - pageCountToDiplayHalf)}">
				<c:forEach begin="${page.totalPages-pageCountToDiplay + 1}" end="${page.totalPages}" var="currentPage">
					<%@ include file="pageLinkCourseTopic.tagf" %>
				</c:forEach>
			</c:when>	
			<c:otherwise>
				<c:forEach begin="${page.thisPage - (pageCountToDiplay / 2) + 1}" end="${(page.thisPage - (pageCountToDiplay / 2) + 1) + pageCountToDiplay - 1}" var="currentPage">
					<%@ include file="pageLinkCourseTopic.tagf" %>
				</c:forEach>
		 	</c:otherwise>
		</c:choose>
	</span>
	
	<c:choose>
		<c:when test="${page.thisPage != page.totalPages}">
			<c:url var="baseUrl" value="/teaching/ShowDocumentRoundRobinTranscription.do"/>
			<c:set var="nextArgs">?topicId=${topicId}&postPageNumber=${page.thisPage + 1}&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>
			<c:set var="lastArgs">?topicId=${topicId}&postPageNumber=${page.totalPages}&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>	
			<span class="nextPaginateButton"><a href="${baseUrl}${nextArgs}" class="paginateForumButton ${buttonClass}">Next</a></span>
			<span class="lastPaginateButton"><a href="${baseUrl}${lastArgs}" class="paginateForumButton ${buttonClass}">Last</a></span>
		</c:when>
		<c:otherwise>
			<span class="nextPaginateButton">Next</span>
			<span class="lastPaginateButton">Last</span>
		</c:otherwise>
	</c:choose>
