<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowVettingChronologyDocumentURL" value="/de/peoplebase/ShowVettingChronologyDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="ShowMenuActionsDocumentURL" value="/de/peoplebase/ShowMenuActionsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="ShowShareLinkDocumentURL" value="/src/peoplebase/ShowShareLinkDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="PrintDocumentURL" value="/src/docbase/PrintDocument.do">
			<c:param name="entryId" value="${document.entryId}" />
		</c:url>
		
	</security:authorize>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div>
		<a id="vettingHistory" href="${ShowVettingChronologyDocumentURL}">Vetting History</a>
		<a id="menuActions" href="${ShowMenuActionsDocumentURL}">Actions</a>
		<a id="buttonPrint" title="Print this record" href="${PrintDocumentURL}"></a>
		<a id="buttonPDF" href="#" title="Save this record as PDF"></a>
		<a id="buttonShareLink" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j("#buttonShareLink").click(
				function() {										
					window.open('${ShowShareLinkDocumentURL}','SHARE DOCUMENT','width=510,height=700,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
					});
	
		$j('#buttonShareLink').tooltip({
			track: true,
			fade: 350 
		});
		
		$j("#buttonPrint").click(
				function() {										
					window.open('${PrintDocumentURL}','PRINT DOCUMENT','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
					});

	});
	</script>
