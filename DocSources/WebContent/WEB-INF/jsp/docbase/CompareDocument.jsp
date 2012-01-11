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
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsDocumentURL" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>

		<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>
	</security:authorize>
	
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

	<div>
		<a href="${ShowDocumentURL}" id="editLink${document.entryId}" class="buttonLarge">Click here to edit this document</a>
	</div>
	
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
	
	
	<div id="EditCorrespondentsOrPeopleDocumentDiv" class="background">
	<div class="title">
		<h5>CORRESPONDENTS/PEOPLE </h5>
	</div>
		<div class="list">
			<div class="row">
				<div class="item">Sender</div> <div class="value80"><a class="linkPeople" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</a></div>
			</div>
			<div class="row">
				<div class="item">From</div> <div class="value80"><a class="linkPeople" href="${CompareFromURL}">${document.senderPlace.placeNameFull} </a></div>
			</div>	
			<div class="row">
				<div class="item">Recipient</div> <div class="value80"><a class="linkPeople" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</a></div>
			</div>
			<div class="row">
				<div class="item">To</div> <div class="value80"><a class="linkPeople" href="${CompareToURL}">${document.recipientPlace.placeNameFull}</a></div>
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
					<div class="item">&nbsp;</div><div class="value80"><a class="linkPeople" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}</a></div>
					
				</div>
			</c:forEach>
			</div>
	</div>

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
		});
	</script>
