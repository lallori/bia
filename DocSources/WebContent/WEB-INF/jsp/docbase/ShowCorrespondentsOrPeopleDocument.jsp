<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
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
			<h5><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.title"/> </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
			<c:if test="${document.entryId > 0}">
			<a id="EditCorrespondentsDocument" href="${EditCorrespondentsOrPeopleDocumentURL}" class="editButton"></a>
			<span id="loading"/>
			</c:if>
		</security:authorize>
		</div>

		<div class="list">
			<div class="row">
<!-- 				Entries like "person name lost" or "to be entered" should be not clickable -->
				<c:if test="${document.senderPeople.personId != 9285 && document.senderPeople.personId != 3905 && document.senderPeople.personId != 198}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.sender"/></div> <div class="value80"><a class="linkPeople" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}<input type="hidden" style="display:none;" class="tabId" value="peopleId${document.senderPeople.personId}" /></a> ${document.senderPeopleUnsure ? '(Unsure)':'' }</div>
				</c:if>
				<c:if test="${document.senderPeople.personId == 9285 || document.senderPeople.personId == 3905 || document.senderPeople.personId == 198}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.sender"/></div> <div class="value80">${document.senderPeople.mapNameLf} ${document.senderPeopleUnsure ? '(Unsure)':'' }</div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${document.senderPlace.placeAllId != 53384 && document.senderPlace.placeAllId != 55627 && document.senderPlace.placeAllId != 54332}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.senderLocation"/></div> <div class="value80"><a class="linkPlace" href="${CompareFromURL}">${document.senderPlace.placeNameFull} </a> ${document.senderPlaceUnsure ? '(Unsure)':'' }</div>
				</c:if>
				<c:if test="${document.senderPlace.placeAllId == 53384 || document.senderPlace.placeAllId == 55627 || document.senderPlace.placeAllId == 54332 }">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.senderLocation"/></div> <div class="value80">${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? '(Unsure)':'' }</div>
				</c:if>
			</div>	
			<div class="row">
				<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.senderNotes"/></div> <div class="value80">${document.sendNotes}</div>
			</div>
			<div class="row">
				<c:if test="${document.recipientPeople.personId != 9285 && document.recipientPeople.personId != 3905 && document.recipientPeople.personId != 198}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.recipient"/></div> <div class="value80"><a class="linkPeople" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}<input type="hidden" style="display:none;" class="tabId" value="peopleId${document.recipientPeople.personId}" /></a> ${document.recipientPeopleUnsure ? '(Unsure)':'' }</div>
				</c:if>
				<c:if test="${document.recipientPeople.personId == 9285 || document.recipientPeople.personId == 3905 || document.recipientPeople.personId == 198}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.recipient"/></div> <div class="value80">${document.recipientPeople.mapNameLf} ${document.senderPeopleUnsure ? '(Unsure)':'' }</div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${document.recipientPlace.placeAllId != 53384 && document.recipientPlace.placeAllId != 55627 && document.recipientPlace.placeAllId != 54332}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.recipientLocation"/></div> <div class="value80"><a class="linkPlace" href="${CompareToURL}">${document.recipientPlace.placeNameFull}</a> ${document.recipientPlaceUnsure ? '(Unsure)':'' }</div>
				</c:if>
				<c:if test="${document.recipientPlace.placeAllId == 53384 || document.recipientPlace.placeAllId == 55627 || document.recipientPlace.placeAllId == 54332}">
					<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.recipientLocation"/></div> <div class="value80">${document.recipientPlace.placeNameFull} ${document.recipientPlaceUnsure ? '(Unsure)':'' }</div>
				</c:if>
			</div>
			<div class="row">
				<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.recipientNotes"/></div> <div class="value80">${document.recipNotes}</div>
			</div>	
			<br>
			<div class="row">
				<div class="item37"><fmt:message key="docbase.showCorrespondentsOrPeopleDocument.peopleReferredTo"/></div> 
				
			<c:forEach items="${document.epLink}" var="currentPeople">
			<!-- This is a method to have a value near the item with the text People. -->	
					<c:if test="${currentPeople.docRole!= 'S' && currentPeople.docRole != 'R'}">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPeople.person.personId}" />
					</c:url>
					<c:if test="${currentPeople.person.personId != 9285 && currentPeople.person.personId != 3905 && currentPeople.person.personId != 198}">
					<div class="value80"><a class="linkPeople" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}<input type="hidden" style="display:none;" class="tabId" value="peopleId${currentPeople.person.personId}" /></a></div>
					</c:if>
					<c:if test="${currentPeople.person.personId == 9285 || currentPeople.person.personId == 3905 || currentPeople.person.personId == 198}">
					<div class="value80">${currentPeople.person.mapNameLf}</div>
					</c:if>
					

				</div>
				<div class="row">
					<div class="item">&nbsp</div>
				</c:if>
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
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'visible');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'visible');
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
				var id = $j(this).find(".tabId").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".linkPlace").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
	</script>
