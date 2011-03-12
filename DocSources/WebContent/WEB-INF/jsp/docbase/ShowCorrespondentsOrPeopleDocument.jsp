<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocument" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditCorrespondentsOrPeopleDocumentDiv">
		<h5>CORRESPONDENTS/PEOPLE </h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditCorrespondentsOrPeopleDocument" href="${EditCorrespondentsOrPeopleDocument}">edit</a>
	</security:authorize>
		<hr id="lineSeparator"/>
		<ul>
			<li><b>Sender:</b> ${document.senderPeople.mapNameLf}</li>
			<li><b>From:</b> ${document.senderPlace.placeNameFull} </li>
			<li><b>To:</b> ${document.recipientPlace.placeNameFull}</li>		
			<li><b>Recipient:</b> ${document.recipientPeople.mapNameLf}</li>
			<li>
				<b>People:</b>
				<ul>
				<c:forEach items="${document.epLink}" var="currentPeople">
					<li>${currentPeople.people.mapNameLf}</li>
					<br/>
				</c:forEach>
				</ul>
			</li>
		</ul>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

			$j("#EditCorrespondentsOrPeopleDocument").click(function(){
				$j("#EditCorrespondentsOrPeopleDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			 
		});
	</script>
