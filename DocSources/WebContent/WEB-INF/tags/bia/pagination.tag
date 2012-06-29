<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.medici.docsources.common.pagination.Page" %>

<c:if test="${page.total > page.elementsForPage}">
	<div class="pagination">
		<!-- Previous page  -->
		<c:if test="${page.thisPage > 1}">
			<c:choose>
				<c:when test="${!isSearch}">
					<c:if test="${page.thisPage - 1 > 0}">
						<c:set var="extraArgs" value="/${page.thisPage - 1}"/>
					</c:if>
					
					<c:if test="${info.id > 0}">
						<c:set var="extraArgs" value="${extraArgs}/${info.id}"/>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:set var="extraArgs" value="AAA"/>
				</c:otherwise>
			</c:choose>
			
			<a href="<jforum:url address='${info.baseUrl}${extraArgs}'/>">&#9668;</a>
		</c:if>
		
		<c:if test="${page.totalPages < 10}">
			<c:forEach begin="1" end="${page.totalPages}" var="currentPage">
				<%@ include file="pageLink.tagf" %>
			</c:forEach>
		</c:if>
		
		<c:if test="${page.totalPages >= 10}">
			<!-- Always write the first 3 links -->
			<c:forEach begin="1" end="3" var="currentPage">
				<%@ include file="pageLink.tagf" %>
			</c:forEach>
			
			<!-- Intemediate links -->
			<c:choose>
				<c:when test="${page.thisPage > 1 && page.thisPage < page.totalPages}">
					<c:if test="${page.thisPage > 5}">
						<span class="gensmall">...</span>
					</c:if>
					
					<c:choose>
						<c:when test="${page.thisPage > 4}">
							<c:set var="min" value="${page.thisPage - 1}"/>
						</c:when>
						<c:otherwise>
							<c:set var="min" value="4"/>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${page.thisPage < info.totalPages - 4}">
							<c:set var="max" value="${page.thisPage + 2}"/>
						</c:when>
						<c:otherwise>
							<c:set var="max" value="${page.totalPages - 2}"/>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${max >= min + 1}">
						<c:forEach begin="${min}" end="${max - 1}" var="currentPage">
							<%@ include file="pageLink.tagf" %>
						</c:forEach>
					</c:if>
					
					<c:if test="${page.thisPage < info.totalPages - 4}">
						<span class="gensmall">...</span>
					</c:if>
				</c:when>
				<c:otherwise>
					<span class="gensmall">...</span>
				</c:otherwise>
			</c:choose>
			
			<!-- Write the last 3 links -->
			<c:forEach begin="${page.totalPages - 2}" end="${page.totalPages}" var="page">
				<%@ include file="pageLink.tagf" %>
			</c:forEach>
		</c:if>
		
		<c:remove var="extraArgs"/>
		<!-- Next page -->
		<c:if test="${page.thisPage < info.totalPages}">
			<c:set var="extraArgs" value="/${page.thisPage + 1}"/>
			
			<c:if test="${info.id > 0}">
				<c:set var="extraArgs" value="${extraArgs}/${info.id}"/>
			</c:if>
			
			<a href="<jforum:url address='${info.baseUrl}${extraArgs}'/>">&#9658;</a>
		</c:if>
		
		<c:if test="${showGotoBox}">
			<a href="#goto" onClick="return overlay(this, 'goToBox', 'rightbottom');"><jforum:i18n key='ForumIndex.goToGo'/></a>
			<div id="goToBox">
				<div class="title"><jforum:i18n key='goToPage'/>...</div>
				<div class="form">
					<input type="text" style="width: 50px;" id="pageToGo">
					<input type="button" value=" <jforum:i18n key='ForumIndex.goToGo'/> " onClick="goToAnotherPage(${totalPages}, ${recordsPerPage}, '${contextPath}', '${moduleName}', '${action}', ${id}, '${extension}');">
					<input type="button" value="<jforum:i18n key='cancel'/>" onClick="document.getElementById('goToBox').style.display = 'none';">
				</div>
			</div>
		</c:if>
	</div>
</c:if>