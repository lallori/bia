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
	
	<div id="EditDetailsDocumentDiv">
		<h5>DOCUMENT DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsDocument" href="${EditDetailsDocument}">edit</a></security:authorize></h5>
		<div id="createdby"><h6>CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></h6></div>
		<hr id="lineSeparator"/>
		<div id="DocumentImageDiv">
			<img src="<c:url value="/images/image_document.png"/>" alt="document image" />
			<p><a href="#">Show in manuscript viewer</a></p>
			<p><a href="#">Attach folios</a>
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

			// Loading image on editlink js funct
			$j("#EditDetailsDocument").click(function(){
				$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
				 $j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			$j("#EditFactCheckDocument").click(function(){
				$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
				 $j("#EditFactCheckDocument").load($j(this).attr("href"));
				return false;
			});
			$j("#EditCorrespondentsOrPeopleDocument").click(function(){
				$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
				 $j("#EditCorrespondentsOrPeopleDocument").load($j(this).attr("href"));
				return false;
			});
			$j("#EditTopicsDocument").click(function(){
				$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
				 $j("#EditTopicsDocument").load($j(this).attr("href"));
				return false;
			});
			$j("#EditExtractOrSynopsisDocument").click(function(){
				$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
				 $j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
