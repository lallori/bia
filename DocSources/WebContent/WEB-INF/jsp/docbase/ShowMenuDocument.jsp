<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowVettingChronologyDocumentURL" value="/de/docbase/ShowVettingChronologyDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="ShowMenuActionsDocumentURL" value="/de/docbase/ShowMenuActionsDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="ShareDocumentURL" value="/src/docbase/ShareDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="PrintDocumentURL" value="/src/docbase/PrintDocument.do">
		<c:param name="entryId" value="${document.entryId}" />
	</c:url>
		
	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="vettingHistory" href="${ShowVettingChronologyDocumentURL}">Vetting History</a>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="menuActions" href="${ShowMenuActionsDocumentURL}">Actions</a>
		</security:authorize>
		<a id="buttonPrint" href="${PrintDocumentURL}" title="Print this record"></a>
		<a id="buttonPDF" href="#" title="Save this record as PDF"></a>
		<a id="buttonShareLink" href="${ShareDocumentURL}" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j('#buttonShareLink').tooltip({track: true, fade: 350 });

		$j("#buttonShareLink").click(function() {										
			window.open($j(this).attr("href"),'SHARE DOCUMENT','width=510,height=700,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
			return false;
		});
		$j("#buttonPrint").click(function() {
			window.open($j(this).attr("href"),'PRINT DOCUMENT','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});
		$j("#menuActions").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "DOCUMENT ACTIONS MENU", width: 750, height: 190});return false;
		});	
	});
	</script>
