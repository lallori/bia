<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocumentURL" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="CompareSenderURL" value="/src/peoplebase/ComparePerson.do">
		<c:param name="personId"   value="${document.senderPeople.personId}" />
	</c:url>
	<c:url var="CompareRecipientURL" value="/src/peoplebase/ComparePerson.do">
		<c:param name="personId"   value="${document.recipientPeople.personId}" />
	</c:url>
	<div id="EditCorrespondentsOrPeopleDocumentDiv" class="background">
		<div class="title">
			<h5>CORRESPONDENTS/PEOPLE </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${document.entryId > 0}">
			<a id="EditCorrespondentsOrPeopleDocument" href="${EditCorrespondentsOrPeopleDocumentURL}" class="editButton"></a>
			<span id="loading"/>
			</c:if>
		</security:authorize>
		</div>

		<div class="listDetails">
			<div class="row">
				<div class="item">Sender:</div> <div class="value"><a class="linkPeople" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</a></div>
			</div>
			<div class="row">
				<div class="item">From:</div> <div class="value"><a class="linkPeople" href="${CompareFromURL}">${document.senderPlace.placeNameFull} </a></div>
			</div>	
			<div class="row">
				<div class="item">Recipient:</div> <div class="value"><a class="linkPeople" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</a></div>
			</div>
			<div class="row">
				<div class="item">To:</div> <div class="value"><a class="linkPeople" href="${CompareToURL}">${document.recipientPlace.placeNameFull}</a></div>
			</div>	
			<br>
			<div class="row">
				<div class="item">People:</div> <div class="value"></div>
			</div>	
			<c:forEach items="${document.epLink}" var="currentPeople">
				<div class="row">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPeople.person.personId}" />
					</c:url>
					<div class="item">&nbsp;</div><div class="value"><a class="linkPeople" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}</a></div>
					<br/>
				</div>
			</c:forEach>
			</div>
		</div>
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
			
			$j(".linkPeople").click(function() {
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			});
		});
	</script>
