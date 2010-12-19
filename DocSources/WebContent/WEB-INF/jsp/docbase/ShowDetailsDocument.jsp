<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsDocument" value="/de/docbase/EditCorrespondentsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditDetailsDocument" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocument" value="/de/volbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditFactChecksDocument" value="/de/volbase/EditFactChecksDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditDetailsDocumentDiv">
		<h5>DOCUMENT DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsDocument" href="${EditDetailsDocument}">edit</a></security:authorize></h5>
		<div id="createdby"><h6>CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></h6></div>
		<hr id="lineSeparator"/>
		<div id="DocumentImageDiv">
			<img src="<c:url value="/images/image_document.png"/>" alt="document image" />
			<p><a href="#">Show in manuscript viewer</a></p>
			<p><a href="#">Attach folios</a>
		</div>
		
		<ul>
			<li><b>Doc ID:</b> ${document.entryId == 0 ? '' : document.entryId}</li>
			<li><b>Volume (MDP):</b> ${document.volume.volNum}</li>
			<li><b>Insert/Part:</b> ${document.insertNum} / ${document.insertLet}</li>
			<li><b>Folio Start:</b> ${document.folioNum} / ${document.folioMod}</li>
			<li><b>Paginated:</b> ${document.folioNum}</li>
			<li><b>Modern Date:</b> ${document.yearModern}</li>
			<li><b>Recorded year:</b> ${document.docYear} ${document.docMonthNum} ${document.docDay}</li>
			<li><b>Date Notes:</b> ${document.dateNotes}</li>
		</ul>
	</div>
	
	<br />
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditDetailsDocument").click(function(){$("#EditDetailsDocumentDiv").load($(this).attr("href"));return false;});
		});
	</script>
