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
		<c:url var="ShowDocumentInManuscriptViewer" value="/src/mview/ShowDocumentInManuscriptViewer.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowDocumentExplorer" value="/src/docbase/ShowExplorerDocument.do">
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
		<h5>DOCUMENT DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsDocument" href="${EditDetailsDocument}">edit</a></security:authorize></h5>
		<div id="createdby"><h6>CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></h6></div>
		<hr id="lineSeparator"/>
		<div id="DocumentImageDiv">
			<c:if test="${not empty image}">
			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>">
			<p><a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewer}">Show in manuscript viewer</a></p>
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
			<li><b>Document Typology (other than letter):</b> ${document.unpaged}</li>
			<li><b>Modern Date:</b> ${document.yearModern}</li>
			<li><b>Recorded year:</b> ${document.docYear} ${document.docMonthNum} ${document.docDay}</li>
			<li><b>Date Notes:</b> ${document.dateNotes}</li>
		</ul>
	</div>
	
	<br />
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsDocument").attr('href', "${EditDetailsDocument}");
			$j("#EditFactCheckDocument").attr('href', "${EditFactCheckDocument}");
			$j("#EditCorrespondentsOrPeopleDocument").attr('href', "${EditCorrespondentsOrPeopleDocument}");
			$j("#EditTopicsDocument").attr('href', "${EditTopicsDocument}");
			$j("#EditExtractOrSynopsisDocument").attr('href', "${EditExtractOrSynopsisDocument}");

			$j("#EditDetailsDocument").click(function(){
				$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
				 $j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			$j("#EditDetailsDocumentDiv").documentExplorer( {  
				showExplorerDocumentUrl     : "${ShowDocumentExplorer}",
				target                      : $j("#body_right")
			});  

			$j("#ShowDocumentInManuscriptViewer").open({width: $j(window).width(), height: $j(window).height(), scrollbars: false});
		});
	</script>
