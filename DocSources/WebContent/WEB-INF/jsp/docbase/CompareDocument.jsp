<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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

	<div id="EditDetailsDocumentDiv">
		<h5>DOCUMENT DETAILS </h5>
		<div id="CreatedSharePrintDiv">
			<div id="createdby">CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div>
			<a title="Print this record" href="#" id="buttonPrint"></a>
			<div id="buttonShareLink">
				<a href="#"><img src="/DocSources/images/1024/img_transparent.png"></a>
				<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span>
			</div>
		</div>
		
		<div id="DocumentImageDiv">
			<c:if test="${not empty image}">
			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>">
			<p><a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewerURL}">Show in manuscript viewer</a></p>
			</c:if>
			<c:if test="${empty image}">
			<img src="<c:url value="/images/1024/img_document.png"/>" alt="document image" />
			</c:if>
		</div>
		
		<div class="listDetails">
			<div class="row">
				<div class="item">Doc ID</div> <div class="value">${document.entryId == 0 ? '' : document.entryId}</div>
			</div>
			<div class="row">
				<div class="item">Volume (MDP)</div> <div class="value">${document.volume.volNum}${document.volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item">Insert/Part</div> <div class="value">${document.insertNum} / ${document.insertLet}</div>
			</div>
			<div class="row">
				<div class="item">Document starts at folio </div> <div class="value">${document.folioNum} / ${document.folioMod}</div>
			</div>
			<div class="row">
				<div class="item">Paginated</div> <div class="value">${document.unpaged}</div>
			</div>
			<div class="row">
				<div class="item">Document Typology (other than letter)</div> <div class="value">${document.docTypology}</div>
			</div>
			<div class="row">
				<div class="item">Modern Date</div> <div class="value">${document.yearModern}</div>
			</div>
			<div class="row">
				<div class="item">Recorded year</div> <div class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</div>
			</div>
			<div class="row">
				<div class="item">Date uncertain or approximate</div> <div class="value">${document.dateUns}</div>
			</div>
			<div class="row">
				<div class="item">Undated</div> <div class="value">${document.undated}</div>
			</div>
			<div class="row">
				<div class="item">Date Notes</div> <div class="value">${document.dateNotes}</div>
			</div>
		</div>
	</div>
	
	<br />
	
	<div id="EditFactCheckDocumentDiv">
		<h5>FACT CHECK </h5>

		<hr id="lineSeparator"/>
		<ul>
			<li>${document.factChecks.addLRes}</li>
		</ul>
	</div>
	
	<div id="EditCorrespondentsOrPeopleDocumentDiv">
		<h5>CORRESPONDENTS/PEOPLE </h5>

		<hr id="lineSeparator"/>
		<ul>
			<li><b>Sender</b> <a class="linkSearch" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</a></li>
			<li><b>From</b> <a class="linkSearch" href="">${document.senderPlace.placeNameFull} </a></li>	
			<li><b>Recipient</b> <a class="linkSearch" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</a></li>
			<li><b>To</b> <a class="linkSearch" href="">${document.recipientPlace.placeNameFull}</a></li>	
			<br>
			<li>
				<b>People</b>
				<ul>
				<c:forEach items="${document.epLink}" var="currentPeople">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPeople.people.personId}" />
					</c:url>
					<li><a class="linkSearch" href="${ComparePersonURL}">${currentPeople.people.mapNameLf}</a></li>
					<br/>
				</c:forEach>
				</ul>
			</li>
		</ul>
	</div>

	<div id="EditExtractOrSynopsisDocumentDiv">
		<h5>EXTRACT/SYNOPSIS </h5>

		<hr id="lineSeparator"/>
		<ul>
			<li><b>Extract:</b></li>
			<li>${document.synExtract.docExtract}</li>
		</ul>
		<ul>
			<li><b>Synopsis:</b></li>
			<li>${document.synExtract.synopsis}</li>
		</ul>
	</div>

	<div id="EditTopicsDocumentDiv">
		<h5>TOPICS </h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditTopicsDocument" href="${EditTopicsDocumentURL}">edit</a><span id="loading"/>
	</security:authorize>
		<hr id="lineSeparator"/>
		<ul>
			<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
				<li><b>Topic:</b> ${currentTopicAndPlace.topic.topicTitle}</li>
				<li><b>Topic Place:</b> ${currentTopicAndPlace.place.placeNameFull}</li>
				<br/>
			</c:forEach>
		</ul>
	</div>
