<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="currentPage" value="/de/mview/EditDocumentInManuscriptViewer.do">
			<c:param name="entryId" value="${command.entryId}" />
			<c:param name="volNum" value="${command.volNum}" />
			<c:param name="volLetExt" value="${command.volLetExt}" />
			<c:param name="imageOrder" value="${command.imageOrder + 1}" />
			<c:param name="total" value="${command.total}" />
			<c:param name="totalRubricario" value="${command.totalRubricario}" />
			<c:param name="totalCarta" value="${command.totalCarta}" />
			<c:param name="totalAppendix" value="${command.totalAppendix}" />
			<c:param name="totalOther" value="${command.totalOther}" />
			<c:param name="totalG" value="${command.totalG}" />
		</c:url>
	
		<c:url var="nextPage" value="/de/mview/EditDocumentInManuscriptViewer.do">
			<c:param name="entryId" value="${command.entryId}" />
			<c:param name="volNum" value="${command.volNum}" />
			<c:param name="volLetExt" value="${command.volLetExt}" />
			<c:param name="imageOrder" value="${command.imageOrder + 1}" />
			<c:param name="total" value="${command.total}" />
			<c:param name="totalRubricario" value="${command.totalRubricario}" />
			<c:param name="totalCarta" value="${command.totalCarta}" />
			<c:param name="totalAppendix" value="${command.totalAppendix}" />
			<c:param name="totalOther" value="${command.totalOther}" />
			<c:param name="totalG" value="${command.totalG}" />
		</c:url>
	
		<c:url var="previousPage" value="/de/mview/EditDocumentInManuscriptViewer.do">
			<c:param name="entryId" value="${command.entryId}" />
			<c:param name="volNum" value="${command.volNum}" />
			<c:param name="volLetExt" value="${command.volLetExt}" />
			<c:param name="imageOrder" value="${command.imageOrder - 1}" />
			<c:param name="total" value="${command.total}" />
			<c:param name="totalRubricario" value="${command.totalRubricario}" />
			<c:param name="totalCarta" value="${command.totalCarta}" />
			<c:param name="totalAppendix" value="${command.totalAppendix}" />
			<c:param name="totalOther" value="${command.totalOther}" />
			<c:param name="totalG" value="${command.totalG}" />
			<c:param name="flashVersion" value="true" />
		</c:url>
	</security:authorize>

	<div id="prevNextButtons">
		<div style="text-align:center; color:#6D5C4D">Flip throught</div>
		<br />
	    <div id="prevButton">
		<c:if test="${command.imageOrder == 1}">
	    	<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
		</c:if>
		<c:if test="${command.imageOrder > 1}">
			<a id="previous" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
		</c:if>
		</div>
		<div id="nextButton">
		<c:if test="${command.imageOrder == command.total}">
			<img src="<c:url value="/images/button_next.png" />" alt="next" />
		</c:if>
		<c:if test="${command.imageOrder < command.total}">
			<a id="next" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
		</c:if>
		</div>
	</div>
	
	<div id="line"></div>
	
	<div id="rubricarioMoveTo">
		<form id="moveToRubricarioForm" action="/DocSources/de/volbase/moveToRubricario.do" method="post" class="edit">
			<label id="secondRecordLabel" for="secondRecord" >Move to rubricario (page)</label>
			<input id="secondRecord" name="secondRecord" class="input_4c" type="text" value="" />
			<input id="goR" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
		</form>
	</div>
	
	<div id="folioMoveTo">
		<form id="moveToFolioForm" action="/DocSources/de/volbase/moveToFolio.do" method="post" class="edit">
			<label id="firstRecordLabel" for="firstRecord" >Move to folio (page)</label>
			<input id="firstRecord" name="firstRecord" class="input_4c" type="text" value="" />
			<input id="go" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
		</form>
	</div>
	
	<div id="line2"></div>
	
	<div id="personalNotes">
		<a href="#"><img src="<c:url value="/images/button_personalNotes.png" />" alt="Personal Notes" /></a>
	</div>
	
	<div id="exit">
		<a href="#"><img src="<c:url value="/images/button_exit.png" />" alt="Exit" /></a>
	</div>