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
		<c:url var="EditExtractOrSynopsisDocument" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditFactCheckDocument" value="/de/docbase/EditFactCheckDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditTopicsDocument" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditCorrespondentsOrPeopleDocumentDiv">
		<h5>CORRESPONDENTS/PEOPLE <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditCorrespondentsOrPeopleDocument" href="${EditCorrespondentsOrPeopleDocument}">edit</a></security:authorize></h5>
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
			 $j("#EditDetailsDocument").attr('href', "${EditDetailsDocument}");
			 $j("#EditFactCheckDocument").attr('href', "${EditFactCheckDocument}");
			 $j("#EditCorrespondentsOrPeopleDocument").attr('href', "${EditCorrespondentsOrPeopleDocument}");
			 $j("#EditTopicsDocument").attr('href', "${EditTopicsDocument}");
			 $j("#EditExtractOrSynopsisDocument").attr('href', "${EditExtractOrSynopsisDocument}");

			 $j("#EditCorrespondentsOrPeopleDocument").click(function(){$j("#EditCorrespondentsOrPeopleDocumentDiv").load($j(this).attr("href"));return false;});
			 
			 $j("#EditCorrespondentsOrPeopleDocument").click(function(){
					$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
					 $j("#EditCorrespondentsOrPeopleDocument").load($j(this).attr("href"));
					return false;
				});
				
		});
	</script>
