<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.bia.common.pagination.Page" %>
<%@ attribute name="topicId" required="true" type="java.lang.Integer" %>
<%@ attribute name="buttonClass" required="true" type="java.lang.String" %>
<%@ attribute name="baseUrl" required="true" type="java.lang.String" %>
<%@ attribute name="onlyInnerArgs" required="true" type="java.lang.Boolean" %>
<c:set var="pageCountToDiplay" value="5"/>
<c:set var="pageCountToDiplayHalf" value="3"/>

	<c:choose>
		<c:when test="${onlyInnerArgs == true}">
			<c:set var="urlSeparator" value="?" />
		</c:when>
		<c:otherwise>
			<c:set var="urlSeparator" value="&" />
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${page.thisPage != 1}">
			<c:set var="firstArgs">${urlSeparator}topicId=${topicId}&postPageNumber=1&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>
			<c:set var="prevArgs">${urlSeparator}topicId=${topicId}&postPageNumber=${page.thisPage - 1}&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>
			<a href="${baseUrl}${firstArgs}" class="firstPaginateButton paginateForumButton ${buttonClass}">First</a>
			<a href="${baseUrl}${prevArgs}" class="previousPaginateButton paginateForumButton ${buttonClass}">Previous</a>
		</c:when>
		<c:otherwise>
			<span class="firstPaginateButton paginateForumButton">First</span>
			<span class="previousPaginateButton paginateForumButton">Previous</span>
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
			<c:set var="nextArgs">${urlSeparator}topicId=${topicId}&postPageNumber=${page.thisPage + 1}&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>
			<c:set var="lastArgs">${urlSeparator}topicId=${topicId}&postPageNumber=${page.totalPages}&postPageTotal=${page.totalPages}&postsForPage=${page.elementsForPage}&completeDOM=false</c:set>	
			<a href="${baseUrl}${nextArgs}" class="nextPaginateButton paginateForumButton ${buttonClass}">Next</a>
			<a href="${baseUrl}${lastArgs}" class="lastPaginateButton paginateForumButton ${buttonClass}">Last</a>
		</c:when>
		<c:otherwise>
			<span class="nextPaginateButton paginateForumButton">Next</span>
			<span class="lastPaginateButton paginateForumButton">Last</span>
		</c:otherwise>
	</c:choose>
