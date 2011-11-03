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
	
	<c:url var="CompareFromURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${document.senderPlace.placeAllId}" />
	</c:url>
	
	<c:url var="CompareToURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${document.recipientPlace.placeAllId}" />
	</c:url>

	<div id="EditCorrespondentsDocumentDiv" class="background">
		<div class="title">
			<h5>CORRESPONDENTS/PEOPLE </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${document.entryId > 0}">
			<a id="EditCorrespondentsDocument" href="${EditCorrespondentsOrPeopleDocumentURL}" class="editButton"></a>
			<span id="loading"/>
			</c:if>
		</security:authorize>
		</div>

		<div class="list">
			<div class="row">
				<c:if test="${document.senderPeople.personId != 9285 && document.senderPeople.personId != 3905}">
					<div class="item">Sender</div> <div class="value80"><a class="linkPeople" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</a></div>
				</c:if>
				<c:if test="${document.recipientPeople.personId == 9285 || document.senderPeople.personId == 3905}">
					<div class="item">Sender</div> <div class="value80">${document.senderPeople.mapNameLf}</div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${document.senderPlace.placeAllId != 53384 && document.senderPlace.placeAllId != 55627 && document.senderPlace.placeAllId != 54332}">
					<div class="item">From</div> <div class="value80"><a class="linkPeople" href="${CompareFromURL}">${document.senderPlace.placeNameFull} </a></div>
				</c:if>
				<c:if test="${document.senderPlace.placeAllId == 53384 || document.senderPlace.placeAllId == 55627 || document.senderPlace.placeAllId == 54332 }">
					<div class="item">From</div> <div class="value80">${document.senderPlace.placeNameFull} </div>
				</c:if>
			</div>	
			<div class="row">
				<c:if test="${document.recipientPeople.personId != 9285 && document.recipientPeople.personId != 3905}">
					<div class="item">Recipient</div> <div class="value80"><a class="linkPeople" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</a></div>
				</c:if>
				<c:if test="${document.recipientPeople.personId == 9285 || document.recipientPeople.personId == 3905}">
					<div class="item">Recipient</div> <div class="value80">${document.recipientPeople.mapNameLf}</div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${document.recipientPlace.placeAllId != 53384 && document.recipientPlace.placeAllId != 55627 && document.recipientPlace.placeAllId != 54332}">
					<div class="item">To</div> <div class="value80"><a class="linkPeople" href="${CompareToURL}">${document.recipientPlace.placeNameFull}</a></div>
				</c:if>
				<c:if test="${document.recipientPlace.placeAllId == 53384 || document.recipientPlace.placeAllId == 55627 || document.recipientPlace.placeAllId == 54332}">
					<div class="item">To</div> <div class="value80">${document.recipientPlace.placeNameFull}</div>
				</c:if>
			</div>	
			<br>
			<div class="row">
				<div class="item">People</div> <div class="value80"></div>
			</div>	
			<c:forEach items="${document.epLink}" var="currentPeople">
				<div class="row">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPeople.person.personId}" />
					</c:url>
					<c:if test="${currentPeople.person.personId != 9285 && currentPeople.person.personId != 3905}">
					<div class="item">&nbsp;</div><div class="value80"><a class="linkPeople" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}</a></div>
					</c:if>
					<c:if test="${document.recipientPeople.personId == 9285 || currentPeople.person.personId == 3905}">
					<div class="item">&nbsp;</div><div class="value80">${currentPeople.person.mapNameLf}</div>
					</c:if>
					
				</div>
			</c:forEach>
			</div>
		
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

			$j("#EditCorrespondentsDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditCorrespondentsDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j(".linkPeople").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
	</script>
