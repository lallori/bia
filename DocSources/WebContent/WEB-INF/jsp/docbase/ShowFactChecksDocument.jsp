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
	
	<div id="EditFactChecksDocumentDiv">
		<h5>FACT CHECK <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsDocument" href="${EditDetailsDocument}">edit</a></security:authorize></h5>
		<ul>
			<li>YES</li>
		</ul>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditFactChecksDocument").click(function(){$("#EditFactChecksDocumentDiv").load($(this).attr("href"));return false;});
		});
	</script>
