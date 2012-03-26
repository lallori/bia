<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowVettingChronologyPlaceURL" value="/de/peoplebase/ShowVettingChronologyPlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="ShowMenuActionsPlaceURL" value="/de/geobase/ShowMenuActionsPlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="SharePlaceURL" value="/src/geobase/SharePlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="PrintPlaceURL" value="/src/geobase/PrintPlace.do">
		<c:param name="placeAllId" value="${place.placeAllId}" />
	</c:url>

	<div id="topBodyLeftMenuDiv">
		<div id="createdby">CREATED BY ${place.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${place.dateEntered}" /></div>
		<security:authorize ifNotGranted="ROLE_GUESTS">
			<c:if test="${(not empty historyNavigator.previousHistoryUrl)}"> 
				<a id="lastRecord" title="Go back to your last Record" href="${historyNavigator.previousHistoryUrl}"></a>
			</c:if>
			<c:if test="${(not empty historyNavigator.nextHistoryUrl)}"> 
				<a id="nextRecord" title="Go back to the next Record" href="${historyNavigator.nextHistoryUrl}"></a>
			</c:if>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="vettingHistory" href="${ShowVettingChronologyPlaceURL}">Vetting History</a>
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="menuActions" href="${ShowMenuActionsPlaceURL}">Actions</a>
		</security:authorize>
		<a id="buttonPrint" title="Print this record" href="${PrintPlaceURL}"></a>
		<a id="buttonPDF" href="#" title="Save this record as PDF"></a>
		<a id="buttonShareLink" href="${SharePlaceURL}" title="Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.">Share/Link</a>
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j('#buttonShareLink').tooltip({track: true, fade: 350, showURL: false });
		
		$j("#buttonShareLink").click(function() {
			window.open($j(this).attr("href"),'SHARE PERSON','width=510,height=700,screenX=0,screenY=0,scrollbars=yes,resizable=no');return false;
		});
	
		$j("#buttonPrint").click(function() {	
			window.open($j(this).attr("href"),'PRINT PLACE','width=687,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
		});

		$j("#menuActions").click( function() {															
			Modalbox.show($j(this).attr("href"), {title: "PLACE ACTIONS MENU", width: 750, height: 190});return false;
		});	

		$j('#lastRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});
		$j('#nextRecord').click(function() {
			$j("#body_left").load($j(this).attr("href"));
			return false;
		});
	});
	</script>
