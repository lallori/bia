<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- Required tag attributes --%>
<%@ attribute name="page" required="true" type="org.medici.bia.common.pagination.Page" 
	description="the current page to paginate"%>

<%@ attribute name="buttonClass" required="true" type="java.lang.String" 
	description="the paginator buttons style class (it is applied to all buttons except the active page button)"%>
	
<%@ attribute name="activeButtonClass" required="true" type="java.lang.String" 
	description="the active page button style class"%>
	
<%@ attribute name="url" required="true" type="java.lang.String" 
	description="the base url of the paginator buttons"%>

<%-- Optional attributes --%>
<%@ attribute name="thisPageAlias" required="false" type="java.lang.String" %>
<%@ attribute name="totalPagesAlias" required="false" type="java.lang.String" %>
<%@ attribute name="elementsForPageAlias" required="false" type="java.lang.String" %>

<%@ attribute name="firstButtonCaption" required="false" type="java.lang.String" %>
<%@ attribute name="previousButtonCaption" required="false" type="java.lang.String" %>
<%@ attribute name="nextButtonCaption" required="false" type="java.lang.String" %>
<%@ attribute name="lastButtonCaption" required="false" type="java.lang.String" %>
	
<%@ attribute name="pageViewId" required="false" type="java.lang.String" 
	description="the DOM container identifier where to load the url results. You have to provide it only if the standard click handlers has to be attached to the paginator buttons."%>
	
<%@ attribute name="useInternalCSS" required="false" type="java.lang.Boolean" %>

<%-- Other tag options --%>
	<c:set var="pageCountToDisplay" value="5"/>
	<c:set var="pageCountToDisplayHalf" value="3"/>
	
<%-- Paginator layout --%>

	<c:set var="thisPage" value="${empty thisPageAlias ? 'thisPage' : thisPageAlias}" />
	<c:set var="totalPages" value="${empty totalPagesAlias ? 'totlaPages' : totalPagesAlias}" />
	<c:set var="elementsForPage" value="${empty elementsForPageAlias ? 'elementsForPage' : elementsForPageAlias}" />

	<c:set var="firstBtn" value="${empty firstButtonCaption ? 'First' : firstButtonCaption}" />
	<c:set var="previousBtn" value="${empty previousButtonCaption ? 'Previous' : previousButtonCaption}" />
	<c:set var="nextBtn" value="${empty nextButtonCaption ? 'Next' : nextButtonCaption}" />
	<c:set var="lastBtn" value="${empty lastButtonCaption ? 'Last' : lastButtonCaption}" />

	<c:set var="urlSeparator" value="${fn:contains(url, '?') ? '&' : '?'}" />
	
	<c:choose>
		<c:when test="${page.thisPage != 1}">
			<a class="${buttonClass} paginatorBtn" href="${url}${urlSeparator}${thisPage}=1&${totalPages}=${page.totalPages}&${elementsForPage}=${page.elementsForPage}">${firstBtn}</a>
			<a class="${buttonClass} paginatorBtn" href="${url}${urlSeparator}${thisPage}=${page.thisPage - 1}&${totalPages}=${page.totalPages}&${elementsForPage}=${page.elementsForPage}">${previousBtn}</a>
		</c:when>
		<c:otherwise>
			<span class="${buttonClass} paginatorBtn">${firstBtn}</span>
			<span class="${buttonClass} paginatorBtn">${previousBtn}</span>
		</c:otherwise>
	</c:choose>
	
	<span>
		<c:choose>
		  	<c:when test="${page.totalPages <= pageCountToDisplay}">
				<c:forEach begin="1" end="${page.totalPages}" var="currentPage">
					<%@ include file="pageLink.tagf" %>
				</c:forEach>
			</c:when>
		  	<c:when test="${page.thisPage <= pageCountToDisplayHalf}">
				<c:forEach begin="1" end="${pageCountToDisplay}" var="currentPage">
					<%@ include file="pageLink.tagf" %>
				</c:forEach>
			</c:when>	
		  	<c:when test="${page.thisPage >= (page.totalPages - pageCountToDisplayHalf)}">
				<c:forEach begin="${page.totalPages-pageCountToDisplay + 1}" end="${page.totalPages}" var="currentPage">
					<%@ include file="pageLink.tagf" %>
				</c:forEach>
			</c:when>	
			<c:otherwise>
				<c:forEach begin="${page.thisPage - (pageCountToDisplay / 2) + 1}" end="${(page.thisPage - (pageCountToDisplay / 2) + 1) + pageCountToDisplay - 1}" var="currentPage">
					<%@ include file="pageLink.tagf" %>
				</c:forEach>
		 	</c:otherwise>
		</c:choose>
	</span>
	
	<c:choose>
		<c:when test="${page.thisPage != page.totalPages}">
			<a class="${buttonClass} paginatorBtn" href="${url}${urlSeparator}${thisPage}=${page.thisPage + 1}&${totalPages}=${page.totalPages}&${elementsForPage}=${page.elementsForPage}">${nextBtn}</a>
			<a class="${buttonClass} paginatorBtn" href="${url}${urlSeparator}${thisPage}=${page.totalPages}&${totalPages}=${page.totalPages}&${elementsForPage}=${page.elementsForPage}">${lastBtn}</a>
		</c:when>
		<c:otherwise>
			<span class="${buttonClass} paginatorBtn">${nextBtn}</span>
			<span class="${buttonClass} paginatorBtn">${lastBtn}</span>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${not empty pageViewId}">
		<!--
			The following script is included if the standard click handlers have to be attached to the paginator buttons.
			The standard click handlers load the button url in the DOM container whose DOM identifier is defined by the 'pageViewId' parameter.
			If the standard click handlers are not attached to the paginator buttons you have to provide them externally. 
		 -->
		<script>
			$j(document).ready(function() {
				var pageViewId = '${pageViewId}';
				
				$j(".paginatorBtn").die();
				$j(".paginatorBtn").click(function() {
					if (typeof $j(this).attr('href') !== 'undefined') {
						$j("#" + pageViewId).load($j(this).attr('href'), function(responseText, statusText, xhr) {
							var _this = $j(this);
							if (statusText !== 'error') {
								setTimeout(function() {
									console.log('Scrolling to top');
									_this.scrollTo(0, 0);
					    		},200);
							} else {
								// TODO: handle error
							}
						});
					}
					return false;
				});
				
			});
		</script>
	</c:if>
	
	<c:if test="${useInternalCSS}">
		<style>
			.paginatorBtn,.paginateActive {
				margin: 0 3px;
				background-color: #ead9d6;
				border: 1px solid #dcc0ba;
				border-radius: 5px;
				cursor: pointer;
				padding: 2px 5px;
				font-size: 10px;
			}
			
			.paginatorBtn:hover,.paginateActive {
				background-color: #dcc0ba;
			}
		</style>
	</c:if>
	