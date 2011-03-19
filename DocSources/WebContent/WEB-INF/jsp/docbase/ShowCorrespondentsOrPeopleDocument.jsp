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
		<a id="EditCorrespondentsOrPeopleDocument" href="${EditCorrespondentsOrPeopleDocument}">edit</a><span id="loading"/>
	</security:authorize>
		<hr id="lineSeparator"/>
		<ul>
			<li><b>Sender:</b> <a class="linkSearch" href="">${document.senderPeople.mapNameLf}</a></li>
			<li><b>From:</b> <a class="linkSearch" href="">${document.senderPlace.placeNameFull} </a></li>
			<li><b>To:</b> <a class="linkSearch" href="">${document.recipientPlace.placeNameFull}</a></li>		
			<li><b>Recipient:</b> <a class="linkSearch" href="">${document.recipientPeople.mapNameLf}</a></li>
			<li>
				<b>People:</b>
				<ul>
				<c:forEach items="${document.epLink}" var="currentPeople">
					<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
						<c:param name="personId"   value="${currentPeople.people.personId}" />
					</c:url>
					<li><a class="linkSearch" href="${ShowPersonURL}">${currentPeople.people.mapNameLf}</a></li>
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
				$j(this).next().css('visibility', 'visible');
				$j("#EditCorrespondentsOrPeopleDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j(".linkSearch").click(function() {
				$j("#body_right").load($j(this).attr("href"));
				return false;
			});
			 
		});
	</script>
