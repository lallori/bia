<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ShowVettingChronologyVolumeURL" value="/de/volbase/ShowVettingChronologyVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
		<c:url var="ShowMenuActionsVolumeURL" value="/de/volbase/ShowMenuActionsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
		<c:url var="ShowShareLinkVolumeURL" value="/src/volbase/ShowShareLinkVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">Created by ${volume.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${volume.dateCreated}" /></div>
		<a id="vettingChronology" href="${ShowVettingChronologyVolumeURL}"></a>
		<a id="menuActions" href="${ShowMenuActionsVolumeURL}"></a>
		<a id="buttonPrint" title="Print this record" href="#"></a>
		<a id="buttonShareLink" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc."></a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j("#buttonShareLink").click(
				function() {										
					window.open('${ShowShareLinkVolumeURL}','SHARE VOLUME','width=510,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
				});
			
		$j('#buttonShareLink').tooltip({
				track: true,
				fade: 350 
		});

	});
	</script>