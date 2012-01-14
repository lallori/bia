<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowVettingChronologyVolumeURL" value="/de/volbase/ShowVettingChronologyVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShowMenuActionsVolumeURL" value="/de/volbase/ShowMenuActionsVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="ShareVolumeURL" value="/src/volbase/ShareVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="PrintVolumeURL" value="/src/volbase/PrintVolume.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></div>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="vettingHistory" href="${ShowVettingChronologyVolumeURL}">Vetting History</a>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="menuActions" href="${ShowMenuActionsVolumeURL}">Actions</a>
		</security:authorize>
		<a id="buttonPrint" title="Print this record" href="${PrintVolumeURL}"></a>
		<a id="buttonPDF" href="#" title="Save this record as PDF"></a>
		<a id="buttonShareLink" href="${ShareVolumeURL}" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j('#buttonShareLink').tooltip({ track: true, fade: 350 });
		
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE VOLUME','width=510,height=700,screenX=0,screenY=0,scrollbars=yes');
			return false;
		});
			
		$j("#buttonPrint").click(function() {
			window.open($j(this).attr("href"),'PRINT VOLUME','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');
			return false;
		});

		$j("#menuActions").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "VOLUME ACTIONS MENU", width: 750, height: 190});return false;
		});	
	});
	</script>