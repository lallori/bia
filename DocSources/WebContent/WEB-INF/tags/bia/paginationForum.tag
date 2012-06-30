<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.docsources.common.pagination.Page" %>
<c:set var="pageCountToDiplay" value="5"/>
<c:set var="pageCountToDiplayHalf" value="3"/>

<div class="forumPaginate">
<span id="firstPaginateButton">First</span>
<span id="previousPaginateButton">Previous</span>

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
<span id="nextPaginateButton">Next</span>
<span id="lastPaginateButton">Last</span>
</div>
