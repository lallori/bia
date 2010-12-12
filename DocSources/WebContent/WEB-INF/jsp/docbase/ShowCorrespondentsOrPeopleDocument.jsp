<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsDocument" value="/de/docbase/EditCorrespondentsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditDetailsDocument" value="/de/volbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocument" value="/de/volbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditFactChecksDocument" value="/de/volbase/EditFactChecksDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditCorrespondentsDocumentDiv">
		<h5>CORRESPONDENTS/PEOPLE <a id="EditCorrespondentsDocument" href="/DocSources/de/docbase/EditCorrespondentsDocument.html">edit</a></h5>
		<ul>
			<li><b>Sender:</b> Tudor, Henry VII</li>
			<li><b>From:</b> City of London / England</li>
			<li><b>To:</b> Firenze / Toscan</li>		
			<li><b>Recipient:</b> Medici, Francesco i de'</li>
			<li><b>Ref:</b> Niccolini, Agnolo di Matte</li>
		</ul>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditCorrespondentsDocument").click(function(){$("#EditCorrespondentsDocumentDiv").load($(this).attr("href"));return false;});
		});
	</script>
