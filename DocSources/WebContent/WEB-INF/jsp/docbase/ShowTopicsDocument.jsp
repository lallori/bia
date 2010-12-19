<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsDocument" value="/de/docbase/EditCorrespondentsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditDetailsDocument" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocument" value="/de/volbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditFactChecksDocument" value="/de/volbase/EditFactChecksDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditDetailsDocumentDiv">
		<div id="createdby"><h6>CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></h6></div>
		<h5>DOCUMENT DETAILS <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsDocument" href="${EditDetailsDocument}">edit</a></security:authorize></h5>
		
		<ul>
			<li><b>Doc ID:</b> 9915</li>
			<li><b>Volume (MDP):</b> 2949</li>
			<li><b>Insert/Part:</b> 1573 April 2</li>
			<li><b>Folio Start:</b> 24</li>
			<li><b>Paginated:</b> No</li>
			<li><b>Modern Date:</b> 1577</li>
			<li><b>Rcorded year:</b> 1577</li>
			<li><b>Date Notes:</b> Undated document but inserted between documents dated 1577</li>
		</ul>
	</div>
	
	<br />
	
	<div id="EditFactChecksDocumentDiv">
		<h5>FACT CHECK <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditDetailsDocument" href="${EditDetailsDocument}">edit</a></security:authorize></h5>
		<ul>
			<li>YES</li>
		</ul>
	</div>
	
	<br />
	
	<div id="EditCorrespondentsDocumentDiv">
		<h5>CORRESPONDENTS/PEOPLE <a id="EditCorrespondentsDocument" href="/DocSources/de/docbase/EditCorrespondentsDocument.html">edit</a></h5>
		<ul>
			<li><b>Sender:</b> Tudor, Henry VII</li>
			<li><b>From:</b> City of London / England</li>
			<li><b>To:</b> Firenze / Toscan</li>		
			<li><b>Recipient:</b> Medici, Francesco i de'</li>
			<li><b>Ref:</b> Niccolini, Agnolo di Matte</li>
		</ul>
	</div>
	
	<br />
	
	<div id="EditExtSynDocumentDiv">
		<h5>EXTRACT/SYNOPSIS <a id="EditExtSynDocument" href="/DocSources/de/docbase/EditExtSynDocument.html">edit</a></h5>
		<ul>
			<li><b>Extract:</b></li>
			<li>Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</li>
		</ul>
		<ul>
			<li><b>Synopsis:</b></li>
			<li>Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</li>
		</ul>
	</div>
	
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditDetailsDocument").click(function(){$("#EditDetailsDocumentDiv").load($(this).attr("href"));return false;});
			$("#EditFactChecksDocument").click(function(){$("#EditFactChecksDocumentDiv").load($(this).attr("href"));return false;});
			$("#EditCorrespondentsDocument").click(function(){$("#EditCorrespondentsDocumentDiv").load($(this).attr("href"));return false;});
			$("#EditExtSynDocument").click(function(){$("#EditExtSynDocumentDiv").load($(this).attr("href"));return false;});
		});
	</script>
