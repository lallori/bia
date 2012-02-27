<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${document.entryId}" />
	</c:url>
	
	<c:url var="ShareDocumentURL" value="/de/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	
	<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="flashVersion"   value="false" />
	</c:url>

	<c:url var="ShowDocumentExplorerURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="volNum"   value="${document.volume.volNum}" />
		<c:param name="volLetExt"   value="${document.volume.volLetExt}" />
		<c:param name="folioNum"   value="${document.folioNum}" />
		<c:param name="folioMod"   value="${document.folioMod}" />
		<c:param name="imageType"   value="C" />
		<c:param name="imageProgTypeNum"   value="${document.folioNum}" />
		<c:param name="flashVersion"   value="true" />
	</c:url>
	
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

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div>
		<a href="${ShowDocumentURL}" id="editLink${document.entryId}" class="buttonMedium">Edit this Document</a>
	</div>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div>
		<a href="${ShowDocumentURL}" id="editLink${document.entryId}" class="buttonMedium">Show this Document</a>
	</div>
	</security:authorize>
	
	
	<div id="EditDetailsDocumentDiv" class="background">
	<div class="title">
		<h5>DOCUMENT DETAILS </h5>
		</div>
<!-- 		<div id="CreatedSharePrintDiv"> -->
<%-- 			<div id="createdby">CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div> --%>
<!-- 			<a title="Print this record" href="#" id="buttonPrint"></a> -->
<!-- 			<div id="buttonShareLink"> -->
<!-- 				<a href="#"><img src="/DocSources/images/1024/img_transparent.png"></a> -->
<!-- 				<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
<!-- 		<div id="DocumentImageDiv"> -->
<%-- 			<c:if test="${not empty image}"> --%>
<%-- 			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>"> --%>
<%-- 			<p><a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewerURL}">Show in manuscript viewer</a></p> --%>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${empty image}"> --%>
<%-- 			<img src="<c:url value="/images/1024/img_document.png"/>" alt="document image" /> --%>
<%-- 			</c:if> --%>
<!-- 		</div> -->
		
		<div class="listDetails">
			<div class="row">
				<div class="item60">Doc ID</div> <div class="value">${document.entryId == 0 ? '' : document.entryId}</div>
			</div>
			<div class="row">
				<div class="item60">Volume (MDP)</div> <div class="value">${document.volume.volNum}${document.volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item60">Insert/Part</div> <div class="value">${document.insertNum} / ${document.insertLet}</div>
			</div>
			<div class="row">
				<div class="item60">Document starts at folio </div> <div class="value">${document.folioNum} / ${document.folioMod}</div>
			</div>
			<div class="row">
				<div class="item60">Paginated</div> <div class="value">${document.unpaged ? 'Yes' : 'NO'}</div>
			</div>
			<div class="row">
				<div class="item60">Nonconsecutive</div> <div class="value">${document.contDisc ? 'Yes' : 'NO'}</div>
			</div>
			<div class="row">
				<div class="item60">Document Typology (other than letter)</div> <div class="value">${document.docTypology}</div>
			</div>
			<div class="row">
				<div class="item60">Modern Date</div> <div class="valueHilight">${document.yearModern}</div>
			</div>
			<div class="row">
				<div class="item60">Recorded year</div> <div class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</div>
			</div>
			<div class="row">
				<div class="item60">Date uncertain or approximate</div> <div class="value">${document.dateUns ? 'Yes' : 'NO'}</div>
			</div>
			<div class="row">
				<div class="item60">Undated</div> <div class="value">${document.undated ? 'Yes' : 'NO'}</div>
			</div>
		</div>
		<div class="list">
			<div class="row">
				<div class="item37">Date Notes</div> <div class="value50">${document.dateNotes}</div>
			</div>
		</div>
	</div>
	
	<br />
	<br />
	
	<div id="EditFactCheckDocumentDiv" class="background">
	<div class="title">
		<h5>FACT CHECK </h5>
		</div>

		<div class="list">
			<div class="row">
				<div class="valueHilight">${document.factChecks.addLRes}</div>
			</div>
		</div>
	</div>
	
	<br />
	<br />
	
	<div id="EditCorrespondentsOrPeopleDocumentDiv" class="background">
	<div class="title">
		<h5>CORRESPONDENTS/PEOPLE </h5>
	</div>
		<div class="list">
			<div class="row">
<!-- 				Entries like "person name lost" or "to be entered" should be not clickable -->
				<c:if test="${document.senderPeople.personId != 9285 && document.senderPeople.personId != 3905 && document.senderPeople.personId != 198}">
					<div class="item">Sender</div> <div class="value80"><a class="linkPeople" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</a></div>
				</c:if>
				<c:if test="${document.senderPeople.personId == 9285 || document.senderPeople.personId == 3905 || document.senderPeople.personId == 198}">
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
				<c:if test="${document.recipientPeople.personId != 9285 && document.recipientPeople.personId != 3905 && document.recipientPeople.personId != 198}">
					<div class="item">Recipient</div> <div class="value80"><a class="linkPeople" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</a></div>
				</c:if>
				<c:if test="${document.recipientPeople.personId == 9285 || document.recipientPeople.personId == 3905 || document.recipientPeople.personId == 198}">
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
				<div class="item">People</div> 
				
			<c:forEach items="${document.epLink}" var="currentPeople">
			<!-- This is a method to have a value near the item with the text People. -->	
					<c:if test="${currentPeople.docRole!= 'S' && currentPeople.docRole != 'R'}">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPeople.person.personId}" />
					</c:url>
					<c:if test="${currentPeople.person.personId != 9285 && currentPeople.person.personId != 3905 && currentPeople.person.personId != 198}">
					<div class="value80"><a class="linkPeople" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}</a></div>
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
	
	<br />
	<br />

	<div id="EditExtractOrSynopsisDocumentDiv" class="background">
	<div class="title">
		<h5>EXTRACT/SYNOPSIS </h5>
		</div>

		<div class="list">
			<div class="row">
				<div class="item">Extract</div>
				<div class="value80" id="extract">${document.synExtract.docExtract}</div>
			</div>
			<div class="row">
				<div class="item">Synopsis</div>
				<div class="value80" id="synopsis">${document.synExtract.synopsis}</div>
			</div>
		</div>
	</div>
	
	<br />

	<div id="EditTopicsDocumentDiv" class="background">
	<div class="title">
		<h5>TOPICS </h5>
		</div>
	
		<div class="list">
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
			<div class="row">
				<div class="item">Topic:</div>
				<div class="value80"> ${currentTopicAndPlace.topic.topicTitle}</div>
			</div>
			<div class="row">
				<div class="item">Topic Place:</div>
				<div class="value80"> ${currentTopicAndPlace.place.placeNameFull}</div>
			</div>
			<br/>
		</c:forEach>
	</div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function(){
			$j("#editLink${document.entryId}").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				var selected = $j("#tabs").tabs('option', 'selected');
				$j("#tabs").tabs('remove', selected);
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
