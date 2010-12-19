<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocument" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
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
	
	<div id="EditCorrespondentsOrPeopleDocumentDiv">
		<h5>CORRESPONDENTS/PEOPLE <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditCorrespondentsOrPeopleDocument" href="${EditCorrespondentsOrPeopleDocument}">edit</a></security:authorize></h5>
		<ul>
			<li><b>Sender:</b> ${document.senderPeople.last}, ${document.senderPeople.first}</li>
			<li><b>From:</b> ${document.senderPlace.placeNameFull} </li>
			<li><b>To:</b> ${document.receiverPlace.placeNameFull}</li>		
			<li><b>Recipient:</b> ${document.receiverPeople.last}, ${document.receiverPeople.first}</li>
			<li><b>Ref:</b> ???? QUA CHE CI METTO ????</li>
		</ul>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			 $("#EditDetailsDocument").attr('href', "${EditDetailsDocument}");
			 $("#EditFactChecksDocument").attr('href', "${EditFactChecksDocument}");
			 $("#EditCorrespondentsOrPeopleDocument").attr('href', "${EditCorrespondentsOrPeopleDocument}");
			 $("#EditExtractOrSynopsisDocument").attr('href', "${EditExtractOrSynopsisDocument}");

			 $("#EditCorrespondentsOrPeopleDocument").click(function(){$("#EditCorrespondentsOrPeopleDocumentDiv").load($(this).attr("href"));return false;});
		});
	</script>
