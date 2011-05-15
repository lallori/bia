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
		<hr id="lineSeparator"/>
		<div id="DocumentImageDiv">
			<c:if test="${not empty image}">
			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>">
			<p><a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewerURL}">Show in manuscript viewer</a></p>
			</c:if>
			<c:if test="${empty image}">
			<img src="<c:url value="/images/image_document.png"/>" alt="document image" />
			</c:if>
		</div>
		
		<ul>
			<li><b>Doc ID:</b> ${document.entryId == 0 ? '' : document.entryId}</li>
			<li><b>Volume (MDP):</b> ${document.volume.volNum}</li>
			<li><b>Insert/Part:</b> ${document.insertNum} / ${document.insertLet}</li>
			<li><b>Document starts at folio :</b> ${document.folioNum} / ${document.folioMod}</li>
			<li><b>Paginated:</b> ${document.unpaged}</li>
			<li><b>Document Typology (other than letter):</b> ${document.docTypology}</li>
			<li><b>Modern Date:</b> ${document.yearModern}</li>
			<li><b>Recorded year:</b> ${document.docYear} ${document.docMonthNum} ${document.docDay}</li>
			<li><b>Date Notes:</b> ${document.dateNotes}</li>
		</ul>
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
			<li><b>Sender:</b> <a class="linkSearch" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</a></li>
			<li><b>From:</b> <a class="linkSearch" href="">${document.senderPlace.placeNameFull} </a></li>	
			<li><b>Recipient:</b> <a class="linkSearch" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</a></li>
			<li><b>To:</b> <a class="linkSearch" href="">${document.recipientPlace.placeNameFull}</a></li>	
			<br>
			<li>
				<b>People:</b>
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
